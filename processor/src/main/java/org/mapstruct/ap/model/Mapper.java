/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.Generated;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import org.mapstruct.ap.util.TypeFactory;

/**
 * Represents a type implementing a mapper interface (annotated with {@code @Mapper}). This is the root object of the
 * mapper model.
 *
 * @author Gunnar Morling
 */
public class Mapper extends AbstractModelElement {

    private static final String IMPLEMENTATION_SUFFIX = "Impl";

    private final TypeFactory typeFactory;
    private final String packageName;
    private final boolean superTypeIsInterface;
    private final String interfaceName;
    private final String implementationName;
    private final List<Annotation> annotations;
    private final List<MappingMethod> mappingMethods;
    private final List<MapperReference> referencedMappers;
    private final Options options;

    private Mapper(TypeFactory typeFactory, String packageName, boolean superTypeIsInterface, String interfaceName,
                   String implementationName, List<MappingMethod> mappingMethods,
                   List<MapperReference> referencedMappers, Options options) {
        this.packageName = packageName;
        this.superTypeIsInterface = superTypeIsInterface;
        this.interfaceName = interfaceName;
        this.implementationName = implementationName;
        this.annotations = new ArrayList<Annotation>();
        this.mappingMethods = mappingMethods;
        this.referencedMappers = referencedMappers;
        this.options = options;
        this.typeFactory = typeFactory;
    }

    public static class Builder {

        private TypeFactory typeFactory;
        private TypeElement element;
        private List<MappingMethod> mappingMethods;
        private List<MapperReference> mapperReferences;
        private Options options;
        private Elements elementUtils;

        public Builder element(TypeElement element) {
            this.element = element;
            return this;
        }

        public Builder mappingMethods(List<MappingMethod> mappingMethods) {
            this.mappingMethods = mappingMethods;
            return this;
        }

        public Builder mapperReferences(List<MapperReference> mapperReferences) {
            this.mapperReferences = mapperReferences;
            return this;
        }

        public Builder options(Options options) {
            this.options = options;
            return this;
        }

        public Builder typeFactory(TypeFactory typeFactory) {
            this.typeFactory = typeFactory;
            return this;
        }

        public Builder elementUtils(Elements elementUtils) {
            this.elementUtils = elementUtils;
            return this;
        }

        public Mapper build() {
            return new Mapper(
                typeFactory,
                elementUtils.getPackageOf( element ).getQualifiedName().toString(),
                element.getKind() == ElementKind.INTERFACE ? true : false,
                element.getSimpleName().toString(),
                element.getSimpleName() + IMPLEMENTATION_SUFFIX,
                mappingMethods,
                mapperReferences,
                options
            );
        }
    }

    @Override
    public SortedSet<Type> getImportTypes() {
        SortedSet<Type> importedTypes = new TreeSet<Type>();
        importedTypes.add( typeFactory.getType( Generated.class ) );

        for ( MappingMethod mappingMethod : mappingMethods ) {
            for ( Type type : mappingMethod.getImportTypes() ) {
                addWithDependents( importedTypes, type );
            }
        }

        for ( MapperReference mapperReference : referencedMappers ) {
            for ( Type type : mapperReference.getImportTypes() ) {
                addWithDependents( importedTypes, type );
            }
        }

        for ( Annotation annotation : annotations ) {
            addWithDependents( importedTypes, annotation.getType() );
        }

        return importedTypes;
    }

    private void addWithDependents(Collection<Type> collection, Type typeToAdd) {
        if ( typeToAdd == null ) {
            return;
        }

        if ( typeToAdd.getPackageName() != null &&
            !typeToAdd.getPackageName().equals( packageName ) &&
            !typeToAdd.getPackageName().startsWith( "java.lang" ) ) {
            collection.add( typeToAdd );
        }

        addWithDependents( collection, typeToAdd.getImplementationType() );

        for ( Type type : typeToAdd.getTypeParameters() ) {
            addWithDependents( collection, type );
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder( "Mapper {" );

        sb.append( "\n    packageName='" + packageName + "\'," );
        sb.append( "\n    interfaceName='" + interfaceName + "\'," );
        sb.append( "\n    implementationName='" + implementationName + "\'," );
        sb.append( "\n    beanMappings=[" );

        for ( MappingMethod beanMapping : mappingMethods ) {
            sb.append( "\n        " + beanMapping.toString().replaceAll( "\n", "\n        " ) );
        }
        sb.append( "\n    ]" );
        sb.append( "\n    referencedMappers=" + referencedMappers );
        sb.append( "\n}," );

        return sb.toString();
    }

    public String getPackageName() {
        return packageName;
    }

    /**
     * Whether the mapper super-type is an interface or not.
     *
     * @return {@code true} if the mapper is generated from an interface, {@code false} when generated from an abstract
     *         class.
     */
    public boolean isSuperTypeInterface() {
        return superTypeIsInterface;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getImplementationName() {
        return implementationName;
    }

    public List<MappingMethod> getMappingMethods() {
        return mappingMethods;
    }

    public List<MapperReference> getReferencedMappers() {
        return referencedMappers;
    }

    public Options getOptions() {
        return options;
    }

    public void addAnnotation(Annotation annotation) {
        annotations.add( annotation );
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }
}
