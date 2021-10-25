/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.innerclasses;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-16T21:06:53+0200",
    comments = "version: , compiler: javac, environment: Java 17 (Oracle Corporation)"
)
public class InnerClassMapperImpl implements InnerClassMapper {

    @Override
    public TargetWithInnerClass sourceToTarget(SourceWithInnerClass source) {
        if ( source == null ) {
            return null;
        }

        TargetWithInnerClass targetWithInnerClass = new TargetWithInnerClass();

        targetWithInnerClass.setInnerClassMember( innerSourceToInnerTarget( source.getInnerClassMember() ) );

        return targetWithInnerClass;
    }

    @Override
    public TargetWithInnerClass.TargetInnerClass innerSourceToInnerTarget(SourceWithInnerClass.SourceInnerClass source) {
        if ( source == null ) {
            return null;
        }

        TargetWithInnerClass.TargetInnerClass targetInnerClass = new TargetWithInnerClass.TargetInnerClass();

        targetInnerClass.setValue( source.getValue() );

        return targetInnerClass;
    }

    @Override
    public TargetWithInnerClass.TargetInnerClass.TargetInnerInnerClass innerSourceToInnerInnerTarget(SourceWithInnerClass.SourceInnerClass source) {
        if ( source == null ) {
            return null;
        }

        TargetWithInnerClass.TargetInnerClass.TargetInnerInnerClass targetInnerInnerClass = new TargetWithInnerClass.TargetInnerClass.TargetInnerInnerClass();

        targetInnerInnerClass.setValue( source.getValue() );

        return targetInnerInnerClass;
    }
}
