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

import java.util.regex.Pattern;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;

import org.mapstruct.ap.spi.MappingExclusionProvider;

/**
 * The default implementation of the {@link MappingExclusionProvider} service provider interface.
 *
 * With the default implementation, MapStruct will not consider classes in the {@code java} and {@code javax} package
 * as source / target for an automatic sub-mapping. The only exception is the {@link java.util.Collection},
 * {@link java.util.Map} and {@link java.util.stream.Stream} types.
 *
 * @author Filip Hrisafov
 * @since 1.2
 */
class DefaultMappingExclusionProvider implements MappingExclusionProvider {
    private static final Pattern JAVA_JAVAX_PACKAGE = Pattern.compile( "^javax?\\..*" );

    @Override
    public boolean isExcluded(TypeElement typeElement) {
        Name name = typeElement.getQualifiedName();
        return name.length() != 0 && isFullyQualifiedNameExcluded( name );
    }

    protected boolean isFullyQualifiedNameExcluded(Name name) {
        return JAVA_JAVAX_PACKAGE.matcher( name ).matches();
    }
}
