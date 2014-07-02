/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.builtin;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Source {

    private JAXBElement<String> prop1;
    private List<JAXBElement<String>> prop2;
    private Date prop3;
    private XMLGregorianCalendar prop4;
    private String prop5;
    private String prop5NoFormat;
    private XMLGregorianCalendar prop6;
    private XMLGregorianCalendar prop6NoFormat;
    private Calendar prop7;
    private XMLGregorianCalendar prop8;
    private Calendar prop9;
    private Date prop10;
    private String prop11;
    private Calendar prop12;

    public JAXBElement<String> getProp1() {
        return prop1;
    }

    public void setProp1( JAXBElement<String> prop1 ) {
        this.prop1 = prop1;
    }

    public List<JAXBElement<String>> getProp2() {
        return prop2;
    }

    public void setProp2( List<JAXBElement<String>> prop2 ) {
        this.prop2 = prop2;
    }

    public Date getProp3() {
        return prop3;
    }

    public void setProp3( Date prop3 ) {
        this.prop3 = prop3;
    }

    public XMLGregorianCalendar getProp4() {
        return prop4;
    }

    public void setProp4( XMLGregorianCalendar prop4 ) {
        this.prop4 = prop4;
    }

    public String getProp5() {
        return prop5;
    }

    public void setProp5( String prop5 ) {
        this.prop5 = prop5;
    }

    public String getProp5NoFormat() {
        return prop5NoFormat;
    }

    public void setProp5NoFormat( String prop5NoFormat ) {
        this.prop5NoFormat = prop5NoFormat;
    }

    public XMLGregorianCalendar getProp6() {
        return prop6;
    }

    public void setProp6( XMLGregorianCalendar prop6 ) {
        this.prop6 = prop6;
    }

    public XMLGregorianCalendar getProp6NoFormat() {
        return prop6NoFormat;
    }

    public void setProp6NoFormat( XMLGregorianCalendar prop6NoFormat ) {
        this.prop6NoFormat = prop6NoFormat;
    }

    public Calendar getProp7() {
        return prop7;
    }

    public void setProp7( Calendar prop7 ) {
        this.prop7 = prop7;
    }

    public XMLGregorianCalendar getProp8() {
        return prop8;
    }

    public void setProp8( XMLGregorianCalendar prop8 ) {
        this.prop8 = prop8;
    }

    public Calendar getProp9() {
        return prop9;
    }

    public void setProp9(Calendar prop9) {
        this.prop9 = prop9;
    }

    public Date getProp10() {
        return prop10;
    }

    public void setProp10(Date prop10) {
        this.prop10 = prop10;
    }

    public String getProp11() {
        return prop11;
    }

    public void setProp11(String prop11) {
        this.prop11 = prop11;
    }

    public Calendar getProp12() {
        return prop12;
    }

    public void setProp12(Calendar prop12) {
        this.prop12 = prop12;
    }
}
