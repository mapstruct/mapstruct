/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.lombok;

import org.mapstruct.ap.spi.DefaultAccessorNamingStrategy;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import java.util.List;

/**
 * LombokBuilderAccessorNamingStrategy is an extension of a default naming strategy
 * that recognizes lombok mapstruct adders defined by a default @lombok.Singular annotation.
 * Lombok adders don't have the 'add' prefix OOTB, an adder is herein recognized by
 * the presence of another builder method with an 's' suffix.
 *
 * @see <a href="https://projectlombok.org/features/BuilderSingular">Lombok @Singular</a>
 */
public class LombokBuilderAccessorNamingStrategy extends DefaultAccessorNamingStrategy {

    private boolean isLombokBuilderAdder(ExecutableElement method) {
        // check that the method is defined in a lombok Builder class
        Element enclosingElement = method.getEnclosingElement();
        if (enclosingElement == null || !enclosingElement.getSimpleName().toString().endsWith( "Builder" )) {
            return false;
        }
        Element parentElement = enclosingElement.getEnclosingElement();
        if (parentElement == null || (enclosingElement.getKind() != ElementKind.CLASS
                && !enclosingElement.getKind().name().equals( "RECORD" ))) {
            return false;
        }
        // check if there is a plural setter for this adder in the builder
        List<? extends Element> enclosedElements = enclosingElement.getEnclosedElements();
        String plural1 = method.getSimpleName().toString() + "s";
        for (Element enclosedElement : enclosedElements) {
            if (!(enclosedElement instanceof ExecutableElement)) {
                continue;
            }
            ExecutableElement setterMethod = (ExecutableElement) enclosedElement;
            String name = enclosedElement.getSimpleName().toString();
            if (plural1.equals( name )) {
                if (setterMethod.getParameters().size() != 1) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    protected boolean isFluentSetter(ExecutableElement method) {
        return super.isFluentSetter( method ) &&
                !isLombokBuilderAdder( method );
    }

    public boolean isAdderMethod(ExecutableElement method) {
        return super.isAdderMethod( method ) || isLombokBuilderAdder( method );
    }
}
