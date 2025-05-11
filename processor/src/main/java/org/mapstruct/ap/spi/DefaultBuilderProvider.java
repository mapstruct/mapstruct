/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.lang.model.util.SimpleTypeVisitor6;
import javax.lang.model.util.Types;

/**
 * Default implementation of {@link BuilderProvider}.
 *
 * The default builder provider considers all public static parameterless methods of a {@link TypeMirror}
 * as potential builder creation methods. For each potential builder creation method checks in the return type
 * of the method if there exists a method that returns the initial {@link TypeMirror} if such a combination is found
 * the {@link BuilderInfo} is created with those 2 methods.
 * Example:
 * <pre><code>
 * public class Person {
 *
 *     private final String firstName;
 *     private final String lastName;
 *
 *     private Person(String firstName, String lastName) {
 *         this.firstName = firstName;
 *         this.lastName = lastName;
 *     }
 *
 *     //getters
 *
 *     public static Builder builder() {
 *         return new Builder();
 *     }

 *     public static class Builder {
 *
 *         private String firstName;
 *         private String lastName;
 *
 *         private Builder() {}
 *
 *         //fluent setters
 *
 *         public Person create() {
 *             return new Person( firstName, lastName );
 *         }
 *     }
 * }
 * </code></pre>
 *
 * In the example above, when searching for a builder for the {@code Person} type. The {@code Person#builder} method
 * would be a builder creation candidate. Then the return type of {@code Person#builder}, {@code Builder}, is
 * investigated for a parameterless method that returns {@code Person}. When {@code Builder#create} is found
 * the {@link BuilderInfo} is created with the {@code Person#builder} as a builder creation method and
 * {@code Builder#create} as a build method.
 * <p>
 * <b>IMPORTANT</b>: Types from the {@code java} and {@code javax} packages are excluded from inspection
 *
 * @author Filip Hrisafov
 */
public class DefaultBuilderProvider implements BuilderProvider {

    private static final Pattern JAVA_JAVAX_PACKAGE = Pattern.compile( "^javax?\\..*" );

    protected Elements elementUtils;
    protected Types typeUtils;

    @Override
    public void init(MapStructProcessingEnvironment processingEnvironment) {
        this.elementUtils = processingEnvironment.getElementUtils();
        this.typeUtils = processingEnvironment.getTypeUtils();
    }

    @Override
    public BuilderInfo findBuilderInfo(TypeMirror type) {
        TypeElement typeElement = getTypeElement( type );
        if ( typeElement == null ) {
            return null;
        }

        return findBuilderInfo( typeElement );
    }

    /**
     * Find the {@link TypeElement} for the given {@link TypeMirror}.
     *
     * @param type for which the {@link TypeElement} needs to be found/
     *
     * @return the type element or {@code null} if the {@link TypeMirror} is not a {@link DeclaredType}
     * and the declared type element is not {@link TypeElement}
     *
     * @throws TypeHierarchyErroneousException if the {@link TypeMirror} is of kind {@link TypeKind#ERROR}
     */
    protected TypeElement getTypeElement(TypeMirror type) {
        DeclaredType declaredType = getDeclaredType( type );
        return getTypeElement( declaredType );
    }

    /**
     * Find the {@link TypeElement} for the given {@link DeclaredType}.
     *
     * @param declaredType for which the {@link TypeElement} needs to be found.
     * @return the type element or {@code null} if the declared type element is not {@link TypeElement}
     */
    private TypeElement getTypeElement(DeclaredType declaredType) {
        if ( declaredType == null ) {
            return null;
        }

        return declaredType.asElement().accept(
            new SimpleElementVisitor6<TypeElement, Void>() {
                @Override
                public TypeElement visitType(TypeElement e, Void p) {
                    return e;
                }
            },
            null
        );
    }

