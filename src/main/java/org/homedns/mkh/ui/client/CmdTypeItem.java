/*
 * Copyright 2014 Mikhail Khodonov
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

package org.homedns.mkh.ui.client;

/**
 * Represents item name/command type pair
 *
 */
public class CmdTypeItem {
	private String _sName;
	private Class< ? > _commandType;

	/**
	 * @param sName the item name
	 * @param commandType the command type will be linked with item
	 */
	public CmdTypeItem( String sName, Class< ? > commandType ) {
		_sName = sName;
		_commandType = commandType;
	}

	/**
	 * Returns item name
	 * 
	 * @return the item name
	 */
	public String getName( ) {
		return( _sName );
	}

	/**
	 * Returns associated command type 
	 * 
	 * @return the command type
	 */
	public Class< ? > getCommandType( ) {
		return( _commandType );
	}
}
