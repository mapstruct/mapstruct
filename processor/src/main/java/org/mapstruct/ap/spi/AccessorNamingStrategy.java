/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import javax.lang.model.element.ExecutableElement;

/**
 * A service provider interface for the mapping between method names and properties.
 *
 * @author Christian Schuster
 * @author Gunnar Morling
 */
public interface AccessorNamingStrategy {

    /**
     * Initializes the accessor naming strategy with the MapStruct processing environment.
     *
     * @param processingEnvironment environment for facilities
     *
     * @since 1.3
     */
    default void init(MapStructProcessingEnvironment processingEnvironment) {

    }

    /**
     * Returns the type of the given method.
     *
     * @param method to be analyzed.
     *
     * @return the method type.
     */
    MethodType getMethodType(ExecutableElement method);

    /**
     * Returns the name of the property represented by the given getter or setter method.
     * <p>
     * The default implementation will e.g. return "name" for {@code public String getName()} or {@code public void
     * setName(String name)}.
     *
     * @param getterOrSetterMethod to be analyzed.
     *
     * @return property name derived from the getterOrSetterMethod
     */
    String getPropertyName(ExecutableElement getterOrSetterMethod);

    /**
     * Returns the element name of the given adder method.
     * <p>
     * The default implementation will e.g. return "item" for {@code public void addItem(String item)}.
     *
     * @param adderMethod to be getterOrSetterMethod.
     *
     * @return getter name for collections
     */
    String getElementName(ExecutableElement adderMethod);


    /**
     * Returns the getter name of the given collection property.
     * <p>
     * The default implementation will e.g. return "getItems" for "items".
     *
     * @param property to be getterOrSetterMethod.
     *
     * @return getter name for collection properties
     *
     * @deprecated MapStruct will not call this method anymore. Use {@link #getMethodType(ExecutableElement)} to
     * determine the {@link MethodType}. When collections somehow need to be treated special, it should be done in
     * {@link #getMethodType(ExecutableElement) } as well. In the future, this method will be removed.
     */
    @Deprecated
    String getCollectionGetterName(String property);
}
