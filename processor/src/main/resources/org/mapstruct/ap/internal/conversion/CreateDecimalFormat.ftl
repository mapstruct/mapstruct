<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
private DecimalFormat ${name}( String numberFormat, Locale locale ) {

    DecimalFormat df = new DecimalFormat( numberFormat );
    df.setParseBigDecimal( true );
    df.setDecimalFormatSymbols( DecimalFormatSymbols.getInstance( locale ) );
    return df;
}