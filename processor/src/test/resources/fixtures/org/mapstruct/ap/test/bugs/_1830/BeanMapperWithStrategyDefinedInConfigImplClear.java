package org.mapstruct.ap.test.bugs._1830;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-19T09:34:23+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.2 (Azul Systems, Inc.)"
)
public class BeanMapperWithStrategyDefinedInConfigImpl implements BeanMapperWithStrategyDefinedInConfig {

    @Override
    public BeanDTO map(Bean source, BeanDTO target) {
        if ( source == null ) {
            return target;
        }

        if ( target.getList() != null ) {
            Collection<String> collection = source.getList();
            if ( collection != null ) {
                target.getList().clear();
                target.getList().addAll( collection );
            }
            else {
                target.getList().clear();
            }
        }
        else {
            Collection<String> collection = source.getList();
            if ( collection != null ) {
                target.setList( new ArrayList<String>( collection ) );
            }
        }

        return target;
    }

    @Override
    public BeanDTO map(NestedBean source, BeanDTO target) {
        if ( source == null ) {
            return target;
        }

        Collection<String> list = sourceBeanList( source );
        if ( target.getList() != null ) {
            Collection<String> collection = list;
            if ( collection != null ) {
                target.getList().clear();
                target.getList().addAll( collection );
            }
            else {
                target.getList().clear();
            }
        }
        else {
            Collection<String> collection = list;
            if ( collection != null ) {
                target.setList( new ArrayList<String>( collection ) );
            }
        }

        return target;
    }

    private Collection<String> sourceBeanList(NestedBean nestedBean) {
        Bean bean = nestedBean.getBean();
        if ( bean == null ) {
            return null;
        }
        return bean.getList();
    }
}