/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.selection.generics;

import java.io.Serializable;

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

    public <T extends TypeB & Serializable> T getWildCardExtendsMBType(WildCardExtendsMBWrapper<? extends T> t) {
        return t.getWrapped();
    }
}
