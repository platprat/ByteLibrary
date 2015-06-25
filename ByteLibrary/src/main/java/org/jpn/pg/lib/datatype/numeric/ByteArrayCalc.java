package org.jpn.pg.lib.datatype.numeric;

import org.jpn.pg.lib.datatype.numeric.exception.ValueRangeException;

public class ByteArrayCalc {

    private ByteArrayCalc() {
        super();
    }


    public static int getPositive(byte[] src) throws ValueRangeException {
        if(src == null) {
            return 0;
        }

        int dest = 0;
        for(int n=0; n<src.length; n++) {
            dest <<= 8;
            dest += ByteCalc.getPositive(src[n]);
        }

        if(dest < 0) {
            throw new ValueRangeException("[ByteArrayCalc.getPositive()] value is not positive.");
        }

        return dest;
    }

}
