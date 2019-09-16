package com.github.nut077.docker.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Arrays;

@Log4j2
@EnableCaching
@Configuration
public class CacheConfig {

  @Bean
  public SimpleCacheManager buildSimpleCacheManager() {
    CaffeineCache studentCache = buildCaffeineCache(CacheName.STUDENT, 100);
    CaffeineCache studentsCache = buildCaffeineCache(CacheName.STUDENTS, 10);
    CaffeineCache schoolsCache = buildCaffeineCache(CacheName.SCHOOLS, 10);
    SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
    simpleCacheManager.setCaches(Arrays.asList(studentCache, studentsCache, schoolsCache));
    simpleCacheManager.initializeCaches();
    return simpleCacheManager;
  }

  private CaffeineCache buildCaffeineCache(String name, long maxSize) {
    log.info(() -> "Build CaffeineCache[" + name + "], maximumSize[" + maxSize + "]");
    return new CaffeineCache(name, Caffeine.newBuilder()
            .softValues() // เมื่อ memory ใกล้จะเต็ม gb ก็จะลบ cache ตัวแรกท้ายทิ้ง
            .maximumSize(maxSize) // ค่าสุงสุดที่จะเก็บ cache ไว้
            .expireAfterAccess(Duration.ofHours(1)) // กำหนด record นั้นใน cache ถูก expire หลังจากถูก access เมื่อไร
            .expireAfterWrite(Duration.ofHours(24)) // หลังจากที่ถูกเขียนค่าเข้า cache จะถูก expire เมื่อไร ต่อให้มีคน access มาเท่าไรเมื่อถึง 1 วันแล้วก็จะถูก expire ทิ้ง เพื่อทำการโหลดค่าขึ้นมาใหม่จาก database
            .build());
  }

  public static class CacheName {
    public static final String STUDENT = "STUDENT";
    public static final String STUDENTS = "STUDENTS";
    public static final String SCHOOLS = "SCHOOLS";
  }
}
