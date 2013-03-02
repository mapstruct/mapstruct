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
package org.mapstruct.ap.conversion;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.mapstruct.ap.model.Type;

import static org.mapstruct.ap.conversion.ReverseConversion.reverse;

public class Conversions {

	private static ConcurrentMap<Key, Conversion> conversions = new ConcurrentHashMap<Conversions.Key, Conversion>();

	static {
		register( int.class, Long.class, new IntLongConversion() );
		register( int.class, String.class, new IntStringConversion() );
	}

	private static void register(Class<?> sourceType, Class<?> targetType, Conversion conversion) {
		conversions.put( Key.forClasses( sourceType, targetType ), conversion );
		conversions.put( Key.forClasses( targetType, sourceType ), reverse( conversion ) );
	}

	public static Conversion getConversion(Type sourceType, Type targetType) {
		return conversions.get( new Key( sourceType, targetType ) );
	}

	private static class Key {
		private final Type sourceType;
		private final Type targetType;

		private static Key forClasses(Class<?> sourceType, Class<?> targetType) {
			return new Key( Type.forClass( sourceType ), Type.forClass( targetType ) );
		}

		private Key(Type sourceType, Type targetType) {
			this.sourceType = sourceType;
			this.targetType = targetType;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ( ( sourceType == null ) ? 0 : sourceType.hashCode() );
			result = prime * result
					+ ( ( targetType == null ) ? 0 : targetType.hashCode() );
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
			Key other = (Key) obj;
			if ( sourceType == null ) {
				if ( other.sourceType != null ) {
					return false;
				}
			}
			else if ( !sourceType.equals( other.sourceType ) ) {
				return false;
			}
			if ( targetType == null ) {
				if ( other.targetType != null ) {
					return false;
				}
			}
			else if ( !targetType.equals( other.targetType ) ) {
				return false;
			}
			return true;
		}
	}
}
