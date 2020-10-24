package org.mapstruct.ap.test.selection.wildcards;

import java.math.BigInteger;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReturnTypeWildCardExtendsMapper {

    ReturnTypeWildCardExtendsMapper INSTANCE = Mappers.getMapper( ReturnTypeWildCardExtendsMapper.class );

    Target map(Source source);

    default Wrapper<BigInteger> wrap(String in) {
         return new Wrapper<>(  new BigInteger( in ) );
    }

    class Source {

        private String prop;

        public Source(String prop) {
            this.prop = prop;
        }

        public String getProp() {
            return prop;
        }

        public Source setProp(String prop) {
            this.prop = prop;
            return this;
        }
    }

    class Target {

        private Wrapper<? super BigInteger> prop;

        public Target setProp(Wrapper<? super  BigInteger> prop) {
            this.prop = prop;
            return this;
        }

        public Wrapper<? super  BigInteger> getProp() {
            return prop;
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

}
