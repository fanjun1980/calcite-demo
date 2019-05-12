package com.fanjun1980.calcite.planner.physical.rules;

import org.apache.calcite.plan.Convention;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.convert.ConverterRule;
import org.apache.calcite.rel.core.TableScan;
import org.apache.calcite.rel.logical.LogicalTableScan;
import com.fanjun1980.calcite.planner.physical.TConvention;
import com.fanjun1980.calcite.planner.physical.TTableScanRel;

public class TTableScanRule extends ConverterRule {
    public static final TTableScanRule INSTANCE = new TTableScanRule();

    private TTableScanRule() {
        super(LogicalTableScan.class, Convention.NONE, TConvention.INSTANCE, "TTableScanRule");
    }

    @Override
    public RelNode convert(RelNode rel) {
        final TableScan scan = (TableScan) rel;

        return new TTableScanRel(scan.getCluster(), scan.getTraitSet().replace(TConvention.INSTANCE), scan.getTable());
    }
}
