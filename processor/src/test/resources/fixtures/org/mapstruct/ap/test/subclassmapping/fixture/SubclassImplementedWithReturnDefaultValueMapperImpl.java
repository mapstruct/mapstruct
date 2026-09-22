/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.fixture;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-17T23:21:13+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.12.1 (Eclipse Adoptium)"
)
public class SubclassImplementedWithReturnDefaultValueMapperImpl implements SubclassImplementedWithReturnDefaultValueMapper {

    @Override
    public ImplementedParentTarget map(ImplementedParentSource item) {

        if (item instanceof SubSource) {
            return subSourceToSubTarget( (SubSource) item );
        }
        else if (item instanceof SubSourceOther) {
            return subSourceOtherToSubTargetOther( (SubSourceOther) item );
        }
        else {
            ImplementedParentTarget implementedParentTarget = new ImplementedParentTarget();

            if ( item != null ) {
                implementedParentTarget.setParentValue( item.getParentValue() );
                implementedParentTarget.setImplementedParentValue( item.getImplementedParentValue() );
            }

            return implementedParentTarget;
        }
    }

    protected SubTarget subSourceToSubTarget(SubSource subSource) {

        SubTarget subTarget = new SubTarget();

        subTarget.setParentValue( subSource.getParentValue() );
        subTarget.setImplementedParentValue( subSource.getImplementedParentValue() );
        subTarget.setValue( subSource.getValue() );

        return subTarget;
    }

    protected SubTargetOther subSourceOtherToSubTargetOther(SubSourceOther subSourceOther) {

        String finalValue = null;

        finalValue = subSourceOther.getFinalValue();

        SubTargetOther subTargetOther = new SubTargetOther( finalValue );

        subTargetOther.setParentValue( subSourceOther.getParentValue() );
        subTargetOther.setImplementedParentValue( subSourceOther.getImplementedParentValue() );

        return subTargetOther;
    }
}
