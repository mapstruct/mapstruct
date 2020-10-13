/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import java.io.Writer;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class AbstractElementUtilsDecorator implements ElementUtils {

    private final Elements delegate;

    AbstractElementUtilsDecorator(ProcessingEnvironment processingEnv) {
        this.delegate = processingEnv.getElementUtils();
    }

    @Override
    public PackageElement getPackageElement(CharSequence name) {
        return delegate.getPackageElement( name );
    }

    @Override
    public TypeElement getTypeElement(CharSequence name) {
        return delegate.getTypeElement( name );
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
}
