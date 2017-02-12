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

import org.homedns.mkh.ui.client.WidgetConfig;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.layout.BorderLayoutData;

/**
 * Region configuration object
 *
 */
@SuppressWarnings( "serial" )
public class RegionConfig extends WidgetConfig {
	/**
	* Key constants to get access to the panel configuration attributes
	* 
	* True to make the panel collapsible and have the expand/collapse toggle button automatically 
	* rendered into the header tool button area, default true
	*/
	public static final Integer COLLAPSIBLE = 800;
	/**
	* True if panel collapsed, default true 
	*/
	public static final Integer COLLAPSED = 801;
	/**
	* When collapseMode is set to 'mini' the region's split bar will also display a small 
	* collapse button in the center of the bar. In 'mini' mode the region will collapse 
	* to a thinner bar than in normal mode, default false 
	*/
	public static final Integer COLLAPSE_MODE_MINI = 802;
	/**
	* True to display a SplitBar between this region and its neighbor, default true   
	*/
	public static final Integer SPLIT = 803;
	/**
	* When SPLIT is true, a minSize in pixels for the region,
	* default 175 for east and west, 100 for north and south  
	*/
	public static final Integer MIN_SIZE = 804;
	/**
	* When SPLIT is true, a maxSize in pixels for the region,
	* default 600 for east and west, 300 for north and south  
	*/
	public static final Integer MAX_SIZE = 805;
	/**
	* Region's margins, default {0, 0, 0, 0}  
	*/
	public static final Integer MARGINS = 806;
	/**
	* Region position 
	*/
	public static final Integer POSITION = 807;
	/**
	* Region position 
	*/
	public static final Integer TITLE = 808;
	/**
	* Header element creation flag
	*/
	public static final Integer HEADER = 809;

	/**
	 * @param position the region position 
	 */
	public RegionConfig( RegionPosition position) {
		setAttribute( POSITION, position );
		setAttribute( COLLAPSIBLE, true );
		setAttribute( COLLAPSED, false );
		setAttribute( COLLAPSE_MODE_MINI, true );
		setAttribute( SPLIT, true );
		if( RegionPosition.NORTH.equals( position ) || RegionPosition.SOUTH.equals( position ) ) {
			setAttribute( MIN_SIZE, 100 );
			setAttribute( MAX_SIZE, 300 );				
		} 
		setAttribute( HEIGHT, 150 );
		if( RegionPosition.WEST.equals( position ) || RegionPosition.EAST.equals( position ) ) {
			setAttribute( MIN_SIZE, 175 );
			setAttribute( MAX_SIZE, 600 );				
		}
		setAttribute( WIDTH, 420 );				
		setAttribute( MARGINS, new Integer[] { 0, 0, 0, 0 } );			
		setAttribute( TITLE, null );				
		setAttribute( BORDER, false );
		setAttribute( HEADER, false );
	}
	
	/**
	 * Returns border layout data
	 * 
	 * @return the border layout data
	 */
	public BorderLayoutData getLayoutData( ) {
		RegionPosition position = ( RegionPosition )getAttribute( POSITION );
		BorderLayoutData layoutData = new BorderLayoutData( position );
		Integer[] margins = ( Integer[] )getAttribute( MARGINS );
		layoutData.setMargins( margins[ 0 ], margins[ 1 ], margins[ 2 ], margins[ 3 ] );
		if( !RegionPosition.CENTER.equals( position ) ) {
			layoutData.setSplit( ( Boolean )getAttribute( SPLIT ) );
			layoutData.setCollapseModeMini( ( Boolean )getAttribute( COLLAPSE_MODE_MINI ) );
		}
		return( layoutData );
	}
}
