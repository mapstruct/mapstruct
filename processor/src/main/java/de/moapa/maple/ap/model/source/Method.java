/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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
package de.moapa.maple.ap.model.source;

import java.util.List;

import de.moapa.maple.ap.model.Type;

public class Method {

	private final String name;
	private final String parameterName;
	private final Type sourceType;
	private final Type targetType;
	private final List<MappedProperty> mappedProperties;

	public Method(String name, String parameterName, Type sourceType, Type targetType, List<MappedProperty> mappedProperties) {
		this.name = name;
		this.parameterName = parameterName;
		this.sourceType = sourceType;
		this.targetType = targetType;
		this.mappedProperties = mappedProperties;
	}

	public String getName() {
		return name;
	}

	public String getParameterName() {
		return parameterName;
	}

	public Type getSourceType() {
		return sourceType;
	}

	public Type getTargetType() {
		return targetType;
	}

	public List<MappedProperty> getMappedProperties() {
		return mappedProperties;
	}

	public boolean reverses(Method method) {
		return
			equals( sourceType, method.getTargetType() ) &&
			equals( targetType, method.getSourceType() );
	}
	
	private boolean equals(Object o1, Object o2) {
	    return (o1 == null && o2 == null) || (o1 != null) && o1.equals( o2 );
	}

	@Override
	public String toString() {
		return targetType + " " + name + "(" + sourceType + " " + parameterName + ")";
	}
}
