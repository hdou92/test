package com.hd.test.collection;

/**
 * 队列数据信息
 */
public class QueueDataInfo<T> {
    /**
     * 时间戳
     */
    private long timestamp;
    /**
     * 数据信息
     */
    private T info;

    public QueueDataInfo() {

    }

    public QueueDataInfo(T info) {
        this.info = info;
        this.timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "QueueDataInfo{" +
                "timestamp=" + timestamp +
                ", info=" + info +
                '}';
    }
}
