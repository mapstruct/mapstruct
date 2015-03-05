package org.mapstruct.ap.test.bugs._new;

import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Qualifier
@java.lang.annotation.Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface DoubleMultiplier {
}
