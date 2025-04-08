package com.metarash.backend.aspect;

import com.metarash.backend.exceptionHandler.UnauthorizedException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthenticationCheckAspect {

    @Before("execution(* com.metarash.backend.controller.TaskController.*(..)) || " +
            "execution(* com.metarash.backend.controller.UserController.getCurrentUser(..))")
    public void checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Unauthorized access");
        }
    }

}
