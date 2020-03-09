package com.hd.test.cache;

import com.hd.test.common.LocalCacheManager;
import com.hd.test.db.entity.User;
import com.hd.test.db.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserCache {

    private final LocalCacheManager<String, User> caches;
    private final UserService userService;

    public UserCache(UserService userService) {
        this.userService = userService;
        this.caches = new LocalCacheManager<>(this::getCacheTime);
    }

    public User selectById(long id) {
        return selectById(String.valueOf(id));
    }

    public User selectById(String id) {
        return caches.get(getKey(id), key -> userService.selectById(String.valueOf(id)));
    }

    private String getKey(String id) {
        return "user:" + String.valueOf(id);
    }

    /**
     *  由于定时任务的周期是一分钟一次。
     * @return
     */
    private int getCacheTime() {
        return 60;
    }
}
