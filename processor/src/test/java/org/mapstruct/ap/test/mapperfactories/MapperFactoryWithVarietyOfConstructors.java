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

import org.mapstruct.factory.MapperFactory;

/**
 *
 * @author Sjaak Derksen
 */
@MapperFactory
public interface MapperFactoryWithVarietyOfConstructors {

    MapperWithSeveralConstructors createEmpty();

    MapperWithSeveralConstructors createInteger(Integer arg);

    MapperWithSeveralConstructors createString(String arg);

    MapperWithSeveralConstructors createIntegerString(Integer arg1, String arg2);

    MapperWithSeveralConstructors createStringInteger(String arg1, Integer arg2);

}
