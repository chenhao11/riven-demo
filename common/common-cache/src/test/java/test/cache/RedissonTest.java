package test.cache;

import club.cache.redisson.RedissonConfiguration;
import club.cache.redisson.controller.model.BloomEntry;
import club.cache.redisson.controller.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.*;
import org.redisson.client.codec.LongCodec;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.BitSet;
import java.util.concurrent.ExecutionException;

/**
 * @author riven
 * @date 2021-07-07 14:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedissonConfiguration.class)
public class RedissonTest {

    public static final String BUCKET_TEST_KEY = "BUCKET_TEST_KEY";

    @Resource
    private RedissonClient client;

    @Test
    public void bucketTest() {
        RBucket<Student> rBucket = client.getBucket(BUCKET_TEST_KEY);
        rBucket.set(new Student("riven", 24));
        System.out.println(rBucket.get());
    }

    /**
     * 地理空间对象桶
     */
    @Test
    public void geoTest() {
        //计算两个地理位置之间的距离
        RGeo<String> geo = client.getGeo("test");
        geo.add(new GeoEntry(13.361389, 38.115556, "Palermo"),
                new GeoEntry(15.087269, 37.502669, "Catania"));
        geo.addAsync(37.618423, 55.751244, "Moscow");
        Double distance = geo.dist("Moscow", "Catania", GeoUnit.KILOMETERS);
        System.out.println(distance);
//        geo.hashAsync("Palermo", "Catania");
//        Map<String, GeoPosition> positions = geo.pos("test2", "Palermo", "test3", "Catania", "test1");
//        List<String> cities = geo.radius(15, 37, 200, GeoUnit.KILOMETERS);
//        cities.forEach(System.out::println);
//        Map<String, GeoPosition> citiesWithPositions = geo.radiusWithPosition(15, 37, 200, GeoUnit.KILOMETERS);

    }

    /**
     * BitSet
     * 需要注意的是RBitSet的大小受Redis限制，最大长度为4294967295（40亿)
     */
    @Test
    public void bitSetTest() throws ExecutionException, InterruptedException {
        RBitSet set = client.getBitSet("simpleBitset");
        set.set(1, true);
//        set.set(1819, true);
        //set.clear(1819);
//        set.clear();

        //获取下标为0的位置的元素值
        System.out.println(set.get(0));
        System.out.println(set.get(1));
        System.out.println(set.get(1813));
        //总共多少位
        System.out.println(set.size());
        //已经占了多少位
        System.out.println(set.length());


        //把两个set做或运算（与运算、异或运算)
        RBitSet set2 = client.getBitSet("simpleBitset2");
        set2.set(2);
        set.or("simpleBitset2");
        System.out.println(set.get(0));
        System.out.println(set.get(2));
        //set.and("simpleBitset2");
        //set.xor("simpleBitset2");

        //转为Bitset,使用BitSet的基本操作
        BitSet bitSet = set.asBitSet();

    }

    /**
     * 对比set和setAsync的效率
     */
    @Test
    public void bitSet2Test() {
        //用时49826
        RBitSet bitSet = client.getBitSet("bitSetTime3");
        int number = 1000000;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < number; i++) {
            if (i % 2 == 0) {
                bitSet.set(i);
            }
        }
        System.out.println(System.currentTimeMillis() - startTime);
        //用时3021
//        RBitSet bitSet = client.getBitSet("bitSetTim4");
//        int number = 1000000;
//        long startTime = System.currentTimeMillis();
//        for (int i = 0; i < number; i++) {
//            if (i % 2 == 0) {
//                bitSet.setAsync(i);
//            }
//        }
//        System.out.println(System.currentTimeMillis() - startTime);
    }

    /**
     * 原子整长型
     */
    @Test
    public void Test() {

        RAtomicLong atomicLong = client.getAtomicLong("myAtomicLong");
//        atomicLong.set(3);
        if (atomicLong.compareAndSet(3, 4)) {
            System.out.println(atomicLong.get());
        }
//        System.out.println(atomicLong.incrementAndGet());
        atomicLong.get();
    }

    /**
     * 布隆过滤器
     */
    @Test
    public void bloomTest() {
        RBloomFilter<BloomEntry> bloomFilter = client.getBloomFilter("sample");
// 初始化布隆过滤器，预计统计元素数量为55000000，期望误差率为0.03
        bloomFilter.tryInit(55000000L, 0.03);
        bloomFilter.add(BloomEntry.of("field1Value", "field2Value"));
        bloomFilter.add(BloomEntry.of("field5Value", "field8Value"));
        bloomFilter.contains(BloomEntry.of("field1Value", "field8Value"));
    }

    /**
     * 基数估计算法
     */
    @Test
    public void hypeLogLogTest() {
        RHyperLogLog<Integer> log = client.getHyperLogLog("log");
        log.add(1);
        log.add(2);
        log.add(3);
        log.add(2);
        System.out.println(log.count());
    }

    /**
     * 限流器
     */
    @Test
    public void rateLimitTest() {
//        RRateLimiter limiter = client.getRateLimiter("limit" + "17853146479");
//        limiter.trySetRate(RateType.PER_CLIENT, 1, 10, RateIntervalUnit.SECONDS);
//        if (limiter.tryAcquire()) {
//            System.out.println("发短信");
//        }
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        for (int i = 0; i < 10; i++) {
//            executorService.submit(() -> {
//                try {
//                    limiter.acquire();
//                    System.out.println("线程" + Thread.currentThread().getId() + "进入数据区：" + System.currentTimeMillis());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//        }
        int i = 0;
        while (true) {
            System.out.println(i++);
            sendMsg();
        }
    }

    @Test
    public void sendMsg() {
        RRateLimiter limiter = client.getRateLimiter("limit" + "17853146477");
//        limiter.clearExpire();
        limiter.trySetRate(RateType.OVERALL, 1, 10, RateIntervalUnit.SECONDS);
        if (limiter.tryAcquire()) {
            System.out.println("发短信1");
        }

//        RRateLimiter limiter = client.getRateLimiter("limit" + "17853146478");
//        limiter.trySetRate(RateType.OVERALL, 1, 10, RateIntervalUnit.SECONDS);
//        if (limiter.tryAcquire()) {
//            System.out.println("发短信2");
//        }
    }

    /******************************** 分布式集合 ****************************/

    @Test
    public void mapTest() throws ExecutionException, InterruptedException {
        RMap<String, String> map = client.getMap("mapDemo8", LongCodec.INSTANCE);
//        Long firstTime = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++) {
//            map.fastPutAsync(String.valueOf(i), String.valueOf(i));
//        }
//        System.out.println(System.currentTimeMillis() - firstTime);

//        RMap<String, String> map = client.getMap("mapDemo5");
//        Long firstTime = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++) {
//            map.fastPut(String.valueOf(i), String.valueOf(i));
//        }
//        System.out.println(System.currentTimeMillis() - firstTime);

        map.put("key1", "1");

        System.out.println(map.put("key1", "2"));
        //join()才是返回异步的值
        System.out.println(map.putAsync("key2", "3").join());
//        //如果没有这个key的话才会赋值，不会覆盖
//        map.putIfAbsent("key1", "2");
//        map.remove("key1");
//        System.out.println(map.fastPut("key1", "4"));
//        System.out.println(map.get("key1"));
    }
}
