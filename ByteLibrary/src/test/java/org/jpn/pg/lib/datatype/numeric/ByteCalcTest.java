package org.jpn.pg.lib.datatype.numeric;

import junit.framework.TestCase;

public class ByteCalcTest extends TestCase {

    protected static void setUpBeforeClass() throws Exception {
    }

    protected static void tearDownAfterClass() throws Exception {
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetPositive() {
        assertEquals(0, ByteCalc.getPositive((byte)0));
        assertEquals(1, ByteCalc.getPositive((byte)1));
        assertEquals(2, ByteCalc.getPositive((byte)2));
        assertEquals(0x0080, ByteCalc.getPositive((byte)0x80));
        assertEquals(255, ByteCalc.getPositive((byte)0xFF));
    }

}
