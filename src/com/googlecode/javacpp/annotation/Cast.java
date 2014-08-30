package com.googlecode.javacpp.annotation;

import com.googlecode.javacpp.Generator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates a type cast required on the argument to satisfy the native compiler.
 * When used with {@link Adapter} a second cast can also be specified making it
 * possible to apply a cast to both the argument and the adapter, in this order.
 * <p>
 * At the moment, {@link Generator} makes use of the simple C-style cast. If one
 * requires a different kind of type conversion, such as the {@code dynamic_cast}
 * operator, those can be accessed as if they were functions (with the {@link Name}
 * annotation to specify the type) because they have the same syntax.
 *
 * @see Generator
 *
 * @author Samuel Audet
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
public @interface Cast {

    /**
     *
     * @return
     */
    String[] value();
}