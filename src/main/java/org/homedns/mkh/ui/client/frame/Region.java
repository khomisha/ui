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

import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

/**
 * Region within split panel
 *
 */
public class Region extends Panel {
	private RegionConfig cfg;

	public Region( ) {
	}

	/**
	 * @param cfg the region configuration object
	 */
	public Region( RegionConfig cfg ) {
		setCfg( cfg );
		init( );
	}

	/**
	 * Inits region
	 */
	protected void init( ) {
		setLayout( new FitLayout( ) );
		String sTitle = ( String )cfg.getAttribute( RegionConfig.TITLE );
		if( sTitle != null ) {
			setTitle( sTitle );
		}
		setBorder( ( Boolean )cfg.getAttribute( RegionConfig.BORDER ) );
		RegionPosition position = ( RegionPosition )cfg.getAttribute( RegionConfig.POSITION );
		if( !RegionPosition.CENTER.equals( position ) ) {
			setHeader( ( Boolean )cfg.getAttribute( RegionConfig.HEADER ) );
			setCollapsible( ( Boolean )cfg.getAttribute( RegionConfig.COLLAPSIBLE ) );
			if( RegionPosition.NORTH.equals( position ) || RegionPosition.SOUTH.equals( position ) ) {
				setHeight( ( Integer )cfg.getAttribute( RegionConfig.HEIGHT ) );
			} 
			if( RegionPosition.WEST.equals( position ) || RegionPosition.EAST.equals( position ) ) {
				setWidth( ( Integer )cfg.getAttribute( RegionConfig.WIDTH ) );
			}
		}
	}
	
	/**
	 * @see org.homedns.mkh.ui.client.frame.RegionConfig#getLayoutData()
	 */
	public BorderLayoutData getBorderLayoutData( ) {
		return( cfg.getLayoutData( ) );
	}
	
	/**
	 * Returns region configuration object
	 * 
	 * @return the region configuration object
	 */
	public RegionConfig getCfg( ) {
		return( cfg );
	}

	/**
	 * Sets region configuration object
	 * 
	 * @param cfg the region configuration object to set
	 */
	public void setCfg( RegionConfig cfg ) {
		this.cfg = cfg;
	}
}
