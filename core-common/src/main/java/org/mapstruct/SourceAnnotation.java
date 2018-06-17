/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
 * Marks a mapping method parameter as SourceAnnotation.
 *
 * Annotations present on the source accessor (e.g. a 'get method') will be matched to this parameter. This gives the
 * user the option to act on this annotation in for instance a handwritten method.
 *
 * Example:
 *
 * <pre>
 * <code>
 *
 * public class SourceEntity {
 *
 *     &#64;MyAnnotation( text = "someInfo" )
 *     public NestedSourceEntity getNestedSource() {
 *         // ...
 *     }
 * }
 *
 * &#64;Mapper
 * public abstract class MyMapper{
 *
 *    ...
 *
 *    public abstract TargetDto toTargetDto(SourceEntity source);
 *
 *    public NestedTargetDto toTargetDto(NestedSourceEntity source, &#64;SourceAnnotation MyAnnotation annotation) {
 *        if ( "someInfo".equals( annotation.text() ) {
 *            // do things
 *        }
 *        else {
 *            // to other things
 *        }
 *    }
 * }
 *
 * // generates:
 *
 * public TargetDto toTargetDto(SourceEntity source, &#64;MyAnnotation annotation) {
 *     if ( source == null ) {
 *         return null;
 *     }
 *
 *     TargetDto targetDto = new TargetDto();
 *
 *     MyAnnotation sourceAnnotation = new MyAnnotation(){
 *
 *         &#64;Override
 *         public String text() {
 *             return  "someInfo";
 *         }
 *
 *         ...
 *     }
 *     target.setNestedTarget( mapToNestedTarget( source.getNestedSource(), sourceAnnotation ) );
 *
 *     return targetDto;
 * }
 * </code>
 * </pre>
 *
 * @author Sjaak Derksen
 * @since 1.3
 * @see TargetAnnotation
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.CLASS)
public @interface SourceAnnotation {

}
