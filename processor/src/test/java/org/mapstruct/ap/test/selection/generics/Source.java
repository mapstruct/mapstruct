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

import java.math.BigDecimal;

public class Source {

    private Wrapper<Integer> fooInteger;
    private Wrapper<String> fooString;
    private Wrapper<String[]> fooStringArray;
    private ArrayWrapper<Long> fooLongArray;
    private TwoArgWrapper<Integer, Boolean> fooTwoArgs;
    private Wrapper<Wrapper<BigDecimal>> fooNested;
    private UpperBoundWrapper<TypeB> fooUpperBoundCorrect;
    private WildCardExtendsWrapper<String> fooWildCardExtendsString;
    private WildCardExtendsWrapper<TypeC> fooWildCardExtendsTypeCCorrect;
    private WildCardExtendsWrapper<TypeB> fooWildCardExtendsTypeBCorrect;
    private WildCardSuperWrapper<String> fooWildCardSuperString;
    private WildCardExtendsMBWrapper<TypeC> fooWildCardExtendsMBTypeCCorrect;
    private WildCardSuperWrapper<TypeB> fooWildCardSuperTypeBCorrect;

    public Wrapper<Integer> getFooInteger() {
        return fooInteger;
    }

    public void setFooInteger(Wrapper<Integer> fooInteger) {
        this.fooInteger = fooInteger;
    }

    public Wrapper<String> getFooString() {
        return fooString;
    }

    public void setFooString(Wrapper<String> fooString) {
        this.fooString = fooString;
    }

    public Wrapper<String[]> getFooStringArray() {
        return fooStringArray;
    }

    public void setFooStringArray(Wrapper<String[]> fooStringArray) {
        this.fooStringArray = fooStringArray;
    }

    public ArrayWrapper<Long> getFooLongArray() {
        return fooLongArray;
    }

    public void setFooLongArray(ArrayWrapper<Long> fooLongArray) {
        this.fooLongArray = fooLongArray;
    }

    public TwoArgWrapper<Integer, Boolean> getFooTwoArgs() {
        return fooTwoArgs;
    }

    public void setFooTwoArgs(TwoArgWrapper<Integer, Boolean> fooTwoArgs) {
        this.fooTwoArgs = fooTwoArgs;
    }

    public Wrapper<Wrapper<BigDecimal>> getFooNested() {
        return fooNested;
    }

    public void setFooNested(Wrapper<Wrapper<BigDecimal>> fooNested) {
        this.fooNested = fooNested;
    }

    public UpperBoundWrapper<TypeB> getFooUpperBoundCorrect() {
        return fooUpperBoundCorrect;
    }

    public void setFooUpperBoundCorrect(UpperBoundWrapper<TypeB> fooUpperBoundCorrect) {
        this.fooUpperBoundCorrect = fooUpperBoundCorrect;
    }

    public WildCardExtendsWrapper<String> getFooWildCardExtendsString() {
        return fooWildCardExtendsString;
    }

    public void setFooWildCardExtendsString(WildCardExtendsWrapper<String> fooWildCardExtendsString) {
        this.fooWildCardExtendsString = fooWildCardExtendsString;
    }

    public void setFooWildCardExtendsTypeCCorrect(WildCardExtendsWrapper<TypeC> fooWildCardExtendsTypeCCorrect) {
        this.fooWildCardExtendsTypeCCorrect = fooWildCardExtendsTypeCCorrect;
    }

    public WildCardExtendsWrapper<TypeC> getFooWildCardExtendsTypeCCorrect() {
        return fooWildCardExtendsTypeCCorrect;
    }

    public WildCardExtendsWrapper<TypeB> getFooWildCardExtendsTypeBCorrect() {
        return fooWildCardExtendsTypeBCorrect;
    }

    public void setFooWildCardExtendsTypeBCorrect(WildCardExtendsWrapper<TypeB> fooWildCardExtendsTypeBCorrect) {
        this.fooWildCardExtendsTypeBCorrect = fooWildCardExtendsTypeBCorrect;
    }

    public WildCardSuperWrapper<String> getFooWildCardSuperString() {
        return fooWildCardSuperString;
    }

    public void setFooWildCardSuperString(WildCardSuperWrapper<String> fooWildCardSuperString) {
        this.fooWildCardSuperString = fooWildCardSuperString;
    }

    public WildCardExtendsMBWrapper<TypeC> getFooWildCardExtendsMBTypeCCorrect() {
        return fooWildCardExtendsMBTypeCCorrect;
    }

    public void setFooWildCardExtendsMBTypeCCorrect(WildCardExtendsMBWrapper<TypeC> fooWildCardExtendsMBTypeCCorrect) {
        this.fooWildCardExtendsMBTypeCCorrect = fooWildCardExtendsMBTypeCCorrect;
    }

    public WildCardSuperWrapper<TypeB> getFooWildCardSuperTypeBCorrect() {
        return fooWildCardSuperTypeBCorrect;
    }

    public void setFooWildCardSuperTypeBCorrect(WildCardSuperWrapper<TypeB> fooWildCardSuperTypeBCorrect) {
        this.fooWildCardSuperTypeBCorrect = fooWildCardSuperTypeBCorrect;
    }
}
