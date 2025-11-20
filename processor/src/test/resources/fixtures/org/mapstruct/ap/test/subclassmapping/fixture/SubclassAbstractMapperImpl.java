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

    protected SubTarget subSourceToSubTarget(SubSource subSource) {
        if ( subSource == null ) {
            return null;
        }

        SubTarget subTarget = new SubTarget();

        subTarget.setParentValue( subSource.getParentValue() );
        subTarget.setImplementedParentValue( subSource.getImplementedParentValue() );
        subTarget.setValue( subSource.getValue() );

        return subTarget;
    }

    protected SubTargetOther subSourceOtherToSubTargetOther(SubSourceOther subSourceOther) {
        if ( subSourceOther == null ) {
            return null;
        }

        String finalValue = null;

        finalValue = subSourceOther.getFinalValue();

        SubTargetOther subTargetOther = new SubTargetOther( finalValue );

        subTargetOther.setParentValue( subSourceOther.getParentValue() );
        subTargetOther.setImplementedParentValue( subSourceOther.getImplementedParentValue() );

        return subTargetOther;
    }

    protected SubSourceSeparate subTargetSeparateToSubSourceSeparate(SubTargetSeparate subTargetSeparate) {
        if ( subTargetSeparate == null ) {
            return null;
        }

        String separateValue = null;

        separateValue = subTargetSeparate.getSeparateValue();

        SubSourceSeparate subSourceSeparate = new SubSourceSeparate( separateValue );

        subSourceSeparate.setParentValue( subTargetSeparate.getParentValue() );
        subSourceSeparate.setImplementedParentValue( subTargetSeparate.getImplementedParentValue() );

        return subSourceSeparate;
    }

    protected SubSourceOverride subTargetOtherToSubSourceOverride(SubTargetOther subTargetOther) {
        if ( subTargetOther == null ) {
            return null;
        }

        String finalValue = null;

        finalValue = subTargetOther.getFinalValue();

        SubSourceOverride subSourceOverride = new SubSourceOverride( finalValue );

        subSourceOverride.setParentValue( subTargetOther.getParentValue() );
        subSourceOverride.setImplementedParentValue( subTargetOther.getImplementedParentValue() );

        return subSourceOverride;
    }

    protected SubSource subTargetToSubSource(SubTarget subTarget) {
        if ( subTarget == null ) {
            return null;
        }

        SubSource subSource = new SubSource();

        subSource.setParentValue( subTarget.getParentValue() );
        subSource.setImplementedParentValue( subTarget.getImplementedParentValue() );
        subSource.setValue( subTarget.getValue() );

        return subSource;
    }
}
