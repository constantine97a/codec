//package org.pillar.codec.binary.wirte;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.ByteBufUtil;
//import io.netty.buffer.Unpooled;
//
//import java.nio.ByteOrder;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.apache.commons.codec.binary.Hex;
//import org.junit.Before;
//import org.junit.Test;
//
//import org.pillar.codec.binary.codec.ByteOrders;
//import org.pillar.codec.binary.codec.StringCodec;
//import org.pillar.codec.binary.codec.UInt16Codec;
//import org.pillar.codec.binary.codec.UInt32Codec;
//import org.pillar.codec.binary.codec.UInt8Codec;
//import org.pillar.codec.binary.config.NettyCheckFrameConfig;
//import org.pillar.codec.binary.handler.ChecksumDuplexHandler;
//import org.pillar.codec.binary.handler.LengthOutboundSendHandler;
//import org.pillar.codec.binary.util.TransportNettyUtil;
///**
// * Created by pillar on 2015/8/11.
// */
//public class PcdzStringTest {
//    private LengthOutboundSendHandler lhandler;
//
//    private ChecksumDuplexHandler chandler;
//
//    @Before
//    public void setUp() throws Exception {
//    	lhandler = new LengthOutboundSendHandler();
//    	chandler = new ChecksumDuplexHandler(new NettyCheckFrameConfig(6, 1));
//    }
//
//    @Test
//    public void test_register() throws Exception {
//    	UInt16Codec a16 = new UInt16Codec();
//    	UInt8Codec a8 = new UInt8Codec();
//    	UInt32Codec a32 = new UInt32Codec();
//    	ByteBuf byteBuf = Unpooled.buffer(1024);
//        byteBuf.order(ByteOrders.byteOrder);
//        //起始域
//        byteBuf.writeBytes(Hex.decodeHex("faf5".toCharArray()));
//        //长度域
//        byteBuf.writeBytes(a16.encode(0));
//        //版本域
//        byteBuf.writeByte(128);
//        //序列号域
//        byteBuf.writeByte(0);
//        //命令域
//        byteBuf.writeByte(0x11);
//
//        //厂家编号
//        byteBuf.writeByte(0x01);
//        //充电桩编号
//        byteBuf.writeBytes(a16.encode(17));
//
//        //以上是公共部分
//
//        //系统软件版本
//        byteBuf.writeBytes(a32.encode(0L));
//
//        //启动次数
//        byteBuf.writeBytes(a32.encode(111L));
//        //存储空间容量
//        byteBuf.writeBytes(a32.encode(111L));
//
//        //充电桩智能终端软件已经持续运行时间
//        byteBuf.writeBytes(a32.encode(111L));
//
//        //最近一次启动时间
//        byteBuf.writeBytes(TransportNettyUtil.dateToByte(new Date()));
//        //最近一次签到时间
//        byteBuf.writeBytes(TransportNettyUtil.dateToByte(new Date()));
//
//        //黑名单 时间戳序号 时间戳序号 时间戳序号
//
//        byteBuf.writeBytes(TransportNettyUtil.dateToByte(new Date()));
//
//        //添加校验域
//        byteBuf = chandler.setChecksum(byteBuf);
//
//        //设置长度
//        byteBuf =  lhandler.setLengthOfByteBuf(byteBuf);
//
//
//        System.out.println("充电桩签到：========" + ByteBufUtil.hexDump(byteBuf));
//
//    }
//
//    /**
//     * 充电桩上报总状态
//     * @throws Exception
//     */
//    @Test
//    public void test_totalstatus() throws Exception {
//    	UInt16Codec a16 = new UInt16Codec();
//    	UInt8Codec a8 = new UInt8Codec();
//    	UInt32Codec a32 = new UInt32Codec();
//    	ByteBuf byteBuf = Unpooled.buffer(1024, 1024);
//        byteBuf.order(ByteOrder.LITTLE_ENDIAN);
//        //起始域
//
//        byteBuf.writeBytes(Hex.decodeHex("faf5".toCharArray()));
//        //长度域
//
//        byteBuf.writeShort(0);
//        //版本域
//
//        byteBuf.writeByte(128);
//        //序列号域
//
//        byteBuf.writeByte(0);
//        //命令域
//
//        byteBuf.writeByte(0x13);
//
//        //厂家编号
//
//
//        byteBuf.writeByte(0x01);
//        //充电桩编号
//
//        byteBuf.writeBytes(a16.encode(3));
//
//        //以上是公共部分
//
//       //充电桩类型
//
//        byteBuf.writeByte(1);
//
//        //经度
//
//        byteBuf.writeBytes(a32.encode(1111111L));
//
//        //纬度
//
//        byteBuf.writeBytes(a32.encode(1111112L));
//
//        //可服务电池类型
//
//        byteBuf.writeByte(1);
//
//        //执行方式  重启
//
//        byteBuf.writeByte(1);
//        //执行方式  开启
//
//        byteBuf.writeByte(0);
//        //执行方式 关闭启
//
//        byteBuf.writeByte(0);
//
//        //充电枪个数
//
//        byteBuf.writeByte(5);
//
//        //最高允许输出电压
//
//        byteBuf.writeBytes(a16.encode(233));
//
//        //最低允许输出电压
//
//        byteBuf.writeBytes(a16.encode(231));
//
//        //最大允许输出电流
//
//        byteBuf.writeBytes(a16.encode(23));
//
//        for(int i =0; i<5; i++) {
//
//        	//充电枪位置1 充电类型 1 1=直流；2=交流；
//
//        	 byteBuf.writeByte(1);
//
//        	 //充电枪N 标识
//
//
//        	 byteBuf.writeByte(i+1);
//        }
//
//        //以下为公共部分
//
//        //添加校验域
//        byteBuf = chandler.setChecksum(byteBuf);
//
//        //设置长度
//        byteBuf =  lhandler.setLengthOfByteBuf(byteBuf);
//
//
//
//        System.out.println("充电桩上报总状态：========" + ByteBufUtil.hexDump(byteBuf));
//
//    }
//
//    /**
//     * 充电桩运行状态上报
//     * @throws Exception
//     */
//    @Test
//    public void test_runstatus() throws Exception {
//
//    	UInt16Codec a16 = new UInt16Codec();
//    	UInt8Codec a8 = new UInt8Codec();
//    	UInt32Codec a32 = new UInt32Codec();
//    	ByteBuf byteBuf = Unpooled.buffer(1024, 1024);
//        byteBuf.order(ByteOrder.LITTLE_ENDIAN);
//
//        byteBuf.order(ByteOrder.LITTLE_ENDIAN);
//        //起始域
//
//        byteBuf.writeBytes(Hex.decodeHex("faf5".toCharArray()));
//        //长度域
//
//        byteBuf.writeShort(2356);
//        //版本域
//
//        byteBuf.writeByte(128);
//        //序列号域
//
//        byteBuf.writeByte(0);
//        //命令域
//
//        byteBuf.writeByte(0x31);
//
//        //厂家编号
//
//
//        byteBuf.writeByte(0x01);
//        //充电桩编号
//
//        byteBuf.writeBytes(a16.encode(17));
//
//        //以上是公共部分
//
//        //以上是公共部分
//
//        //当前日期时间
//
//        byteBuf.writeBytes(TransportNettyUtil.dateToByte(new Date()));
//
//        //输入电源状态
//
//
//        byteBuf.writeByte(1);
//
//        //平均温度
//
//        byteBuf.writeByte(63);
//
//        //外部温度
//
//        byteBuf.writeByte(73);
//
//        //工作状态
//
//
//        byteBuf.writeByte(2);
//
//        //可充电机模块数量
//
//        byteBuf.writeByte(63);
//
//        //输出有功率
//
//        byteBuf.writeBytes(a16.encode(234));
//
//        //输出无功率
//
//
//        byteBuf.writeBytes(a16.encode(231));
//
//        //充电桩输出总压
//
//        byteBuf.writeBytes(a16.encode(236));
//
//        //充电桩输出总流
//
//
//        byteBuf.writeBytes(a16.encode(23));
//
//        //充电桩输出总功率
//
//        byteBuf.writeBytes(a16.encode(2336));
//
//        //充电系统总体状态
//
//        byteBuf.writeByte(1);
//
//        for(int i = 1;i<5;i++) {
//        	//充电枪 充电枪 1标示
//
//
//            byteBuf.writeByte(i);
//
//            // 充电枪位置 1充电类型
//
//            byteBuf.writeByte(2);
//
//            //充电枪 充电枪 1状态
//
//
//            byteBuf.writeByte(1);
//
//            //充电枪 充电枪 1工作状态
//
//            byteBuf.writeByte(2);
//        }
//
//
//
//        //以下为公共部分
//
//        //添加校验域
//        byteBuf = chandler.setChecksum(byteBuf);
//
//        //设置长度
//        byteBuf =  lhandler.setLengthOfByteBuf(byteBuf);
//
//
//
//        System.out.println("充电桩运行状态：========" + ByteBufUtil.hexDump(byteBuf));
//
//    }
//
//
//    /**
//     * 用户验证（暂时只实现 有卡方式）
//     * @throws Exception
//     */
//    @Test
//    public void test_checkuser() throws Exception {
//    	UInt16Codec a16 = new UInt16Codec();
//    	UInt8Codec a8 = new UInt8Codec();
//    	UInt32Codec a32 = new UInt32Codec();
//    	ByteBuf byteBuf = Unpooled.buffer(1024, 1024);
//    	StringCodec ss = new StringCodec(16);
//    	StringCodec sss = new StringCodec(1);
//        byteBuf.order(ByteOrder.LITTLE_ENDIAN);
//        //起始域
//        byteBuf.writeBytes(Hex.decodeHex("faf5".toCharArray()));
//        //长度域
//        byteBuf.writeShort(2356);
//        //版本域
//        byteBuf.writeByte(128);
//        //序列号域
//        byteBuf.writeByte(0);
//        //命令域
//        byteBuf.writeByte(0x51);
//
//        //厂家编号
//
//        byteBuf.writeByte(0x01);
//        //充电桩编号
//        byteBuf.writeBytes(a16.encode(17));
//
//        //以上是公共部分
//
//        //当前日期时间
//        byteBuf.writeBytes(TransportNettyUtil.dateToByte(new Date()));
//
//        //账号类型
//
//        byteBuf.writeByte(1);
//
//        //用户名
//
//        byteBuf.writeBytes(ss.encode("3140200200000001"));
//
//        //密码
//
//        byteBuf.writeBytes(sss.encode("0"));
//
//
//        //以下为公共部分
//
//        //添加校验域
//        byteBuf = chandler.setChecksum(byteBuf);
//
//        //设置长度
//        byteBuf =  lhandler.setLengthOfByteBuf(byteBuf);
//
//
//
//        System.out.println("用户验证：========" + ByteBufUtil.hexDump(byteBuf));
//
//    }
//
//
//
//    /**
//     * 故障信息
//     * @throws Exception
//     */
//    @Test
//    public void test_Fault() throws Exception {
//    	ByteBuf byteBuf = Unpooled.buffer(1024, 1024);
//        byteBuf.order(ByteOrder.LITTLE_ENDIAN);
//        //起始域
//        byteBuf.writeBytes(Hex.decodeHex("faf5".toCharArray()));
//        //长度域
//
//        byteBuf.writeShort(2356);
//        //版本域
//        byteBuf.writeByte(128);
//        //序列号域
//        byteBuf.writeByte(0);
//        //命令域
//        byteBuf.writeByte(0x51);
//
//        //厂家编号
//
//        byteBuf.writeByte(0x01);
//        //充电桩编号
//        byteBuf.writeShort(23);
//
//        //以上是公共部分
//
//        //当前日期时间
//
//        byteBuf.writeBytes(TransportNettyUtil.dateToByte(new Date()));
//
//
//        //充电系统故障状态
//        byteBuf.writeInt(12);
//
//        //AC/DC 控制模块故障状态
//
//        byteBuf.writeInt(12);
//
//        //风扇故障
//        byteBuf.writeByte(1);
//
//        //空调故障
//
//        byteBuf.writeByte(1);
//
//        //加热器故障
//
//        byteBuf.writeByte(1);
//
//        //烟雾报警故障
//        byteBuf.writeByte(1);
//        //震动传感器报警故障
//        byteBuf.writeByte(1);
//
//
//        //以下为公共部分
//
//        //添加校验域
//        byteBuf = chandler.setChecksum(byteBuf);
//
//        //设置长度
//        byteBuf =  lhandler.setLengthOfByteBuf(byteBuf);
//
//
//
//        System.out.println("用户验证：========" + ByteBufUtil.hexDump(byteBuf));
//
//    }
//
//    @Test
//    public void test_fault() throws Exception {
//    	UInt16Codec a16 = new UInt16Codec();
//    	UInt8Codec a8 = new UInt8Codec();
//    	UInt32Codec a32 = new UInt32Codec();
//    	ByteBuf byteBuf = Unpooled.buffer(1024, 1024);
//    	StringCodec ss = new StringCodec(16);
//        byteBuf.order(ByteOrder.LITTLE_ENDIAN);
//        //起始域
//        byteBuf.writeBytes(Hex.decodeHex("faf5".toCharArray()));
//        //长度域
//        byteBuf.writeShort(2356);
//        //版本域
//        byteBuf.writeByte(128);
//        //序列号域
//        byteBuf.writeByte(0);
//        //命令域
//        byteBuf.writeByte(0xB1);
//
//        //厂家编号
//
//        byteBuf.writeByte(0x01);
//        //充电桩编号
//        byteBuf.writeBytes(a16.encode(17));
//
//        //以上是公共部分
//
//        //当前日期时间
//        byteBuf.writeBytes(TransportNettyUtil.dateToByte(new Date()));
//
//        //充电系统故障状态
//
//        byteBuf.writeBytes(a32.encode(1L));
//
//        //AC/DC  控制模块故障状态
//        byteBuf.writeBytes(a32.encode(1L));
//        //风扇故障
//        byteBuf.writeByte(1);
//        //空调故障
//        byteBuf.writeByte(0);
//        //加热器故障
//        byteBuf.writeByte(0);
//        //烟雾报警故障
//        byteBuf.writeByte(0);
//        //震动传感器报警故障
//        byteBuf.writeByte(0);
//
//        //以下为公共部分
//
//        //添加校验域
//        byteBuf = chandler.setChecksum(byteBuf);
//
//        //设置长度
//        byteBuf =  lhandler.setLengthOfByteBuf(byteBuf);
//
//
//
//        System.out.println("故障：========" + ByteBufUtil.hexDump(byteBuf));
//    }
//
//    @Test
//    public void test_ChargingRecords() throws Exception {
//    	UInt16Codec a16 = new UInt16Codec();
//    	UInt8Codec a8 = new UInt8Codec();
//    	UInt32Codec a32 = new UInt32Codec();
//    	ByteBuf byteBuf = Unpooled.buffer(1024, 1024);
//    	StringCodec ss = new StringCodec(16);
//        byteBuf.order(ByteOrder.LITTLE_ENDIAN);
//        //起始域
//        byteBuf.writeBytes(Hex.decodeHex("faf5".toCharArray()));
//        //长度域
//        byteBuf.writeShort(2356);
//        //版本域
//        byteBuf.writeByte(128);
//        //序列号域
//        byteBuf.writeByte(0);
//        //命令域
//        byteBuf.writeByte(0x17);
//
//        //厂家编号
//
//        byteBuf.writeByte(0x01);
//        //充电桩编号
//        byteBuf.writeBytes(a16.encode(3));
//
//        //以上是公共部分
//
//        //当前日期时间
//        byteBuf.writeBytes(TransportNettyUtil.dateToByte(new Date()));
//
//        byteBuf.writeBytes(ss.encode("0000015195751901"));
//
//        //以下为公共部分
//
//        //添加校验域
//        byteBuf = chandler.setChecksum(byteBuf);
//
//        //设置长度
//        byteBuf =  lhandler.setLengthOfByteBuf(byteBuf);
//
//
//
//        System.out.println("故障：========" + ByteBufUtil.hexDump(byteBuf));
//    }
//
//    @Test
//    public void test_ChargingReserve() throws Exception {
//    	UInt16Codec a16 = new UInt16Codec();
//    	UInt8Codec a8 = new UInt8Codec();
//    	UInt32Codec a32 = new UInt32Codec();
//    	ByteBuf byteBuf = Unpooled.buffer(1024, 1024);
//    	StringCodec ss = new StringCodec(16);
//        byteBuf.order(ByteOrder.LITTLE_ENDIAN);
//        //起始域
//        byteBuf.writeBytes(Hex.decodeHex("faf5".toCharArray()));
//        //长度域
//        byteBuf.writeShort(2356);
//        //版本域
//        byteBuf.writeByte(128);
//        //序列号域
//        byteBuf.writeByte(0);
//        //命令域
//        byteBuf.writeByte(0x25);
//
//        //厂家编号
//
//        byteBuf.writeByte(0x01);
//        //充电桩编号
//        byteBuf.writeBytes(a16.encode(3));
//
//        //以上是公共部分
//
//        //当前日期时间
//
//        String time="2015-10-12 10:34:19";
//
//        Date date=null;
//        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        date=formatter.parse(time);
//        byteBuf.writeBytes(TransportNettyUtil.dateToByte(date));
//
//        byteBuf.writeByte(1);
//
//        byteBuf.writeBytes(ss.encode("0000015195751901"));
//        time="2015-10-08 16:34:20";
//        date=formatter.parse(time);
//
//        byteBuf.writeBytes(TransportNettyUtil.dateToByte(new Date()));
//        //以下为公共部分
//
//        //添加校验域
//        byteBuf = chandler.setChecksum(byteBuf);
//
//        //设置长度
//        byteBuf =  lhandler.setLengthOfByteBuf(byteBuf);
//
//
//
//        System.out.println("预约：========" + ByteBufUtil.hexDump(byteBuf));
//    }
//
//    @Test
//    public void test() {
//    	ByteBuf message = Unpooled.buffer(1024);
//    	for(int i=0;i<1024;i++) {
//    		message.writeByte(24);
//    	}
//    	ByteBuf buf = Unpooled.buffer(message.readableBytes());
//    	buf.order(ByteOrder.LITTLE_ENDIAN);
//    	buf.writeBytes(message, 0, 2);
//    	//logger.info("After the replacement length, the last of the 16 string:"+ ByteBufUtil.hexDump(buf));
//        buf.writeBytes(new UInt16Codec().encode(message.readableBytes()-5));
//        //(new UInt16Codec()(message.readableBytes()-bodylengthc)));
//        //logger.info("After the replacement length, the last of the 16 string:"+ ByteBufUtil.hexDump(buf));
//        buf.writeBytes(message, 4, message.readableBytes()-4);
//        System.out.println("预约：========" + ByteBufUtil.hexDump(buf));
//
//    }
//}