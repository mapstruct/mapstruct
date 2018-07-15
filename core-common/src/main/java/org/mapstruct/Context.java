/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a parameter of a method to be treated as <em>mapping context</em>. Such parameters are passed to other mapping
 * methods, {@code @}{@link ObjectFactory} methods or {@code @}{@link BeforeMapping}/{@code @}{@link AfterMapping}
 * methods when applicable and can thus be used in custom code.
 * <p>
 * The type of an {@code @Context} parameter is also inspected for
 * {@code @}{@link BeforeMapping}/{@code @}{@link AfterMapping} methods, which are called on the provided context
 * parameter value if applicable.
 * <p>
 * <strong>Note:</strong> no {@code null} checks are performed before calling before/after mapping methods or object
 * factory methods on {@code @}{@link Context} annotated parameters. The caller needs to make sure that no {@code null}
 * are passed in that case.
 * <p>
 * For generated code to call a method that is declared with {@code @Context} parameters, the declaration of the mapping
 * method being generated needs to contain at least those (or assignable) {@code @Context} parameters as well. MapStruct
 * will not create new instances of missing {@code @Context} parameters nor will it pass {@code null} instead.
 * <p>
 * <strong>Example 1:</strong> Using {@code @Context} parameters for passing data down to hand-written property mapping
 * methods and {@code @}{@link BeforeMapping} methods:
 *
 * <pre>
 * <code>
 * // multiple &#64;Context parameters can be added
 * public abstract CarDto toCar(Car car, &#64;Context VehicleRegistration context, &#64;Context Locale localeToUse);
 *
 * protected OwnerManualDto translateOwnerManual(OwnerManual ownerManual, &#64;Context Locale locale) {
 *     // manually implemented logic to translate the OwnerManual with the given Locale
 * }
 *
 * &#64;BeforeMapping
 * protected void registerVehicle(Vehicle mappedVehicle, &#64;Context VehicleRegistration context) {
 *     context.register( mappedVehicle );
 * }
 *
 * &#64;BeforeMapping
 * protected void notCalled(Vehicle mappedVehicle, &#64;Context DifferentMappingContextType context) {
 *     // not called, because no context parameter of type DifferentMappingContextType is available
 *     // within toCar(Car, VehicleRegistration, Locale)
 * }
 *
 * // generates:
 *
 * public CarDto toCar(Car car, VehicleRegistration context, Locale localeToUse) {
 *     registerVehicle( car, context );
 *
 *     if ( car == null ) {
 *         return null;
 *     }
 *
 *     CarDto carDto = new CarDto();
 *
 *     carDto.setOwnerManual( translateOwnerManual( car.getOwnerManual(), localeToUse );
 *     // more generated mapping code
 *
 *     return carDto;
 * }
 * </code>
 * </pre>
 * <p>
 * <strong>Example 2:</strong> Using an {@code @Context} parameter with a type that provides its own {@code @}
 * {@link BeforeMapping} methods to handle cycles in Graph structures:
 *
 * <pre>
 * <code>
 * // type of the context parameter
 * public class CyclicGraphContext {
 *     private Map&lt;Object, Object&gt; knownInstances = new IdentityHashMap&lt;&gt;();
 *
 *     &#64;BeforeMapping
 *     public &lt;T extends NodeDto&gt; T getMappedInstance(Object source, &#64;TargetType Class&lt;T&gt; targetType) {
 *         return (T) knownInstances.get( source );
 *     }
 *
 *     &#64;BeforeMapping
 *     public void storeMappedInstance(Object source, &#64;MappingTarget NodeDto target) {
 *         knownInstances.put( source, target );
 *     }
 * }
 *
 * &#64;Mapper
 * public interface GraphMapper {
 *     NodeDto toNodeDto(Node node, &#64;Context CyclicGraphContext cycleContext);
 * }
 *
 *
 * // generates:
 *
 * public NodeDto toNodeDto(Node node, CyclicGraphContext cycleContext) {
 *     NodeDto target = cycleContext.getMappedInstance( node, NodeDto.class );
 *     if ( target != null ) {
 *         return target;
 *     }
 *
 *     if ( node == null ) {
 *         return null;
 *     }
 *
 *     NodeDto nodeDto = new NodeDto();
 *
 *     cycleContext.storeMappedInstance( node, nodeDto );
 *
 *     nodeDto.setParent( toNodeDto( node.getParent(), cycleContext ) );
 *     List&lt;NodeDto&gt; list = nodeListToNodeDtoList( node.getChildren(), cycleContext );
 *     if ( list != null ) {
 *         nodeDto.setChildren( list );
 *     }
 *
 *     // more mapping code
 *
 *     return nodeDto;
 * }
 * </code>
 * </pre>
 * <p>
 * <strong>Example 3:</strong> Using {@code @Context} parameters for creating an entity object by calling an
 * {@code @}{@link ObjectFactory} methods:
 *
 * <pre>
 * <code>
 * // type of the context parameter
 * public class ContextObjectFactory {
 *     &#64;PersistenceContext(unitName = "my-unit")
 *     private EntityManager em;
 *
 *     &#64;ObjectFactory
 *     public Valve create( String id ) {
 *        Query query = em.createNamedQuery("Valve.findById");
 *        query.setParameter("id", id);
 *        Valve result = query.getSingleResult();
 *        if ( result != null ) {
 *            result = new Valve( id );
 *        }
 *        return result;
 *     }
 *
 * }
 *
 * &#64;Mapper
 * public interface ContextWithObjectFactoryMapper {
 *     Valve map(ValveDto dto, &#64;Context ContextObjectFactory factory, String id);
 * }
 *
 *
 * // generates:
 * public class ContextWithObjectFactoryMapperImpl implements ContextWithObjectFactoryMapper {
 *
 *   &#64;Override
 *   public Valve map(ValveDto dto, ContextObjectFactory factory, String id) {
 *       if ( dto == null ) {
 *           return null;
 *       }
 *
 *       Valve valve = factory.create( id );
 *
 *       valve.setOneWay( dto.isOneWay() );
 *
 *       return valve;
 *   }
 * }
 * </code>
 * </pre>
 * @author Andreas Gudian
 * @since 1.2
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.CLASS)
public @interface Context {

}
