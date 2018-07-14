<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#assign sourceVarName><#if assignment.sourceLocalVarName?? >${assignment.sourceLocalVarName}<#else>${assignment.sourceReference}</#if></#assign>
<#if (thrownTypes?size == 0) >
    <#compress>
        <#if directAssignment>
        Function.identity()
        <#else>
        ${sourceVarName} -> <@_assignment/></#if>
    </#compress>
<#else>
    <#compress>
        ${sourceVarName} -> {
            try {
                return <@_assignment/>;
            }
            <#list thrownTypes as exceptionType>
            catch ( <@includeModel object=exceptionType/> e ) {
                throw new RuntimeException( e );
            }
            </#list>
        }
    </#compress>
</#if>
<#macro _assignment>
    <@includeModel object=assignment
    targetBeanName=ext.targetBeanName
    existingInstanceMapping=ext.existingInstanceMapping
    targetReadAccessorName=ext.targetReadAccessorName
    targetWriteAccessorName=ext.targetWriteAccessorName
    targetType=ext.targetType/>
</#macro>
