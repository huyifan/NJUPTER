package com.example.hugo.njupter.utils;

/**
 * Created by Shine on 2016/4/6.
 */
public class ByteUtils {
    /**
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序，和和intToBytes（）配套使用
     *
     * @param src    byte数组
     * @param offset 从数组的第offset位开始
     * @return int数值
     */
    public static int bytesToInt(byte[] src, int offset) {
        int value;
        value = ((src[offset] & 0xFF)
                | ((src[offset + 1] & 0xFF) << 8)
                | ((src[offset + 2] & 0xFF) << 16)
                | ((src[offset + 3] & 0xFF) << 24));
        return value;
    }

    /**
     * @param src1 E0
     * @param src2 07
     * @return 2016
     */
    public static int twoBytesToInt(byte src1, byte src2) {
        int value;
        value = (src1 & 0xFF) | ((src2 & 0xFF) << 8);
        return value;
    }

    public static int byteToInt(byte src) {
        int value;
        value = src & 0xFF;
        return value;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用
     */
    public static int bytesToInt2(byte[] src, int offset) {
        int value;
        value = ((src[offset] & 0xFF) << 24)
                | ((src[offset + 1] & 0xFF) << 16)
                | ((src[offset + 2] & 0xFF) << 8)
                | (src[offset + 3] & 0xFF);
        return value;
    }

}
