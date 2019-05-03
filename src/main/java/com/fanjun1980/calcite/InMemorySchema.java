package com.fanjun1980.calcite;

import com.fanjun1980.calcite.function.MathFunction;
import com.fanjun1980.calcite.function.StringFunction;
import com.fanjun1980.calcite.storage.Storage;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import org.apache.calcite.schema.Function;
import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;
import org.apache.calcite.schema.impl.ScalarFunctionImpl;

import java.util.HashMap;
import java.util.Map;

public class InMemorySchema extends AbstractSchema {
    private String dbName;
    private Map<String, Object> operand;

    public InMemorySchema(String name, Map<String, Object> operand) {
        super();
        this.operand = operand;
        this.dbName = name;
        System.out.println("");
        System.out.println("in this class ==> "+ this);

    }
    @Override
    public Map<String, Table> getTableMap() {

        Map<String, Table> tables = new HashMap<String, Table>();

        Storage.getTables().forEach(it->{
            //System.out.println("it = "+it.getName());
            tables.put(it.getName(),new InMemoryTable(it. getName(),it));
        });

        return tables;
    }

    @Override
    protected Multimap<String, Function> getFunctionMultimap() {
        final ImmutableMultimap.Builder<String, Function> builder = ImmutableMultimap.builder();
        builder.put("SQUARE_FUNC",ScalarFunctionImpl.create(MathFunction.class,"square"));
        builder.put("TOSTRING_FUNC",ScalarFunctionImpl.create(StringFunction.class,"parseString"));
        builder.put("CONCAT_FUNC",ScalarFunctionImpl.create(StringFunction.class,"concat"));
        return builder.build();
    }
}
