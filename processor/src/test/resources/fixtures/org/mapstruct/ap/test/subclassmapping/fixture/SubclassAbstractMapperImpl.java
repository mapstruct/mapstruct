/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.fixture;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-31T19:35:15+0100",
    comments = "version: , compiler: Eclipse JDT (Batch) 3.20.0.v20191203-2131, environment: Java 11.0.12 (Azul Systems, Inc.)"
)
public class SubclassAbstractMapperImpl implements SubclassAbstractMapper {

    @Override
    public AbstractParentTarget map(AbstractParentSource item) {
        if ( item == null ) {
            return null;
        }

        if (item instanceof SubSource) {
            return subSourceToSubTarget( (SubSource) item );
        }
        else if (item instanceof SubSourceOther) {
            return subSourceOtherToSubTargetOther( (SubSourceOther) item );
        }
        else {
            throw new IllegalArgumentException("Not all subclasses are supported for this mapping. Missing for " + item.getClass());
        }
    }

    @Override
    public AbstractParentSource map(AbstractParentTarget item) {
        if ( item == null ) {
            return null;
        }

        if (item instanceof SubTargetSeparate) {
            return subTargetSeparateToSubSourceSeparate( (SubTargetSeparate) item );
        }
        else if (item instanceof SubTargetOther) {
            return subTargetOtherToSubSourceOverride( (SubTargetOther) item );
        }
        else if (item instanceof SubTarget) {
            return subTargetToSubSource( (SubTarget) item );
        }
        else {
            throw new IllegalArgumentException("Not all subclasses are supported for this mapping. Missing for " + item.getClass());
        }
    }

    protected SubTarget subSourceToSubTarget(SubSource item) {
        if ( item == null ) {
            return null;
        }

        SubTarget subTarget = new SubTarget();

        subTarget.setValue( item.getValue() );

        return subTarget;
    }

    protected SubTargetOther subSourceOtherToSubTargetOther(SubSourceOther item) {
        if ( item == null ) {
            return null;
        }

        String finalValue = null;

        finalValue = item.getFinalValue();

        SubTargetOther subTargetOther = new SubTargetOther( finalValue );

        return subTargetOther;
    }

    protected SubSourceSeparate subTargetSeparateToSubSourceSeparate(SubTargetSeparate item) {
        if ( item == null ) {
            return null;
        }

        String separateValue = null;

        separateValue = item.getSeparateValue();

        SubSourceSeparate subSourceSeparate = new SubSourceSeparate( separateValue );

        return subSourceSeparate;
    }

    protected SubSourceOverride subTargetOtherToSubSourceOverride(SubTargetOther item) {
        if ( item == null ) {
            return null;
        }

        String finalValue = null;

        finalValue = item.getFinalValue();

        SubSourceOverride subSourceOverride = new SubSourceOverride( finalValue );

        return subSourceOverride;
    }

    protected SubSource subTargetToSubSource(SubTarget item) {
        if ( item == null ) {
            return null;
        }

        SubSource subSource = new SubSource();

        subSource.setValue( item.getValue() );

        return subSource;
    }
}
