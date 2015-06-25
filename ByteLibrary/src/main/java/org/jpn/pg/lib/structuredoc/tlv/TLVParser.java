package org.jpn.pg.lib.structuredoc.tlv;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.jpn.pg.lib.structuredoc.exception.IllegalStructureException;
import org.jpn.pg.lib.structuredoc.exception.UnsupportedSizeException;

public class TLVParser {

    private TLVParser() {
        super();
    }

    public static TLVLeaf[] parse(InputStream src) throws UnsupportedSizeException, IOException, IllegalStructureException {
        ArrayList listTLV = new ArrayList();
        while(true) {
            TLVLeaf cur = getNextLeaf(src);
            if(cur != null) {
                listTLV.add(cur);
            } else {
                break;
            }
        }

        return (TLVLeaf[]) listTLV.toArray();
    }

    public static TLVLeaf[] parse(InputStream src, long limitLength) throws UnsupportedSizeException, IOException, IllegalStructureException {
        ArrayList listTLV = new ArrayList();
        while(true) {
            TLVLeaf cur = getNextLeaf(src);
            if(cur != null) {
                listTLV.add(cur);
            } else {
                break;
            }
        }

        return (TLVLeaf[]) listTLV.toArray();
    }

    private static boolean isAllON(byte target, byte specificBits) {
        if(((byte)(target & specificBits)) == specificBits) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isStructural(byte firstTagValue) {
        return isAllON(firstTagValue, (byte)0x20);
    }

    private static boolean isContinueTagForFirst(byte firstTagValue) {
        return isAllON(firstTagValue, (byte)0x1F);
    }

    private static boolean isContinueTag(byte preTagValue) {
        return isAllON(preTagValue, (byte)0x80);
    }

    private static boolean isContinueLength(byte firstLengthValue) {
        return isAllON(firstLengthValue, (byte)0x80);
    }


    public static TLVLeaf getNextLeaf(InputStream src) throws IOException, UnsupportedSizeException, IllegalStructureException {

        // データ無しは終了
        if (src == null) {
            return null;
        }

        TLVLeaf curLeaf = new TLVLeaf();

        // タグ部読取
        // まず1byte取得
        int lengthTagPart = 0;
        byte firstTagPart = read(src);
        lengthTagPart ++;
        if(isEOF(firstTagPart)) {
            // 先頭が読めなかった場合、TLVがなかったとみなして終了
            return null;
        }
        curLeaf.addTag(firstTagPart);

        // 子タグありかどうか
        curLeaf.setStructural(isStructural(firstTagPart));

        // タグ部が続くか
        if(isContinueTagForFirst(firstTagPart)) {
            byte[] nextTagPart;
            // タグ部が続く限り読み取る
            do {
                nextTagPart = read(src, 1);
                curLeaf.addTag(nextTagPart[0]);
                lengthTagPart ++;
            } while(isContinueTag(nextTagPart[0]));
        }
        curLeaf.setTagRawLenght(lengthTagPart);


        // 長さ部読取
        byte[] firstLengthPart = read(src, 1);

        if(!isContinueLength(firstLengthPart[0])) {
            curLeaf.setLength(firstLengthPart[0]);
            curLeaf.setLengthRawLength(1);
        } else {
            // 長さ部の長さ分読み取って長さを取得
            int lenLengthArea = firstLengthPart[0] & 0x7F;
            byte[] lengthPart = read(src, lenLengthArea);
            curLeaf.setLength(lengthPart);
            curLeaf.setLengthRawLength(lenLengthArea + 1);
        }

        // データ部読取
        if(curLeaf.getLength() > 0) {
            if(!curLeaf.isStructural()) {
                // 子タグなしであればそのまま読取
                curLeaf.setValue(read(src, curLeaf.getLength()));
            } else {
                // 子タグありなら再入
                curLeaf.setChildren(parse(src, curLeaf.getLength()));
            }
        }

        return curLeaf;
    }

    /**
     * 1バイトを読み取る.
     *
     * @param src
     * @return
     * @throws IOException
     */
    private static byte read(InputStream src) throws IOException {
        int readed = src.read();
        if (isEOF(readed)) {
            return -1;
        }
        return (byte) readed;
    }

    /**
     * 指定バイト数を読み取る.
     *
     * @param src
     * @param len
     * @return
     * @throws IOException
     * @throws IllegalStructureException
     */
    private static byte[] read(InputStream src, int len) throws IOException, IllegalStructureException {
        byte[] readed = new byte[len];

        for (int n = 0; n < len; n++) {
            readed[n] = read(src);
            if (readed[n] < 0) {
                // 必要桁数が読み取れなかったのでエラー
                throw new IllegalStructureException("length too short.");
            }
        }

        return readed;
    }

    private static boolean isEOF(long src) {
        if(src < 0) {
            return true;
        } else {
            return false;
        }
    }
}
