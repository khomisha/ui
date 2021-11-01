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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.layout.BorderLayout;

/**
 * It supports multiple nested panels, automatic split bars between regions and
 * built-in expanding and collapsing of regions
 */
public class SplitPanel extends GenericPanel {
	public static final String NORTH = "north";
	public static final String SOUTH = "south";
	public static final String WEST = "west";
	public static final String EAST = "east";
	public static final String CENTER = "center";
	
	private Map< String, RegionConfig > configs;
	private int iCompletedCount = 0;
	
	public SplitPanel( ) {
		setLayout( new BorderLayout( ) );
		setBorder( false );
		configs = new HashMap< String, RegionConfig >( );
		configs.put( CENTER, new RegionConfig( RegionPosition.CENTER ) );
	}

	/**
	 * @see org.homedns.mkh.ui.client.frame.GenericPanel#init()
	 */
	@Override
	public void init( ) {
		super.init( );
		Set< String > names = configs.keySet( );
		for( String sName : names ) {
			RegionConfig cfg = configs.get( sName );
			Region region = new Region( cfg );
			putPanel( sName, region );
			add( region, cfg.getLayoutData( ) );
		}
	}

	/**
	 * @see org.homedns.mkh.ui.client.frame.GenericPanel#addPanels()
	 */
	@Override
	public void addPanels( ) {
		iCompletedCount++;
		if( iCompletedCount == configs.keySet( ).size( ) ) {
			super.addPanels( );
			doLayout( );
		}
	}

	/**
	 * Returns region configuration object
	 * 
	 * @param sRegion
	 *            the region name
	 * 
	 * @return the region configuration object
	 */
	public RegionConfig getRegionConfig( String sRegion ) {
		return( configs.get( sRegion ) );
	}

	/**
	 * Sets region configuration object
	 * 
	 * @param sRegion
	 *            the region name
	 * @param config
	 *            the region configuration object
	 */
	public void setRegionConfig( String sRegion, RegionConfig config ) {
		configs.put( sRegion, config );
	}
}
