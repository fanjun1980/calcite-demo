package com.fanjun1980.calcite.planner.physical;

import org.apache.calcite.plan.*;

public enum TConvention implements Convention {
    INSTANCE;

    @Override
    public Class getInterface() {
        return TRel.class;
    }

    @Override
    public String getName() {
        return "T_PHYSICAL";
    }

    @Override
    public RelTraitDef getTraitDef() {
        return ConventionTraitDef.INSTANCE;
    }

    @Override
    public boolean satisfies(RelTrait trait) {
        return this == trait;
    }

    @Override
    public void register(RelOptPlanner planner) {

    }

    @Override
    public String toString() {
        return getName();
    }
}
