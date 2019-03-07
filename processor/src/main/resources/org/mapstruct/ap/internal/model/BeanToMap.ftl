<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.BeanToMap" -->
private static class ${className} {

    private final Map &lt;String, Object&gt; result;

    private ${className} (Map< &lt;String, Object&gt; result) {
        this.result = result;
    }

<#list mapEntries as mapEntry>
    private void set${capitalize(mapEntry.name)}(<@includeModel object=mapEntry.type/> ${mapEntry.name} ){
        result.put( "${mapEntry.name}", ${mapEntry.name} );
    }

    private <@includeModel object=mapEntry.type/> get${capitalize(mapEntry.name)}( ){
        return (<@includeModel object=mapEntry.type/>) result.get( "${mapEntry.name}" );
    }

</#list>
}