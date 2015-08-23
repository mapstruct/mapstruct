/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
 * Specifies a decorator to be applied to a generated mapper, which e.g. can be used to amend mappings performed by
 * generated mapping methods.
 * <p>
 * A typical decorator implementation will be an abstract class and only implement/override a subset of the methods of
 * the mapper type which it decorates. All methods not implemented or overridden by the decorator will be implemented by
 * the code generator by delegating to the generated mapper implementation.
 * <p>
 * <b>NOTE:</b> The usage of decorated mappers differs depending on the selected component model.
 * <p>
 * <b>NOTE:</b> This annotation is not supported for the component model {@code cdi}. Use CDI's own
 * <a href="https://docs.jboss.org/cdi/spec/1.0/html/decorators.html">{@code @Decorator}</a> feature instead.
 * <p>
 * <b>NOTE:</b> The decorator feature when used with component model {@code jsr330} is considered <em>experimental</em>
 * and it may change in future releases.
 * <p>
 * <h2>Examples</h2>
 * <p>
 * For the examples below, consider the following mapper declaration:
 *
 * <pre>
 * &#64;Mapper(componentModel = "...")
 * &#64;DecoratedWith(PersonMapperDecorator.class)
 * public interface PersonMapper {
 *
 *     &#64;Mapping(target = "name", ignore = true)
 *     PersonDto personToPersonDto(Person person);
 *
 *     AddressDto addressToAddressDto(Address address); // not touched by the decorator
 * }
 * </pre>
 *
 * <h3>1. Component model 'default'</h3>
 * <h4>Referencing the original mapper in the decorator</h4>
 * <p>
 * If a constructor with a single parameter accepting the type of the decorated mapper is present, a delegate with
 * generated implementations of all the mapper methods will be passed to this constructor. A typical implementation will
 * store the passed delegate in a field of the decorator and make use of it in the decorator methods:
 *
 * <pre>
 * public abstract class PersonMapperDecorator implements PersonMapper {
 *
 *     private PersonMapper delegate;
 *
 *     public PersonMapperDecorator(PersonMapper delegate) {
 *         this.delegate = delegate;
 *     }
 *
 *     &#64;Override
 *     public PersonDto personToPersonDto(Person person) {
 *         PersonDto dto = delegate.personToPersonDto( person );
 *         dto.setName( person.getFirstName() + " " + person.getLastName() );
 *
 *         return dto;
 *     }
 * }
 * </pre>
 *
 * <h4>Using the decorated mapper</h4>
 * <p>
 * Nothing special needs to be done. When using {@code Mappers.getMapper( PersonMapper.class )}, the <em>decorator</em>
 * is returned, with the injected original mapper.
 * <h3>2. Component model 'spring'</h3>
 * <h4>Referencing the original mapper in the decorator</h4>
 * <p>
 * The generated implementation of the original mapper is annotated with the Spring annotation
 * {@code @Qualifier("delegate")}. To autowire that bean in your decorator, add that qualifier annotation as well:
 *
 * <pre>
 * public abstract class PersonMapperDecorator implements PersonMapper {
 *
 *     &#64;Autowired
 *     &#64;Qualifier("delegate")
 *     private PersonMapper delegate;
 *
 *     &#64;Override
 *     public PersonDto personToPersonDto(Person person) {
 *         PersonDto dto = delegate.personToPersonDto( person );
 *         dto.setName( person.getFirstName() + " " + person.getLastName() );
 *
 *         return dto;
 *     }
 * }
 * </pre>
 *
 * <h4>Using the decorated mapper in the decorator</h4>
 * <p>
 * The generated class that extends the decorator is annotated with Spring's {@code @Primary} annotation. To autowire
 * the decorated mapper in the application, nothing special needs to be done:
 *
 * <pre>
 * &#64;Autowired
 * private PersonMapper personMapper; // injects the decorator, with the injected original mapper
 * </pre>
 *
 * <h3>3. Component model 'jsr330'</h3>
 * <h4>Referencing the original mapper</h4>
 * <p>
 * JSR 330 doesn't specify qualifiers and only allows to specifically name the beans. Hence, the generated
 * implementation of the original mapper is annotated with {@code @Named("fully-qualified-name-of-generated-impl")}
 * (please note that when using a decorator, the class name of the mapper implementation ends with an underscore). To
 * inject that bean in your decorator, add the same annotation to the delegate field (e.g. by copy/pasting it from the
 * generated class):
 *
 * <pre>
 * public abstract class PersonMapperDecorator implements PersonMapper {
 *
 *     &#64;Inject
 *     &#64;Named("org.examples.PersonMapperImpl_")
 *     private PersonMapper delegate;
 *
 *     &#64;Override
 *     public PersonDto personToPersonDto(Person person) {
 *         PersonDto dto = delegate.personToPersonDto( person );
 *         dto.setName( person.getFirstName() + " " + person.getLastName() );
 *
 *         return dto;
 *     }
 * }
 * </pre>
 *
 * <h4>Using the decorated mapper in the decorator</h4>
 * <p>
 * Unlike with the other component models, the usage site must be aware if a mapper is decorated or not, as for
 * decorated mappers, the parameterless {@code @Named} annotation must be added to select the <em>decorator</em> to be
 * injected:
 *
 * <pre>
 * &#64;Inject
 * &#64;Named
 * private PersonMapper personMapper; // injects the decorator, with the injected original mapper
 * </pre>
 * <p>
 *
 * @author Gunnar Morling
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface DecoratedWith {

    /**
     * The decorator type. Must be an abstract class that extends or implements the mapper type to which it is applied.
     * <p>
     * For component-model {@code default}, the decorator type must either have a default constructor or a constructor
     * with a single parameter accepting the type of the decorated mapper.
     *
     * @return the decorator type
     */
    Class<?> value();
}
