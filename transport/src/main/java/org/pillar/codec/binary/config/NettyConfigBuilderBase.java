package org.pillar.codec.binary.config;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

/**
 * Created by pillar on 2015/8/10.
 */
public abstract class NettyConfigBuilderBase<T extends NettyConfigBuilderBase<T>> extends AbastractConfigBuilder {
    // These constants come directly from Netty but are private in Netty.
    public static final int DEFAULT_BOSS_THREAD_COUNT = 1;

    public static final int DEFAULT_WORKER_THREAD_COUNT = Runtime.getRuntime().availableProcessors() * 2;

    private String threadName;

    private int bossThreadCount = DEFAULT_BOSS_THREAD_COUNT;

    private int workerThreadCount = DEFAULT_WORKER_THREAD_COUNT;

    private final Map<String, Object> options = new HashMap<>();


    /**
     * Sets an identifier which will be added to all thread created by the boss and worker
     * executors.
     *
     * @param threadName
     * @return
     */
    protected T setThreadName(String threadName) {
        Preconditions.checkNotNull(threadName, "threadName cannot be null");
        this.threadName = threadName;
        return (T) this;
    }

    public String getThreadName() {
        return threadName;
    }


    /**
     * Sets the number of threads that will be used to manage
     *
     * @param bossThreadCount
     * @return
     */
    public T setBossThreadCount(int bossThreadCount) {
        this.bossThreadCount = bossThreadCount;
        return (T) this;
    }

    protected int getBossThreadCount() {
        return bossThreadCount;
    }


    public T setWorkerThreadCount(int workerThreadCount) {
        this.workerThreadCount = workerThreadCount;
        return (T) this;
    }

    protected int getWorkerThreadCount() {
        return workerThreadCount;
    }


    public ThreadFactory getBossThreadFactory() {
        return renamingThreadFactory(threadNamePattern("-boss-%s"));
    }

    public ThreadFactory getWorkerThreadFactory() {
        return renamingThreadFactory(threadNamePattern("-worker-%s"));

    }


    private String threadNamePattern(String suffix) {
        String niftyName = getThreadName();
        return "netty-server" + (Strings.isNullOrEmpty(niftyName) ? "" : "-" + niftyName) + suffix;
    }

    private ThreadFactory renamingThreadFactory(String nameFormat) {
        return new ThreadFactoryBuilder().setNameFormat(nameFormat).build();
    }

    protected class Magic implements InvocationHandler {
        private final String prefix;

        public Magic(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            // we are only interested in setters with single arg
            if (proxy != null) {
                if (method.getName().equals("toString")) {
                    return "this is a magic proxy";
                } else if (method.getName().equals("equals")) {
                    return Boolean.FALSE;
                } else if (method.getName().equals("hashCode")) {
                    return 0;
                }
            }
            // we don't support multi-arg setters
            if (method.getName().startsWith("set") && args.length == 1) {
                String attributeName = method.getName().substring(3);
                // camelCase it
                attributeName = attributeName.substring(0, 1).toLowerCase() + attributeName.substring(1);
                // now this is our key
                options.put(prefix + attributeName, args[0]);
                return null;
            }
            throw new UnsupportedOperationException();
        }
    }
}
