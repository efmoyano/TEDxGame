package com.googlecode.javacpp.annotation;

import com.googlecode.javacpp.Generator;
import com.googlecode.javacpp.Pointer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.Buffer;

/**
 * Allows passing and returning Java objects with native functions and raw JNI types.
 * By default, only descendents of {@link Pointer}, direct NIO {@link Buffer},
 * {@link String}, primitive types, and their arrays are allowed as arguments to
 * native functions. We can use this annotation to tell the {@link Generator}
 * that we wish to manipulate the actual raw JNI objects in C++.
 *
 * @see Generator
 *
 * @author Samuel Audet
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface Raw {
    /** If {@code true}, passes the {@code JNIEnv*} as first argument to the native function.
     * @return  */
    boolean withEnv() default false;
}