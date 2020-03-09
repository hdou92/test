package com.hd.test.common;

import com.hd.test.collection.ExConcurrentHashMap;
import com.hd.test.collection.QueueDataInfo;
import com.hd.test.collection.TwoValue;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 本地缓存
 */
public class LocalCacheManager<TK, TV> {
    private final ExConcurrentHashMap<TK, QueueDataInfo<TV>> configs = new ExConcurrentHashMap<>();

    private final Supplier<Integer> getCacheTime;

    /**
     * @param getCacheTime 获取缓存时间（秒）
     */
    public LocalCacheManager(Supplier<Integer> getCacheTime) {
        this.getCacheTime = getCacheTime;
    }

    public TV get(TK id, Function<TK, TV> getVal) {
        TwoValue<Boolean, TV> getExpired = getAndCheckExpired(id);

        if (getExpired.getVal1()) { //过期了
            QueueDataInfo<TV> oconfig = configs.computeIfAbsentEx(id, key -> new QueueDataInfo<>(getVal.apply(key)));
            return oconfig.getInfo();
        } else {
            return getExpired.getVal2();
        }
    }

    public void set(TK id, TV val) {
        configs.put(id, new QueueDataInfo<>(val));
    }

    /**
     * 过期了，那么重新设置
     */
    public boolean checkExpiredAndSet(TK id, Function<TK, TV> getExpiredSetVal) {
        QueueDataInfo<TV> oconfig = configs.get(id);
        if (oconfig != null) {
            if (DateUtils.isExpired(oconfig.getTimestamp(), getCacheTime())) {
                //过期了，那么重新设置
                set(id, getExpiredSetVal.apply(id));
                return true;
            }

            return false;
        } else {
            //没有设置，那么也认为是过期了的，设置
            set(id, getExpiredSetVal.apply(id));
            return true;
        }
    }

    /**
     * 获取和检测是否过期
     */
    public TwoValue<Boolean, TV> getAndCheckExpired(TK id) {
        QueueDataInfo<TV> oconfig = configs.get(id);
        if (oconfig != null) {
            if (DateUtils.isExpired(oconfig.getTimestamp(), getCacheTime())) {
                //过期了，那么删除
                configs.remove(id);
                return new TwoValue<>(true, oconfig.getInfo());
            }

            return new TwoValue<>(false, oconfig.getInfo());
        } else {
            //没有设置，那么也认为是过期了的
            return new TwoValue<>(true, null);
        }
    }

    public void remove(TK id) {
        configs.remove(id);
    }

    private int getCacheTime() {
        return getCacheTime.get();
    }

}
