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
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.Generated;

import org.mapstruct.ap.model.common.Accessibility;
import org.mapstruct.ap.model.common.ModelElement;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.TypeFactory;

/**
 * A type generated by MapStruct, e.g. representing a mapper type.
 *
 * @author Gunnar Morling
 */
public abstract class GeneratedType extends ModelElement {

    private final String packageName;
    private final String name;
    private final String superClassName;
    private final String interfaceName;

    private final List<Annotation> annotations;
    private final List<MappingMethod> methods;
    private final List<? extends ModelElement> fields;
    private final Initializers constructors;

    private final boolean suppressGeneratorTimestamp;
    private final Accessibility accessibility;

    /**
     * Type representing the {@code @Generated} annotation
     */
    private final Type generatedType;

    protected GeneratedType(TypeFactory typeFactory, String packageName, String name, String superClassName,
                            String interfaceName,
                            List<MappingMethod> methods,
                            List<? extends Field> fields,
                            Initializers constructors,
                            boolean suppressGeneratorTimestamp, Accessibility accessibility) {
        this.packageName = packageName;
        this.name = name;
        this.superClassName = superClassName;
        this.interfaceName = interfaceName;

        this.annotations = new ArrayList<Annotation>();
        this.methods = methods;
        this.fields = fields;
        this.constructors = constructors;

        this.suppressGeneratorTimestamp = suppressGeneratorTimestamp;
        this.accessibility = accessibility;

        this.generatedType = typeFactory.getType( Generated.class );
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void addAnnotation(Annotation annotation) {
        annotations.add( annotation );
    }

    public List<MappingMethod> getMethods() {
        return methods;
    }

    public List<? extends ModelElement> getFields() {
        return fields;
    }

    public Initializers getConstructors() {
        return constructors;
    }

    public boolean isSuppressGeneratorTimestamp() {
        return suppressGeneratorTimestamp;
    }

    public Accessibility getAccessibility() {
        return accessibility;
    }

    @Override
    public SortedSet<Type> getImportTypes() {
        SortedSet<Type> importedTypes = new TreeSet<Type>();
        importedTypes.add( generatedType );

        for ( MappingMethod mappingMethod : methods ) {
            for ( Type type : mappingMethod.getImportTypes() ) {
                addWithDependents( importedTypes, type );
            }
        }

        for ( ModelElement field : fields ) {
            for ( Type type : field.getImportTypes() ) {
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

        if ( typeToAdd.isImported() &&
            typeToAdd.getPackageName() != null &&
            !typeToAdd.getPackageName().equals( packageName ) &&
            !typeToAdd.getPackageName().startsWith( "java.lang" ) ) {
            collection.add( typeToAdd );
        }

        addWithDependents( collection, typeToAdd.getImplementationType() );

        for ( Type type : typeToAdd.getTypeParameters() ) {
            addWithDependents( collection, type );
        }
    }
}
