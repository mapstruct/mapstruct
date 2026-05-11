/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.util.Experimental;

/**
 * Builder provider for Immutables. A custom provider is needed because Immutables creates an implementation of an
 * interface and that implementation has the builder. This implementation would try to find the type created by
 * Immutables and would look for the builder in it. Only types annotated with the
 * {@code org.immutables.value.Value.Immutable} are considered for this discovery.
 *
 * @author Filip Hrisafov
 */
@Experimental("The Immutables builder provider might change in a subsequent release")
public class ImmutablesBuilderProvider extends DefaultBuilderProvider {

    private static final Pattern JAVA_JAVAX_PACKAGE = Pattern.compile( "^javax?\\..*" );
    private static final String IMMUTABLE_FQN = "org.immutables.value.Value.Immutable";

    @Override
protected BuilderInfo findBuilderInfo(TypeElement typeElement) {
    Name name = typeElement.getQualifiedName();
    if (name.length() == 0 || JAVA_JAVAX_PACKAGE.matcher(name).matches()) {
        return null;
    }

    // First look if there is a builder defined in my own type
    BuilderInfo info = findBuilderInfo(typeElement, false);
    if (info != null) {
        return info;
    }

    // Check for a builder in the generated immutable type
    BuilderInfo immutableInfo = findBuilderInfoForImmutables(typeElement);
    if (immutableInfo != null) {
        return immutableInfo;
    }

    // FIX: superclass is TypeMirror, not TypeElement
    TypeMirror superclass = typeElement.getSuperclass();
    if (superclass.getKind() == TypeKind.NONE) {
        return null;
    }

    if (superclass instanceof DeclaredType declaredType) {
        Element superElement = declaredType.asElement();
        if (superElement instanceof TypeElement superTypeElement) {
            return super.findBuilderInfo(superTypeElement);
        }
    }

    return null;
}
protected TypeElement asImmutableElement(TypeElement typeElement) {
    StringBuilder fqName = new StringBuilder();

    Element current = typeElement.getEnclosingElement();

    // FIX: support nested classes properly
    while (current != null && current.getKind() != ElementKind.PACKAGE) {
        fqName.insert(0, ((TypeElement) current).getSimpleName() + "$");
        current = current.getEnclosingElement();
    }

    if (current instanceof PackageElement pkg) {
        fqName.insert(0, pkg.getQualifiedName().toString() + ".");
    }

    fqName.append("Immutable").append(typeElement.getSimpleName());

    // FIX: must pass String
    return elementUtils.getTypeElement(fqName.toString());
}

}
