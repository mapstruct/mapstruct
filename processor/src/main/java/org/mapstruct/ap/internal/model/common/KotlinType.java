/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.ElementFilter;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Jakub Wójcik
 */
public class KotlinType {
    private static final String KOTLIN_ANNOTATION = "kotlin.Metadata";
    private static final Pattern COMPONENT_METHOD_PATTERN = Pattern.compile( "component\\d+" );

    private final Type type;
    private final long componentMethodCount;

    private KotlinType(Type type) {
        this.type = type;
        componentMethodCount = countComponentMethods();
    }

    /**
     * Recognizes Kotlin classes by the annotation {@value KOTLIN_ANNOTATION},
     * that is present on any class file produced by the Kotlin compiler.
     *
     * @return A kotlin type or <code>null</code>.
     * @see <a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-metadata/">@Metadata documentation</a>
     */
    public static KotlinType of(Type type) {
        if ( isKotlinClass( type ) ) {
            return new KotlinType( type );
        }
        return null;
    }

    private static boolean isKotlinClass(Type type) {
        return type.getTypeElement().getAnnotationMirrors().stream()
            .anyMatch(
                annotationMirror -> KOTLIN_ANNOTATION.equals( annotationMirror.getAnnotationType().toString() )
            );
    }

    /**
     * Data classes are recognized by the presence of <code>componentN()</code> methods as described in
     * <a href="https://kotlinlang.org/docs/data-classes.html">the documentation about data classes</a>.
     *
     * @return Whether the class is <i>likely</i> a data class.
     */
    public boolean isDataClass() {
        return componentMethodCount > 0;
    }

    /**
     * @return The constructors suitable to create a Kotlin data class.
     * <br>
     * <br>
     * Only constructors with the same number of parameters
     * as the number of <code>componentN()</code> methods are returned.
     */
    public List<ExecutableElement> getDataClassConstructors() {
        return ElementFilter.constructorsIn( type.getTypeElement().getEnclosedElements() )
            .stream()
            .filter(
                constructor -> constructor.getParameters().size() == componentMethodCount
            )
            .collect( Collectors.toList() );
    }

    private long countComponentMethods() {
        return ElementFilter.methodsIn( type.getTypeElement().getEnclosedElements() )
            .stream()
            .filter(
                method -> COMPONENT_METHOD_PATTERN.matcher( method.getSimpleName() ).matches()
            )
            .count();
    }
}
