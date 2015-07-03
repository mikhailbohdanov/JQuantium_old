package com.quantium.web.dao.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by FREEMAN on 24.02.15.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Row {

    String name() default "";

    int length() default 10;
    boolean unsigned() default false;
    boolean autoIncrement() default false;

    boolean notNull() default false;
    String defaultValue() default "";

    boolean primary() default false;
    String unique() default "";

}

