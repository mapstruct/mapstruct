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
package org.mapstruct.ap.test.bugs._1170;

import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Generated;
import org.mapstruct.ap.test.bugs._1170._target.Target;
import org.mapstruct.ap.test.bugs._1170.source.Source;
import org.mapstruct.factory.Mappers;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-05-21T21:54:47+0200",
    comments = "version: , compiler: javac, environment: Java 1.8.0_131 (Oracle Corporation)"
)
public class AdderSourceTargetMapperImpl implements AdderSourceTargetMapper {

    private final PetMapper petMapper = Mappers.getMapper( PetMapper.class );

    @Override
    public Target toTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        List<Long> list = petMapper.toPets( source.getWildcardAdderToSetters() );
        if ( list != null ) {
            target.setWildcardAdderToSetters( list );
        }
        if ( source.getWithoutWildcards() != null ) {
            for ( String withoutWildcard : source.getWithoutWildcards() ) {
                target.addWithoutWildcard( petMapper.toPet( withoutWildcard ) );
            }
        }
        if ( source.getWildcardInSources() != null ) {
            for ( String wildcardInSource : source.getWildcardInSources() ) {
                target.addWildcardInSource( petMapper.toPet( wildcardInSource ) );
            }
        }
        if ( source.getWildcardInTargets() != null ) {
            for ( String wildcardInTarget : source.getWildcardInTargets() ) {
                target.addWildcardInTarget( petMapper.toPet( wildcardInTarget ) );
            }
        }
        if ( source.getWildcardInBoths() != null ) {
            for ( String wildcardInBoth : source.getWildcardInBoths() ) {
                target.addWildcardInBoth( petMapper.toPet( wildcardInBoth ) );
            }
        }
        if ( target.getWildcardInSourcesAddAll() != null ) {
            List<Long> list1 = petMapper.toPets( source.getWildcardInSourcesAddAll() );
            if ( list1 != null ) {
                target.getWildcardInSourcesAddAll().addAll( list1 );
            }
        }
        if ( source.getSameTypeWildcardInSources() != null ) {
            for ( BigDecimal sameTypeWildcardInSource : source.getSameTypeWildcardInSources() ) {
                target.addSameTypeWildcardInSource( sameTypeWildcardInSource );
            }
        }
        if ( source.getSameTypeWildcardInTargets() != null ) {
            for ( BigDecimal sameTypeWildcardInTarget : source.getSameTypeWildcardInTargets() ) {
                target.addSameTypeWildcardInTarget( sameTypeWildcardInTarget );
            }
        }
        if ( source.getSameTypeWildcardInBoths() != null ) {
            for ( BigDecimal sameTypeWildcardInBoth : source.getSameTypeWildcardInBoths() ) {
                target.addSameTypeWildcardInBoth( sameTypeWildcardInBoth );
            }
        }

        return target;
    }

    @Override
    public Source toSource(Target source) {
        if ( source == null ) {
            return null;
        }

        Source source1 = new Source();

        if ( source.getWithoutWildcards() != null ) {
            for ( Long withoutWildcard : source.getWithoutWildcards() ) {
                source1.addWithoutWildcard( petMapper.toSourcePets( withoutWildcard ) );
            }
        }
        if ( source.getWildcardInSources() != null ) {
            for ( Long wildcardInSource : source.getWildcardInSources() ) {
                source1.addWildcardInSource( petMapper.toSourcePets( wildcardInSource ) );
            }
        }
        if ( source.getWildcardInTargets() != null ) {
            for ( Long wildcardInTarget : source.getWildcardInTargets() ) {
                source1.addWildcardInTarget( petMapper.toSourcePets( wildcardInTarget ) );
            }
        }
        if ( source.getWildcardInBoths() != null ) {
            for ( Long wildcardInBoth : source.getWildcardInBoths() ) {
                source1.addWildcardInBoth( petMapper.toSourcePets( wildcardInBoth ) );
            }
        }
        if ( source.getWildcardInSourcesAddAll() != null ) {
            for ( Long wildcardInSourcesAddAll : source.getWildcardInSourcesAddAll() ) {
                source1.addWildcardInSourcesAddAll( petMapper.toSourcePets( wildcardInSourcesAddAll ) );
            }
        }
        if ( source.getWildcardAdderToSetters() != null ) {
            for ( Long wildcardAdderToSetter : source.getWildcardAdderToSetters() ) {
                source1.addWildcardAdderToSetter( petMapper.toSourcePets( wildcardAdderToSetter ) );
            }
        }
        if ( source.getSameTypeWildcardInSources() != null ) {
            for ( BigDecimal sameTypeWildcardInSource : source.getSameTypeWildcardInSources() ) {
                source1.addSameTypeWildcardInSource( sameTypeWildcardInSource );
            }
        }
        if ( source.getSameTypeWildcardInTargets() != null ) {
            for ( BigDecimal sameTypeWildcardInTarget : source.getSameTypeWildcardInTargets() ) {
                source1.addSameTypeWildcardInTarget( sameTypeWildcardInTarget );
            }
        }
        if ( source.getSameTypeWildcardInBoths() != null ) {
            for ( BigDecimal sameTypeWildcardInBoth : source.getSameTypeWildcardInBoths() ) {
                source1.addSameTypeWildcardInBoth( sameTypeWildcardInBoth );
            }
        }

        return source1;
    }
}
