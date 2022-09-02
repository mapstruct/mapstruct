/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.selector;

import javax.lang.model.element.Element;

import org.mapstruct.ap.internal.gem.XmlElementDeclGem;
import org.mapstruct.ap.internal.gem.XmlElementRefGem;
import org.mapstruct.ap.internal.util.TypeUtils;

/**
 * The concrete implementation of the {@link XmlElementDeclSelector} that
 * works with {@link javax.xml.bind.annotation.XmlElementRef} and
 * {@link javax.xml.bind.annotation.XmlElementDecl}.
 *
 * @author Iaroslav Bogdanchikov
 */
class JavaxXmlElementDeclSelector extends XmlElementDeclSelector {

    JavaxXmlElementDeclSelector(TypeUtils typeUtils) {
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
