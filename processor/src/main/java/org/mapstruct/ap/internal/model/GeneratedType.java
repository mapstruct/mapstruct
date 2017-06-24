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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.lang.model.type.TypeKind;

import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.version.VersionInformation;

/**
 * A type generated by MapStruct, e.g. representing a mapper type.
 *
 * @author Gunnar Morling
 */
public abstract class GeneratedType extends ModelElement {

    private static final String JAVA_LANG_PACKAGE = "java.lang";

    private final String packageName;
    private final String name;
    private final String superClassName;
    private final String interfacePackage;
    private final String interfaceName;

    private final List<Annotation> annotations;
    private final List<MappingMethod> methods;
    private final SortedSet<Type> extraImportedTypes;

    private final boolean suppressGeneratorTimestamp;
    private final boolean suppressGeneratorVersionComment;
    private final VersionInformation versionInformation;
    private final Accessibility accessibility;
    private List<? extends Field> fields;
    private Constructor constructor;

    /**
     * Type representing the {@code @Generated} annotation
     */
    private final Type generatedType;
    private final boolean generatedTypeAvailable;

    // CHECKSTYLE:OFF
    protected GeneratedType(TypeFactory typeFactory, String packageName, String name, String superClassName,
                            String interfacePackage, String interfaceName,
                            List<MappingMethod> methods,
                            List<? extends Field> fields,
                            Options options,
                            VersionInformation versionInformation,
                            Accessibility accessibility,
                            SortedSet<Type> extraImportedTypes,
                            Constructor constructor ) {
        this.packageName = packageName;
        this.name = name;
        this.superClassName = superClassName;
        this.interfacePackage = interfacePackage;
        this.interfaceName = interfaceName;
        this.extraImportedTypes = extraImportedTypes;

        this.annotations = new ArrayList<Annotation>();
        this.methods = methods;
        this.fields = fields;

        this.suppressGeneratorTimestamp = options.isSuppressGeneratorTimestamp();
        this.suppressGeneratorVersionComment = options.isSuppressGeneratorVersionComment();
        this.versionInformation = versionInformation;
        this.accessibility = accessibility;

        this.generatedTypeAvailable = typeFactory.isTypeAvailable( "javax.annotation.Generated" );
        if ( generatedTypeAvailable ) {
            this.generatedType = typeFactory.getType( "javax.annotation.Generated" );
        }
        else {
            this.generatedType = null;
        }

        this.constructor = constructor;
    }

    // CHECKSTYLE:ON

    public String getPackageName() {
        return packageName;
    }

    public boolean hasPackageName() {
        return !Strings.isEmpty( packageName );
    }

    public String getName() {
        return name;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public String getInterfacePackage() {
        return interfacePackage;
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

    public List<? extends Field> getFields() {
        return fields;
    }

    public void setFields(List<? extends Field> fields) {
        this.fields = fields;
    }

    public boolean isSuppressGeneratorTimestamp() {
        return suppressGeneratorTimestamp;
    }

    public boolean isSuppressGeneratorVersionComment() {
        return suppressGeneratorVersionComment;
    }

    public boolean isGeneratedTypeAvailable() {
        return generatedTypeAvailable;
    }

    public VersionInformation getVersionInformation() {
        return versionInformation;
    }

    public Accessibility getAccessibility() {
        return accessibility;
    }

    @Override
    public SortedSet<Type> getImportTypes() {
        SortedSet<Type> importedTypes = new TreeSet<Type>();
        addIfImportRequired( importedTypes, generatedType );

        for ( MappingMethod mappingMethod : methods ) {
            for ( Type type : mappingMethod.getImportTypes() ) {
                addIfImportRequired( importedTypes, type );
            }
        }

        for ( Field field : fields ) {
            if ( field.isTypeRequiresImport() ) {
                for ( Type type : field.getImportTypes() ) {
                    addIfImportRequired( importedTypes, type );
                }
            }
        }

        for ( Annotation annotation : annotations ) {
            addIfImportRequired( importedTypes, annotation.getType() );
        }

        for ( Type extraImport : extraImportedTypes ) {
            addIfImportRequired( importedTypes, extraImport );
        }

        return importedTypes;
    }

    public SortedSet<String> getImportTypeNames() {
        SortedSet<String> importTypeNames = new TreeSet<String>();
        for ( Type type : getImportTypes() ) {
            importTypeNames.add( type.getImportName() );
        }
        return importTypeNames;
    }

    public Constructor getConstructor() {
        return constructor;
    }

    public void removeConstructor() {
        constructor = null;
    }

    protected void addIfImportRequired(Collection<Type> collection, Type typeToAdd) {
        if ( typeToAdd == null ) {
            return;
        }

        if ( needsImportDeclaration( typeToAdd ) ) {
            collection.add( typeToAdd );
        }
    }

    private boolean needsImportDeclaration(Type typeToAdd) {
        if ( !typeToAdd.isImported() ) {
            return false;
        }

        if ( typeToAdd.getTypeMirror().getKind() != TypeKind.DECLARED && !typeToAdd.isArrayType() ) {
            return false;
        }

        if ( typeToAdd.getPackageName() != null ) {
            if ( typeToAdd.getPackageName().equals( JAVA_LANG_PACKAGE ) ) {
                // only the types in the java.lang package are implicitly imported, the packages under java.lang
                // like java.lang.management are not.
                return false;
            }

            if ( typeToAdd.getPackageName().equals( packageName ) ) {
                if ( !typeToAdd.getTypeElement().getNestingKind().isNested() ) {
                    return false;
                }
            }
        }

        return true;
    }
}
