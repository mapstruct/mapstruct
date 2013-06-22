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

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.Generated;

public class Mapper extends AbstractModelElement {

    private final String packageName;
    private final String interfaceName;
    private final String implementationName;
    private final List<BeanMapping> beanMappings;
    private final List<Type> usedMapperTypes;
    private final Options options;
    private final SortedSet<Type> importedTypes;

    public Mapper(String packageName, String interfaceName,
                  String implementationName, List<BeanMapping> beanMappings, List<Type> usedMapperTypes,
                  Options options) {
        this.packageName = packageName;
        this.interfaceName = interfaceName;
        this.implementationName = implementationName;
        this.beanMappings = beanMappings;
        this.usedMapperTypes = usedMapperTypes;
        this.options = options;
        this.importedTypes = determineImportedTypes();
    }

    private SortedSet<Type> determineImportedTypes() {
        SortedSet<Type> importedTypes = new TreeSet<Type>();
        importedTypes.add( Type.forClass( Generated.class ) );

        for ( BeanMapping beanMapping : beanMappings ) {

            for ( Type type : beanMapping.getMappingMethod().getReferencedTypes() ) {
                addWithDependents( importedTypes, type );
            }
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

        addWithDependents( collection, typeToAdd.getCollectionImplementationType() );
        addWithDependents( collection, typeToAdd.getIterableImplementationType() );
        addWithDependents( collection, typeToAdd.getElementType() );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder( "Mapper {" );

        sb.append( "\n    packageName='" + packageName + "\'," );
        sb.append( "\n    interfaceName='" + interfaceName + "\'," );
        sb.append( "\n    implementationName='" + implementationName + "\'," );
        sb.append( "\n    beanMappings=[" );

        for ( BeanMapping beanMapping : beanMappings ) {
            sb.append( "\n        " + beanMapping.toString().replaceAll( "\n", "\n        " ) );
        }
        sb.append( "\n    ]" );
        sb.append( "\n    usedMapperTypes=" + usedMapperTypes );
        sb.append( "\n}," );

        return sb.toString();
    }

    public String getPackageName() {
        return packageName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getImplementationName() {
        return implementationName;
    }

    public List<BeanMapping> getBeanMappings() {
        return beanMappings;
    }

    public List<Type> getUsedMapperTypes() {
        return usedMapperTypes;
    }

    public Options getOptions() {
        return options;
    }

    public SortedSet<Type> getImportedTypes() {
        return importedTypes;
    }
}
