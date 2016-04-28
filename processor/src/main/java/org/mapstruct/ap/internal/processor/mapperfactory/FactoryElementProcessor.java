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
package org.mapstruct.ap.internal.processor.mapperfactory;

import org.mapstruct.ap.internal.processor.ModelElementProcessor;
import org.mapstruct.ap.internal.processor.ModelElementProcessor.ProcessorContext;


/**
 * A processor which performs one task of the factory generation, e.g. retrieving
 * methods from the source {@link FactoryGenerationInfo}, performing validity checks or
 * generating the output source file.
 *
 * @param <P> The parameter type processed by this processor
 * @param <R> The return type created by this processor
 *
 * @author Sjaak Derksen
 */
public interface FactoryElementProcessor<P, R> extends ModelElementProcessor {

    R process(ProcessorContext context, FactoryGenerationInfo factoryInfo, P sourceModel);

}
