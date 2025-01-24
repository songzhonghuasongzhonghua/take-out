package com.song.context;

public class BaseContext {
    public static ThreadLocal<Long> THREAD_LOCAL = new ThreadLocal<>();


    public static void setThreadLocal(Long threadId) {
        THREAD_LOCAL.set(threadId);
    }

    public static Long getThreadLocal() {
        return THREAD_LOCAL.get();
    }

    public static void removeThreadLocal() {
        THREAD_LOCAL.remove();
    }
}
