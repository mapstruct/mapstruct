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
package org.mapstruct.ap.internal.model.assignment;

/**
 * Wraps the assignment in a target setter, if {@link org.mapstruct.Mapper#sourceValuePresenceCheckStrategy}
 * or {@link org.mapstruct.Mapping#sourceValuePresenceCheckStrategy} is set to
 * {@link org.mapstruct.SourceValuePresenceCheckStrategy#CUSTOM}.
 *
 * <pre>
 * {@code
 *  public void sourceToTargetWithCustom(Source source, Target target) {
 *      if ( source == null ) {
 *          return;
 *      }
 *
 *      if ( source.hasSomeObject() ) {
 *          target.setSomeObject( source.getSomeObject() );
 *      }
 *      if ( source.hasSomePrimitiveDouble() ) {
 *          target.setSomePrimitiveDouble( source.getSomePrimitiveDouble() );
 *      }
 *      if ( source.hasSomeLong1() ) {
 *          target.setSomeLong1( customMapper.toMyLongWrapperViaPrimitive( source.getSomeLong1() ) );
 *      }
 *      if ( source.hasSomeList() ) {
 *          if ( target.getSomeList() != null ) {
 *              target.getSomeList().clear();
 *              target.getSomeList().addAll( source.getSomeList() );
 *          }
 *          else {
 *              target.setSomeList( new ArrayList<String>( source.getSomeList() ) );
 *          }
 *      }
 *  }
 * }
 * </pre>
 *
 * @author Sean Huang
 */
public class UpdatePresenceCheckWrapper extends AssignmentWrapper {

    private final String sourcePresenceChecker;

    public UpdatePresenceCheckWrapper( Assignment decoratedAssignment, String sourceHasMethod) {
        super( decoratedAssignment );
        this.sourcePresenceChecker = sourceHasMethod;
    }

    @Override
    public String getSourcePresenceCheckMethod() {
        return sourcePresenceChecker;
    }
}
