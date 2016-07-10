package com.ywg.androidcommon.utils;

/**
 * SystemUtils
 * 
 */
public class SystemUtil {

    public static final int DEFAULT_THREAD_POOL_SIZE = getDefaultThreadPoolSize();

    private SystemUtil() {
        throw new AssertionError();
    }


    /**
     *
     * @return 返回线程池的个数
     */
    public static int getDefaultThreadPoolSize() {

        return getDefaultThreadPoolSize(8);
    }

    /**
     * get recommend default thread pool size
     * 
     * @param max  最大线程数
     * @return if 2 * availableProcessors + 1 less than max, return it, else return max;
     */
    public static int getDefaultThreadPoolSize(int max) {
        int availableProcessors = 2 * Runtime.getRuntime().availableProcessors() + 1;
        return availableProcessors > max ? max : availableProcessors;
    }
}