package com.fanjun1980.calcite.planner.physical;

import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.Project;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rex.RexNode;

import java.util.List;

public class TProjectRel extends Project implements TRel {
    public TProjectRel(RelOptCluster cluster, RelTraitSet traits, RelNode input, List<? extends RexNode> projects, RelDataType rowType) {
        super(cluster, traits, input, projects, rowType);
    }

    @Override
    public Project copy(RelTraitSet traitSet, RelNode input, List<RexNode> projects, RelDataType rowType) {
        return new TProjectRel(getCluster(), traitSet, input, projects, rowType);
    }

    @Override
    public void doSomething(int level) throws Exception {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < level; i++) {
            buffer.append("\t");
        }

        buffer.append("Inside TProjectRel");

        System.out.println(buffer.toString());

        ((TRel) getInput()).doSomething(level + 1);
    }
}
