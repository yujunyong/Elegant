package org.elegant.annotation.test;

import org.springframework.boot.test.autoconfigure.jooq.JooqTest;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@JooqTest
public @interface RepositoryTest {
}
