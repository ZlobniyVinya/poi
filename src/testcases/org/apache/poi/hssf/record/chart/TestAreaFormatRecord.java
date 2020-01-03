/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package org.apache.poi.hssf.record.chart;


import static org.apache.poi.hssf.record.TestcaseRecordInputStream.confirmRecordEncoding;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.poi.hssf.record.TestcaseRecordInputStream;
import org.junit.Test;

/**
 * Tests the serialization and deserialization of the AreaFormatRecord
 * class works correctly.  Test data taken directly from a real
 * Excel file.
 */
public final class TestAreaFormatRecord {
    byte[] data = new byte[] {
        (byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0x00,    // forecolor
        (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,    // backcolor
        (byte)0x01,(byte)0x00,                          // pattern
        (byte)0x01,(byte)0x00,                          // format
        (byte)0x4E,(byte)0x00,                          // forecolor index
        (byte)0x4D,(byte)0x00                           // backcolor index

    };

    @Test
    public void testLoad() {
        AreaFormatRecord record = new AreaFormatRecord(TestcaseRecordInputStream.create(0x100a, data));
        assertEquals( 0xFFFFFF, record.getForegroundColor());
        assertEquals( 0x000000, record.getBackgroundColor());
        assertEquals( 1, record.getPattern());
        assertEquals( 1, record.getFormatFlags());
        assertTrue(record.isAutomatic());
        assertFalse(record.isInvert());
        assertEquals( 0x4e, record.getForecolorIndex());
        assertEquals( 0x4d, record.getBackcolorIndex());


        assertEquals( 20, record.getRecordSize() );
    }

    @SuppressWarnings("squid:S2699")
    @Test
    public void testStore() {
        AreaFormatRecord record = new AreaFormatRecord();
        record.setForegroundColor( 0xFFFFFF );
        record.setBackgroundColor( 0x000000 );
        record.setPattern( (short)1 );
        record.setAutomatic( true );
        record.setInvert( false );
        record.setForecolorIndex( (short)0x4e );
        record.setBackcolorIndex( (short)0x4d );


        byte [] recordBytes = record.serialize();
        confirmRecordEncoding(AreaFormatRecord.sid, data, recordBytes);
    }
}
