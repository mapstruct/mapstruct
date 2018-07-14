/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Meta-model of assignments. There are currently three types of assignment:
 * <ul>
 * <li>Simple</li>
 * <li>TypeConversion</li>
 * <li>MethodReference</li>
 * </ul>
 * The assignments can be wrapped. E.g. in a collection or map constructor, a null check, a try-catch, etc.
 */
package org.mapstruct.ap.internal.model.assignment;
