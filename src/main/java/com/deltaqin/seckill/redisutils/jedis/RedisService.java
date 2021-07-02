package com.deltaqin.seckill.redisutils.jedis;

import com.alibaba.fastjson.JSON;
import com.deltaqin.seckill.redisutils.keyprefix.KeyPrefix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author deltaqin
 * @date 2021/6/13 下午11:16
 */

// 需要使用切面改进
@Service
@Slf4j
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    public Long setNx(String key, String value) {
        Jedis jedis = null;
        Long result = null;
        try{
            jedis = jedisPool.getResource();
            result = jedis.setnx(key, value);
        } catch (Exception e) {
            log.error("setnx {} error : {}", key, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
            //jedisPool.returnResource();
            return result;
        }
    }

    public Long expire(String key, int exTime) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("expire key {} error {}", key, e);
        } finally {
            returnToPool(jedis);
            return result;
        }
    }

    // class 是关键字
    // 设置返回的类型
    public  <T>T get(KeyPrefix keyPrefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = keyPrefix.getPrefix() + key;
            String res = jedis.get(realKey);
            T t = strToBean(res, clazz);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }

    // 默认返回字符串
    public  String get(String key) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.get(key);
        } catch (Exception e) {
            log.error("get : {} error: {}", key, e);
        } finally {
            returnToPool(jedis);
        }
        // 返回语句写在外面，是正常之后才会返回
        return res;
    }

    public String getSet(String key, String val) {
        Jedis jedis = null;
        String old = null;
        try {
            jedis = jedisPool.getResource();
            String set = jedis.getSet(key, val);
        } catch (Exception exception) {
            log.error("getset : {} error: {}" , key, exception);
        } finally {
            returnToPool(jedis);
        }
        return old;
    }

    // 根据key判断超时时间，是同一设置的
    public <T> boolean set(KeyPrefix keyPrefix, String key, T val) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = beanToStr(val);
            if (str == null || str.length() <= 0) {
                return false;
            }
            // 类名+属性名+key
            String realKey = keyPrefix.getPrefix() + key;
            int seconds = keyPrefix.expireSeconds();
            if (seconds <= 0) {
                jedis.set(realKey, str);
            } else {
                jedis.setex(realKey, seconds, str);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    public <T> boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    public boolean delete(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            Long del = jedis.del(realKey);
            return del > 0;
        } finally {
            returnToPool(jedis);
        }
    }

    // 直接将键类型下的所有值全部删除（借助scan先找到所有包含了当前prefix 的键）
    public boolean delete(KeyPrefix prefix) {
        if (prefix == null) return false;
        List<String> keys = scanKeys(prefix.getPrefix());
        if (keys == null || keys.isEmpty()) {
            return true;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // 转换数组的时候需要指定数据类型，否则就会Object的数组
            jedis.del(keys.toArray(new String[0]));
            return true;
        } catch (Exception e) {
            log.error("error delete {}", e);
            return false;
        } finally {
            returnToPool(jedis);
        }
    }


    public Long delete(String key) {
        Jedis jedis = null;
        Long res  = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.del(key);
        } catch (Exception e) {
            log.error("del key: {} error {}", key, e);
        } finally {
            returnToPool(jedis);
        }
        return res;
    }

    public List<String> scanKeys(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<String> keys = new ArrayList<>();
            String course = "0";
            ScanParams sp = new ScanParams();
            sp.match("*" + key + "*");
            sp.count(100);
            do {
                ScanResult<String> scan = jedis.scan(course, sp);
                List<String> result = scan.getResult();
                if (result != null && result.size() > 0) {
                    keys.addAll(result);
                }
                course = scan.getCursor();
            } while (!course.equals("0"));

            return keys;
        } finally {
            returnToPool(jedis);
        }
    }

    public Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realPrefix = prefix.getPrefix() + key;
            return jedis.incr(realPrefix);
        } finally {
            returnToPool(jedis);
        }
    }

    public Long incr(KeyPrefix prefix, String key, int count ) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realPrefix = prefix.getPrefix() + key;
            return jedis.incrBy(realPrefix, count);
        } finally {
            returnToPool(jedis);
        }
    }

    public Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            Long decr = jedis.decr(realKey);
            return decr;
        } finally {
            returnToPool(jedis);
        }
    }
    public Long decr(KeyPrefix prefix, String key, int count) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            Long decr = jedis.decrBy(realKey, count);
            return decr;
        } finally {
            returnToPool(jedis);
        }
    }





    // 注意泛型的写法
    private <T> String beanToStr(T val) {
        if (val == null) return null;
        Class<?> clazz = val.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return "" + val;
        } else if (clazz == String.class) {
            return (String)val;
        } else if (clazz == Long.class || clazz == long.class) {
            return "" + val;
        } else {
            return JSON.toJSONString(val);
        }
    }

    // 基础类型和自定义类型（使用fastjson 的 JSON工具）
    private <T> T strToBean(String res, Class<T> clazz) {
        if (res == null || res.length() <= 0 || clazz == null) return null;
        if (clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(res);
        } else if (clazz == String.class) {
            return (T)res;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T)Long.valueOf(res);
        } else {
            return JSON.toJavaObject(JSON.parseObject(res), clazz);
        }
    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
