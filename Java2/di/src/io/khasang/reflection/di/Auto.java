package io.khasang.reflection.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Target(value = {ElementType.FIELD, ElementType.METHOD})
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Auto {
//    boolean isRequired; // но так нельзя
//    boolean isRequired = false; // но так нельзя
    boolean isRequired() default false;
}
