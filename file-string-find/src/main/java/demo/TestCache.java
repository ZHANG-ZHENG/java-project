package demo;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;

public class TestCache {
    public static void main(String[] args) {
        // 初始化缓存，设置了 1 分钟的写过期，100 的缓存最大个数
        Cache<Integer, Integer> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();

        int key = 1;
        // 使用 getIfPresent 方法从缓存中获取值。如果缓存中不存指定的值，则方法将返回 null
        System.out.println("不存在值，返回null：" + cache.getIfPresent(key));

        // 也可以使用 get 方法获取值，该方法将一个参数为 key 的 Function 作为参数传入。
        // 如果缓存中不存在该 key 则该函数将用于提供默认值，该值在计算后插入缓存中：
        System.out.println("返回默认值：" + cache.get(key, a -> 2));

        // 校验 key 对应的 value 是否插入缓存中
        System.out.println("返回key对应的value：" + cache.getIfPresent(key));

        // 手动 put 数据填充缓存中
        int value = 2;
        cache.put(key, value);

        // 使用 getIfPresent 方法从缓存中获取值。如果缓存中不存指定的值，则方法将返回 null
        System.out.println("返回key对应的value：" + cache.getIfPresent(key));

        // 移除数据，让数据失效
        cache.invalidate(key);
        System.out.println("返回key对应的value：" + cache.getIfPresent(key));
    }
}
