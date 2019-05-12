package com.fanjun1980.calcite.planner.physical;

import org.apache.calcite.plan.Convention;
import org.apache.calcite.rel.RelNode;

public interface TRel extends RelNode {

    public static final Convention T_PHYSICAL = new Convention.Impl("T_PHYSICAL", TRel.class);

    void doSomething(int level) throws Exception;
}
