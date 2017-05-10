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
package org.mapstruct.ap.test.bugs._1029;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.TreeMap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author Andreas Gudian
 */
@Mapper
public interface ErroneousIssue1029Mapper {

    /**
     * reports unmapped properties 'lastUpdated' and 'computedMapping'
     */
    Deck toDeck(DeckForm form);

    /**
     * reports unmapped property 'lastUpdated'. Property 'outdated' is read-only anyway, but can still be ignored
     * explicitly without raising any errors.
     */
    @Mappings({
        @Mapping(target = "outdated", ignore = true),
        @Mapping(target = "computedMapping", ignore = true),
        @Mapping(target = "knownProp", ignore = true)
    })
    Deck toDeckWithSomeIgnores(DeckForm form);

    /**
     * reports unknown property 'unknownProp' as error.
     */
    @Mapping(target = "unknownProp", ignore = true)
    Deck toDeckWithUnknownProperty(DeckForm form);

    class DeckForm {
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    class Deck {
        private Long id;

        private LocalDateTime lastUpdated;

        private String knownProp;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getKnownProp() {
            return knownProp;
        }

        public void setKnownProp(String knownProp) {
            this.knownProp = knownProp;
        }

        public LocalDateTime getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(LocalDateTime lastUpdated) {
            this.lastUpdated = lastUpdated;
        }

        // COMPUTED getters

        public boolean isOutdated() {
            long daysBetween = ChronoUnit.DAYS.between( lastUpdated, LocalDateTime.now() );
            return daysBetween > 30;
        }

        public Map<String, Integer> getComputedMapping() {
            return new TreeMap<String, Integer>();
        }
    }
}
