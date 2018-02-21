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

package org.homedns.mkh.ui.client.form;

import org.homedns.mkh.dataservice.client.AbstractEntryPoint;
import org.homedns.mkh.ui.client.CmdTypeItem;
import org.homedns.mkh.ui.client.UIConstants;
import org.homedns.mkh.ui.client.command.NullCmd;

/**
 * Grid form configuration object
 *
 */
@SuppressWarnings( "serial" )
public class GridFormConfig extends FormConfig {
	/**
	* Default grid form init state 
	*/
	public static final Integer INIT_STATE = 200;
	/**
	* State transition object
	*/
	public static final Integer TRANSITION = 201;
	
	private static final UIConstants CONSTANTS = ( UIConstants )AbstractEntryPoint.getConstants( );

	public GridFormConfig( ) {
		CmdTypeItem[] items = new CmdTypeItem[] {
			new CmdTypeItem( CONSTANTS.save( ), NullCmd.class ),
			new CmdTypeItem( CONSTANTS.update( ), NullCmd.class ),
			new CmdTypeItem( CONSTANTS.add( ), NullCmd.class ),
			new CmdTypeItem( CONSTANTS.cancel( ), NullCmd.class )
		};
		setAttribute( BUTTONS, items );
		setAttribute( INIT_STATE, GridFormStates.NO_STATE );
		setAttribute( TRANSITION, new GridFormTransition( ) );
	}
}
