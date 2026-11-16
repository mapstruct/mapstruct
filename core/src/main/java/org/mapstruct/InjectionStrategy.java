/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

/**
 * Strategy for handling injection. This is only used on annotated based component models such as CDI, Spring and
 * JSR330 / Jakarta.
 *
 * @author Kevin Grüneberg
 * @author Lucas Resch
 */
public enum InjectionStrategy {

    /** Annotations are written on the field **/
    FIELD,

    /** Annotations are written on the constructor **/
    CONSTRUCTOR,

    /** A dedicated setter method is created */
    SETTER
}
