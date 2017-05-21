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
package org.mapstruct.ap.test.bugs._1170._target;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cornelius Dirmeier
 */
public class Target {

    private List<Long> withoutWildcards = new ArrayList<Long>();

    private List<Long> wildcardInSources = new ArrayList<Long>();

    private List<Long> wildcardInTargets = new ArrayList<Long>();

    private List<Long> wildcardInBoths = new ArrayList<Long>();

    private List<Long> wildcardAdderToSetters = new ArrayList<Long>();

    private List<Long> wildcardInSourcesAddAll = new ArrayList<Long>();

    private List<BigDecimal> sameTypeWildcardInSources = new ArrayList<BigDecimal>();

    private List<BigDecimal> sameTypeWildcardInTargets = new ArrayList<BigDecimal>();

    private List<BigDecimal> sameTypeWildcardInBoths = new ArrayList<BigDecimal>();

    public List<Long> getWithoutWildcards() {
        return withoutWildcards;
    }

    public Long addWithoutWildcard(Long pet) {
        withoutWildcards.add( pet );
        return pet;
    }

    public List<Long> getWildcardInSources() {
        return wildcardInSources;
    }

    public Long addWildcardInSource(Long pet) {
        wildcardInSources.add( pet );
        return pet;
    }

    public List<? extends Long> getWildcardInTargets() {
        return wildcardInTargets;
    }

    public Long addWildcardInTarget(Long pet) {
        wildcardInTargets.add( pet );
        return pet;
    }

    public List<? extends Long> getWildcardInBoths() {
        return wildcardInTargets;
    }

    public Long addWildcardInBoth(Long pet) {
        wildcardInBoths.add( pet );
        return pet;
    }

    public List<Long> getWildcardAdderToSetters() {
        return wildcardAdderToSetters;
    }

    public void setWildcardAdderToSetters(List<Long> pets) {
         wildcardAdderToSetters = pets;
    }

    public List<Long> getWildcardInSourcesAddAll() {
        return wildcardInSourcesAddAll;
    }

    public List<? extends BigDecimal> getSameTypeWildcardInSources() {
        return sameTypeWildcardInSources;
    }

    public void addSameTypeWildcardInSource(BigDecimal pet) {
        sameTypeWildcardInSources.add( pet );
    }

    public List<BigDecimal> getSameTypeWildcardInTargets() {
        return sameTypeWildcardInTargets;
    }

    public void addSameTypeWildcardInTarget(BigDecimal pet) {
        sameTypeWildcardInTargets.add( pet );
    }

    public List<? extends BigDecimal> getSameTypeWildcardInBoths() {
        return sameTypeWildcardInBoths;
    }

    public void addSameTypeWildcardInBoth(BigDecimal pet) {
        sameTypeWildcardInBoths.add( pet );
    }

}
