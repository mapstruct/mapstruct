<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.assignment.StreamAdderWrapper" -->
<#import "../macro/CommonMacros.ftl" as lib>
<@lib.handleExceptions>
  if ( ${sourceReference} != null ) {
      ${sourceReference}.forEach( ${ext.targetBeanName}::${ext.targetWriteAccessorName} );
  }
</@lib.handleExceptions>