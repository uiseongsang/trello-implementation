package com.winner.trelloimplementation;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    String username() default "testuser";

    String nickname() default "testnick";

    String password() default "testpass";

    String email() default "test@test.com";

    String introduction() default "testintro";
}