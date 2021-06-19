/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;

import static org.mapstruct.ap.internal.util.Collections.asSet;

import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConstructorFragment;
import org.mapstruct.ap.internal.model.common.FieldReference;
import org.mapstruct.ap.internal.model.common.FinalField;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.XmlConstants;

/**
 * @author Sjaak Derksen
 */
public abstract class AbstractToXmlGregorianCalendar extends BuiltInMethod {

    private final Type returnType;
    private final Set<Type> importTypes;
    private final Type dataTypeFactoryType;

    public AbstractToXmlGregorianCalendar(TypeFactory typeFactory) {
        this.returnType = typeFactory.getType( XmlConstants.JAVAX_XML_XML_GREGORIAN_CALENDAR );
        this.dataTypeFactoryType = typeFactory.getType( XmlConstants.JAVAX_XML_DATATYPE_FACTORY );
        this.importTypes = asSet(
            returnType,
            dataTypeFactoryType,
            typeFactory.getType( XmlConstants.JAVAX_XML_DATATYPE_CONFIGURATION_EXCEPTION )
        );
    }

    @Override
    public Set<Type> getImportTypes() {
        return importTypes;
    }

    @Override
    public Type getReturnType() {
        return returnType;
    }

    @Override
    public FieldReference getFieldReference() {
        return new FinalField( dataTypeFactoryType, "datatypeFactory" );
    }

    @Override
    public ConstructorFragment getConstructorFragment() {
        return new NewDatatypeFactoryConstructorFragment( );
    }
}
