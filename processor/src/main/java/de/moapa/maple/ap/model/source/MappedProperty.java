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

import de.moapa.maple.ap.model.Type;

public class MappedProperty {

	private final String sourceName;
	private final Type sourceType;
	private final String targetName;
	private final Type targetType;
	private final Type converterType;

	public MappedProperty(String sourceName, Type sourceType, String targetName,
						  Type targetType, Type converterType) {
		this.sourceName = sourceName;
		this.sourceType = sourceType;
		this.targetName = targetName;
		this.targetType = targetType;
		this.converterType = converterType;
	}

	public String getSourceName() {
		return sourceName;
	}

	public Type getSourceType() {
		return sourceType;
	}

	public String getTargetName() {
		return targetName;
	}

	public Type getTargetType() {
		return targetType;
	}

	public Type getConverterType() {
		return converterType;
	}

	@Override
	public String toString() {
		return sourceType + " " + sourceName + " <=> " + targetType + " " + targetName + " (" + ( converterType != null ? converterType : "no converter" ) + ")";
	}
}
