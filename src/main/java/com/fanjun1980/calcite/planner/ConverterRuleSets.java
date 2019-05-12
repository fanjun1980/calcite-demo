package com.fanjun1980.calcite.planner;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.tools.RuleSet;
import com.fanjun1980.calcite.planner.physical.rules.TProjectRule;
import com.fanjun1980.calcite.planner.physical.rules.TTableScanRule;

import java.util.Iterator;

public class ConverterRuleSets {

    private static final ImmutableSet<RelOptRule> converterRules = ImmutableSet.<RelOptRule>builder().add(
            TProjectRule.INSTANCE,
            TTableScanRule.INSTANCE
    ).build();

    public static RuleSet[] getRuleSets() {
        return new RuleSet[]{new TRuleSet(converterRules)};
    }

    public static class TRuleSet implements RuleSet {
        final ImmutableSet<RelOptRule> rules;

        public TRuleSet(ImmutableSet<RelOptRule> rules) {
            this.rules = rules;
        }

        public TRuleSet(ImmutableList<RelOptRule> rules) {
            this.rules = ImmutableSet.<RelOptRule>builder().addAll(rules).build();
        }

        @Override
        public Iterator<RelOptRule> iterator() {
            return rules.iterator();
        }
    }
}
