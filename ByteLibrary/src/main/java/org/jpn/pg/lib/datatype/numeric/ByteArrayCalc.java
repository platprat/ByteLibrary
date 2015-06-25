package org.jpn.pg.lib.datatype.numeric;

public class ByteArrayCalc {

    private ByteArrayCalc() {
        super();
    }


    public static int getPositive(byte[] src) {
        if(src == null) {
            return 0;
        }

        int dest = 0;
        for(int n=0; n<src.length; n++) {
            dest <<= 8;
            dest += ByteCalc.getPositive(src[n]);
        }

        return dest;
    }

}
