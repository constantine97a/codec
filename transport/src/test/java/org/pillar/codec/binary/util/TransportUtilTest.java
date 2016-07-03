package org.pillar.codec.binary.util;

import io.netty.buffer.Unpooled;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Calendar;

import static org.junit.Assert.assertTrue;

/**
 * Created by pillar on 2015/8/1.
 */

@ActiveProfiles("test")
public class TransportUtilTest {

    @Test
    public void test_validateCheckCode() throws Exception {

        // String ss =
        // "7e0100002d013355000062000000320000050403020105000000000000000000000000000000000000000000000000000001cbd54538383838387d0159";
        String ss = "7e01007F";

        byte[] aa = TransportNettyUtil.hexStringToBytes(ss);

        System.out.println(TransportNettyUtil.validateChecksum(aa));

    }

    @Test
    public void test_getCheckCode() throws Exception {

        // String ss =
        // "7e0100002d013355000062000000320000050403020105000000000000000000000000000000000000000000000000000001cbd54538383838387d0159";
        String ss = "7e0100";

        byte[] aa = TransportNettyUtil.hexStringToBytes(ss);
        // 预期输出是7F
        System.out.println(TransportNettyUtil.checksum(aa));
        System.out.println(TransportNettyUtil.checksum(Unpooled.copiedBuffer(aa)));
        assertTrue(0x7f == TransportNettyUtil.checksum(Unpooled.copiedBuffer(aa)));

    }

    @Test
    public void test_escapeMsg() throws Exception {

        // String ss =
        // "7e0100002d013355000062000000320000050403020105000000000000000000000000000000000000000000000000000001cbd54538383838387d0159";
        String ss = "faf5fdedfdeeffffffffff00";

        // 预期测试结果：fdfeffffffffff

        byte[] aa = TransportNettyUtil.hexStringToBytes(ss);
        // 预期输出是7F
        System.out.println(TransportNettyUtil.bytesToHexString(TransportNettyUtil
                .escapeMsg(aa)));

    }

    @Test
    public void test_msgEscape() throws Exception {

        // String ss =
        // "7e0100002d013355000062000000320000050403020105000000000000000000000000000000000000000000000000000001cbd54538383838387d0159";
        String ss = "fdfffefeff";

        // 预期测试结果：faf5fdedfffdeefdeeff

        byte[] aa = TransportNettyUtil.hexStringToBytes(ss);
        // 预期输出是7F
        System.out.println(TransportNettyUtil.bytesToHexString(TransportNettyUtil
                .msgEscape(aa)));
        Calendar calendar = Calendar.getInstance();
        /*
         * System.out.println(calendar.get(Calendar.YEAR));
		 * System.out.println(calendar.get(Calendar.MONTH)); byte[] cc = new
		 * byte[2]; cc[1]=(byte)(2012>>8) & 0xff; cc[0]=(byte)2012;
		 * System.out.println(TransportNettyUtil.bytesToHexString(cc));
		 */

    }

    @Test
    public void test_getBytesByDateBytes() throws Exception {

        // String ss =
        // "7e0100002d013355000062000000320000050403020105000000000000000000000000000000000000000000000000000001cbd54538383838387d0159";
        //String ss = "fdfffefeff";

        // 预期测试结果：faf5fdedfffdeefdeeff

        ///byte[] aa = TransportNettyUtil.hexStringToBytes(ss);

        // 预期输出是7F
//		System.out.println(TransportNettyUtil.bytesToHexString(TransportNettyUtil
//				.dateToByte()));

        System.out.println(TransportNettyUtil.byteToDate(TransportNettyUtil.hexStringToBytes("df0709080f1f3bff")).getTime());
        System.out.println(TransportNettyUtil.byteToDate(TransportNettyUtil.hexStringToBytes("df0709080f1f3bff")).getTime());
        System.out.println(TransportNettyUtil.byteToDate(TransportNettyUtil.hexStringToBytes("df0709080f1f3bff")).getTime());
        System.out.println(TransportNettyUtil.byteToDate(TransportNettyUtil.hexStringToBytes("df0709080f1f3bff")).getTime());

    }

    @Test
    public void test_getDateByBytes() throws Exception {

        // String ss =
        // "7e0100002d013355000062000000320000050403020105000000000000000000000000000000000000000000000000000001cbd54538383838387d0159";
        String ss = "df0708080e271dff";
        ss.getBytes();

        // 预期测试结果：faf5fdedfffdeefdeeff

        byte[] aa = TransportNettyUtil.hexStringToBytes(ss);
        // 预期输出是7F
        System.out.println(TransportNettyUtil
                .byteToDate(aa));
    }

    @Test
    public void test_longToByte() throws Exception {


        System.out.println(TransportNettyUtil.bytesToHexString(TransportNettyUtil.longToByte(10000000)));
    }

    /**
     * 测试物理卡号互相转byte[]
     *
     * @throws Exception
     */
    @Test
    public void test_lns1() throws Exception {

        String ss = "3140200200000001";
        System.out.println(TransportNettyUtil.bytesToHexString(ss.getBytes()));
        String sss = "33313430323030323030303030303031";
        System.out.println(new String(TransportNettyUtil.hexStringToBytes(sss)));
    }


    @Test
    public void test_charingin_checksum() throws Exception {
        String value = "7301ffffdf0708160f052dff0108df0708160f052dff08f50801016162636461626364616263646162636401000800080009f9f1b3000000ffffffff01df0708160f052dffdf0708160f052dff01";
        byte[] bytes = TransportNettyUtil.hexStringToBytes(value);
        byte checksum = TransportNettyUtil.checksum(bytes);
        System.out.println(TransportNettyUtil.byteToHexString(checksum));

        String strValue = "faf54b0080007301ffffdf0708160f052dff0108df0708160f052dff08f50801016162636461626364616263646162636401000800080009f9f1b3000000ffffffff01df0708160f052dffdf0708160f052dff01";
        int length
                = strValue.length() / 2 - 4;
        System.out.println(length);
        System.out.println(TransportNettyUtil.byteToHexString((byte) length));


    }
}