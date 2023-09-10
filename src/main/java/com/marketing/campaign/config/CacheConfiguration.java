package com.marketing.campaign.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.marketing.campaign.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.marketing.campaign.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.marketing.campaign.domain.User.class.getName());
            createCache(cm, com.marketing.campaign.domain.Authority.class.getName());
            createCache(cm, com.marketing.campaign.domain.User.class.getName() + ".authorities");
            createCache(cm, com.marketing.campaign.domain.Campaign.class.getName());
            createCache(cm, com.marketing.campaign.domain.Campaign.class.getName() + ".events");
            createCache(cm, com.marketing.campaign.domain.Segment.class.getName());
            createCache(cm, com.marketing.campaign.domain.Segment.class.getName() + ".segmentFilters");
            createCache(cm, com.marketing.campaign.domain.Template.class.getName());
            createCache(cm, com.marketing.campaign.domain.Template.class.getName() + ".templateParams");
            createCache(cm, com.marketing.campaign.domain.TemplateParam.class.getName());
            createCache(cm, com.marketing.campaign.domain.TemplateParam.class.getName() + ".templates");
            createCache(cm, com.marketing.campaign.domain.Events.class.getName());
            createCache(cm, com.marketing.campaign.domain.FilterMetadata.class.getName());
            createCache(cm, com.marketing.campaign.domain.FilterMetadata.class.getName() + ".filterPossibleValues");
            createCache(cm, com.marketing.campaign.domain.FilterSourceMapping.class.getName());
            createCache(cm, com.marketing.campaign.domain.FilterPossibleValue.class.getName());
            createCache(cm, com.marketing.campaign.domain.SegmentFilter.class.getName());
            createCache(cm, com.marketing.campaign.domain.SegmentFilter.class.getName() + ".values");
            createCache(cm, com.marketing.campaign.domain.ApprovalAuthority.class.getName());
            createCache(cm, com.marketing.campaign.domain.ApprovalStatus.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
