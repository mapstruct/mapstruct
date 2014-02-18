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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;

public class Target {

    private String prop1;
    private List<String> prop2;
    private XMLGregorianCalendar prop3;
    private Date prop4;
    private XMLGregorianCalendar prop5;
    private XMLGregorianCalendar prop5NoFormat;
    private String prop6;
    private String prop6NoFormat;
    private XMLGregorianCalendar prop7;
    private Calendar prop8;


    public String getProp1() {
        return prop1;
    }

    public void setProp1( String prop1 ) {
        this.prop1 = prop1;
    }

    public List<String> getProp2() {
        return prop2;
    }

    public void setProp2( List<String> prop2 ) {
        this.prop2 = prop2;
    }

    public XMLGregorianCalendar getProp3() {
        return prop3;
    }

    public void setProp3( XMLGregorianCalendar prop3 ) {
        this.prop3 = prop3;
    }

    public Date getProp4() {
        return prop4;
    }

    public void setProp4( Date prop4 ) {
        this.prop4 = prop4;
    }

    public XMLGregorianCalendar getProp5() {
        return prop5;
    }

    public void setProp5( XMLGregorianCalendar prop5 ) {
        this.prop5 = prop5;
    }

    public XMLGregorianCalendar getProp5NoFormat() {
        return prop5NoFormat;
    }

    public void setProp5NoFormat( XMLGregorianCalendar prop5NoFormat ) {
        this.prop5NoFormat = prop5NoFormat;
    }

    public String getProp6() {
        return prop6;
    }

    public void setProp6( String prop6 ) {
        this.prop6 = prop6;
    }

    public String getProp6NoFormat() {
        return prop6NoFormat;
    }

    public void setProp6NoFormat( String prop6NoFormat ) {
        this.prop6NoFormat = prop6NoFormat;
    }

    public XMLGregorianCalendar getProp7() {
        return prop7;
    }

    public void setProp7( XMLGregorianCalendar prop7 ) {
        this.prop7 = prop7;
    }

    public Calendar getProp8() {
        return prop8;
    }

    public void setProp8( Calendar prop8 ) {
        this.prop8 = prop8;
    }
}
