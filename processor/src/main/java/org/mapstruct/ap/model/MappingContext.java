/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import org.mapstruct.ap.model.common.TypeFactory;
import org.mapstruct.ap.model.source.SourceMethod;
import org.mapstruct.ap.option.Options;
import org.mapstruct.ap.prism.MapperPrism;
import org.mapstruct.ap.util.MapperConfig;

/**
 * This class provides the context for the builders.
 *
 * <p>
 * The following mappers make use of this context:
 * <ul>
 * <li>{@link BeanMappingMethod.Builder}</li>
 * <li>{@link PropertyMappingMethod.Builder}</li>
 * <li>{@link IterableMappingMethod.Builder}</li>
 * <li>{@link MapMappingMethod.Builder}</li>
 * <li>{@link EnumMappingMethod.Builder}</li>
 * </ul>
 * </p>
 * <p>
 * The context provides:
 * <ul>
 * <li>Input for the building process, such as the source model (mapping methods found) and mapper references.</li>
 * <li>Required factory, utility, reporting methods for building the mappings.</li>
 * <li>Means to harbor results produced by the builders, such as forged- and virtual mapping methods that should be
 * generated in a later stage.</li>
 * </ul>
 * </p>
 *
 * @author Sjaak Derksen
 */
public class MappingContext {

    private final TypeFactory typeFactory;
    private final Elements elementUtils;
    private final Types typeUtils;
    private final Messager messager;
    private final Options options;
    private final TypeElement mapperTypeElement;
    private final List<SourceMethod> sourceModel;
    private final List<MapperReference> mapperReferences;
    private MappingResolver mappingResolver;
    private final List<MappingMethod> mappingsToGenerate = new ArrayList<MappingMethod>();

    /**
     * Private methods which are not present in the original mapper interface and are added to map certain property
     * types.
     */
    private final Set<VirtualMappingMethod> usedVirtualMappings = new HashSet<VirtualMappingMethod>();



    public MappingContext( TypeFactory typeFactory,
            Elements elementUtils,
            Types typeUtils,
            Messager messager,
            Options options,
            TypeElement mapper,
            List<SourceMethod> sourceModel ) {
        this.typeFactory = typeFactory;
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
        this.messager = messager;
        this.options = options;
        this.mapperTypeElement = mapper;
        this.sourceModel = sourceModel;
        this.mapperReferences = initReferencedMappers( mapper );
    }

    public TypeElement getMapperTypeElement() {
        return mapperTypeElement;
    }

    public List<SourceMethod> getSourceModel() {
        return sourceModel;
    }

    public List<MapperReference> getMapperReferences() {
        return mapperReferences;
    }

    public TypeFactory getTypeFactory() {
        return typeFactory;
    }

    public Elements getElementUtils() {
        return elementUtils;
    }

    public Types getTypeUtils() {
        return typeUtils;
    }

    public Messager getMessager() {
        return messager;
    }

    public Options getOptions() {
        return options;
    }

    public MappingResolver getMappingResolver() {
        return mappingResolver;
    }

    public void setMappingResolver(MappingResolver mappingResolver) {
        this.mappingResolver = mappingResolver;
    }

    public List<MappingMethod> getMappingsToGenerate() {
        return mappingsToGenerate;
    }

    public Set<VirtualMappingMethod> getUsedVirtualMappings() {
        return usedVirtualMappings;
    }

    private List<MapperReference> initReferencedMappers(TypeElement element) {
        List<MapperReference> result = new LinkedList<MapperReference>();
        List<String> variableNames = new LinkedList<String>();

        MapperConfig mapperPrism = MapperConfig.getInstanceOn( element );

        for ( TypeMirror usedMapper : mapperPrism.uses() ) {
            DefaultMapperReference mapperReference = DefaultMapperReference.getInstance(
                typeFactory.getType( usedMapper ),
                MapperPrism.getInstanceOn( typeUtils.asElement( usedMapper ) ) != null,
                typeFactory,
                variableNames
            );

            result.add( mapperReference );
            variableNames.add( mapperReference.getVariableName() );
        }

        return result;
    }



}
