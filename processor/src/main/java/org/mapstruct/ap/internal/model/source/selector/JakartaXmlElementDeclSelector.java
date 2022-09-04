/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.selector;

import javax.lang.model.element.Element;

import org.mapstruct.ap.internal.gem.jakarta.XmlElementDeclGem;
import org.mapstruct.ap.internal.gem.jakarta.XmlElementRefGem;
import org.mapstruct.ap.internal.util.TypeUtils;

/**
 * The concrete implementation of the {@link XmlElementDeclSelector} that
 * works with {@link jakarta.xml.bind.annotation.XmlElementRef} and
 * {@link jakarta.xml.bind.annotation.XmlElementDecl}.
 *
 * @author Iaroslav Bogdanchikov
 */
class JakartaXmlElementDeclSelector extends XmlElementDeclSelector {

    JakartaXmlElementDeclSelector(TypeUtils typeUtils) {
        super( typeUtils );
    }

    @Override
    XmlElementDeclInfo getXmlElementDeclInfo(Element element) {
        XmlElementDeclGem gem = XmlElementDeclGem.instanceOn( element );

        if (gem == null) {
            return null;
        }

        return new XmlElementDeclInfo( gem.name().get(), gem.scope().get() );
    }

    @Override
    XmlElementRefInfo getXmlElementRefInfo(Element element) {
        XmlElementRefGem gem = XmlElementRefGem.instanceOn( element );

        if (gem == null) {
            return null;
        }

        return new XmlElementRefInfo( gem.name().get(), gem.type().get() );
    }
}
