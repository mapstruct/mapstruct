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
package org.mapstruct.ap.model.source;

import org.mapstruct.ap.model.Type;

/**
 * Represents a property mapped from source to target with the names of its
 * accessor methods.
 *
 * @author Gunnar Morling
 */
public class MappedProperty {

    private final String sourceName;
    private final String sourceReadAccessorName;
    private final String sourceWriteAccessorName;
    private final Type sourceType;
    private final String targetName;
    private final String targetReadAccessorName;
    private final String targetWriteAccessorName;
    private final Type targetType;

    public MappedProperty(String sourceName, String sourceReadAccessorName, String sourceWriteAccessorName,
                          Type sourceType, String targetName, String targetReadAccessorName,
                          String targetWriteAccessorName,
                          Type targetType) {
        this.sourceName = sourceName;
        this.sourceReadAccessorName = sourceReadAccessorName;
        this.sourceWriteAccessorName = sourceWriteAccessorName;
        this.sourceType = sourceType;
        this.targetName = targetName;
        this.targetReadAccessorName = targetReadAccessorName;
        this.targetWriteAccessorName = targetWriteAccessorName;
        this.targetType = targetType;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getSourceReadAccessorName() {
        return sourceReadAccessorName;
    }

    public String getSourceWriteAccessorName() {
        return sourceWriteAccessorName;
    }

    public Type getSourceType() {
        return sourceType;
    }

    public String getTargetName() {
        return targetName;
    }

    public String getTargetReadAccessorName() {
        return targetReadAccessorName;
    }

    public String getTargetWriteAccessorName() {
        return targetWriteAccessorName;
    }

    public Type getTargetType() {
        return targetType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( sourceName == null ) ? 0 : sourceName.hashCode() );
        result = prime * result + ( ( sourceType == null ) ? 0 : sourceType.hashCode() );
        result = prime * result + ( ( targetName == null ) ? 0 : targetName.hashCode() );
        result = prime * result + ( ( targetType == null ) ? 0 : targetType.hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        MappedProperty other = (MappedProperty) obj;
        if ( sourceName == null ) {
            if ( other.sourceName != null ) {
                return false;
            }
        }
        else if ( !sourceName.equals( other.sourceName ) ) {
            return false;
        }
        if ( sourceType == null ) {
            if ( other.sourceType != null ) {
                return false;
            }
        }
        else if ( !sourceType.equals( other.sourceType ) ) {
            return false;
        }
        if ( targetName == null ) {
            if ( other.targetName != null ) {
                return false;
            }
        }
        else if ( !targetName.equals( other.targetName ) ) {
            return false;
        }
        if ( targetType == null ) {
            if ( other.targetType != null ) {
                return false;
            }
        }
        else if ( !targetType.equals( other.targetType ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return sourceType + " " + sourceName + " <=> " + targetType + " " + targetName;
    }
}
