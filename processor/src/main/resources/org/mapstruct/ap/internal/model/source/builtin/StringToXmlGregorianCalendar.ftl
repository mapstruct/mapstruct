<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.VirtualMappingMethod" -->
<#--

     Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
     and/or other contributors as indicated by the @authors tag. See the
     copyright.txt file in the distribution for a full listing of all
     contributors.

     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.

-->
private static javax.xml.datatype.XMLGregorianCalendar ${name}( String date, String dateFormat ) {
    if ( date == null ) {
        return null;
    }

    try {
        if ( dateFormat != null ) {
            java.text.DateFormat df = new java.text.SimpleDateFormat( dateFormat );
            java.util.GregorianCalendar c = new java.util.GregorianCalendar();
            c.setTime( df.parse( date ) );
            return javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar( c );
        }
        else {
            return javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar( date );
        }
    }
    catch ( javax.xml.datatype.DatatypeConfigurationException ex ) {
        throw new RuntimeException( ex );
    }
    catch ( java.text.ParseException ex ) {
        throw new RuntimeException( ex );
    }
}
