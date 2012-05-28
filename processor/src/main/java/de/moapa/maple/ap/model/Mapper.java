/**
 *  Copyright 2012 Gunnar Morling (http://www.gunnarmorling.de/)
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
package de.moapa.maple.ap.model;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

	private final String packageName;

	private final String implementationType;

	private final String interfaceType;

	private final List<MapperMethod> mapperMethods;

	private final List<Converter> converters;

	public Mapper(String packageName, String implementationType, String interfaceType, List<MapperMethod> mapperMethods) {
		this.packageName = packageName;
		this.implementationType = implementationType;
		this.interfaceType = interfaceType;
		this.mapperMethods = mapperMethods;
		this.converters = collectConverters( mapperMethods );
	}

	private List<Converter> collectConverters(List<MapperMethod> mapperMethods) {

		List<Converter> converters = new ArrayList<Converter>();

		for ( MapperMethod oneMapperMethod : mapperMethods ) {
			for ( Binding oneBinding : oneMapperMethod.getBindings() ) {
				if ( oneBinding.getConverterType() != null ) {
					converters.add(
							new Converter(
									oneBinding.getConverterType(),
									oneBinding.getSourceType(),
									oneBinding.getTargetType()
							)
					);
				}
			}
		}

		return converters;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getImplementationType() {
		return implementationType;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public List<MapperMethod> getMapperMethods() {
		return mapperMethods;
	}

	public List<Converter> getConverters() {
		return converters;
	}
}
