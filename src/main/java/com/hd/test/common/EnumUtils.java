package com.hd.test.common;


import java.util.HashMap;
import java.util.Map;

/**
 * 枚举工具类
 *
 * @author Sands
 */
public class EnumUtils {

    /**
     * 将实现了EnumValue的枚举转换为Map（key为value，value为枚举）
     *
     * @param evals 枚举的values
     * @param <TV>  值类型
     * @param <TE>  枚举类型
     * @return
     */
    public static <TV, TE extends EnumValue<TV>> Map<TV, TE> getValueMap(TE[] evals) {
//        Map<TV, TE> map = new ConcurrentHashMap<>();
        Map<TV, TE> map = new HashMap<>();
        for (TE eval : evals) {
            map.put(eval.value(), eval);
        }

        if (map.size() < evals.length) {
            throw new IllegalArgumentException("每一个枚举的value值必须是唯一的");
        }

        return map;
    }

    /**
     * 将实现了IntEnumValue的枚举转换为Map（key为value，value为枚举）
     *
     * @param evals 枚举的values
     * @param <TE>  枚举类型
     * @return
     */
    public static <TE extends IntEnumValue> Map<Integer, TE> getIntValueMap(TE[] evals) {
//        Map<Integer, TE> map = new ConcurrentHashMap<>();
        Map<Integer, TE> map = new HashMap<>();
        for (TE eval : evals) {
            map.put(eval.value(), eval);
        }

        if (map.size() < evals.length) {
            throw new IllegalArgumentException("每一个枚举的value值必须是唯一的");
        }

        return map;
    }

}
