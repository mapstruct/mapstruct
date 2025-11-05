/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import java.util.List;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.spi.util.IntrospectorUtils;

/**
 * AccessorNamingStrategy implementation that provides support for Protocol Buffers (protobuf) generated classes.
 * <p>
 * This strategy extends {@link DefaultAccessorNamingStrategy} to handle protobuf-specific naming patterns
 * for classes that implement {@code com.google.protobuf.MessageOrBuilder}.
 * <p>
 * Protobuf-specific patterns:
 * <ul>
 *   <li>Repeated fields: {@code getXxxList()} getters and {@code addXxx()} adders</li>
 *   <li>Map fields: {@code getXxxMap()} getters and {@code putXxx()}/{@code putAllXxx()} putters</li>
 * </ul>
 * <p>
 * Examples:
 * <pre>
 * // Protobuf repeated field "items"
 * public List&lt;Item&gt; getItemsList() { ... }  // Recognized as getter for "items" property
 * public Builder addItems(Item value) { ... }  // Recognized as adder for "items" property
 *
 * // Protobuf map field "attributes"
 * public Map&lt;String, String&gt; getAttributesMap() { ... }  // Recognized as getter for "attributes" property
 * public Builder putAttributes(String key, String value) { ... }  // Recognized as putter for "attributes" property
 * public Builder putAllAttributes(Map&lt;String, String&gt; map) { ... }  // Recognized as setter for "attributes" property
 * </pre>
 *
 * @author MapStruct Authors
 */
public class ProtobufAccessorNamingStrategy extends DefaultAccessorNamingStrategy {

    public static final String PROTOBUF_MESSAGE_OR_BUILDER = "com.google.protobuf.MessageOrBuilder";
    public static final String LIST_SUFFIX = "List";
    public static final String MAP_SUFFIX = "Map";

    private static final String GET_PREFIX = "get";
    private static final String PUT_PREFIX = "put";
    private static final String PUT_ALL_PREFIX = "putAll";

    private static final int MIN_LIST_GETTER_LENGTH = GET_PREFIX.length() + 1 + LIST_SUFFIX.length();
    private static final int MIN_MAP_GETTER_LENGTH = GET_PREFIX.length() + 1 + MAP_SUFFIX.length();
    private static final int MIN_PUTTER_LENGTH = PUT_PREFIX.length() + 1;
    private static final int MIN_PUT_ALL_LENGTH = PUT_ALL_PREFIX.length() + 1;

    protected TypeMirror protobufMessageOrBuilderType;

    @Override
    public void init(MapStructProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        TypeElement typeElement = elementUtils.getTypeElement(PROTOBUF_MESSAGE_OR_BUILDER);
        if (typeElement != null) {
            protobufMessageOrBuilderType = typeElement.asType();
        }
    }

    @Override
    public MethodType getMethodType(ExecutableElement method) {
        Element receiver = method.getEnclosingElement();

        // Only apply protobuf logic if the receiver is a protobuf type
        if (isProtobufType(receiver)) {
            String methodName = method.getSimpleName().toString();

            // Handle getXxxList() -> GETTER for repeated fields
            if (isProtobufListGetter(method)) {
                return MethodType.GETTER;
            }

            // Handle getXxxMap() -> GETTER for map fields
            if (isProtobufMapGetter(method)) {
                return MethodType.GETTER;
            }

            // Handle putXxx(K, V) -> PUTTER for map entries
            if (isPutterMethod(method)) {
                return MethodType.PUTTER;
            }

            // Handle putAllXxx(Map) -> SETTER for bulk map operations
            if (isPutAllMethod(method)) {
                return MethodType.SETTER;
            }
        }

        // Fall back to default behavior for standard JavaBeans patterns
        return super.getMethodType(method);
    }

