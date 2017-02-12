/*
* Copyright 2011-2013 Mikhail Khodonov
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

import org.homedns.mkh.ui.client.CmdTypeItem;
import org.homedns.mkh.ui.client.WidgetConfig;
import org.homedns.mkh.ui.client.command.NullCmd;
import com.gwtext.client.core.Position;

/**
* Form configuration object
*/
@SuppressWarnings( "serial" )
public class FormConfig extends WidgetConfig {
	/**
	* Label width, default 110 
	*/
	public static final Integer LABEL_WIDTH = 100;
	/**
	* Padding, default 15
	*/
	public static final Integer PADDING = 101;
	/**
	* Field length, default 150 
	*/
	public static final Integer FIELD_LEN = 102;
	/**
	* Form title, default empty
	*/
	public static final Integer TITLE = 103;
	/**
	* Fields name list, which will be added to form,
	* if null all fields defined in based grid or store will be added,
	* default is null.
	*/
	public static final Integer FIELD_LIST = 104;
	/**
	* Buttons names 
	*/
	public static final Integer BUTTONS = 105;	
	/**
	* Load select record to the form onRowSelect event in the parent grid,
	* default true
	*/
	public static final Integer LOAD_ON_ROW_SELECT = 106;	
	/**
	* If set to true, form.reset() resets to the last loaded or setValues() data 
	* instead of when the form was first created. 
	* Default false.
	*/
	public static final Integer TRACK_ON_RESET = 107;	
	/**
	* Button align, default right
	*/
	public static final Integer BUTTON_ALIGN = 108;
	/**
	* Label align, default right
	*/
	public static final Integer LABEL_ALIGN = 109;

	/**
	 * default configuration settings
	 */
	public FormConfig( ) {
		super( );
		setAttribute( WIDTH, 320 );
		setAttribute( LABEL_WIDTH, 110 );
		setAttribute( PADDING, 15 );
		setAttribute( FIELD_LEN, 150 );
		setAttribute( TITLE, "" );
		setAttribute( FIELD_LIST, ( String[] )null );
		setAttribute( LOAD_ON_ROW_SELECT, true );
		setAttribute( TRACK_ON_RESET, false );
		setAttribute( BUTTON_ALIGN, Position.RIGHT );
		setAttribute( LABEL_ALIGN, Position.RIGHT );
		setAttribute( AUTO_SCROLL, false );
		setAttribute( 
			BUTTONS, 
			new CmdTypeItem[] { 
				new CmdTypeItem( CONSTANTS.ok( ), NullCmd.class ), 
				new CmdTypeItem( CONSTANTS.cancel( ), NullCmd.class )
			} 
		);
	}
}