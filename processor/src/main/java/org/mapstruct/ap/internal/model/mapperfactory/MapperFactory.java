/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.model.mapperfactory;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.Generated;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;
import static org.mapstruct.ap.internal.model.Mapper.CLASS_NAME_PLACEHOLDER;
import static org.mapstruct.ap.internal.model.Mapper.PACKAGE_NAME_PLACEHOLDER;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.version.VersionInformation;

/**
 *
 * @author Sjaak Derksen
 */
public class MapperFactory extends ModelElement {

    private static final String JAVA_LANG_PACKAGE = "java.lang";

    private final List<MapperFactoryMethod> methods;
    private final VersionInformation versionInformation;
    private final String interfaceName;
    private final String name;
    private final String packageName;
    private final boolean suppressGeneratorTimestamp;
    private final boolean suppressGeneratorVersionComment;
    private final Type generatedType; // Type representing the {@code @Generated} annotation

    public static class Builder {

        private List<MapperFactoryMethod> factoryMethods;
        private VersionInformation versionInformation;
        private String implName;
        private String implPackage;
        private TypeElement element;
        private Elements elementUtils;
        private TypeFactory typeFactory;
        private Options options;

        public Builder element(TypeElement element) {
            this.element = element;
            return this;
        }

        public Builder elementUtils(Elements elementUtils) {
            this.elementUtils = elementUtils;
            return this;
        }

        public Builder typeFactory(TypeFactory typeFactory) {
            this.typeFactory = typeFactory;
            return this;
        }

        public Builder options(Options options) {
            this.options = options;
            return this;
        }

        public Builder factoryMethods(List<MapperFactoryMethod> factoryMethods) {
            this.factoryMethods = factoryMethods;
            return this;
        }

        public Builder versionInformation(VersionInformation versionInformation) {
            this.versionInformation = versionInformation;
            return this;
        }

        public Builder implName(String implName) {
            this.implName = implName;
            return this;
        }

        public Builder implPackage(String implPackage) {
            this.implPackage = implPackage;
            return this;
        }

        public MapperFactory build() {
            String lInterfaceName = element.getSimpleName().toString();
            String lImplName = implName.replace( CLASS_NAME_PLACEHOLDER, element.getSimpleName() );
            String elemPackage = elementUtils.getPackageOf( element ).getQualifiedName().toString();
            String lPackageName = implPackage.replace( PACKAGE_NAME_PLACEHOLDER, elemPackage );
            return new MapperFactory(
                factoryMethods,
                versionInformation,
                lInterfaceName,
                lImplName,
                lPackageName,
                options.isSuppressGeneratorTimestamp(),
                options.isSuppressGeneratorVersionComment(),
                typeFactory.getType( Generated.class )

            );
        }
    }

    public MapperFactory(List<MapperFactoryMethod> methods, VersionInformation versionInformation, String interfaceName,
        String name, String packageName, boolean suppressGeneratorTimestamp, boolean suppressGeneratorVersionComment,
        Type generatedType ) {
        this.methods = methods;
        this.versionInformation = versionInformation;
        this.interfaceName = interfaceName;
        this.name = name;
        this.packageName = packageName;
        this.suppressGeneratorTimestamp = suppressGeneratorTimestamp;
        this.suppressGeneratorVersionComment = suppressGeneratorVersionComment;
        this.generatedType = generatedType;
    }

    @Override
    public Set<Type> getImportTypes() {
        SortedSet<Type> importedTypes = new TreeSet<Type>();

        importedTypes.add( generatedType );

        for ( MapperFactoryMethod factoryMethod : methods ) {
            for ( Type type : factoryMethod.getImportTypes() ) {
                addIfImportRequired( importedTypes, type );
            }

        }
        return importedTypes;
    }

    public List<MapperFactoryMethod> getMethods() {
        return methods;
    }

    public VersionInformation getVersionInformation() {
        return versionInformation;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
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

        if ( typeToAdd.getTypeMirror().getKind() != TypeKind.DECLARED ) {
            return false;
        }

        if ( typeToAdd.getPackageName() != null ) {
            if ( typeToAdd.getPackageName().startsWith( JAVA_LANG_PACKAGE ) ) {
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

    public boolean isSuppressGeneratorTimestamp() {
        return suppressGeneratorTimestamp;
    }

    public boolean isSuppressGeneratorVersionComment() {
        return suppressGeneratorVersionComment;
    }

}
