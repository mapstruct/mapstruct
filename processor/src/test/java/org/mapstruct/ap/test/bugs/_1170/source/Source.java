/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1170.source;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cornelius Dirmeier
 */
public class Source {

    private List<String> withoutWildcards = new ArrayList<>();

    private List<String> wildcardInSources = new ArrayList<>();

    private List<String> wildcardInTargets = new ArrayList<>();

    private List<String> wildcardInBoths = new ArrayList<>();

    private List<String> wildcardInSourcesAddAll = new ArrayList<>();

    private List<String>  wildcardAdderToSetters = new ArrayList<>();

    private List<BigDecimal> sameTypeWildcardInSources = new ArrayList<>();

    private List<BigDecimal> sameTypeWildcardInTargets = new ArrayList<>();

    private List<BigDecimal> sameTypeWildcardInBoths = new ArrayList<>();

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
