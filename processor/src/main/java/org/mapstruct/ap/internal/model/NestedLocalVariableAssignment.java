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

import static java.util.Collections.emptySet;

import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/**
 *
 * @author Sjaak Derksen
 *
 * In the process of creating target mappings, MapStruct creates local variables.
 * <pre>
 * {@code Chart chart = new Chart();
 *
 *       Label label = new Label();
 *       Artist artist = new Artist();
 *       Song song = new Song();
 *       Studio studio = new Studio();
 *       artist.setLabel( label ); // NestedLocalVariableAssignment
 *       song.setArtist( artist ); // NestedLocalVariableAssignment
 *       label.setStudio( studio );// NestedLocalVariableAssignment
 *       chart.setSong( song ); // NestedLocalVariableAssignment
 * }
 *</pre>
 */
public class NestedLocalVariableAssignment extends ModelElement {

    private String targetBean;
    private final String setterName;
    private final String sourceRef;
    private final boolean fieldAssignment;

    public NestedLocalVariableAssignment(String targetBean, String setterName, String sourceRef,
                                         boolean fieldAssignment) {
        this.targetBean = targetBean;
        this.setterName = setterName;
        this.sourceRef = sourceRef;
        this.fieldAssignment = fieldAssignment;
    }

    /**
     *
     * @return the targetBean on which the property setter with {@link setterName} is called
     */
    public String getTargetBean() {
        return targetBean;
    }

    /**
     *
     * @param targetBean the targetBean on which the property setter with {@link setterName} is called
     */
    public void setTargetBean(String targetBean) {
        this.targetBean = targetBean;
    }

    /**
     *
     * @return the name of the setter (target accessor for the property)
     */
    public String getSetterName() {
        return setterName;
    }

    /**
     *
     * @return source reference, to be used a argument in the setter.
     */
    public String getSourceRef() {
        return sourceRef;
    }

    @Override
    public Set<Type> getImportTypes() {
        return emptySet();
    }

    /**
     * @return {@code true}if field assignment should be used, {@code false} otherwise
     */
    public boolean isFieldAssignment() {
        return fieldAssignment;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + ( this.targetBean != null ? this.targetBean.hashCode() : 0 );
        hash = 97 * hash + ( this.sourceRef != null ? this.sourceRef.hashCode() : 0 );
        hash = 97 * hash + ( this.fieldAssignment ? 1 : 0 );
        return hash;
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
        final NestedLocalVariableAssignment other = (NestedLocalVariableAssignment) obj;
        if ( ( this.targetBean == null ) ? ( other.targetBean != null ) :
            !this.targetBean.equals( other.targetBean ) ) {
            return false;
        }
        if ( ( this.sourceRef == null ) ? ( other.sourceRef != null ) : !this.sourceRef.equals( other.sourceRef ) ) {
            return false;
        }
        return this.fieldAssignment == other.fieldAssignment;
    }

}
