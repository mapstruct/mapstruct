<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
public ${name}() {
    this( new ${delegateName}() );
}

private ${name}(${delegateName} delegate) {
    <#if invokeSuperConstructor>
    super( delegate );
    </#if>
    this.delegate = delegate;
}