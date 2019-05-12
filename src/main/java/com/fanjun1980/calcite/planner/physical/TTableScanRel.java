package com.fanjun1980.calcite.planner.physical;

import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelOptTable;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.core.TableScan;

public class TTableScanRel extends TableScan implements TRel {
    public TTableScanRel(RelOptCluster cluster, RelTraitSet traitSet, RelOptTable table) {
        super(cluster, traitSet, table);
    }

    @Override
    public void doSomething(int level) throws Exception {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < level; i++) {
            buffer.append("\t");
        }

        buffer.append("Inside TTableScanRel");

        System.out.println(buffer.toString());
    }
}
