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

public class Mapping {

	private final String sourceName;
	private final String targetName;
	private final Type converterType;

	public Mapping(String sourceName, String targetName, Type converterType) {
		this.sourceName = sourceName;
		this.targetName = targetName;
		this.converterType = converterType;
	}

	public String getSourceName() {
		return sourceName;
	}

	public String getTargetName() {
		return targetName;
	}

	public Type getConverterType() {
		return converterType;
	}
}
