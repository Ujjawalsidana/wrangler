/*
 * Copyright © 2025 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package io.cdap.wrangler.directive;

import io.cdap.wrangler.api.Directive;
import io.cdap.wrangler.api.ExecutorContext;
import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.api.annotations.PublicEvolving;
import io.cdap.wrangler.api.parser.*;
import io.cdap.wrangler.api.Arguments;
import io.cdap.wrangler.api.RecipeSymbol;
import io.cdap.wrangler.api.TokenGroup;

import java.util.List;

@PublicEvolving
public class AggregateStats implements Directive {
  private String byteSizeColumn;
  private String timeDurationColumn;
  private String outputByteColumn;
  private String outputTimeColumn;

  private long totalBytes = 0;
  private long totalMilliseconds = 0;
  private int rowCount = 0;

  @Override
  public void initialize(Arguments arguments) {
    TokenGroup group = arguments.toTokenGroup();

    this.byteSizeColumn = ((ColumnName) group.get(0)).value();
    this.timeDurationColumn = ((ColumnName) group.get(1)).value();
    this.outputByteColumn = ((ColumnName) group.get(2)).value();
    this.outputTimeColumn = ((ColumnName) group.get(3)).value();
  }

  @Override
  public List<Row> execute(List<Row> rows, ExecutorContext context) {
    for (Row row : rows) {
      Object byteVal = row.getValue(byteSizeColumn);
      Object timeVal = row.getValue(timeDurationColumn);

      if (byteVal instanceof String && timeVal instanceof String) {
        ByteSize size = new ByteSize((String) byteVal);
        TimeDuration duration = new TimeDuration((String) timeVal);

        totalBytes += size.getBytes();
        totalMilliseconds += duration.getMilliseconds();
        rowCount++;
      }
    }

    Row result = new Row();
    result.add(outputByteColumn, totalBytes / (1024.0 * 1024.0)); // Convert to MB
    result.add(outputTimeColumn, totalMilliseconds / 1000.0);     // Convert to seconds

    return List.of(result);  // Only one aggregated result row
  }

  @Override
  public UsageDefinition define() {
    return UsageDefinition.builder("aggregate-stats")
      .addRequiredArg(ColumnName.class) // byte size input
      .addRequiredArg(ColumnName.class) // time input
      .addRequiredArg(ColumnName.class) // byte size output
      .addRequiredArg(ColumnName.class) // time output
      .build();
  }
}
