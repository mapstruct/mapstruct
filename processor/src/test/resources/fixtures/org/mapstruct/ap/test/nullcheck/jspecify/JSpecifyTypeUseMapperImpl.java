/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-25T22:21:39+0900",
    comments = "version: , compiler: Eclipse JDT (Batch) 3.20.0.v20191203-2131, environment: Java 25.0.2 (Oracle Corporation)"
)
public class JSpecifyTypeUseMapperImpl implements JSpecifyTypeUseMapper {

    @Override
    public JSpecifyTypeUseTargetBean map(JSpecifyTypeUseSourceBean source) {

        JSpecifyTypeUseTargetBean jSpecifyTypeUseTargetBean = new JSpecifyTypeUseTargetBean();

        String[] nullableArray = source.getNullableArray();
        if ( nullableArray != null ) {
            jSpecifyTypeUseTargetBean.setNullableArray( Arrays.copyOf( nullableArray, nullableArray.length ) );
        }
        String[] nullableArrayElements = source.getNullableArrayElements();
        if ( nullableArrayElements != null ) {
            jSpecifyTypeUseTargetBean.setNullableArrayElements( Arrays.copyOf( nullableArrayElements, nullableArrayElements.length ) );
        }
        jSpecifyTypeUseTargetBean.setNullableListElements( mapElements( source.getNullableListElements() ) );
        jSpecifyTypeUseTargetBean.setNullableElementArray( jSpecifyTypeUseSourceElementArrayToJSpecifyTypeUseTargetElementArray( source.getNullableElementArray() ) );
        jSpecifyTypeUseTargetBean.setNestedNullableListElements( mapNested( source.getNestedNullableListElements() ) );

        return jSpecifyTypeUseTargetBean;
    }

    @Override
    public JSpecifyTypeUseTargetElement map(JSpecifyTypeUseSourceElement source) {

        JSpecifyTypeUseTargetElement jSpecifyTypeUseTargetElement = new JSpecifyTypeUseTargetElement();

        jSpecifyTypeUseTargetElement.setValue( source.getValue() );

        return jSpecifyTypeUseTargetElement;
    }

    @Override
    public List<JSpecifyTypeUseTargetElement> mapElements(List<JSpecifyTypeUseSourceElement> source) {

        List<JSpecifyTypeUseTargetElement> list = new ArrayList<>( source.size() );
        for ( JSpecifyTypeUseSourceElement jSpecifyTypeUseSourceElement : source ) {
            if ( jSpecifyTypeUseSourceElement != null ) {
                list.add( map( jSpecifyTypeUseSourceElement ) );
            }
            else {
                list.add( null );
            }
        }

        return list;
    }

    @Override
    public List<List<JSpecifyTypeUseTargetElement>> mapNested(List<List<JSpecifyTypeUseSourceElement>> source) {

        List<List<JSpecifyTypeUseTargetElement>> list1 = new ArrayList<>( source.size() );
        for ( List<JSpecifyTypeUseSourceElement> list : source ) {
            list1.add( mapElements( list ) );
        }

        return list1;
    }

    protected JSpecifyTypeUseTargetElement[] jSpecifyTypeUseSourceElementArrayToJSpecifyTypeUseTargetElementArray(JSpecifyTypeUseSourceElement[] jSpecifyTypeUseSourceElementArray) {
        if ( jSpecifyTypeUseSourceElementArray == null ) {
            return new JSpecifyTypeUseTargetElement[0];
        }

        JSpecifyTypeUseTargetElement[] jSpecifyTypeUseTargetElementTmp = new JSpecifyTypeUseTargetElement[jSpecifyTypeUseSourceElementArray.length];
        int i = 0;
        for ( JSpecifyTypeUseSourceElement jSpecifyTypeUseSourceElement : jSpecifyTypeUseSourceElementArray ) {
            if ( jSpecifyTypeUseSourceElement != null ) {
                jSpecifyTypeUseTargetElementTmp[i] = map( jSpecifyTypeUseSourceElement );
            }
            else {
                jSpecifyTypeUseTargetElementTmp[i] = null;
            }
            i++;
        }

        return jSpecifyTypeUseTargetElementTmp;
    }
}
