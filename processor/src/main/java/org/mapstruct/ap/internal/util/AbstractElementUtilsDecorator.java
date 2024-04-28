/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

import org.mapstruct.ap.spi.TypeHierarchyErroneousException;

import static javax.lang.model.util.ElementFilter.fieldsIn;
import static javax.lang.model.util.ElementFilter.methodsIn;

public abstract class AbstractElementUtilsDecorator implements ElementUtils {

    private final Elements delegate;
    /**
     * The module element when running with the module system,
     * {@code null} otherwise.
     */
    private final Element moduleElement;

    @IgnoreJRERequirement
    AbstractElementUtilsDecorator(ProcessingEnvironment processingEnv, TypeElement mapperElement) {
        this.delegate = processingEnv.getElementUtils();
        if ( SourceVersion.RELEASE_8.compareTo( processingEnv.getSourceVersion() ) >= 0 ) {
            // We are running with Java 8 or lower
            this.moduleElement = null;
        }
        else {
            this.moduleElement = this.delegate.getModuleOf( mapperElement );
        }
    }

    @Override
    @IgnoreJRERequirement
    public PackageElement getPackageElement(CharSequence name) {
        if ( this.moduleElement == null ) {
            return this.delegate.getPackageElement( name );
        }

        // If the module element is not null then we must be running on Java 8+
        return this.delegate.getPackageElement( (ModuleElement) moduleElement, name );
    }

    @Override
    @IgnoreJRERequirement
    public TypeElement getTypeElement(CharSequence name) {
        if ( this.moduleElement == null ) {
            return this.delegate.getTypeElement( name );
        }

        // If the module element is not null then we must be running on Java 8+
        return this.delegate.getTypeElement( (ModuleElement) moduleElement, name );
    }

    @Override
    public Map<? extends ExecutableElement, ? extends AnnotationValue> getElementValuesWithDefaults(
        AnnotationMirror a) {
        return delegate.getElementValuesWithDefaults( a );
    }

    @Override
    public String getDocComment(Element e) {
        return delegate.getDocComment( e );
    }

    @Override
    public boolean isDeprecated(Element e) {
        return delegate.isDeprecated( e );
    }

    @Override
    public Name getBinaryName(TypeElement type) {
        return delegate.getBinaryName( type );
    }

    @Override
    public PackageElement getPackageOf(Element type) {
        return delegate.getPackageOf( type );
    }

    @Override
    public List<? extends Element> getAllMembers(TypeElement type) {
        return delegate.getAllMembers( type );
    }

    @Override
    public List<? extends AnnotationMirror> getAllAnnotationMirrors(Element e) {
        return delegate.getAllAnnotationMirrors( e );
    }

    @Override
    public boolean hides(Element hider, Element hidden) {
        return delegate.hides( hider, hidden );
    }

    @Override
    public boolean overrides(ExecutableElement overrider, ExecutableElement overridden, TypeElement type) {
        return delegate.overrides( overrider, overridden, type );
    }

    @Override
    public String getConstantExpression(Object value) {
        return delegate.getConstantExpression( value );
    }

    @Override
    public void printElements(Writer w, Element... elements) {
        delegate.printElements( w, elements );
    }

    @Override
    public Name getName(CharSequence cs) {
        return delegate.getName( cs );
    }

    @Override
    public boolean isFunctionalInterface(TypeElement type) {
        return delegate.isFunctionalInterface( type );
    }

    @Override
    public List<ExecutableElement> getAllEnclosedExecutableElements(TypeElement element) {
        List<ExecutableElement> enclosedElements = new ArrayList<>();
        element = replaceTypeElementIfNecessary( element );
        addEnclosedMethodsInHierarchy( enclosedElements, new HashSet<>(), element, element );

        return enclosedElements;
    }

    @Override
    public List<VariableElement> getAllEnclosedFields( TypeElement element) {
        List<VariableElement> enclosedElements = new ArrayList<>();
        element = replaceTypeElementIfNecessary( element );
        addEnclosedFieldsInHierarchy( enclosedElements, element, element );

        return enclosedElements;
    }

    private void addEnclosedMethodsInHierarchy(List<ExecutableElement> alreadyAdded,
                                               Collection<String> alreadyVisitedElements,
                                               TypeElement element,
                                               TypeElement parentType) {
        if ( element != parentType ) { // otherwise the element was already checked for replacement
            element = replaceTypeElementIfNecessary( element );
        }

        if ( element.asType().getKind() == TypeKind.ERROR ) {
            throw new TypeHierarchyErroneousException( element );
        }

        if ( !alreadyVisitedElements.add( element.getQualifiedName().toString() ) ) {
            // If we already visited the element we should not go into it again.
            // This can happen when diamond inheritance is used with interfaces
            return;
        }
        addMethodNotYetOverridden( alreadyAdded, methodsIn( element.getEnclosedElements() ) );

        if ( hasNonObjectSuperclass( element ) ) {
            addEnclosedMethodsInHierarchy(
                alreadyAdded,
                alreadyVisitedElements,
                asTypeElement( element.getSuperclass() ),
                parentType
            );
        }

        for ( TypeMirror interfaceType : element.getInterfaces() ) {
            addEnclosedMethodsInHierarchy(
                alreadyAdded,
                alreadyVisitedElements,
                asTypeElement( interfaceType ),
                parentType
            );
        }

    }

