package org.pillar.codec.binary.util;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import io.netty.buffer.ByteBuf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransportNettyUtil {

    private static final Logger logger = LoggerFactory
            .getLogger(TransportNettyUtil.class);


    /**
     * @param msgBytes 充电桩上报的整个包
     * @return 去掉faf5和效验码的反转义值
     */
    public static byte[] escapeMsg(byte[] msgBytes) {
        //TransportNettyUtil.bytesToHexString(msgBytes);
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();

        if (!((msgBytes[0] & 0xff) == ConstantUtil.MESSAGE_HEAD_ONE)
                && !((msgBytes[1] & 0xff) == ConstantUtil.MESSAGE_HEAD_TWO)) {
            return null;
        } else {
            swapStream.write(msgBytes, 0, 2);
        }
        boolean flag = false;
        for (int i = 2; i < msgBytes.length; i++) {
            if (flag) {
                flag = false;
                continue;
            }
            byte temp = msgBytes[i];
            if (!((msgBytes[i] & 0xff) == 0xfd)) {
                swapStream.write(temp);
            } else {
                if (i + 1 < msgBytes.length) { // 待转义byte存在数据进行是否是ed和ee判断
                    if ((msgBytes[i + 1] & 0xff) == 0xed) { // 判断是否是0xfd
                        swapStream.write((byte) 253);
                        flag = true;
                        // i++;
                    } else if ((msgBytes[i + 1] & 0xff) == 0xee) {// 判断是否是0xfe
                        swapStream.write((byte) 254);
                        flag = true;
                        // i++;
                    } else {
                        swapStream.write(temp);
                    }
                } else { // 待转义byte后不存在数据则直接保存
                    swapStream.write(temp);
                }
            }

        }
        //swapStream.write(msgBytes[msgBytes.length - 1]);
        // TransportNettyUtil.bytesToHexString(swapStream.toByteArray());
        return swapStream.toByteArray();
    }


    /**
     * 转义 TODO
     *
     * @param byteBuf byteBuf
     * @return ByteBuf
     */
    public static ByteBuf escape(ByteBuf byteBuf) {
        checkNotNull(byteBuf);
        checkArgument(byteBuf.readableBytes() > 0);
        try {
            return null;
        } finally {
            byteBuf.release();
        }

    }

    /**
     * 反转义 TODO
     *
     * @param byteBuf byteBuf
     * @return ByteBuf
     */

    public static ByteBuf unEscape(ByteBuf byteBuf) {
        checkNotNull(byteBuf);
        checkArgument(byteBuf.readableBytes() > 0);
        return null;

    }

    /**
     * 用于平台上报数据转义 将信息进行转义，首尾补0xfaf5，信息中0xfd -> 0xfded，0xfe -> 0xfdee
     *
     * @param msgBytes 需要转义的信息串
     * @return 转义后的信息
     */
    public static byte[] msgEscape(byte[] msgBytes) throws IOException {
        //TransportNettyUtil.bytesToHexString(msgBytes);
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        // temp
        byte[] temp = new byte[2];
        /*// 标志位
        temp[0] = (byte) ConstantUtil.MESSAGE_HEAD_ONE;
		temp[1] = (byte) ConstantUtil.MESSAGE_HEAD_TWO;
		swapStream.write(temp, 0, 2);*/

        for (int pos = 0; pos < msgBytes.length; pos++) {
            if ((msgBytes[pos]&0xff) ==0xfd) {
                temp[0] = (byte) 0xfd;
                temp[1] = (byte) 0xed;
                swapStream.write(temp);
            } else if ((msgBytes[pos]&0xff)==0xfe) {
                temp[0] = (byte) 0xfd;
                temp[1] = (byte) 0xee;
                swapStream.write(temp);
            } else {
                swapStream.write(msgBytes, pos, 1);
            }
        }
        /*
         * // 标志位 temp[0] = ConstantUtil.MESSAGE_FLAG; swapStream.write(temp, 0,
		 * 1);
		 */
        // TransportNettyUtil.bytesToHexString(swapStream.toByteArray());
        return swapStream.toByteArray();
    }

    /**
     * 校验码核对
     */
    public static boolean validateChecksum(byte[] msgBytes) {
        byte tempByte = msgBytes[0];// 临时保存校验码
        byte checkCode = msgBytes[msgBytes.length - 1];
        for (int i = 1; i < msgBytes.length - 1; i++) {
            tempByte = (byte) (tempByte + msgBytes[i]);
        }
        logger.debug("传入校验码：" + TransportNettyUtil.byteToHexString(checkCode)
                + "，实际校验码：" + TransportNettyUtil.byteToHexString(tempByte));

        return checkCode == tempByte;
    }

    /**
     * 校验码核对
     */
    public static byte checksum(byte[] msgBytes) {
        return checksum(msgBytes, 0, msgBytes.length);
    }

    /**
     * 对Length长度的byte生成校验码
     *
     * @param msgBytes byte组
     * @param start    开始位置
     * @param length   长度
     * @return 效验码
     */
    public static byte checksum(byte[] msgBytes, int start, int length) {
        byte tempByte = msgBytes[start];// 临时保存校验码
        for (int i = start + 1; i < start + length; i++) {
            tempByte = (byte) (tempByte + msgBytes[i]);
        }
        return tempByte;
    }


    /**
     * 使用ByteBuf 计算checksum
     *
     * @param byteBuf byteBuf
     * @return byte
     * @Author:yincc
     */
    public static byte checksum(ByteBuf byteBuf) {
        checkNotNull(byteBuf);
        checkArgument(byteBuf.readableBytes() > 0);
        try {
            int result;
            for (result = byteBuf.readByte(); byteBuf.readableBytes() > 0; ) {
                result += byteBuf.readByte();
            }
            return (byte) result;
        } finally {
            byteBuf.release();
        }
    }

    /**
     * @param date 日期
     * @return 对应字节数组
     */
    public static byte[] dateToByte(Date date) {
        byte[] rdate = new byte[8];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        rdate[0] = (byte) calendar.get(Calendar.YEAR);
        rdate[1] = (byte) ((calendar.get(Calendar.YEAR) >> 8) & 0xff);
        rdate[2] = (byte) ((calendar.get(Calendar.MONTH) + 1));
        rdate[3] = (byte) (calendar.get(Calendar.DAY_OF_MONTH));
        rdate[4] = (byte) (calendar.get(Calendar.HOUR_OF_DAY));
        rdate[5] = (byte) (calendar.get(Calendar.MINUTE));
        rdate[6] = (byte) (calendar.get(Calendar.SECOND));
        rdate[7] = (byte) 0xff;
        return rdate;
    }

    public static Date byteToDate(byte[] bt) {
        Calendar ca = Calendar.getInstance();
        if (bt == null || bt.length < 8) {
            return null;
        }
        //byte[] year = new byte[2];
        int year1 = (bt[1]&0xff)*16*16;
        int year2 = bt[0]&0xff;
        ca.set(Calendar.YEAR, year1+year2);
        ca.set(Calendar.MONTH, (bt[2]&0xff) - 1);
        ca.set(Calendar.DAY_OF_MONTH, bt[3]&0xff);
        ca.set(Calendar.HOUR_OF_DAY, bt[4]&0xff);
        ca.set(Calendar.MINUTE, bt[5]&0xff);
        ca.set(Calendar.SECOND, bt[6]&0xff);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime();
    }

    /**
     * Converter byte[] to hex
     * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
     */
    public static String byteToHexString(byte src) {
        StringBuilder stringBuilder = new StringBuilder("");
        int v = src & 0xFF;
        String hv = Integer.toHexString(v);
        if (hv.length() < 2) {
            stringBuilder.append(0);
        }
        stringBuilder.append(hv);

        return stringBuilder.toString();
    }

    public static String bytesToHexString(byte[] src) {
        if (src == null || src.length <= 0) {
            return null;
        }
        return Hex.encodeHexString(src);
    }

    /**
     * Converter hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     * @throws DecoderException
     */
    public static byte[] hexStringToBytes(String hexString)
            throws DecoderException {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        return Hex.decodeHex(hexString.toCharArray());
    }

    /**
     * Converter char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 把byte数组转化成2进制字符串
     */
    public static String getBinaryStrFromByteArr(byte[] bArr) {
        String result = "";
        for (byte b : bArr) {
            result += getBinaryStrFromByte(b);
        }
        return result;
    }

    /**
     * 把byte转化成2进制字符串
     */
    public static String getBinaryStrFromByte(byte b) {
        String result = "";
        byte a = b;
        for (int i = 0; i < 8; i++) {
            byte c = a;
            a = (byte) (a >> 1);// 每移一位如同将10进制数除以2并去掉余数。
            a = (byte) (a << 1);
            if (a == c) {
                result = "0" + result;
            } else {
                result = "1" + result;
            }
            a = (byte) (a >> 1);
        }
        return result;
    }

    /**
     * 将输入流转化为字节数组
     */
    public static byte[] getBytes(InputStream inputStream) {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100]; // buff用于存放循环读取的临时数据
        int rc = 0;
        try {
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);

        }
        return swapStream.toByteArray();
    }

    /**
     * long类型转成byte数组
     *
     * @param number long类型
     * @return 长度为8的byte 数组
     */
    public static byte[] longToByte(long number) {
        long temp = number;
        byte[] b = new byte[8];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Long(temp & 0xff).byteValue();// 将最低位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    /**
     * byte数组转成long
     *
     * @param b 长度8的byte数组（小端）
     * @return long
     */
    public static long byteToLong(byte[] b) {
        long s = 0;
        long s0 = b[0] & 0xff;// 最低位
        long s1 = b[1] & 0xff;
        long s2 = b[2] & 0xff;
        long s3 = b[3] & 0xff;
        long s4 = b[4] & 0xff;// 最低位
        long s5 = b[5] & 0xff;
        long s6 = b[6] & 0xff;
        long s7 = b[7] & 0xff;

        // s0不变
        s1 <<= 8;
        s2 <<= 16;
        s3 <<= 24;
        s4 <<= 8 * 4;
        s5 <<= 8 * 5;
        s6 <<= 8 * 6;
        s7 <<= 8 * 7;
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
        return s;
    }

    /**
     * int类型转成byte数组
     *
     * @param number int类型
     * @return 长度为4的byte 数组
     */
    public static byte[] intToByte(int number) {
        int temp = number;
        byte[] b = new byte[4];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();// 将最低位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    /**
     * byte数组转成int
     *
     * @param b 长度4的byte数组（小端）
     * @return int
     */
    public static int byteToInt(byte[] b) {
        int s = 0;
        int s0 = b[0] & 0xff;// 最低位
        int s1 = b[1] & 0xff;
        int s2 = b[2] & 0xff;
        int s3 = b[3] & 0xff;
        s3 <<= 24;
        s2 <<= 16;
        s1 <<= 8;
        s = s0 | s1 | s2 | s3;
        return s;
    }

    /**
     * short类型转成byte数组
     *
     * @param number short类型
     * @return 长度为2的byte 数组
     */
    public static byte[] shortToByte(short number) {
        int temp = number;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();// 将最低位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    /**
     * byte数组转成short
     *
     * @param b 长度2的byte数组（小端）
     * @return short
     */
    public static short byteToShort(byte[] b) {
        short s = 0;
        short s0 = (short) (b[0] & 0xff);// 最低位
        short s1 = (short) (b[1] & 0xff);
        s1 <<= 8;
        s = (short) (s0 | s1);
        return s;
    }


}
