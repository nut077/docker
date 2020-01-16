package com.github.nut077.docker.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.nut077.docker.component.property.CaffeineCacheProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;

@Log4j2
@EnableCaching
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class CaffeineCacheConfig {

  private final CaffeineCacheProperty props;

  @Bean
  public SimpleCacheManager buildSimpleCacheManager() {
    CaffeineCache schoolCache = buildCaffeineCache(CacheName.SCHOOL, props.getSchoolMaxSize());
    CaffeineCache studentCache = buildCaffeineCache(CacheName.STUDENT, props.getStudentMaxSize());
    SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
    simpleCacheManager.setCaches(Arrays.asList(schoolCache, studentCache));
    simpleCacheManager.initializeCaches();
    return simpleCacheManager;
  }

  private CaffeineCache buildCaffeineCache(String name, long maxSize) {
    log.info(() -> "Build CaffeineCache[" + name + "], maximumSize[" + maxSize + "]");
    return new CaffeineCache(
      name,
      Caffeine.newBuilder()
        .softValues() // ถ้า garbage collection พบว่า memory ใกล้เต็ม จะทำการลบ value ที่อยู่ใน cache นั้นทิ้ง
        .maximumSize(maxSize) // ค่าสูงสุดที่จะเก็บ cache สมมติว่าให้เก็บแค่ 10 ถ้า ตัวที่ 11 เข้ามา ตัวที่ 1 จะถูกลบออกแล้วแทนที่ด้วยตัวที่ 11 ที่เข้ามาใหม่
        .expireAfterAccess(props.getExpireAfterAccess()) // กำหนดว่า record นั้นๆ ใน cache ถูก expire หลังจากถูก access เมื่อไร
        .expireAfterWrite(props.getExpireAfterWrite()) // คล้ายๆกับตัวบน ใช้คู่กับตัวบน หลังจากที่เขียนค่าเข้า cache จะถูก expire เมื่อไร set ไว้ 1 วัน หมายความว่า ต่อให้มีคน access เข้ามาเท่าไรก็ตาม แต่ถ้าครบ 1 วันเมื่อไรก็จะถูก expire ทิ้ง เพื่อโหลดค่ามาใหม่จาก database
        .build()
    );
  }

  @Scheduled(cron = "0 0 0 * * *") // ทุกๆ เที่ยงคืนให้ทำการเคลียร์ cache
  @Caching(
    evict = {
      @CacheEvict(cacheNames = CacheName.SCHOOL, allEntries = true),
      @CacheEvict(cacheNames = CacheName.STUDENT, allEntries = true)
    }
  )
  public void evictAll() {
    log.info(() -> "Cache evict all");
  }

  public static class CacheName {
    public static final String SCHOOL = "SCHOOL";
    public static final String STUDENT = "STUDENT";

    private CacheName() {
    }
  }
}
