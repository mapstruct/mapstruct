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
package org.mapstruct.ap.test.array;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import org.mapstruct.ap.test.array._target.ScientistDto;
import org.mapstruct.ap.test.array.source.Scientist;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2016-12-28T17:52:06+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_112 (Oracle Corporation)"
)
public class ScienceMapperImpl implements ScienceMapper {

    @Override
    public ScientistDto scientistToDto(Scientist scientist) {
        if ( scientist == null ) {
            return null;
        }

        ScientistDto scientistDto = new ScientistDto();

        scientistDto.setName( scientist.getName() );
        if ( scientist.getPublications() != null ) {
            java.lang.String[] publications = scientist.getPublications();
            scientistDto.setPublications( Arrays.copyOf( publications, publications.length ) );
        }
        scientistDto.setPublicationYears( stringArrayTointArray( scientist.getPublicationYears() ) );

        return scientistDto;
    }

    @Override
    public org.mapstruct.ap.test.array._target.ScientistDto[] scientistsToDtos(org.mapstruct.ap.test.array.source.Scientist[] scientists) {
        if ( scientists == null ) {
            return null;
        }

        ScientistDto[] scientistDtoTmp = new ScientistDto[scientists.length];
        int i = 0;
        for ( Scientist scientist : scientists ) {
            scientistDtoTmp[i] = scientistToDto( scientist );
            i++;
        }

        return scientistDtoTmp;
    }

    @Override
    public org.mapstruct.ap.test.array._target.ScientistDto[] scientistsToDtos(List<Scientist> scientists) {
        if ( scientists == null ) {
            return null;
        }

        ScientistDto[] scientistDtoTmp = new ScientistDto[scientists.size()];
        int i = 0;
        for ( Scientist scientist : scientists ) {
            scientistDtoTmp[i] = scientistToDto( scientist );
            i++;
        }

        return scientistDtoTmp;
    }

    @Override
    public List<ScientistDto> scientistsToDtosAsList(org.mapstruct.ap.test.array.source.Scientist[] scientists) {
        if ( scientists == null ) {
            return null;
        }

        List<ScientistDto> list = new ArrayList<ScientistDto>();
        for ( Scientist scientist : scientists ) {
            list.add( scientistToDto( scientist ) );
        }

        return list;
    }

    @Override
    public org.mapstruct.ap.test.array._target.ScientistDto[] scientistsToDtos(org.mapstruct.ap.test.array.source.Scientist[] scientists, org.mapstruct.ap.test.array._target.ScientistDto[] target) {
        if ( scientists == null ) {
            return null;
        }

        int i = 0;
        for ( Scientist scientist : scientists ) {
            if ( ( i >= target.length ) || ( i >= scientists.length ) ) {
                break;
            }
            target[i] = scientistToDto( scientist );
            i++;
        }

        return target;
    }

    @Override
    public boolean[] nvmMapping(boolean[] source) {
        if ( source == null ) {
            return new boolean[0];
        }

        boolean[] booleanTmp = new boolean[source.length];
        int i = 0;
        for ( boolean boolean_ : source ) {
            booleanTmp[i] = boolean_;
            i++;
        }

        return booleanTmp;
    }

    @Override
    public boolean[] nvmMapping(boolean[] source, boolean[] target) {
        if ( source == null ) {
            for (int j = 0; j < target.length; j++ ) {
                target[j] = false;
            }
            return target;
        }

        int i = 0;
        for ( boolean boolean_ : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = boolean_;
            i++;
        }

        return target;
    }

    @Override
    public short[] nvmMapping(int[] source, short[] target) {
        if ( source == null ) {
            for (int j = 0; j < target.length; j++ ) {
                target[j] = 0;
            }
            return target;
        }

        int i = 0;
        for ( int int_ : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = (short) int_;
            i++;
        }

        return target;
    }

    @Override
    public char[] nvmMapping(java.lang.String[] source, char[] target) {
        if ( source == null ) {
            for (int j = 0; j < target.length; j++ ) {
                target[j] = 0;
            }
            return target;
        }

        int i = 0;
        for ( String string : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = string.charAt( 0 );
            i++;
        }

        return target;
    }

    @Override
    public int[] nvmMapping(int[] source, int[] target) {
        if ( source == null ) {
            for (int j = 0; j < target.length; j++ ) {
                target[j] = 0;
            }
            return target;
        }

        int i = 0;
        for ( int int_ : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = int_;
            i++;
        }

        return target;
    }

    @Override
    public long[] nvmMapping(int[] source, long[] target) {
        if ( source == null ) {
            for (int j = 0; j < target.length; j++ ) {
                target[j] = 0L;
            }
            return target;
        }

        int i = 0;
        for ( int int_ : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = int_;
            i++;
        }

        return target;
    }

    @Override
    public float[] nvmMapping(int[] source, float[] target) {
        if ( source == null ) {
            for (int j = 0; j < target.length; j++ ) {
                target[j] = 0.0f;
            }
            return target;
        }

        int i = 0;
        for ( int int_ : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = int_;
            i++;
        }

        return target;
    }

    @Override
    public double[] nvmMapping(int[] source, double[] target) {
        if ( source == null ) {
            for (int j = 0; j < target.length; j++ ) {
                target[j] = 0.0d;
            }
            return target;
        }

        int i = 0;
        for ( int int_ : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = int_;
            i++;
        }

        return target;
    }

    @Override
    public java.lang.String[] nvmMapping(int[] source, java.lang.String[] target) {
        if ( source == null ) {
            for (int j = 0; j < target.length; j++ ) {
                target[j] = null;
            }
            return target;
        }

        int i = 0;
        for ( int int_ : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = new DecimalFormat( "" ).format( int_ );
            i++;
        }

        return target;
    }

    @Override
    public void nvmMappingVoidReturnNull(int[] source, long[] target) {
        if ( source == null ) {
            return;
        }

        int i = 0;
        for ( int int_ : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = int_;
            i++;
        }
    }

    @Override
    public void nvmMappingVoidReturnDefault(int[] source, long[] target) {
        if ( source == null ) {
            for (int j = 0; j < target.length; j++ ) {
                target[j] = 0L;
            }
            return;
        }

        int i = 0;
        for ( int int_ : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = int_;
            i++;
        }
    }

    protected int[] stringArrayTointArray(java.lang.String[] stringArray) {
        if ( stringArray == null ) {
            return null;
        }

        int[] intTmp = new int[stringArray.length];
        int i = 0;
        for ( String string : stringArray ) {
            intTmp[i] = Integer.parseInt( string );
            i++;
        }

        return intTmp;
    }
}
