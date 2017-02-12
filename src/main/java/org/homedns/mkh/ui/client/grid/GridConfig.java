/*
 * Copyright 2013-2014 Mikhail Khodonov
 *
 * Licensed under the Apache License, Version 2.0 (the License); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an AS IS BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * $Id$
 */

package org.homedns.mkh.ui.client.grid;

import org.homedns.mkh.ui.client.WidgetConfig;

/**
 * Grid configuration object
 *
 */
@SuppressWarnings( "serial" )
public class GridConfig extends WidgetConfig {
	/**
	* Column resize flag
	*/
	public static final Integer COL_RESIZE = 30;
	/**
	* Filter flag
	*/
	public static final Integer FILTER = 31;
	/**
	* Database filter flag
	*/
	public static final Integer REMOTE_FILTER = 32;
	/**
	* Column auto expand flag, default true
	*/
	public static final Integer COL_AUTO_EXPAND = 33;
	/**
	* Context menu command type item list {@link org.homedns.mkh.ui.client.CmdTypeItem}
	*/
	public static final Integer CONTEXT_MENU = 34;
	/**
	* Sort condition
	*/
	public static final Integer SORT = 35;
	/**
	* Grid title
	*/
	public static final Integer TITLE = 36;
	/**
	* Stripe rows flag, default true
	*/
	public static final Integer STRIPE_ROWS = 37;
	/**
	* No toolbar flag doesn't sense if paging switch on, default false
	*/
	public static final Integer NO_TOOLBAR = 38;
	
	public GridConfig( ) {
		super( );
		setAttribute( WIDTH, 800 );
		setAttribute( HEIGHT, 300 );
		setAttribute( AUTO_HEIGHT, false );
		setAttribute( COL_RESIZE, true );
		setAttribute( FILTER, true );
		setAttribute( REMOTE_FILTER, false );
		setAttribute( COL_AUTO_EXPAND, true );
		setAttribute( CONTEXT_MENU, null );
		setAttribute( SORT, null );
		setAttribute( TITLE, null );
		setAttribute( STRIPE_ROWS, true );
		setAttribute( AUTO_SCROLL, true );
		setAttribute( NO_TOOLBAR, false );
	}
}
