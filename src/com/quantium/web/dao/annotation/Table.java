package com.quantium.web.dao.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by FREEMAN on 24.02.15.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {

    String value();

    String engine() default "InnoDB";

    String charset() default "utf8";

    int autoIncrement() default 0;

}
