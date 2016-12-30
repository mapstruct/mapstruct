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
package org.mapstruct.ap.test.bugs._913;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2016-12-30T19:07:28+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_112 (Oracle Corporation)"
)
public class DomainDtoWithPresenceCheckMapperImpl implements DomainDtoWithPresenceCheckMapper {

    private final Helper helper = new Helper();

    @Override
    public Domain create(DtoWithPresenceCheck source) {
        if ( source == null ) {
            return null;
        }

        Domain domain = new Domain();

        if ( source.hasStringsInitialized() ) {
            Set<Long> set = stringListToLongSet( source.getStringsInitialized() );
            domain.setLongsInitialized( set );
        }
        if ( source.hasStrings() ) {
            Set<Long> set_ = stringListToLongSet( source.getStrings() );
            domain.setLongs( set_ );
        }
        if ( source.hasStrings() ) {
            List<String> list = source.getStrings();
            domain.setStrings(       new HashSet<String>( list )
            );
        }
        if ( source.hasStringsWithDefault() ) {
            List<String> list_ = source.getStringsWithDefault();
            domain.setStringsWithDefault(       new ArrayList<String>( list_ )
            );
        }
        else {
            domain.setStringsWithDefault( helper.toList( "3" ) );
        }
        if ( source.hasStringsInitialized() ) {
            List<String> list__ = source.getStringsInitialized();
            domain.setStringsInitialized(       new HashSet<String>( list__ )
            );
        }

        return domain;
    }

    @Override
    public void update(DtoWithPresenceCheck source, Domain target) {
        if ( source == null ) {
            return;
        }

        if ( target.getLongs() != null ) {
            if ( source.hasStrings() ) {
                Set<Long> set = stringListToLongSet( source.getStrings() );
                target.getLongs().clear();
                target.getLongs().addAll( set );
            }
        }
        else {
            if ( source.hasStrings() ) {
                Set<Long> set = stringListToLongSet( source.getStrings() );
                target.setLongs( set );
            }
        }
        if ( target.getStrings() != null ) {
            if ( source.hasStrings() ) {
                List<String> list = source.getStrings();
                target.getStrings().clear();
                target.getStrings().addAll( list );
            }
        }
        else {
            if ( source.hasStrings() ) {
                List<String> list = source.getStrings();
                target.setStrings(       new HashSet<String>( list )
                );
            }
        }
        if ( target.getLongsInitialized() != null ) {
            if ( source.hasStringsInitialized() ) {
                Set<Long> set_ = stringListToLongSet( source.getStringsInitialized() );
                target.getLongsInitialized().clear();
                target.getLongsInitialized().addAll( set_ );
            }
        }
        else {
            if ( source.hasStringsInitialized() ) {
                Set<Long> set_ = stringListToLongSet( source.getStringsInitialized() );
                target.setLongsInitialized( set_ );
            }
        }
        if ( target.getStringsWithDefault() != null ) {
            if ( source.hasStringsWithDefault() ) {
                List<String> list_ = source.getStringsWithDefault();
                target.getStringsWithDefault().clear();
                target.getStringsWithDefault().addAll( list_ );
            }
            else {
                target.setStringsWithDefault( helper.toList( "3" ) );
            }
        }
        else {
            if ( source.hasStringsWithDefault() ) {
                List<String> list_ = source.getStringsWithDefault();
                target.setStringsWithDefault(       new ArrayList<String>( list_ )
                );
            }
            else {
                target.setStringsWithDefault( helper.toList( "3" ) );
            }
        }
        if ( target.getStringsInitialized() != null ) {
            if ( source.hasStringsInitialized() ) {
                List<String> list__ = source.getStringsInitialized();
                target.getStringsInitialized().clear();
                target.getStringsInitialized().addAll( list__ );
            }
        }
        else {
            if ( source.hasStringsInitialized() ) {
                List<String> list__ = source.getStringsInitialized();
                target.setStringsInitialized(       new HashSet<String>( list__ )
                );
            }
        }
    }

    @Override
    public Domain updateWithReturn(DtoWithPresenceCheck source, Domain target) {
        if ( source == null ) {
            return null;
        }

        if ( target.getLongs() != null ) {
            if ( source.hasStrings() ) {
                Set<Long> set = stringListToLongSet( source.getStrings() );
                target.getLongs().clear();
                target.getLongs().addAll( set );
            }
        }
        else {
            if ( source.hasStrings() ) {
                Set<Long> set = stringListToLongSet( source.getStrings() );
                target.setLongs( set );
            }
        }
        if ( target.getStrings() != null ) {
            if ( source.hasStrings() ) {
                List<String> list = source.getStrings();
                target.getStrings().clear();
                target.getStrings().addAll( list );
            }
        }
        else {
            if ( source.hasStrings() ) {
                List<String> list = source.getStrings();
                target.setStrings(       new HashSet<String>( list )
                );
            }
        }
        if ( target.getLongsInitialized() != null ) {
            if ( source.hasStringsInitialized() ) {
                Set<Long> set_ = stringListToLongSet( source.getStringsInitialized() );
                target.getLongsInitialized().clear();
                target.getLongsInitialized().addAll( set_ );
            }
        }
        else {
            if ( source.hasStringsInitialized() ) {
                Set<Long> set_ = stringListToLongSet( source.getStringsInitialized() );
                target.setLongsInitialized( set_ );
            }
        }
        if ( target.getStringsWithDefault() != null ) {
            if ( source.hasStringsWithDefault() ) {
                List<String> list_ = source.getStringsWithDefault();
                target.getStringsWithDefault().clear();
                target.getStringsWithDefault().addAll( list_ );
            }
            else {
                target.setStringsWithDefault( helper.toList( "3" ) );
            }
        }
        else {
            if ( source.hasStringsWithDefault() ) {
                List<String> list_ = source.getStringsWithDefault();
                target.setStringsWithDefault(       new ArrayList<String>( list_ )
                );
            }
            else {
                target.setStringsWithDefault( helper.toList( "3" ) );
            }
        }
        if ( target.getStringsInitialized() != null ) {
            if ( source.hasStringsInitialized() ) {
                List<String> list__ = source.getStringsInitialized();
                target.getStringsInitialized().clear();
                target.getStringsInitialized().addAll( list__ );
            }
        }
        else {
            if ( source.hasStringsInitialized() ) {
                List<String> list__ = source.getStringsInitialized();
                target.setStringsInitialized(       new HashSet<String>( list__ )
                );
            }
        }

        return target;
    }

    protected Set<Long> stringListToLongSet(List<String> list) {
        if ( list == null ) {
            return null;
        }

        Set<Long> set = new HashSet<Long>();
        for ( String string : list ) {
            set.add( Long.parseLong( string ) );
        }

        return set;
    }
}
