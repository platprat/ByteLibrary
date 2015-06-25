package org.jpn.pg.lib.datatype.numeric;

public class ByteCalc {

    private ByteCalc() {
        super();
    }

    public static int getPositive(byte src) {
        int n = src;
        n = n & 0x000000FF;
        return n;
    }

}
