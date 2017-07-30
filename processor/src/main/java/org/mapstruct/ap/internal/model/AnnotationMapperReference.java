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
package org.mapstruct.ap.internal.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Type;

/**
 * Mapper reference which is retrieved via Annotation-based dependency injection.<br>
 * The dependency injection may vary between field and constructor injection. Thus, it is possible to define, whether to
 * include annotations on the field.
 *
 * @author Gunnar Morling
 * @author Andreas Gudian
 * @author Kevin Grüneberg
 */
public class AnnotationMapperReference extends MapperReference {

    private final List<Annotation> annotations;

    private final boolean fieldFinal;

    private final boolean includeAnnotationsOnField;

    public AnnotationMapperReference(Type type, String variableName, List<Annotation> annotations, boolean isUsed,
                                     boolean fieldFinal, boolean includeAnnotationsOnField) {
        super( type, variableName, isUsed );
        this.annotations = annotations;
        this.fieldFinal = fieldFinal;
        this.includeAnnotationsOnField = includeAnnotationsOnField;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = new HashSet<Type>();
        types.add( getType() );

        for ( Annotation annotation : annotations ) {
            types.addAll( annotation.getImportTypes() );
        }

        return types;
    }

    public boolean isFieldFinal() {
        return fieldFinal;
    }

    public boolean isIncludeAnnotationsOnField() {
        return includeAnnotationsOnField;
    }

    public AnnotationMapperReference withNewAnnotations(List<Annotation> annotations) {
        return new AnnotationMapperReference(
            getType(),
            getVariableName(),
            annotations,
            isUsed(),
            isFieldFinal(),
            isIncludeAnnotationsOnField() );
    }
}
