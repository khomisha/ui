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

import org.homedns.mkh.dataservice.client.AbstractEntryPoint;
import org.homedns.mkh.dataservice.shared.AttributeMap;

/**
 * Widget configuration object
 *
 */
@SuppressWarnings( "serial" )
public class WidgetConfig extends AttributeMap< Integer, Object > implements Config {
	/**
	 * locale-sensitive text constants
	 */
	protected static final UIConstants CONSTANTS = ( UIConstants )AbstractEntryPoint.getConstants( );

	/**
	* Default width
	*/
	public static final Integer WIDTH = 1;
	/**
	* Default height
	*/
	public static final Integer HEIGHT = 2;
	/**
	* Auto width flag
	*/
	public static final Integer AUTO_WIDTH = 3;
	/**
	* Auto height flag
	*/
	public static final Integer AUTO_HEIGHT = 4;
	/**
	* Initial arguments values
	*/
	public static final Integer ARGS = 5;
	/**
	* Widget border flag, default false
	*/
	public static final Integer BORDER = 6;
	/**
	* Widget auto scroll flag, default false
	*/
	public static final Integer AUTO_SCROLL = 7;
	/**
	* Widget batch update flag, default false
	*/
	public static final Integer BATCH_UPDATE = 8;
	/**
	* Schedule retrieve command delay in millis, default 0 means not used
	*/
	public static final Integer SCHEDULE_RETRIEVE_CMD = 9;

	public WidgetConfig( ) {
		setAttribute( AUTO_WIDTH, true );
		setAttribute( BORDER, true );
		setAttribute( BATCH_UPDATE, false );
		setAttribute( SCHEDULE_RETRIEVE_CMD, 0 );
	}
}