    @Override
    public boolean isGetterMethod(ExecutableElement method) {
        if (!super.isGetterMethod(method)) {
            return false;
        }

        // Exclude protobuf-specific methods
        if (isBuilderListMethod(method) || isValueListMethod(method) || isGetAllFieldsMethod(method)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isSetterMethod(ExecutableElement method) {
        if (!super.isSetterMethod(method)) {
            return false;
        }

        // Exclude protobuf-specific methods
        if (isProtobufInternalMethod(method) || isRemoveMethod(method) || isMergeFromMethod(method)) {
            return false;
        }

        return true;
    }

    @Override
    public String getPropertyName(ExecutableElement getterOrSetterMethod) {
        Element receiver = getterOrSetterMethod.getEnclosingElement();

        // Only apply protobuf logic if the receiver is a protobuf type
        if (isProtobufType(receiver)) {
            String methodName = getterOrSetterMethod.getSimpleName().toString();

            // Handle getXxxList() -> "xxx"
            if (isProtobufListGetter(getterOrSetterMethod)) {
                String propertyPart = methodName.substring(
                    GET_PREFIX.length(),
                    methodName.length() - LIST_SUFFIX.length()
                );
                return IntrospectorUtils.decapitalize(propertyPart);
            }

            // Handle getXxxMap() -> "xxx"
            if (isProtobufMapGetter(getterOrSetterMethod)) {
                String propertyPart = methodName.substring(
                    GET_PREFIX.length(),
                    methodName.length() - MAP_SUFFIX.length()
                );
                return IntrospectorUtils.decapitalize(propertyPart);
            }

            // Handle putAllXxx() -> "xxx"
            if (isPutAllMethod(getterOrSetterMethod)) {
                return IntrospectorUtils.decapitalize(methodName.substring(PUT_ALL_PREFIX.length()));
            }
        }

        // Fall back to default behavior
        return super.getPropertyName(getterOrSetterMethod);
    }

    @Override
    public String getElementName(ExecutableElement adderMethod) {
        Element receiver = adderMethod.getEnclosingElement();
        String methodName = super.getElementName(adderMethod);

        // For protobuf types, append "List" suffix to adder element names
        // This matches the protobuf pattern: addItem() -> itemsList property
        if (isProtobufType(receiver)) {
            methodName += LIST_SUFFIX;
        }

        // Handle putXxx() -> "xxx" for putter methods
        if (isProtobufType(receiver) && isPutterMethod(adderMethod)) {
            return IntrospectorUtils.decapitalize(
                adderMethod.getSimpleName().toString().substring(PUT_PREFIX.length())
            ) + MAP_SUFFIX;
        }

        return methodName;
    }

    @Override
    public boolean isAdderMethod(ExecutableElement method) {
        // First check if this is a putter - if so, it's not an adder
        if (isPutterMethod(method)) {
            return false;
        }

        // Otherwise use the default logic
        return super.isAdderMethod(method);
    }

    /**
     * Checks if the given element is a protobuf MessageOrBuilder type.
     *
     * @param element the element to check
     * @return true if the element is assignable to MessageOrBuilder
     */
    protected boolean isProtobufType(Element element) {
        return element != null
            && protobufMessageOrBuilderType != null
            && typeUtils.isAssignable(element.asType(), protobufMessageOrBuilderType);
    }

    /**
     * Checks if the method is a protobuf-style getter for a repeated field (ends with "List").
     * Example: {@code getItemsList()} for repeated field "items"
     *
     * @param method the method to check
     * @return true if this is a protobuf list getter
     */
    protected boolean isProtobufListGetter(ExecutableElement method) {
        if (!method.getParameters().isEmpty()) {
            return false;
        }

        String methodName = method.getSimpleName().toString();
        return methodName.startsWith(GET_PREFIX)
            && methodName.endsWith(LIST_SUFFIX)
            && methodName.length() >= MIN_LIST_GETTER_LENGTH
            && method.getReturnType().getKind() != TypeKind.VOID;
    }

    /**
     * Checks if the method is a protobuf-style getter for a map field (ends with "Map").
     * Example: {@code getAttributesMap()} for map field "attributes"
     *
     * @param method the method to check
     * @return true if this is a protobuf map getter
     */
    protected boolean isProtobufMapGetter(ExecutableElement method) {
        if (!method.getParameters().isEmpty()) {
            return false;
        }

        String methodName = method.getSimpleName().toString();
        return methodName.startsWith(GET_PREFIX)
            && methodName.endsWith(MAP_SUFFIX)
            && methodName.length() >= MIN_MAP_GETTER_LENGTH
            && method.getReturnType().getKind() != TypeKind.VOID;
    }

    /**
     * Checks if the method is a protobuf-style putter for map entries.
     * Example: {@code putAttribute(String key, String value)} for map field "attributes"
     *
     * @param method the method to check
     * @return true if this is a putter method
     */
    protected boolean isPutterMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();

        // Must start with "put", have exactly 2 parameters (key, value), and not be "putAll"
        return methodName.startsWith(PUT_PREFIX)
            && !methodName.startsWith(PUT_ALL_PREFIX)
            && methodName.length() >= MIN_PUTTER_LENGTH
            && method.getParameters().size() == 2;
    }

    /**
     * Checks if the method is a protobuf-style bulk putter for maps.
     * Example: {@code putAllAttributes(Map<String, String> map)} for map field "attributes"
     *
     * @param method the method to check
     * @return true if this is a putAll method
     */
    protected boolean isPutAllMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();

        // Must start with "putAll" and have exactly 1 parameter (the map)
        return methodName.startsWith(PUT_ALL_PREFIX)
            && methodName.length() >= MIN_PUT_ALL_LENGTH
            && method.getParameters().size() == 1;
    }

    /**
     * Checks if the method ends with "BuilderList" and returns a List.
     */
    private boolean isBuilderListMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();
        String returnTypeName = typeUtils.erasure(method.getReturnType()).toString();
        return methodName.endsWith("BuilderList") && returnTypeName.equals(List.class.getName());
    }

    /**
     * Checks if the method ends with "ValueList" and returns a List.
     */
    private boolean isValueListMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();
        String returnTypeName = typeUtils.erasure(method.getReturnType()).toString();
        return methodName.endsWith("ValueList") && returnTypeName.equals(List.class.getName());
    }

    /**
     * Checks if the method is getAllFields() returning a Map.
     */
    private boolean isGetAllFieldsMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();
        String returnTypeName = typeUtils.erasure(method.getReturnType()).toString();
        return "getAllFields".equals(methodName) && returnTypeName.equals(Map.class.getName());
    }

    /**
     * Checks if the method takes a protobuf internal type as parameter.
     */
    private boolean isProtobufInternalMethod(ExecutableElement method) {
        if (method.getParameters().isEmpty()) {
            return false;
        }
        String argTypeName = method.getParameters().get(0).asType().toString();
        return argTypeName.startsWith("com.google.protobuf");
    }

    /**
     * Checks if the method is a remove method taking an int parameter.
     */
    private boolean isRemoveMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();
        if (!methodName.startsWith("remove") || method.getParameters().isEmpty()) {
            return false;
        }
        String argTypeName = method.getParameters().get(0).asType().toString();
        return argTypeName.equals("int");
    }

    /**
     * Checks if the method is mergeFrom().
     */
    private boolean isMergeFromMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();
        return "mergeFrom".equals(methodName);
    }
}
