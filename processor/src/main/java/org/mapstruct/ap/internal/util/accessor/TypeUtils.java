package org.mapstruct.ap.internal.util.accessor;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public class TypeUtils {

    private TypeUtils() {
    }

    public static boolean isType(Element element) {
        return element != null &&
                ( element.getKind() == ElementKind.CLASS || element.getKind() == ElementKind.INTERFACE );
    }

    public static boolean isInnerClass(Element element) {
        return isType( element ) && isType( element.getEnclosingElement() );
    }
}
