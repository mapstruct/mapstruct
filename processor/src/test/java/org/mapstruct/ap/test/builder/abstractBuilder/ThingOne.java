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
package org.mapstruct.ap.test.builder.abstractBuilder;

import org.mapstruct.MappedByBuilder;

@MappedByBuilder( ThingOne.ThingOneBuilder.class )
public class ThingOne implements AbstractThing {

    private final String foo;
    private final Integer bar;

    public ThingOne(ThingOneBuilder thingOneBuilder) {
        this.foo = thingOneBuilder.foo;
        this.bar = thingOneBuilder.bar;
    }

    public static ThingOneBuilder builder() {
        return new ThingOneBuilder();
    }

    @Override
    public String getFoo() {
        return foo;
    }

    @Override
    public Integer getBar() {
        return bar;
    }

    public static class ThingOneBuilder extends AbstractThingBuilder<ThingOne> {
        private Integer bar;

        public ThingOneBuilder bar(Integer bar) {
            this.bar = bar;
            return this;
        }

        @Override
        ThingOne build() {
            return new ThingOne(this);
        }
    }
}
