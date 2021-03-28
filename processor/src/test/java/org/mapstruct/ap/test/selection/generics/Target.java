/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

import java.math.BigDecimal;

public class Target {

    private Integer fooInteger;
    private String fooString;
    private String[] fooStringArray;
    private Long[] fooLongArray;
    private TwoArgHolder<Integer, Boolean> fooTwoArgs;
    private BigDecimal fooNested;
    private TypeB fooUpperBoundCorrect;
    private String fooWildCardExtendsString;
    private String fooWildCardSuperString;
    private TypeB fooWildCardSuperTypeBCorrect;

    public Integer getFooInteger() {
        return fooInteger;
    }

    public void setFooInteger(Integer fooInteger) {
        this.fooInteger = fooInteger;
    }

    public String getFooString() {
        return fooString;
    }

    public void setFooString(String fooString) {
        this.fooString = fooString;
    }

    public String[] getFooStringArray() {
        return fooStringArray;
    }

    public void setFooStringArray(String[] fooStringArray) {
        this.fooStringArray = fooStringArray;
    }

    public Long[] getFooLongArray() {
        return fooLongArray;
    }

    public void setFooLongArray(Long[] fooLongArray) {
        this.fooLongArray = fooLongArray;
    }

    public TwoArgHolder<Integer, Boolean> getFooTwoArgs() {
        return fooTwoArgs;
    }

    public void setFooTwoArgs(TwoArgHolder<Integer, Boolean> fooTwoArgs) {
        this.fooTwoArgs = fooTwoArgs;
    }

    public BigDecimal getFooNested() {
        return fooNested;
    }

    public void setFooNested(BigDecimal fooNested) {
        this.fooNested = fooNested;
    }

    public TypeB getFooUpperBoundCorrect() {
        return fooUpperBoundCorrect;
    }

    public void setFooUpperBoundCorrect(TypeB fooUpperBoundCorrect) {
        this.fooUpperBoundCorrect = fooUpperBoundCorrect;
    }

    public String getFooWildCardExtendsString() {
        return fooWildCardExtendsString;
    }

    public void setFooWildCardExtendsString(String fooWildCardExtendsString) {
        this.fooWildCardExtendsString = fooWildCardExtendsString;
    }

    public String getFooWildCardSuperString() {
        return fooWildCardSuperString;
    }

    public void setFooWildCardSuperString(String fooWildCardSuperString) {
        this.fooWildCardSuperString = fooWildCardSuperString;
    }

    public TypeB getFooWildCardSuperTypeBCorrect() {
        return fooWildCardSuperTypeBCorrect;
    }

    public void setFooWildCardSuperTypeBCorrect(TypeB fooWildCardSuperTypeBCorrect) {
        this.fooWildCardSuperTypeBCorrect = fooWildCardSuperTypeBCorrect;
    }
}
