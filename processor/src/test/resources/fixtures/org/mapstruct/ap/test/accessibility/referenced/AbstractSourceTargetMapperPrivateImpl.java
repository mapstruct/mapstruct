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
package org.mapstruct.ap.test.accessibility.referenced;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-03-13T22:39:43+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_121 (Oracle Corporation)"
)
public class AbstractSourceTargetMapperPrivateImpl extends AbstractSourceTargetMapperPrivate {

    @Override
    public Target toTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        target.setReferencedTarget( referencedSourceToReferencedTarget( source.getReferencedSource() ) );

        return target;
    }

    protected ReferencedTarget referencedSourceToReferencedTarget(ReferencedSource referencedSource) {
        if ( referencedSource == null ) {
            return null;
        }

        ReferencedTarget referencedTarget = new ReferencedTarget();

        return referencedTarget;
    }
}
