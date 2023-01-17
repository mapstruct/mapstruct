<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.GetMethodAnnotation" -->
private static <T extends Annotation> T ${name}(Class<?> sourceType, String methodName, Class<T> annotationClass) {
    try {
        return sourceType.getMethod( methodName ).getAnnotation( annotationClass );
    }
    catch (NoSuchMethodException e) {
        throw new RuntimeException(e);
    }
}
