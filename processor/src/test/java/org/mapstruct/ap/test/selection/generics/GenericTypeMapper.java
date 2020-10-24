/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

public class GenericTypeMapper {

    public <T> T getWrapped(Wrapper<T> source) {
        return source.getWrapped();
    }

    public <T> T[] getArrayWrapped(ArrayWrapper<T> t) {
        return t.getWrapped();
    }

    public <T1, T2> TwoArgHolder<T1, T2> getTwoArgWrapped(TwoArgWrapper<T1, T2> t) {
        return t.getWrapped();
    }

    public <T> T getNestedWrapped(Wrapper<Wrapper<T>> t) {
        return t.getWrapped().getWrapped();
    }

    public <T extends TypeB> T getUpperBounded(UpperBoundWrapper<T> t) {
        return t.getWrapped();
    }

    // TODO Lower bound test? The javadoc states: Returns the lower bound of this type variable.
    // While a type parameter cannot include an explicit lower bound declaration, capture conversion can produce
    // a type variable with a non-trivial lower bound. Type variables otherwise have a lower bound of NullType.

    public String getWildCardExtendsString(WildCardExtendsWrapper<? extends String> t) {
        return t.getWrapped();
    }

    public <T extends TypeB> T getWildCardExtendsType(WildCardExtendsWrapper<? extends T> t) {
        return t.getWrapped();
    }

    /**
     * TODO.. My own IDE compiler actually allows all TypeA, TypeB, TypeC. However, I assume
     * that only TypeB is allowed here. This is what the code actually enforces.
     */
    public <T extends TypeB> T getWildCardSuperType(WildCardSuperWrapper<? super T> t) {
        return (T) t.getWrapped();
    }

    public String getWildCardSupersString(WildCardSuperWrapper<? super String> t) {
        return (String) t.getWrapped();
    }

}
