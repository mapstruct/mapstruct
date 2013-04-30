/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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

import java.util.List;

public class Mapper {

    private final String packageName;
    private final String interfaceName;
    private final String implementationName;
    private final List<BeanMapping> beanMappings;
    private final List<Type> usedMapperTypes;

    public Mapper(String packageName, String interfaceName,
                  String implementationName, List<BeanMapping> beanMappings, List<Type> usedMapperTypes) {
        this.packageName = packageName;
        this.interfaceName = interfaceName;
        this.implementationName = implementationName;
        this.beanMappings = beanMappings;
        this.usedMapperTypes = usedMapperTypes;
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
}
