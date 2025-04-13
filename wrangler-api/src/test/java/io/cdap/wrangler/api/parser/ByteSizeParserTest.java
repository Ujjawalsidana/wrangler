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

import io.cdap.wrangler.api.parser.ByteSize;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions.assertNotNull;

public class ByteSizeParserTest {

    @Test
    public void testByteSizeParsing() {
        assertEquals(1024L, ByteSize.of("1KB").getBytes());
        assertEquals(1048576L, ByteSize.of("1MB").getBytes());
        assertEquals(1073741824L, ByteSize.of("1GB").getBytes());
        assertEquals(1073741824L * 2, ByteSize.of("2GB").getBytes());
    }

    @Test
    public void testInvalidByteSizeParsing() {
        assertThrows(IllegalArgumentException.class, () -> ByteSize.of("1ZZZ"));
    }
}
