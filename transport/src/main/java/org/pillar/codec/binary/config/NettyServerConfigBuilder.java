package org.pillar.codec.binary.config;

import com.google.common.base.Strings;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.inject.Inject;
import io.netty.channel.socket.ServerSocketChannelConfig;

import java.lang.reflect.Proxy;
import java.nio.ByteOrder;
import java.util.concurrent.ThreadFactory;

/**
 * Created by pillar on 2015/8/10.
 * 该类封装配置
 */
public class NettyServerConfigBuilder extends NettyConfigBuilderBase<NettyServerConfigBuilder> {


	private final ServerSocketChannelConfig serverSocketChannelConfig = (ServerSocketChannelConfig) Proxy.newProxyInstance(
			getClass().getClassLoader(),
			new Class<?>[]{ServerSocketChannelConfig.class},
			new Magic(""));


	@Inject
	public NettyServerConfigBuilder() {
		//

	}


	/**
	 * 构建配置 不优雅
	 *
	 * @return
	 */
	public NettyServerConfig build() {


		int port = getProperties().getInt("netty.listener.port");
		int revBufferSize = getProperties().getInt("netty.rev.buffer.size");
		int wrtBufferSize = getProperties().getInt("netty.wrt.buffer.size");
		int tcpBackLog = getProperties().getInt("netty.tcp.backlog");
		/**
		 * Boss 线程默认是CPU数
		 */
		int bossThreadCount = getBossThreadCount();
		ThreadFactory bossThreadFactory = getBossThreadFactory();

		/**
		 * 默认的Worker也是CPU数目
		 */
		int workerThreadCount = getWorkerThreadCount();
		ThreadFactory workerThreadFactory = getWorkerThreadFactory();

		int threadPerCpu = getProperties().getInt("thread.per.cpu.thread.limit");
		int maxThreadLimit = getProperties().getInt("thread.total.thread.limit");

		boolean keepAlive = getProperties().getBoolean("channel.option.keep.alive");
		boolean noDelay = getProperties().getBoolean("channel.option.tcp.nodelay");


		return new
				NettyServerConfig(port,
				bossThreadCount,
				workerThreadCount,
				tcpBackLog,
				keepAlive,
				noDelay,
				revBufferSize,
				wrtBufferSize,
				threadPerCpu,
				maxThreadLimit,
				bossThreadFactory,
				workerThreadFactory,
				ByteOrder.LITTLE_ENDIAN);


	}

	private String threadNamePattern(String suffix) {
		String niftyName = getThreadName();
		return "netty-server" + (Strings.isNullOrEmpty(niftyName) ? "" : "-" + niftyName) + suffix;
	}

	private ThreadFactory renamingThreadFactory(String nameFormat) {
		return new ThreadFactoryBuilder().setNameFormat(nameFormat).build();
	}

	public ServerSocketChannelConfig getServerSocketChannelConfig() {
		return serverSocketChannelConfig;
	}
}
