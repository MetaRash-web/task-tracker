package com.metarash.backend.aspect;

import com.metarash.backend.annotations.RateLimit;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {

    private final ProxyManager<String> proxyManager;
    private final BucketConfiguration bucketConfiguration;

    @Around("@annotation(rateLimit)")
    public Object rateLimitCheck(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        String key = resolveKey(rateLimit.key());
        Bucket bucket = proxyManager.builder().build(key, bucketConfiguration);

        if (bucket.tryConsume(1)) {
            return joinPoint.proceed();
        } else {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Rate limit exceeded");
        }
    }

    private String resolveKey(String keyType) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        return switch (keyType.toLowerCase()) {
            case "ip" -> request.getRemoteAddr();
            case "user" -> {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                yield auth != null && auth.isAuthenticated() ? auth.getName() : "anonymous";
            }
            default -> "global";
        };
    }
}