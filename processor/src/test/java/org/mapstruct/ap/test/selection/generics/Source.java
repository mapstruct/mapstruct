/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
    private WildCardSuperWrapper<String> fooWildCardSuperString;
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

    public WildCardSuperWrapper<String> getFooWildCardSuperString() {
        return fooWildCardSuperString;
    }

    public void setFooWildCardSuperString(WildCardSuperWrapper<String> fooWildCardSuperString) {
        this.fooWildCardSuperString = fooWildCardSuperString;
    }

    public WildCardSuperWrapper<TypeB> getFooWildCardSuperTypeBCorrect() {
        return fooWildCardSuperTypeBCorrect;
    }

    public void setFooWildCardSuperTypeBCorrect(WildCardSuperWrapper<TypeB> fooWildCardSuperTypeBCorrect) {
        this.fooWildCardSuperTypeBCorrect = fooWildCardSuperTypeBCorrect;
    }
}
