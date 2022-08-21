<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.BeanMappingMethod" -->
<#list annotations as annotation>
    <#nt><@includeModel object=annotation/>
</#list>
<#if overridden>@Override</#if>
<#if deprecated>
    @Deprecated
</#if>
<#lt>${accessibility.keyword} <@includeModel object=returnType/> ${name}(<#list parameters as param><@includeModel object=param/><#if param_has_next>, </#if></#list>)<@throws/> {
    <#assign targetType = resultType />
    <#if !existingInstanceMapping>
        <#assign targetType = returnTypeToConstruct />
    </#if>
    <#list beforeMappingReferencesWithoutMappingTarget as callback>
    	<@includeModel object=callback targetBeanName=resultName targetType=targetType/>
    	<#if !callback_has_next>

    	</#if>
    </#list>
    <#if !mapNullToDefault>
    if ( <#list sourceParametersExcludingPrimitives as sourceParam>${sourceParam.name} == null<#if sourceParam_has_next> && </#if></#list> ) {
        return<#if returnType.name != "void"> <#if existingInstanceMapping>${resultName}<#if finalizerMethod??>.<@includeModel object=finalizerMethod /></#if><#else>null</#if></#if>;
    }
    </#if>

    <#if hasSubclassMappings()>
        <#list subclassMappings as subclass>
            <#if subclass_index &gt; 0>else</#if> if (${subclass.sourceArgument} instanceof <@includeModel object=subclass.sourceType/>) {
                <@includeModel object=subclass.assignment existingInstanceMapping=existingInstanceMapping/>
            }
        </#list>
        else {
    </#if>
    <#if isAbstractReturnType()>
        throw new IllegalArgumentException("Not all subclasses are supported for this mapping. Missing for " + ${subclassMappings[0].sourceArgument}.getClass());
    <#else>
    <#if !existingInstanceMapping>
        <#if hasConstructorMappings()>
            <#if (sourceParameters?size > 1)>
                <#list sourceParametersNeedingNullCheck as sourceParam>
                    <#if (constructorPropertyMappingsByParameter(sourceParam)?size > 0)>
                        <#list constructorPropertyMappingsByParameter(sourceParam) as propertyMapping>
                            <@includeModel object=propertyMapping.targetType /> ${propertyMapping.targetWriteAccessorName} = ${propertyMapping.targetType.null};
                        </#list>
                        if ( ${sourceParam.name} != null ) {
                        <#list constructorPropertyMappingsByParameter(sourceParam) as propertyMapping>
                            <@includeModel object=propertyMapping existingInstanceMapping=existingInstanceMapping defaultValueAssignment=propertyMapping.defaultValueAssignment/>
                        </#list>
                        }
                    </#if>
                </#list>
                <#list sourceParametersNotNeedingNullCheck as sourceParam>
                    <#if (constructorPropertyMappingsByParameter(sourceParam)?size > 0)>
                        <#list constructorPropertyMappingsByParameter(sourceParam) as propertyMapping>
                            <@includeModel object=propertyMapping.targetType /> ${propertyMapping.targetWriteAccessorName} = ${propertyMapping.targetType.null};
                            <@includeModel object=propertyMapping existingInstanceMapping=existingInstanceMapping defaultValueAssignment=propertyMapping.defaultValueAssignment/>
                        </#list>
                    </#if>
                </#list>
            <#else>
                <#list constructorPropertyMappingsByParameter(sourceParameters[0]) as propertyMapping>
                    <@includeModel object=propertyMapping.targetType /> ${propertyMapping.targetWriteAccessorName} = ${propertyMapping.targetType.null};
                </#list>
                <#if mapNullToDefault>if ( ${sourceParameters[0].name} != null ) {</#if>
                <#list constructorPropertyMappingsByParameter(sourceParameters[0]) as propertyMapping>
                    <@includeModel object=propertyMapping existingInstanceMapping=existingInstanceMapping defaultValueAssignment=propertyMapping.defaultValueAssignment/>
                </#list>
                <#if mapNullToDefault>
                    }
                </#if>
            </#if>
            <#list constructorConstantMappings as constantMapping>

                <@compress single_line=true>
                    <@includeModel object=constantMapping.targetType /> <@includeModel object=constantMapping existingInstanceMapping=existingInstanceMapping/>
                </@compress>
            </#list>


            <@includeModel object=returnTypeToConstruct/> ${resultName} = <@includeModel object=factoryMethod targetType=returnTypeToConstruct/>;
        <#else >
            <@includeModel object=returnTypeToConstruct/> ${resultName} = <#if factoryMethod??><@includeModel object=factoryMethod targetType=returnTypeToConstruct/><#else>new <@includeModel object=returnTypeToConstruct/>()</#if>;
        </#if>

    </#if>
    <#list beforeMappingReferencesWithMappingTarget as callback>
    	<@includeModel object=callback targetBeanName=resultName targetType=targetType/>
    	<#if !callback_has_next>

    	</#if>
    </#list>
    <#if (sourceParameters?size > 1)>
        <#list sourceParametersNeedingNullCheck as sourceParam>
            <#if (propertyMappingsByParameter(sourceParam)?size > 0)>
                if ( ${sourceParam.name} != null ) {
                    <#list propertyMappingsByParameter(sourceParam) as propertyMapping>
                        <@includeModel object=propertyMapping targetBeanName=resultName existingInstanceMapping=existingInstanceMapping defaultValueAssignment=propertyMapping.defaultValueAssignment/>
                    </#list>
                }
            </#if>
        </#list>
        <#list sourceParametersNotNeedingNullCheck as sourceParam>
            <#if (propertyMappingsByParameter(sourceParam)?size > 0)>
                <#list propertyMappingsByParameter(sourceParam) as propertyMapping>
                    <@includeModel object=propertyMapping targetBeanName=resultName existingInstanceMapping=existingInstanceMapping defaultValueAssignment=propertyMapping.defaultValueAssignment/>
                </#list>
            </#if>
        </#list>
    <#else>
        <#if mapNullToDefault>if ( ${sourceParameters[0].name} != null ) {</#if>
        <#list propertyMappingsByParameter(sourceParameters[0]) as propertyMapping>
            <@includeModel object=propertyMapping targetBeanName=resultName existingInstanceMapping=existingInstanceMapping defaultValueAssignment=propertyMapping.defaultValueAssignment/>
        </#list>
        <#if mapNullToDefault>}</#if>
    </#if>
    <#list constantMappings as constantMapping>
         <@includeModel object=constantMapping targetBeanName=resultName existingInstanceMapping=existingInstanceMapping/>
    </#list>
    <#list afterMappingReferences as callback>
    	<#if callback_index = 0>

    	</#if>
    	<@includeModel object=callback targetBeanName=resultName targetType=targetType/>
    </#list>
    <#if returnType.name != "void">

    <#if finalizerMethod??>
        return ${resultName}.<@includeModel object=finalizerMethod />;
    <#else>
        return ${resultName};
    </#if>
    </#if>
    </#if>
    <#if hasSubclassMappings()>
        }
    </#if>
}
<#macro throws>
    <#if (thrownTypes?size > 0)><#lt> throws </#if><@compress single_line=true>
        <#list thrownTypes as exceptionType>
            <@includeModel object=exceptionType/>
            <#if exceptionType_has_next>, </#if><#t>
        </#list>
    </@compress>
</#macro>