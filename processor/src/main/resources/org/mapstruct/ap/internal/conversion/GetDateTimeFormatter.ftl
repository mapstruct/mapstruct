<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->

private static class ${templateParameter['className']} {
<#if templateParameter['dateFormat']??>
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern( "${templateParameter['dateFormat']}" );
<#else>
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.${templateParameter['defaultFormatterSuffix']};
</#if>
}

private DateTimeFormatter ${name}() {
    return ${templateParameter['className']}.DATETIME_FORMATTER;
}
