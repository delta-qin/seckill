package com.deltaqin.seckill.localcache;

/**
 * @author deltaqin
 * @date 2021/6/14 下午5:26
 */

// 本地缓存操作服务
public interface LocalCacheService {
    // 存缓存
    void setLocalCache(String key, Object val);

    // 取
    Object getFromLocalCache(String key);

}
