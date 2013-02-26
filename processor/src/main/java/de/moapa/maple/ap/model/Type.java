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
package de.moapa.maple.ap.model;

/**
 * Represents the type of a bean property, parameter etc.
 *
 * @author Gunnar Morling
 */
public class Type {

	private final String packageName;
	private final String name;
	private final Type elementType;

	public Type(String name) {
		this.packageName = null;
		this.name = name;
		this.elementType = null;
	}

	public Type(String packageName, String name) {
		this.packageName = packageName;
		this.name = name;
		this.elementType = null;
	}

	public Type(String packageName, String name, Type elementType) {
		this.packageName = packageName;
		this.name = name;
		this.elementType = elementType;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getName() {
		return name;
	}

	public Type getElementType() {
		return elementType;
	}

	@Override
	public String toString() {
		if ( packageName == null ) {
			return name;
		}
		else if ( elementType == null ) {
			return packageName + "." + name;
		}
		else {
			return packageName + "." + name + "<" + elementType + ">";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ( ( elementType == null ) ? 0 : elementType.hashCode() );
		result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
		result = prime * result
				+ ( ( packageName == null ) ? 0 : packageName.hashCode() );
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if ( this == obj ) {
			return true;
		}
		if ( obj == null ) {
			return false;
		}
		if ( getClass() != obj.getClass() ) {
			return false;
		}
		Type other = (Type) obj;
		if ( elementType == null ) {
			if ( other.elementType != null ) {
				return false;
			}
		}
		else if ( !elementType.equals( other.elementType ) ) {
			return false;
		}
		if ( name == null ) {
			if ( other.name != null ) {
				return false;
			}
		}
		else if ( !name.equals( other.name ) ) {
			return false;
		}
		if ( packageName == null ) {
			if ( other.packageName != null ) {
				return false;
			}
		}
		else if ( !packageName.equals( other.packageName ) ) {
			return false;
		}
		return true;
	}
}
