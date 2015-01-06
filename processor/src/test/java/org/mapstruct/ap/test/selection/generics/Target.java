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

public class Target {

    private Integer fooInteger;
    private String fooString;
    private String[] fooStringArray;
    private Long[] fooLongArray;
    private TwoArgHolder<Integer, Boolean> fooTwoArgs;
    private BigDecimal fooNested;
    private TypeB fooUpperBoundCorrect;
    private String fooWildCardExtendsString;
    private TypeC fooWildCardExtendsTypeCCorrect;
    private TypeB fooWildCardExtendsTypeBCorrect;
    private String fooWildCardSuperString;
    private TypeC fooWildCardExtendsMBTypeCCorrect;
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

    public TypeC getFooWildCardExtendsTypeCCorrect() {
        return fooWildCardExtendsTypeCCorrect;
    }

    public void setFooWildCardExtendsTypeCCorrect(TypeC fooWildCardExtendsTypeCCorrect) {
        this.fooWildCardExtendsTypeCCorrect = fooWildCardExtendsTypeCCorrect;
    }

    public TypeB getFooWildCardExtendsTypeBCorrect() {
        return fooWildCardExtendsTypeBCorrect;
    }

    public void setFooWildCardExtendsTypeBCorrect(TypeB fooWildCardExtendsTypeBCorrect) {
        this.fooWildCardExtendsTypeBCorrect = fooWildCardExtendsTypeBCorrect;
    }

    public String getFooWildCardSuperString() {
        return fooWildCardSuperString;
    }

    public void setFooWildCardSuperString(String fooWildCardSuperString) {
        this.fooWildCardSuperString = fooWildCardSuperString;
    }

    public TypeC getFooWildCardExtendsMBTypeCCorrect() {
        return fooWildCardExtendsMBTypeCCorrect;
    }

    public void setFooWildCardExtendsMBTypeCCorrect(TypeC fooWildCardExtendsMBTypeCCorrect) {
        this.fooWildCardExtendsMBTypeCCorrect = fooWildCardExtendsMBTypeCCorrect;
    }

    public TypeB getFooWildCardSuperTypeBCorrect() {
        return fooWildCardSuperTypeBCorrect;
    }

    public void setFooWildCardSuperTypeBCorrect(TypeB fooWildCardSuperTypeBCorrect) {
        this.fooWildCardSuperTypeBCorrect = fooWildCardSuperTypeBCorrect;
    }
}
