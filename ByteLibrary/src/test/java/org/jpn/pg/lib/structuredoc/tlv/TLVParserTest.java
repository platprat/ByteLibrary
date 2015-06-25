package org.jpn.pg.lib.structuredoc.tlv;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.jpn.pg.lib.structuredoc.exception.IllegalStructureException;
import org.jpn.pg.lib.structuredoc.exception.UnsupportedSizeException;

import junit.framework.TestCase;

public class TLVParserTest extends TestCase {

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

    public void testParse() throws UnsupportedSizeException, IllegalStructureException, IOException {
        TLVLeaf[] result = callTLVParser(new byte[]{0x01, 0x02, 0x03, 0x04});
        assertEquals("TLVLEN:", 1, result.length);
        check(result[0], 1, 2, new byte[]{0x03,0x04});
    }


    private TLVLeaf[] callTLVParser(byte[] target) throws UnsupportedSizeException, IllegalStructureException, IOException {
        return TLVParser.parse(new ByteArrayInputStream(target));
    }

    private void check(TLVLeaf result, long expectTag, long expectLength, byte[] expectValue) {
        assertEquals("TAG:", expectTag, result.getTag());
        assertEquals("LEN:", expectLength, result.getLength());
        assertEquals("VALLEN:", expectValue.length, result.getValue().length);
        for(int n=0; n<expectValue.length; n++) {
            assertEquals("VAL"+n+":", expectValue[n], result.getValue()[n]);
        }
    }

}
