/*
 * Copyright 2016 Sjaak Derksen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mapstruct.ap.test.mapperfactories;

import org.mapstruct.Mapper;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public abstract class MapperWithSeveralConstructors {


    private Integer state1;
    private String state2;

    public MapperWithSeveralConstructors() {
    }

    public MapperWithSeveralConstructors(Integer state1) {
        this.state1 = state1;
    }

    public MapperWithSeveralConstructors(String state2) {
        this.state2 = state2;
    }

    public MapperWithSeveralConstructors(Integer state1, String state2) {
        this.state1 = state1;
        this.state2 = state2;
    }

    public MapperWithSeveralConstructors(String state2, Integer state1) {
        this.state1 = state1;
        this.state2 = state2;
    }

    public Integer getState1() {
        return state1;
    }

    public String getState2() {
        return state2;
    }
    
}
