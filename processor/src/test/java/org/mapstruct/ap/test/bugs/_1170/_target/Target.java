/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1170._target;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cornelius Dirmeier
 */
public class Target {

    private List<Long> withoutWildcards = new ArrayList<>();

    private List<Long> wildcardInSources = new ArrayList<>();

    private List<Long> wildcardInTargets = new ArrayList<>();

    private List<Long> wildcardInBoths = new ArrayList<>();

    private List<Long> wildcardAdderToSetters = new ArrayList<>();

    private List<Long> wildcardInSourcesAddAll = new ArrayList<>();

    private List<BigDecimal> sameTypeWildcardInSources = new ArrayList<>();

    private List<BigDecimal> sameTypeWildcardInTargets = new ArrayList<>();

    private List<BigDecimal> sameTypeWildcardInBoths = new ArrayList<>();

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
