package org.mapstruct.ap.test.nullcheck.jspecify.annotation;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;

/**
 * Array annotation positions are not intuitive. This tests checks that mapstruct adds them in order.
 * <a href="https://www.oracle.com/technical-resources/articles/java/ma14-architect-annotations.html">
 *     type in use annotations</a>
 */
@Mapper
@NullMarked
public interface JSpecifyAnnotationArrayMapper {

    // The NonNull[Nullable] fromStringArray Nullable[NonNull]
    @Nullable Long[] fromStringArray(String @Nullable [] s);

    // The NonNull[NonNull][Nullable] fromStringArray2 Nullable[Nullable][NonNull]
    Long[] @Nullable [] fromStringArray2(@Nullable String @Nullable [][] s);

    // The NonNull[NonNull][NonNull][Nullable] fromStringArray3 Nullable[Nullable][Nullable][NonNull]
    Long [] @Nullable [][] fromStringArray3(@Nullable String @Nullable [][] @Nullable [] s);

    // The NonNull[Nullable][NonNull][Nullable] fromStringArray3 NonNull[Nullable][Nullable][NonNull]
    @Nullable Long [] @Nullable [][] fromStringArray3V2(@Nullable String [][] @Nullable [] s);
}
