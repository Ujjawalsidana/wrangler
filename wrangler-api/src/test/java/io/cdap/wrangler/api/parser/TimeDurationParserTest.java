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



package io.cdap.wrangler.tests;

import io.cdap.wrangler.api.parser.TimeDuration;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions.assertNotNull;

public class TimeDurationParserTest {

    @Test
    public void testTimeDurationParsing() {
        // Test valid durations
        assertEquals(5000, TimeDuration.of("5s").getMillis());
        assertEquals(7200000, TimeDuration.of("2h").getMillis());
        assertEquals(180000, TimeDuration.of("3min").getMillis());
        assertEquals(60000, TimeDuration.of("1min").getMillis());
    }

    @Test
    public void testInvalidTimeDurationParsing() {
        // Test invalid durations
        assertThrows(IllegalArgumentException.class, () -> TimeDuration.of("5minutes"));
        assertThrows(IllegalArgumentException.class, () -> TimeDuration.of("XYZ"));
    }
}
