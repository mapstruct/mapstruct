package org.mapstruct.ap.test.selection.methodgenerics.wildcards;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SourceWildCardExtendsMapper {

    SourceWildCardExtendsMapper INSTANCE = Mappers.getMapper( SourceWildCardExtendsMapper.class );

    Target map( Source source);

    default <T extends TypeB> T unwrap(Wrapper<? extends T> t) {
        return t.getWrapped();
    }

    class Source {

        private final Wrapper<TypeC> propB;
        private final Wrapper<TypeC> propC;

        public Source(Wrapper<TypeC> propB, Wrapper<TypeC> propC) {
            this.propB = propB;
            this.propC = propC;
        }

        public Wrapper<TypeC> getPropB() {
            return propB;
        }

        public Wrapper<TypeC> getPropC() {
            return propC;
        }

    }

    class Wrapper<T> {

        private final T wrapped;

        public Wrapper(T wrapped) {
            this.wrapped = wrapped;
        }

        public T getWrapped() {
            return wrapped;
        }

    }

    class Target {

        private TypeB propB;
        private TypeC propC;

        public TypeB getPropB() {
            return propB;
        }

        public void setPropB(TypeB propB) {
            this.propB = propB;
        }

        public TypeC getPropC() {
            return propC;
        }

        public void setPropC(TypeC propC) {
            this.propC = propC;
        }
    }

    class TypeC extends TypeB {
    }

    class TypeB extends TypeA {
    }

    class TypeA {
    }

}
