package org.jpn.pg.lib.structuredoc.tlv;

import org.jpn.pg.lib.datatype.numeric.ByteArrayCalc;
import org.jpn.pg.lib.datatype.numeric.exception.ValueRangeException;
import org.jpn.pg.lib.structuredoc.exception.UnsupportedSizeException;

public class TLVLeaf {

    private long tag = 0;
    private int length = 0;
    private byte[] value = null;
    private TLVLeaf[] children = null;

    private boolean isStructural = false;
    private long tagRawLenght = 0;
    private long lengthRawLength = 0;

    public TLVLeaf()
    {
        super();
    }


    /**
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        TLVLeaf cloneTLVLeaf = new TLVLeaf();
        cloneTLVLeaf.tag = this.tag;
        cloneTLVLeaf.length = this.length;
        if(this.value != null) {
            byte[] valueClone = new byte[this.value.length];
            System.arraycopy(this.value, 0, valueClone, 0, this.value.length);
            cloneTLVLeaf.value = valueClone;
        }
        if(this.children != null) {
            TLVLeaf[] childrenClone = new TLVLeaf[this.children.length];
            System.arraycopy(this.children, 0, childrenClone, 0, this.children.length);
            cloneTLVLeaf.children = childrenClone;
        }
        cloneTLVLeaf.isStructural = this.isStructural;
        cloneTLVLeaf.tagRawLenght = this.tagRawLenght;
        cloneTLVLeaf.lengthRawLength = this.lengthRawLength;
        return cloneTLVLeaf;
    }


    public void addTag(byte tagPart)
    {
        tag = tag << 8;
        tag += tagPart;
    }

    public void setLength(byte[] lengthPart) throws UnsupportedSizeException {
        if(lengthPart.length > 4) {
            throw new UnsupportedSizeException("[TLVLeaf.setLength()] TLV length over. This library supports only 4bytes or less. Your lenght size = " + lengthPart.length);
        }

        try {
            this.length = ByteArrayCalc.getPositive(lengthPart);
        } catch (ValueRangeException e) {
            throw new UnsupportedSizeException("[TLVLeaf.setLength()] TLV length over. This library supports only Integer.MAX_VALUE or less. Your first byte = " + lengthPart[0]);
        }
    }

    public long getMyTLVLength() {
        long myLength = this.tagRawLenght + this.lengthRawLength + (long)this.length;
        return myLength;
    }


    public long getTag() {
        return tag;
    }

    public void setTag(long tag) {
        this.tag = tag;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public TLVLeaf[] getChildren() {
        return children;
    }

    public void setChildren(TLVLeaf[] children) {
        this.children = children;
    }

    public boolean isStructural() {
        return isStructural;
    }

    public void setStructural(boolean isStructural) {
        this.isStructural = isStructural;
    }

    public long getTagRawLenght() {
        return tagRawLenght;
    }

    public void setTagRawLenght(long tagRawLenght) {
        this.tagRawLenght = tagRawLenght;
    }

    public long getLengthRawLength() {
        return lengthRawLength;
    }

    public void setLengthRawLength(long lengthRawLength) {
        this.lengthRawLength = lengthRawLength;
    }



}
