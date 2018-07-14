<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
private DecimalFormat ${name}( String numberFormat ) {

    DecimalFormat df = new DecimalFormat( numberFormat );
    df.setParseBigDecimal( true );
    return df;
}