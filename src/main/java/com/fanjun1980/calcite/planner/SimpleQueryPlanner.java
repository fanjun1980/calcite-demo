package com.fanjun1980.calcite.planner;

import com.fanjun1980.calcite.planner.physical.TConvention;
import com.fanjun1980.calcite.planner.physical.TRel;
import com.google.common.io.Resources;
import org.apache.calcite.config.Lex;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.model.ModelHandler;
import org.apache.calcite.plan.ConventionTraitDef;
import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.plan.RelTraitDef;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelCollationTraitDef;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.type.RelDataTypeSystem;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.tools.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.apache.calcite.plan.Contexts.EMPTY_CONTEXT;

// https://medium.com/@mpathirage/query-planning-with-apache-calcite-part-1-fe957b011c36
public class SimpleQueryPlanner {
    private static final int TREL_CONVERSION_RULES = 0;
    private final Planner planner;

    public SimpleQueryPlanner(SchemaPlus schema) {
        final List<RelTraitDef> traitDefs = new ArrayList<RelTraitDef>();

        traitDefs.add(ConventionTraitDef.INSTANCE);
        traitDefs.add(RelCollationTraitDef.INSTANCE);

        FrameworkConfig calciteFrameworkConfig = Frameworks.newConfigBuilder()
                // Lexical configuration defines how identifiers are quoted, whether they are converted to upper or lower
                // case when they are read, and whether identifiers are matched case-sensitively.
                .parserConfig(SqlParser.configBuilder().setLex(Lex.MYSQL).build())
                .defaultSchema(schema) // Sets the schema to use by the planner
                .traitDefs(traitDefs)
                .context(EMPTY_CONTEXT) // Context can store data within the planner session for access by planner rules
                .ruleSets(RuleSets.ofList()) // Rule sets to use in transformation phases
                .costFactory(null) // Custom cost factory to use during optimization
                .typeSystem(RelDataTypeSystem.DEFAULT)
                .build();

        this.planner = Frameworks.getPlanner(calciteFrameworkConfig);
    }

    public RelNode getLogicalPlan(String query) throws ValidationException, RelConversionException {
        SqlNode sqlNode;

        try {
            sqlNode = planner.parse(query);
        } catch (SqlParseException e) {
            throw new RuntimeException("Query parsing error.", e);
        }
        SqlNode validatedSqlNode = planner.validate(sqlNode);

        System.out.println(validatedSqlNode.toString());
        return planner.rel(validatedSqlNode).project();
    }

    public RelNode getTRel(String query) throws RelConversionException, ValidationException {
        RelNode relNode = getLogicalPlan(query);
        RelTraitSet traitSet = relNode.getTraitSet();
        traitSet = traitSet.simplify();
        return planner.transform(TREL_CONVERSION_RULES, traitSet.plus(TConvention.INSTANCE), relNode);
    }

    public static void main(String[] args) throws Exception {
        Properties info = new Properties();
        info.setProperty("lex", "JAVA");
        CalciteConnection connection = DriverManager.getConnection("jdbc:calcite:", info).unwrap(CalciteConnection.class);
        String schema = Resources.toString(SimpleQueryPlanner.class.getResource("/parts.json"), Charset.defaultCharset());
        // ModelHandler reads the schema and load the schema to connection's root schema and sets the default schema
        new ModelHandler(connection, "inline:" + schema);

        // Create the query planner with the toy schema. conneciton.getSchema returns default schema name specified in parts.json
        SimpleQueryPlanner queryPlanner = new SimpleQueryPlanner(connection.getRootSchema().getSubSchema(connection.getSchema()));
        RelNode logicalPlan = queryPlanner.getLogicalPlan("select id,color from parts where color='aka' and units>=5");
        System.out.println(logicalPlan.getDescription());
        System.out.println(RelOptUtil.toString(logicalPlan));
    }


}
