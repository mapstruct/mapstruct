<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#import "../macro/CommonMacros.ftl" as lib>
<@lib.handleExceptions>
    <@lib.sourceLocalVarAssignment/>
    <@lib.handleSourceReferenceNullCheck>
        ${ext.targetBeanName}.${ext.targetWriteAccessorName}<@lib.handleWrite><@lib.handleAssignment/></@lib.handleWrite>;
    </@lib.handleSourceReferenceNullCheck>
</@lib.handleExceptions>