/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.supertypegeneration.usage;

import org.mapstruct.itest.supertypegeneration.GenBase;

/**
 * @author Gunnar Morling
 */
@GenBase(2)
public class Order extends OrderBase {

	private String item;
	
	public String getItem() {
		return item;
	}
	
	public void setItem(String item) {
		this.item = item;
	}
}
