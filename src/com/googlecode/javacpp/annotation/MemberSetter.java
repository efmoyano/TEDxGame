package com.googlecode.javacpp.annotation;

import com.googlecode.javacpp.Generator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation indicating that a method should behave like a member setter.
 * However, a pair of methods with the same name, one with a return value, the
 * other without, but with the same number of parameters, plus 1, are recognized
 * as a member getter/setter pair even without annotation. This behavior can be
 * changed by annotating the methods with the {@link Function} annotation.
 * <p>
 * A member setter must return no value, or its own {@link Class} as return type,
 * while its number of parameters must be greater than 0. The assigned value is
 * assumed to be a member variable, but anything that follows the same syntax as
 * the assignment of a member variable could potentially work with this annotation.
 * All but the last argument are considered as indices to access a member array.
 *
 * @see Generator
 *
 * @author Samuel Audet
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MemberSetter { }