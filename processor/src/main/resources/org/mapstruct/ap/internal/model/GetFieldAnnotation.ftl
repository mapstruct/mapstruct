<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.GetFieldAnnotation" -->
private static <T extends Annotation> T ${name}(Class<?> sourceType, String fieldName, Class<T> annotationClass) {
    try {
        return sourceType.getField( fieldName ).getAnnotation( annotationClass );
    }
    catch (NoSuchFieldException e) {
        throw new RuntimeException(e);
    }
}
