package com.hd.test.service;

import com.hd.test.common.ZCollectionUtils;
import com.hd.test.common.ZStringUtils;
import com.hd.test.entity.BaseEntity;

import java.lang.reflect.ParameterizedType;
import java.util.*;

public class BaseService<T extends BaseEntity> {

    private final Map<T, String> data = new HashMap<>();

    public String getKey(T entity) {
        return entity.getClass().getSimpleName();
    }

    public void save(T entity) {
        data.put(entity, getKey(entity));
    }

    public List<T> getData() {
        return getAll(getClazz());
    }

    public String getClazz() {
        return ((Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName();
    }

    public int saveAll(Collection<T> list) {
        int count = 0;
        if (ZCollectionUtils.isNotEmpty(list)) {
            for (T v : list) {
                count++;
                save(v);
            }
        }
        return count;
    }

    public List<T> getAll(String name) {
        List<T> list = new ArrayList<>();
        for (T t : data.keySet()) {
            if (ZStringUtils.isEquals(data.get(t), name)) {
                list.add(t);
            }
        }
        return list;
    }

    /**
     * 删除
     *
     * @param key
     * @return
     */
    public boolean remove(String key) {
        return data.remove(key) != null;
    }

    /**
     * 删除
     *
     * @return
     */
    public boolean removeAll() {
        return data.entrySet().removeIf(a -> ZStringUtils.isEquals(getClazz(),a.getValue()));
    }
}
