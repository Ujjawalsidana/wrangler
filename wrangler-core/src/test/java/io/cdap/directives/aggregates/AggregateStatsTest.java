/*
 *  Copyright © 2017-2019 Cask Data, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */
package io.cdap.directives.aggregates;

import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.utils.TestingRig;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AggregateStatsTest {

  @Test
  public void testBasicStats() throws Exception {
    List<Row> input = Arrays.asList(
      new Row("id", 1).add("value", 10.0),
      new Row("id", 2).add("value", 20.0),
      new Row("id", 3).add("value", 30.0)
    );

    List<Row> output = TestingRig.execute(
      "aggregate-stats value stats", input
    );

    Row result = output.get(0);

    Assert.assertEquals(3L, result.getValue("stats_count"));
    Assert.assertEquals(60.0, result.getValue("stats_sum"));
    Assert.assertEquals(10.0, result.getValue("stats_min"));
    Assert.assertEquals(30.0, result.getValue("stats_max"));
    Assert.assertEquals(20.0, result.getValue("stats_avg"));
  }
}
