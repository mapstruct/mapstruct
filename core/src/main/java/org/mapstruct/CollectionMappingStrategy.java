/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

/**
 * Strategy for propagating the value of collection-typed properties from source to target.
 * <p>
 * In the table below, the dash {@code -} indicates a property name.
 * Next, the trailing {@code s} indicates the plural form.
 * The table explains the options and how they are applied to the presence / absence of a
 * {@code set-s}, {@code add-} and / or {@code get-s} method on the target object.
 * <p>
 * <table summary="Collection mapping strategy options">
 *     <tr>
 *         <th>Option</th>
 *         <th>Only target set-s Available</th>
 *         <th>Only target add- Available</th>
 *         <th>Both set-s/add- Available</th>
 *         <th>No set-s/add- Available</th>
 *         <th>Existing Target ({@code @TargetType})</th>
 *     </tr>
 *     <tr>
 *         <td>{@link #ACCESSOR_ONLY}</td>
 *         <td>set-s</td>
 *         <td>get-s</td>
 *         <td>set-s</td>
 *         <td>get-s</td>
 *         <td>get-s</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #SETTER_PREFERRED}</td>
 *         <td>set-s</td>
 *         <td>add-</td>
 *         <td>set-s</td>
 *         <td>get-s</td>
 *         <td>get-s</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #ADDER_PREFERRED}</td>
 *         <td>set-s</td>
 *         <td>add-</td>
 *         <td>add-</td>
 *         <td>get-s</td>
 *         <td>get-s</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #TARGET_IMMUTABLE}</td>
 *         <td>set-s</td>
 *         <td>exception</td>
 *         <td>set-s</td>
 *         <td>exception</td>
 *         <td>set-s</td>
 *     </tr>
 * </table>
 *
 * @author Sjaak Derksen
 */
public enum CollectionMappingStrategy {

    /**
     * The setter of the target property will be used to propagate the value:
     * {@code orderDto.setOrderLines(order.getOrderLines)}.
     * <p>
     * If no setter is available but a getter method, this will be used, under the assumption it has been initialized:
     * {@code orderDto.getOrderLines().addAll(order.getOrderLines)}. This will also be the case when using
     * {@link MappingTarget} (updating existing instances).
     */
    ACCESSOR_ONLY,

    /**
     * If present, the setter of the target property will be used to propagate the value:
     * {@code orderDto.setOrderLines(order.getOrderLines)}.
     * <p>
     * If no setter but and adder method is present, that adder will be invoked for each element of the source
     * collection: {@code order.addOrderLine(orderLine() )}.
     * <p>
     * If neither a setter nor an adder method but a getter for the target property is present, that getter will be
     * used, assuming it returns an initialized collection: If no setter is available, MapStruct will first look for an
     * adder method before resorting to a getter.
     */
    SETTER_PREFERRED,

    /**
     * Identical to {@link #SETTER_PREFERRED}, only that adder methods will be preferred over setter methods, if both
     * are present for a given collection-typed property.
     */
    ADDER_PREFERRED,

    /**
     * Identical to {@link #SETTER_PREFERRED}, however the target collection will not be cleared and accessed via
     * addAll in case of updating existing bean instances, see: {@link MappingTarget}.
     *
     * Instead the target accessor (e.g. set) will be used on the target bean to set the collection.
     */
    TARGET_IMMUTABLE;
}
