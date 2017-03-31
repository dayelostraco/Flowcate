package com.notiflowcate.repository;

import com.notiflowcate.model.entity.Application;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "applicationRepository")
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Override
    @Caching(evict = {
            @CacheEvict(value = "applications", key = "#p0.apiKey"),
            @CacheEvict(value = "applications", key = "'all'")
    })
    <S extends Application> S save(S application);

    @Cacheable(value = "applications", key = "'all'")
    @Query("SELECT a FROM Application a WHERE a.deleted IS NULL")
    List<Application> findAllApplications();

    @Cacheable(value = "applications", key = "#p0")
    @Query("SELECT a FROM Application a WHERE a.apiKey = :apiKey")
    Application findByApiKey(@Param("apiKey") String apiKey);

    @Cacheable(value = "applications", key = "#p0")
    @Query("SELECT a FROM Application a WHERE a.apiKey = :apiKey AND a.active = true")
    Application findActiveApplicationByApiKey(@Param("apiKey") String apiKey);

    @Cacheable(value = "applications", key = "#p0")
    @Query("SELECT a FROM Application a WHERE a.applicationName = :name AND a.active = true AND a.deleted IS NULL")
    Application findActiveApplicationByName(@Param("name") String name);
}
