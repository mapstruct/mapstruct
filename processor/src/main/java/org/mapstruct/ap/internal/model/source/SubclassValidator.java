/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.TypeUtils;

/**
 * Handles the validation of multiple @SubclassMapping annotations on the same method.
 *
 * @author Ben Zegveld
 */
public class SubclassValidator {

    private final FormattingMessager messager;
    private final List<TypeMirror> handledSubclasses = new ArrayList<>();
    private final TypeUtils typeUtils;

    public SubclassValidator(FormattingMessager messager, TypeUtils typeUtils) {
        this.messager = messager;
        this.typeUtils = typeUtils;
    }

    public boolean isValidUsage(Element e, AnnotationMirror annotation, TypeMirror sourceType) {
        for ( TypeMirror typeMirror : handledSubclasses ) {
            if ( typeUtils.isSameType( sourceType, typeMirror ) ) {
                messager
                        .printMessage(
                            e,
                            annotation,
                            Message.SUBCLASSMAPPING_DOUBLE_SOURCE_SUBCLASS,
                            sourceType );
                return false;
            }
            if ( typeUtils.isAssignable( sourceType, typeMirror ) ) {
                messager
                        .printMessage(
                            e,
                            annotation,
                            Message.SUBCLASSMAPPING_ILLOGICAL_ORDER,
                            sourceType,
                            typeMirror,
                            sourceType,
                            typeMirror );
                return false;
            }
        }
        handledSubclasses.add( sourceType );
        return true;
    }

}
