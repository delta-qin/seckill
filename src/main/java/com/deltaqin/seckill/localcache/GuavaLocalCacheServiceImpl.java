package com.deltaqin.seckill.localcache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author deltaqin
 * @date 2021/6/14 下午5:38
 */

// 使用
@Service
public class GuavaLocalCacheServiceImpl implements LocalCacheService {

    private Cache<String, Object> cache = null;

    // bean加载的时候优先执行这个方法
    // Java中该注解的说明：@PostConstruct该注解被用来修饰一个非静态的void（）方法。
    // 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次。
    // PostConstruct在构造函数之后执行，init（）方法之前执行。
    //
    //通常我们会是在Spring框架中使用到@PostConstruct注解 该注解的方法在整个Bean初始化中的执行顺序：
    //Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)
    @PostConstruct
    public void init() {
        CacheBuilder.newBuilder().initialCapacity(10).maximumSize(100).expireAfterWrite(60, TimeUnit.SECONDS).build();
    }

    @Override
    public void setLocalCache(String key, Object val) {
        cache.put(key, val);
    }

    @Override
    public Object getFromLocalCache(String key) {
        return cache.getIfPresent(key);
    }
}
