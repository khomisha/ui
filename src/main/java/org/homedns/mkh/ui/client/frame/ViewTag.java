/*
 * Copyright 2016 Mikhail Khodonov
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * $Id$
 */

package org.homedns.mkh.ui.client.frame;

/**
 * View tag contains name and class type
 *
 */
public class ViewTag {
	private String sName;
	private Class< ? > type;
	
	public ViewTag( ) {
	}
	
	/**
	 * @param sName the view name
	 * @param type the view class type
	 */
	public ViewTag( String sName, Class< ? > type ) {
		setName( sName );
		setType( type );
	}
	
	/**
	 * Returns view name
	 * 
	 * @return the view name
	 */
	public String getName( ) {
		return sName;
	}
	
	/**
	 * Returns view class type
	 * 
	 * @return the view class type
	 */
	public Class< ? > getType( ) {
		return type;
	}
	
	/**
	 * Sets view name
	 * 
	 * @param sName the view name to set
	 */
	public void setName( String sName ) {
		this.sName = sName;
	}
	
	/**
	 * Sets view class type 
	 * 
	 * @param type the view class type to set
	 */
	public void setType( Class< ? > type ) {
		this.type = type;
	}
}
