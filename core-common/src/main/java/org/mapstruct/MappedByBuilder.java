package org.mapstruct;

import org.mapstruct.util.Experimental;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
@Experimental
public @interface MappedByBuilder {
  Class builderClass();

  String buildMethod() default "build";

  String staticBuildMethod() default "builder";
}
