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
package org.mapstruct.ap.model;

import java.util.List;

public class Mapper {

	private final String packageName;

	private final String interfaceName;

	private final String implementationName;

	private final List<BeanMapping> beanMappings;

	public Mapper(String packageName, String interfaceName,
				  String implementationName, List<BeanMapping> beanMappings) {
		this.packageName = packageName;
		this.interfaceName = interfaceName;
		this.implementationName = implementationName;
		this.beanMappings = beanMappings;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public String getImplementationName() {
		return implementationName;
	}

	public List<BeanMapping> getBeanMappings() {
		return beanMappings;
	}
}
