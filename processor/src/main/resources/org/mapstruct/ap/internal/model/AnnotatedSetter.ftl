 <#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
 <#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.AnnotatedSetter" -->
 <#list methodAnnotations as annotation>
     <#nt><@includeModel object=annotation/>
 </#list>
public void set${fieldName?cap_first}(<#list parameterAnnotations as annotation><#nt><@includeModel object=annotation/> </#list><@includeModel object=type/> ${fieldName}) {
    this.${fieldName} = ${fieldName};
}
