package com.fanjun1980.calcite.planner.physical.rules;

import org.apache.calcite.plan.Convention;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.convert.ConverterRule;
import org.apache.calcite.rel.core.Project;
import org.apache.calcite.rel.logical.LogicalProject;
import com.fanjun1980.calcite.planner.physical.TConvention;
import com.fanjun1980.calcite.planner.physical.TProjectRel;

public class TProjectRule extends ConverterRule {
    public static final TProjectRule INSTANCE = new TProjectRule();

    private TProjectRule() {
        super(LogicalProject.class, Convention.NONE, TConvention.INSTANCE, "TProjectRule");
    }

    @Override
    public RelNode convert(RelNode rel) {
        final Project project = (Project) rel;
        final RelNode input = project.getInput();

        return new TProjectRel(project.getCluster(),
                project.getTraitSet().replace(TConvention.INSTANCE),
                convert(input, input.getTraitSet().replace(TConvention.INSTANCE)), project.getProjects(), project.getRowType());
    }
}
