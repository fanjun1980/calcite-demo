/**
 * Copyright 2016 Milinda Pathirage
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
