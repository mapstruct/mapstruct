/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;

import static org.mapstruct.ap.internal.util.Collections.asSet;

import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.mapstruct.ap.internal.model.common.ConstructorFragment;
import org.mapstruct.ap.internal.model.common.FieldReference;
import org.mapstruct.ap.internal.model.common.FinalField;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.Strings;

/**
 * @author Sjaak Derksen
 */
public abstract class AbstractToXmlGregorianCalendar extends BuiltInMethod {

    private final Type returnType;
    private final Set<Type> importTypes;
    private final Type dataTypeFactoryType;

    public AbstractToXmlGregorianCalendar(TypeFactory typeFactory) {
        this.returnType = typeFactory.getType( XMLGregorianCalendar.class );
        this.dataTypeFactoryType = typeFactory.getType( DatatypeFactory.class );
        this.importTypes = asSet(
            returnType,
            dataTypeFactoryType,
            typeFactory.getType( DatatypeConfigurationException.class )
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
        return new FinalField( dataTypeFactoryType, Strings.decapitalize( DatatypeFactory.class.getSimpleName() ) );
    }

    @Override
    public ConstructorFragment getConstructorFragment() {
        return new NewDatatypeFactoryConstructorFragment( );
    }
}
