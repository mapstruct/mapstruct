<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.BeanMappingMethod" -->
<#list annotations as annotation>
    <#nt><@includeModel object=annotation/>
</#list>
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
    <#list beforeMappingReferencesWithFinalizedReturnType as callback>
    	<@includeModel object=callback targetBeanName=finalizedResultName targetType=finalizedReturnType/>
    	<#if !callback_has_next>

    	</#if>
    </#list>
    <#if !mapNullToDefault && !sourcePresenceChecks.empty>
    if ( <#list sourcePresenceChecks as sourcePresenceCheck><@includeModel object=sourcePresenceCheck.negate() /><#if sourcePresenceCheck_has_next> && </#if></#list> ) {
        <#if returnType.name == "void">
            return;
        <#else>
            <#if existingInstanceMapping>
                <@createReturn applyOptionalAfterMapping=false>${resultName}<#if finalizerMethod??>.<@includeModel object=finalizerMethod /></#if></@createReturn>
            <#else>
                return ${returnType.null};
            </#if>
        </#if>
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
        throw new <@includeModel object=subclassExhaustiveException />("Not all subclasses are supported for this mapping. Missing for " + ${subclassMappings[0].sourceArgument}.getClass());
    <#else>
    <#if !existingInstanceMapping>
        <#if hasConstructorMappings()>
            <#if (sourceParameters?size > 1)>
                <#list sourceParametersNeedingPresenceCheck as sourceParam>
                    <#if (constructorPropertyMappingsByParameter(sourceParam)?size > 0)>
                        <#list constructorPropertyMappingsByParameter(sourceParam) as propertyMapping>
                            <@includeModel object=propertyMapping.targetType /> ${propertyMapping.targetWriteAccessorName} = ${propertyMapping.targetType.null};
                        </#list>
                        if ( <@includeModel object=getPresenceCheckByParameter(sourceParam) /> ) {
                        <#assign sourceParamReassignment = getSourceParameterReassignment(sourceParam)!'' />
                        <#if sourceParamReassignment?has_content>
                            <@includeModel object=sourceParamReassignment.type /> ${sourceParamReassignment.name} = ${sourceParam.name}.get();

                        </#if>
                        <#list constructorPropertyMappingsByParameter(sourceParam) as propertyMapping>
                            <@includeModel object=propertyMapping existingInstanceMapping=existingInstanceMapping defaultValueAssignment=propertyMapping.defaultValueAssignment/>
                        </#list>
                        }
                    </#if>
                </#list>
                <#list sourceParametersNotNeedingPresenceCheck as sourceParam>
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
                <#if mapNullToDefault>if ( <@includeModel object=getPresenceCheckByParameter(sourceParameters[0]) /> ) {</#if>
                <#assign sourceParamReassignment = getSourceParameterReassignment(sourceParameters[0])!'' />
                <#if sourceParamReassignment?has_content>
                    <@includeModel object=sourceParamReassignment.type /> ${sourceParamReassignment.name} = ${sourceParameters[0].name}.get();

                </#if>
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
        <#list sourceParametersNeedingPresenceCheck as sourceParam>
            <#if (propertyMappingsByParameter(sourceParam)?size > 0)>
                if ( <@includeModel object=getPresenceCheckByParameter(sourceParam) /> ) {
                    <#assign sourceParamReassignment = getSourceParameterReassignment(sourceParam)!'' />
                    <#if sourceParamReassignment?has_content>
                        <@includeModel object=sourceParamReassignment.type /> ${sourceParamReassignment.name} = ${sourceParam.name}.get();

                    </#if>
                    <#list propertyMappingsByParameter(sourceParam) as propertyMapping>
                        <@includeModel object=propertyMapping targetBeanName=resultName existingInstanceMapping=existingInstanceMapping defaultValueAssignment=propertyMapping.defaultValueAssignment/>
                    </#list>
                }
            </#if>
        </#list>
        <#list sourceParametersNotNeedingPresenceCheck as sourceParam>
            <#if (propertyMappingsByParameter(sourceParam)?size > 0)>
                <#list propertyMappingsByParameter(sourceParam) as propertyMapping>
                    <@includeModel object=propertyMapping targetBeanName=resultName existingInstanceMapping=existingInstanceMapping defaultValueAssignment=propertyMapping.defaultValueAssignment/>
                </#list>
            </#if>
        </#list>
    <#elseif !propertyMappingsByParameter(sourceParameters[0]).empty>
        <#if mapNullToDefault>if ( <@includeModel object=getPresenceCheckByParameter(sourceParameters[0]) /> ) {</#if>
        <#assign sourceParamReassignment = getSourceParameterReassignment(sourceParameters[0])!'' />
        <#if sourceParamReassignment?has_content>
            <@includeModel object=sourceParamReassignment.type /> ${sourceParamReassignment.name} = ${sourceParameters[0].name}.get();

        </#if>
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
            <#if (afterMappingReferencesWithFinalizedReturnType?size > 0)>
                <@includeModel object=finalizedReturnType /> ${finalizedResultName} = ${resultName}.<@includeModel object=finalizerMethod />;

                <#list afterMappingReferencesWithFinalizedReturnType as callback>
                    <#if callback_index = 0>

                    </#if>
                    <@includeModel object=callback targetBeanName=finalizedResultName targetType=finalizedReturnType/>
                </#list>

                <@createReturn>${finalizedResultName}</@createReturn>
            <#else>
                <@createReturn>${resultName}.<@includeModel object=finalizerMethod /></@createReturn>
            </#if>
        <#else>
            <@createReturn>${resultName}</@createReturn>
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
<#macro createReturn applyOptionalAfterMapping=true>
<#--    <@compress single_line=true>-->
        <#if returnType.optionalType>
             <#if (afterMappingReferencesWithOptionalReturnType?size > 0)>
                <@includeModel object=returnType /> ${optionalResultName} = <@includeModel object=returnType.asRawType()/>.of( <#nested/> );

                <#list afterMappingReferencesWithOptionalReturnType as callback>
                    <#if callback_index = 0>

                    </#if>
                    <@includeModel object=callback targetBeanName=optionalResultName targetType=returnType/>
                </#list>

                return ${optionalResultName};
            <#else>
                return <@includeModel object=returnType.asRawType()/>.of( <#nested/> );
            </#if>
        <#else>
            return <#nested/>;
        </#if>
<#--    </@compress>-->

</#macro>