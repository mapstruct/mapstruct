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
package org.mapstruct.ap.test.bugs._1170.source;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cornelius Dirmeier
 */
public class Source {

    private List<String> withoutWildcards = new ArrayList<String>();

    private List<String> wildcardInSources = new ArrayList<String>();

    private List<String> wildcardInTargets = new ArrayList<String>();

    private List<String> wildcardInBoths = new ArrayList<String>();

    private List<String> wildcardInSourcesAddAll = new ArrayList<String>();

    private List<String>  wildcardAdderToSetters = new ArrayList<String>();

    private List<BigDecimal> sameTypeWildcardInSources = new ArrayList<BigDecimal>();

    private List<BigDecimal> sameTypeWildcardInTargets = new ArrayList<BigDecimal>();

    private List<BigDecimal> sameTypeWildcardInBoths = new ArrayList<BigDecimal>();

    public List<String> getWithoutWildcards() {
        return withoutWildcards;
    }

    public void addWithoutWildcard(String pet) {
        this.withoutWildcards.add( pet );
    }

    public List<? extends String> getWildcardInSources() {
        return wildcardInSources;
    }

    public void addWildcardInSource(String pet) {
        wildcardInSources.add( pet );
    }

    public List<String> getWildcardInTargets() {
        return wildcardInTargets;
    }

    public void addWildcardInTarget(String pet) {
        wildcardInTargets.add( pet );
    }

    public List<? extends String> getWildcardInBoths() {
        return wildcardInBoths;
    }

    public void addWildcardInBoth(String pet) {
        wildcardInBoths.add( pet );
    }

    public List<? extends String> getWildcardInSourcesAddAll() {
        return wildcardInSourcesAddAll;
    }

    public void addWildcardInSourcesAddAll(String pet) {
        wildcardInSourcesAddAll.add( pet );
    }

    public List<? extends String> getWildcardAdderToSetters() {
        return wildcardAdderToSetters;
    }

    public void addWildcardAdderToSetter(String pet) {
        wildcardAdderToSetters.add( pet );
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
