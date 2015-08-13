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
package org.mapstruct.ap.test.source.constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Sjaak Derksen
 */
public class Target {

    private String propertyThatShouldBeMapped;
    private String stringConstant;
    private String emptyStringConstant;
    private int integerConstant;
    private Long longWrapperConstant;
    private Date dateConstant;
    private List<String> nameConstants = new ArrayList<String>();

    public String getPropertyThatShouldBeMapped() {
        return propertyThatShouldBeMapped;
    }

    public void setPropertyThatShouldBeMapped(String propertyThatShouldBeMapped) {
        this.propertyThatShouldBeMapped = propertyThatShouldBeMapped;
    }

    public String getStringConstant() {
        return stringConstant;
    }

    public void setStringConstant(String stringConstant) {
        this.stringConstant = stringConstant;
    }

    public int getIntegerConstant() {
        return integerConstant;
    }

    public void setIntegerConstant(int integerConstant) {
        this.integerConstant = integerConstant;
    }

    public Long getLongWrapperConstant() {
        return longWrapperConstant;
    }

    public void setLongWrapperConstant(Long longWrapperConstant) {
        this.longWrapperConstant = longWrapperConstant;
    }

    public Date getDateConstant() {
        return dateConstant;
    }

    public void setDateConstant(Date dateConstant) {
        this.dateConstant = dateConstant;
    }

    public List<String> getNameConstants() {
        return nameConstants;
    }

    public String getEmptyStringConstant() {
        return emptyStringConstant;
    }

    public void setEmptyStringConstant(String emptyStringConstant) {
        this.emptyStringConstant = emptyStringConstant;
    }

}
