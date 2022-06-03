/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.annotation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.ap.internal.model.common.Type;

/**
 * @author Ben Zegveld
 */
public class EnumAnnotationElement extends AnnotationElement {

    private List<EnumAnnotationElementHolder> values;

    public EnumAnnotationElement(String elementName, List<EnumAnnotationElementHolder> values) {
        super( elementName );
        this.values = values;
    }

    public List<EnumAnnotationElementHolder> getValues() {
        return values;
    }

    @Override
    public Set<Type> getImportTypes() {
        return values.stream().map( EnumAnnotationElementHolder::getEnumClass ).collect( Collectors.toSet() );
    }

}