    /**
     * Find the {@link DeclaredType} for the given {@link TypeMirror}.
     *
     * @param type for which the {@link DeclaredType} needs to be found.
     * @return the declared or {@code null} if the {@link TypeMirror} is not a {@link DeclaredType}
     * @throws TypeHierarchyErroneousException if the {@link TypeMirror} is of kind {@link TypeKind#ERROR}
     */
    private DeclaredType getDeclaredType(TypeMirror type) {
        if ( type.getKind() == TypeKind.ERROR ) {
            throw new TypeHierarchyErroneousException( type );
        }
        return type.accept(
            new SimpleTypeVisitor6<DeclaredType, Void>() {
                @Override
                public DeclaredType visitDeclared(DeclaredType t, Void p) {
                    return t;
                }
            },
            null
        );
    }

    /**
     * Find the {@link BuilderInfo} for the given {@code typeElement}.
     * <p>
     * The default implementation iterates over all the methods in {@code typeElement} and uses
     * {@link DefaultBuilderProvider#isPossibleBuilderCreationMethod(ExecutableElement, TypeElement)} and
     * {@link DefaultBuilderProvider#findBuildMethods(TypeElement, TypeElement)} to create the
     * {@link BuilderInfo}.
     * <p>
     * The default implementation uses {@link DefaultBuilderProvider#shouldIgnore(TypeElement)} to check if the
     * {@code typeElement} should be ignored.
     * <p>
     * In case there are multiple {@link BuilderInfo} then a {@link MoreThanOneBuilderCreationMethodException} is
     * thrown.
     *
     * @param typeElement the type element for which a builder searched
     * @return the {@link BuilderInfo} or {@code null} if no builder was found for the type
     * {@link DefaultBuilderProvider#findBuildMethods(TypeElement, TypeElement)}
     * @throws MoreThanOneBuilderCreationMethodException if there are multiple builder creation methods
     */
    protected BuilderInfo findBuilderInfo(TypeElement typeElement) {
        return findBuilderInfo( typeElement, true );
    }

    protected BuilderInfo findBuilderInfo(TypeElement typeElement, boolean checkParent) {
        if ( shouldIgnore( typeElement ) ) {
            return null;
        }

        // Builder infos which are determined by a static method on the type itself
        List<BuilderInfo> methodBuilderInfos = new ArrayList<>();
        // Builder infos which are determined by an inner builder class in the type itself
        List<BuilderInfo> innerClassBuilderInfos = new ArrayList<>();

        for ( Element enclosedElement : typeElement.getEnclosedElements() ) {
            if ( ElementKind.METHOD == enclosedElement.getKind() ) {
                ExecutableElement method = (ExecutableElement) enclosedElement;
                BuilderInfo builderInfo = determineMethodBuilderInfo( method, typeElement );
                if ( builderInfo != null ) {
                    methodBuilderInfos.add( builderInfo );
                }
            }
            else if ( ElementKind.CLASS == enclosedElement.getKind() ) {
                if ( !methodBuilderInfos.isEmpty() ) {
                    // Small optimization to not check the inner classes
                    // if we already have at least one builder through a method
                    continue;
                }
                TypeElement classElement = (TypeElement) enclosedElement;
                BuilderInfo builderInfo = determineInnerClassBuilderInfo( classElement, typeElement );
                if ( builderInfo != null ) {
                    innerClassBuilderInfos.add( builderInfo );
                }
            }

        }

        if ( methodBuilderInfos.size() == 1 ) {
            return methodBuilderInfos.get( 0 );
        }
        else if ( methodBuilderInfos.size() > 1 ) {
            throw new MoreThanOneBuilderCreationMethodException( typeElement.asType(), methodBuilderInfos );
        }
        else if ( innerClassBuilderInfos.size() == 1 ) {
            return innerClassBuilderInfos.get( 0 );
        }
        else if ( innerClassBuilderInfos.size() > 1 ) {
            throw new MoreThanOneBuilderCreationMethodException( typeElement.asType(), innerClassBuilderInfos );
        }

        if ( checkParent ) {
            return findBuilderInfo( typeElement.getSuperclass() );
        }

        return null;
    }

