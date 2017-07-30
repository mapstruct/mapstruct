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

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * Represents a constructor that is used for constructor injection.
 *
 * @author Kevin Grüneberg
 */
public class AnnotatedConstructor extends ModelElement implements Constructor {

    private final String name;
    private final List<AnnotationMapperReference> mapperReferences;
    private final List<Annotation> annotations;
    private final boolean publicEmptyConstructor;

    public AnnotatedConstructor(String name, List<AnnotationMapperReference> mapperReferences,
                                List<Annotation> annotations, boolean publicEmptyConstructor) {
        this.name = name;
        this.mapperReferences = mapperReferences;
        this.annotations = annotations;
        this.publicEmptyConstructor = publicEmptyConstructor;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = new HashSet<Type>();

        for ( MapperReference mapperReference : mapperReferences ) {
            types.addAll( mapperReference.getImportTypes() );
        }

        for ( Annotation annotation : annotations ) {
            types.addAll( annotation.getImportTypes() );
        }

        return types;
    }

    @Override
    public String getName() {
        return name;
    }

    public List<AnnotationMapperReference> getMapperReferences() {
        return mapperReferences;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public boolean isPublicEmptyConstructor() {
        return publicEmptyConstructor;
    }
}
