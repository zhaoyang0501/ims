package com.hsae.ims.interceptor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.*;
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AvoidDuplicateSubmission {
    boolean needSaveToken() default false;
    boolean needRemoveToken() default false;
}