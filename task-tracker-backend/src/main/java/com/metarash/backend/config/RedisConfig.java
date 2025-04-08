package com.metarash.backend.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.distributed.proxy.ProxyManager;

import io.github.bucket4j.redis.redisson.cas.RedissonBasedProxyManager;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.command.CommandAsyncExecutor;
import org.redisson.command.CommandAsyncService;
import org.redisson.connection.ConnectionManager;
import org.redisson.liveobject.core.RedissonObjectBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;


@Configuration
public class RedisConfig {

    @Bean
    public RedissonClient redissonClient() {
        return Redisson.create();
    }

    @Bean
    public CommandAsyncExecutor commandAsyncExecutor(RedissonClient redissonClient) {
        Redisson redisson = (Redisson) redissonClient;
        return new CommandAsyncService(
                redisson.getConnectionManager(),
                new RedissonObjectBuilder(redissonClient),
                RedissonObjectBuilder.ReferenceType.DEFAULT
        );
    }

    @Bean
    public ProxyManager<String> proxyManager(CommandAsyncExecutor commandExecutor) {
        return RedissonBasedProxyManager
                .builderFor(commandExecutor)
                .withExpirationStrategy(
                        ExpirationAfterWriteStrategy
                                .basedOnTimeForRefillingBucketUpToMax(Duration.ofMinutes(1))
                )
                .build();
    }

    @Bean
    public BucketConfiguration defaultBucketConfig() {
        return BucketConfiguration.builder()
                .addLimit(Bandwidth.simple(1000, Duration.ofMinutes(1)))
                .build();
    }

    @Bean
    public Bucket exampleBucket(ProxyManager<String> proxyManager, BucketConfiguration defaultBucketConfig) {
        return proxyManager.builder().build("some-key", defaultBucketConfig);
    }
}