    /**
     * @param alreadyCollected methods that have already been collected and to which the not-yet-overridden methods will
     *                         be added
     * @param methodsToAdd     methods to add to alreadyAdded, if they are not yet overridden by an element in the list
     */
    private void addMethodNotYetOverridden(List<ExecutableElement> alreadyCollected,
                                           List<ExecutableElement> methodsToAdd) {
        List<ExecutableElement> safeToAdd = new ArrayList<>( methodsToAdd.size() );
        for ( ExecutableElement toAdd : methodsToAdd ) {
            if ( isNotPrivate( toAdd ) && isNotObjectEquals( toAdd )
                && methodWasNotYetOverridden( alreadyCollected, toAdd ) ) {
                safeToAdd.add( toAdd );
            }
        }

        alreadyCollected.addAll( 0, safeToAdd );
    }

    /**
     * @param executable the executable to check
     * @return {@code true}, iff the executable does not represent {@link java.lang.Object#equals(Object)} or an
     * overridden version of it
     */
    private boolean isNotObjectEquals(ExecutableElement executable) {
        if ( executable.getSimpleName().contentEquals( "equals" ) && executable.getParameters().size() == 1
            && asTypeElement( executable.getParameters().get( 0 ).asType() ).getQualifiedName().contentEquals(
            "java.lang.Object"
        ) ) {
            return false;
        }
        return true;
    }

    /**
     * @param alreadyCollected the list of already collected methods of one type hierarchy (order is from sub-types to
     *                         super-types)
     * @param executable       the method to check
     * @return {@code true}, iff the given executable was not yet overridden by a method in the given list.
     */
    private boolean methodWasNotYetOverridden(List<ExecutableElement> alreadyCollected,
                                              ExecutableElement executable) {
        for ( ListIterator<ExecutableElement> it = alreadyCollected.listIterator(); it.hasNext(); ) {
            ExecutableElement executableInSubtype = it.next();
            if ( executableInSubtype == null ) {
                continue;
            }
            if ( delegate.overrides( executableInSubtype, executable, getParentType( executableInSubtype ) ) ) {
                return false;
            }
            else if ( delegate.overrides( executable, executableInSubtype, getParentType( executable ) ) ) {
                // remove the method from another interface hierarchy that is overridden by the executable to add
                it.remove();
                return true;
            }
        }

        return true;
    }

    private TypeElement getParentType(ExecutableElement executableElement) {
        return (TypeElement) executableElement.getEnclosingElement();
    }

    private void addEnclosedFieldsInHierarchy( List<VariableElement> alreadyAdded,
                                               TypeElement element, TypeElement parentType) {
        if ( element != parentType ) { // otherwise the element was already checked for replacement
            element = replaceTypeElementIfNecessary( element );
        }

        if ( element.asType().getKind() == TypeKind.ERROR ) {
            throw new TypeHierarchyErroneousException( element );
        }

        addFields( alreadyAdded, fieldsIn( element.getEnclosedElements() ) );

        if ( hasNonObjectSuperclass( element ) ) {
            addEnclosedFieldsInHierarchy(
                alreadyAdded,
                asTypeElement( element.getSuperclass() ),
                parentType
            );
        }
    }

    private static void addFields(List<VariableElement> alreadyCollected, List<VariableElement> variablesToAdd) {
        List<VariableElement> safeToAdd = new ArrayList<>( variablesToAdd.size() );
        safeToAdd.addAll( variablesToAdd );

        alreadyCollected.addAll( 0, safeToAdd );
    }

    /**
     * @param element the type element to check
     * @return {@code true}, iff the type has a super-class that is not java.lang.Object
     */
    private boolean hasNonObjectSuperclass(TypeElement element) {
        if ( element.getSuperclass().getKind() == TypeKind.ERROR ) {
            throw new TypeHierarchyErroneousException( element );
        }

        return element.getSuperclass().getKind() == TypeKind.DECLARED
            && !asTypeElement( element.getSuperclass() ).getQualifiedName().toString().equals( "java.lang.Object" );
    }

    /**
     * @param mirror the type positionHint
     * @return the corresponding type element
     */
    private TypeElement asTypeElement(TypeMirror mirror) {
        return (TypeElement) ( (DeclaredType) mirror ).asElement();
    }

    /**
     * @param executable the executable to check
     * @return {@code true}, iff the executable does not have a private modifier
     */
    private boolean isNotPrivate(ExecutableElement executable) {
        return !executable.getModifiers().contains( Modifier.PRIVATE );
    }

    protected abstract TypeElement replaceTypeElementIfNecessary(TypeElement element);

}
