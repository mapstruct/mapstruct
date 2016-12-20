/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a parameter of a method to be treated as <em>mapping context</em>. Such parameters are passed to other mapping
 * methods, {@link ObjectFactory} methods or {@link BeforeMapping}/{@link AfterMapping} methods when applicable and can
 * thus be used in custom code. The {@link Context} parameters are otherwise ignored by MapStruct.
 * <p>
 * For generated code to call a method that is declared with {@link Context} parameters, the declaration of the mapping
 * method being generated needs to contain at least those (or assignable) {@link Context} parameters as well. MapStruct
 * will not create new instances of missing {@link Context} parameters nor will it pass {@code null} instead.
 * <p>
 * Example:
 *
 * <pre>
 * <code>
 * public abstract CarDto toCar(Car car, &#64;Context MyMappingContext context);
 *
 * &#64;BeforeMapping
 * protected void registerVehicle(Vehicle mappedVehicle, &#64;Context MyMappingContext context) {
 *     context.doSomethingWithTheVehicle( mappedVehicle );
 * }
 *
 * &#64;BeforeMapping
 * protected void logMappedVehicle(Vehicle mappedVehicle) {
 *     // do something with the vehicle
 * }
 *
 * &#64;BeforeMapping
 * protected void notCalled(Vehicle mappedVehicle, &#64;Context DifferentMappingContextType context) {
 *     // not called, because DifferentMappingContextType is not available within toCar(Car, MyMappingContext)
 * }
 *
 * // generates:
 *
 * public CarDto toCar(Car car, MyMappingContext context) {
 *     registerVehicle( car, context );
 *     logMappedVehicle( car );
 *
 *     if ( car == null ) {
 *         return null;
 *     }
 *
 *     CarDto carDto = new CarDto();
 *
 *     // actual mapping code
 *
 *     return carDto;
 * }
 * </code>
 * </pre>
 *
 * @author Andreas Gudian
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.CLASS)
public @interface Context {

}