    private BuilderInfo determineMethodBuilderInfo(ExecutableElement method,
                                                   TypeElement typeElement) {
        if ( isPossibleBuilderCreationMethod( method, typeElement ) ) {
            TypeElement builderElement = getTypeElement( method.getReturnType() );
            Collection<ExecutableElement> buildMethods = findBuildMethods( builderElement, typeElement );
            if ( !buildMethods.isEmpty() ) {
                return new BuilderInfo.Builder()
                    .builderCreationMethod( method )
                    .buildMethod( buildMethods )
                    .build();
            }
        }

        return null;
    }

    private BuilderInfo determineInnerClassBuilderInfo(TypeElement innerClassElement,
                                                       TypeElement typeElement) {
        if ( innerClassElement.getModifiers().contains( Modifier.PUBLIC )
            && innerClassElement.getModifiers().contains( Modifier.STATIC )
            && innerClassElement.getSimpleName().toString().endsWith( "Builder" ) ) {
            for ( Element element : innerClassElement.getEnclosedElements() ) {
                if ( ElementKind.CONSTRUCTOR == element.getKind() ) {
                    ExecutableElement constructor = (ExecutableElement) element;
                    if ( constructor.getParameters().isEmpty() ) {
                        // We have a no-arg constructor
                        // Now check if we have build methods
                        Collection<ExecutableElement> buildMethods = findBuildMethods( innerClassElement, typeElement );
                        if ( !buildMethods.isEmpty() ) {
                            return new BuilderInfo.Builder()
                                .builderCreationMethod( constructor )
                                .buildMethod( buildMethods )
                                .build();
                        }
                        // If we don't have any build methods
                        // then we can stop since we are only interested in the no-arg constructor
                        return null;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Checks if the {@code method} is a possible builder creation method.
     * <p>
     * The default implementation considers a method as a possible creation method if the following is satisfied:
     * <ul>
     * <li>The method has no parameters</li>
     * <li>It is a {@code public static} method</li>
     * <li>The return type of the {@code method} is not the same as the {@code typeElement}</li>
     * <li></li>
     * </ul>
     *
     * @param method The method that needs to be checked
     * @param typeElement the enclosing element of the method, i.e. the type in which the method is located in
     * @return {@code true} if the {@code method} is a possible builder creation method, {@code false} otherwise
     */
    protected boolean isPossibleBuilderCreationMethod(ExecutableElement method, TypeElement typeElement) {
        return method.getParameters().isEmpty()
            && method.getModifiers().contains( Modifier.PUBLIC )
            && method.getModifiers().contains( Modifier.STATIC )
            && method.getReturnType().getKind() != TypeKind.VOID
            // Only compare raw elements
            // Reason: if the method is a generic method (<T> Holder<T> build()) and the type element is (Holder<T>)
            // then the return type of the method does not match the type of the type element
            && !typeUtils.isSameType(
            typeUtils.erasure( method.getReturnType() ),
            typeUtils.erasure( typeElement.asType() )
        );
    }

    /**
     * Searches for a build method for {@code typeElement} within the {@code builderElement}.
     * <p>
     * The default implementation iterates over each method in {@code builderElement} and uses
     * {@link DefaultBuilderProvider#isBuildMethod(ExecutableElement, DeclaredType, TypeElement)}
     * to check if the method is a build method for {@code typeElement}.
     * <p>
     * The default implementation uses {@link DefaultBuilderProvider#shouldIgnore(TypeElement)} to check if the
     * {@code builderElement} should be ignored, i.e. not checked for build elements.
     * <p>
     * @param builderElement the element for the builder
     * @param typeElement the element for the type that is being built
     * @return the build method for the {@code typeElement} if it exists, or {@code null} if it does not
     * {@code build}
     */
    protected Collection<ExecutableElement> findBuildMethods(TypeElement builderElement, TypeElement typeElement) {
        if ( shouldIgnore( builderElement ) || typeElement == null ) {
            return Collections.emptyList();
        }
        DeclaredType builderType = getDeclaredType( builderElement.asType() );

        if ( builderType == null ) {
            return Collections.emptyList();
        }

        return findBuildMethods( builderElement, builderType, typeElement );
    }

    private Collection<ExecutableElement> findBuildMethods(TypeElement builderElement, DeclaredType builderType,
                                                           TypeElement typeElement) {
        if ( shouldIgnore( builderElement ) ) {
            return Collections.emptyList();
        }

        List<ExecutableElement> builderMethods = ElementFilter.methodsIn( builderElement.getEnclosedElements() );
        List<ExecutableElement> buildMethods = new ArrayList<>();
        for ( ExecutableElement buildMethod : builderMethods ) {
            if ( isBuildMethod( buildMethod, builderType, typeElement ) ) {
                buildMethods.add( buildMethod );
            }
        }

        if ( !buildMethods.isEmpty() ) {
            return buildMethods;
        }

        Collection<ExecutableElement> parentClassBuildMethods = findBuildMethods(
            getTypeElement( builderElement.getSuperclass() ),
            builderType,
            typeElement
        );

        if ( !parentClassBuildMethods.isEmpty() ) {
            return parentClassBuildMethods;
        }

        List<? extends TypeMirror> interfaces = builderElement.getInterfaces();
        if ( interfaces.isEmpty() ) {
            return Collections.emptyList();
        }

        Collection<ExecutableElement> interfaceBuildMethods = new ArrayList<>();

        for ( TypeMirror builderInterface : interfaces ) {
            interfaceBuildMethods.addAll( findBuildMethods(
                getTypeElement( builderInterface ),
                builderType,
                typeElement
            ) );
        }

        return interfaceBuildMethods;
    }

    /**
     * @see #isBuildMethod(ExecutableElement, DeclaredType, TypeElement)
     * @deprecated use {@link #isBuildMethod(ExecutableElement, DeclaredType, TypeElement)} instead
     */
    @Deprecated
    protected boolean isBuildMethod(ExecutableElement buildMethod, TypeElement typeElement) {
        return buildMethod.getParameters().isEmpty() &&
            buildMethod.getModifiers().contains( Modifier.PUBLIC )
            && typeUtils.isAssignable( buildMethod.getReturnType(), typeElement.asType() );
    }

    /**
     * Checks if the {@code buildMethod} is a method that creates the {@code typeElement}
     * as a member of the {@code builderType}.
     * <p>
     * The default implementation considers a method to be a build method if the following is satisfied:
     * <ul>
     * <li>The method has no parameters</li>
     * <li>The method is public</li>
     * <li>The return type of method is assignable to the {@code typeElement}</li>
     * </ul>
     *
     * @param buildMethod the method that should be checked
     * @param builderType the type of the builder in which the {@code buildMethod} is located in
     * @param typeElement the type element that needs to be built
     * @return {@code true} if the {@code buildMethod} is a build method for {@code typeElement}, {@code false}
     * otherwise
     */
    protected boolean isBuildMethod(ExecutableElement buildMethod, DeclaredType builderType, TypeElement typeElement) {
        if ( !buildMethod.getParameters().isEmpty() ) {
            return false;
        }
        if ( !buildMethod.getModifiers().contains( Modifier.PUBLIC ) ) {
            return false;
        }
        TypeMirror buildMethodType = typeUtils.asMemberOf( builderType, buildMethod );
        if ( buildMethodType instanceof ExecutableType ) {
            return typeUtils.isAssignable( ( (ExecutableType) buildMethodType ).getReturnType(), typeElement.asType() );
        }
        return false;
    }

    /**
     * Whether the {@code typeElement} should be ignored, i.e. not used in inspection.
     * <p>
     * The default implementations ignores {@code null} elements and elements that belong to the {@code java} and
     * {@code javax} packages
     * @param typeElement that needs to be checked
     * @return {@code true} if the element should be ignored, {@code false} otherwise
     */
    protected boolean shouldIgnore(TypeElement typeElement) {
        return typeElement == null || JAVA_JAVAX_PACKAGE.matcher( typeElement.getQualifiedName() ).matches();
    }

}
