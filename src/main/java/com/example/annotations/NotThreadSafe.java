package com.example.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识线程不安全
 */
@Target(ElementType.TYPE) // Class, interface (including annotation interface), enum, or record declaration
@Retention(RetentionPolicy.SOURCE) // Annotations are to be discarded by the compiler.
public @interface NotThreadSafe {
    String value() default "";
}