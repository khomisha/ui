/*
 * Copyright 2012-2014 Mikhail Khodonov
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

package org.homedns.mkh.ui.client.geomap;

import java.util.HashMap;
import java.util.Map;
import com.google.gwt.core.client.JavaScriptObject;
import com.gwtext.client.core.Function;
import com.gwtext.client.widgets.CycleButton;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.CycleButtonListenerAdapter;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.map.GoogleMap;
import com.gwtext.client.widgets.map.MapPanel;
import com.gwtext.client.widgets.map.OpenStreetMap;
import com.gwtext.client.widgets.map.Polyline;
import com.gwtext.client.widgets.menu.CheckItem;

/**
 * Geo map
 *
 */
public class GeoMap extends Panel {
	public static final String GOOGLE = "Google";
	public static final String OPEN_STREET_MAP = "OpenStreetMap";
	
	private MapPanel _map;
	private GeoMapConfig _config;
	private Map< String, GeoMarker > _markers = new HashMap< String, GeoMarker >( );
	
	/**
	 * @param config
	 *            the geo map configuration object
	 */
	public GeoMap( GeoMapConfig config ) {
		_config = config;
		setLayout( new FitLayout( ) );
		_map = createGeoMap( ( String )config.getAttribute( GeoMapConfig.PROVIDER ) );
		add( _map );
		add( getButton( ) );
		doLayout( );
	}
	
	/**
	* Returns map panel.
	* 
	* @return geo map
	*/
	public MapPanel getGeoMap( ) {
		return( _map );
	}
	
	/**
	 * Returns geo map configuration object
	 * 
	 * @return the geo map configuration object
	 */
	public GeoMapConfig getGeoMapConfig( ) {
		return( _config );
	}

	/**
	* Rebuilds map panel for current provider.
	*/
	public void rebuild( ) {
		remove( _map, true );
		insert( 0, createGeoMap( ( String )_config.getAttribute( GeoMapConfig.PROVIDER ) ) );
		doLayout( );		
	}
	
	/**
	 * Creates geo map panel.
	 * 
	 * @param sProvider
	 *            the map provider e.g.
	 *            {@link org.homedns.mkh.ui.client.geomap.GeoMap#GOOGLE}
	 *            {@link org.homedns.mkh.ui.client.geomap.GeoMap#OPEN_STREET_MAP}
	 * 
	 * @return geo map panel
	 */
	protected MapPanel createGeoMap( String sProvider ) {
		MapPanel map = getProviderMap( sProvider );
		map.addMapTypeControls( );
		if( ( Boolean )_config.getAttribute( GeoMapConfig.AUTO_CENTER_ZOOM ) ) {
			map.autoCenterAndZoom( );
		} else {
	        map.setCenterAndZoom( 
	        	( GeoPoint )_config.getAttribute( GeoMapConfig.GEO_CENTER_POINT ), 
	        	( Integer )_config.getAttribute( GeoMapConfig.DEFAULT_ZOOM ) 
	        );
		}
		add2Map( );
		return( map );
	}
	
	/**
	* Adds controls to the map.
	*/
	protected void add2Map( ) {
	};
	
	/**
	* Returns provider's map.
	* 
	 * @param sProvider
	 *            the map provider eg
	 *            {@link org.homedns.mkh.ui.client.geomap.GeoMap#GOOGLE}
	 *            {@link org.homedns.mkh.ui.client.geomap.GeoMap#OPEN_STREET_MAP}
	* 
	* @return geo map panel
	*/
	private MapPanel getProviderMap( String sProvider ) {
		MapPanel mapPanel = null;
		if( GOOGLE.equals( sProvider ) ) {
			mapPanel = new GoogleMap( );
			enableScrollWheelZoom( mapPanel );
		} else if( OPEN_STREET_MAP.equals( sProvider ) ) {
			mapPanel = new OpenStreetMap( );
			enableScrollWheelZoom( mapPanel );
		}
		return( mapPanel );
	}

	/**
	* Returns cycle button to map provider.
	* 
	* @return cycle button
	*/
	protected CycleButton getButton( ) {
		String[] asItem = { GOOGLE, OPEN_STREET_MAP };
		CycleButton button = new CycleButton( );
		button.setShowText( true );
		for( String sItem : asItem ) {
			if( GOOGLE.equals( sItem ) ) {
				button.addItem( new CheckItem( sItem, true ) );				
			} else {
				button.addItem( new CheckItem( sItem ) );
			}
		}
		button.addListener(
			new CycleButtonListenerAdapter( ) {
				public void onChange( CycleButton self, CheckItem item ) {
					_config.setAttribute( GeoMapConfig.PROVIDER, item.getText( ) );
					rebuild( );
				}
			}
		);
		button.setStyle( "position:absolute;left:" + 5 + "px;top:" + 7 + "px;z-index:1000000;" );
		return( button );
	}

	/**
	 * Enables wheel scroll zoom.
	 * 
	 * @param map the geo map panel for which scroll zoom enable
	 */
	protected void enableScrollWheelZoom( final MapPanel map ) {
		map.addListener( 
			MapPanel.MAP_RENDERED_EVENT, new Function( ) {
				public void execute( ) {
					doEnableScrollWheelZoom( map.getMap( ) );
				}
			}
		);
	}

    /**
     * Enables wheel scroll zoom.
     * 
     * @param map the geo map panel
     */
    protected native void doEnableScrollWheelZoom( JavaScriptObject map ) /*-{
    	map.enableScrollWheelZoom();
	}-*/;

	/**
	 * Adds polyline to the geo map
	 * 
	 * @param track
	 *            the polyline to add
	 */
	public void addPolyline( Polyline track ) {
		if( track != null ) {
			_map.addPolyline( track );
		}
	}

	/**
	 * Adds marker to the geo map
	 * 
	 * @param marker
	 *            the marker to add
	 */
	public void addMarker( GeoMarker marker ) {
		String sKey = marker.getId( );
		if( _markers.containsKey( sKey ) ) {
			_map.removeMarker( _markers.get( sKey ) );
		}
		_map.addMarker( marker );
		_markers.put( sKey, marker );
	}
	
	/**
	 * Returns true if marker exits, otherwise false
	 * 
	 * @param sId
	 *            marker identifier
	 * 
	 * @return true if marker exits, otherwise false
	 */
	public boolean isMarkerExist( String sId ) {
		return( _markers.containsKey( sId ) );
	}
	
	/**
	* {@link com.gwtext.client.widgets.map.MapPanel#removeAllPolylines()}
	*/
	public void removeAllPolylines( ) {
		_map.removeAllPolylines( );
	}
	
	/**
	* {@link com.gwtext.client.widgets.map.MapPanel#removeAllMarkers()}
	*/
	public void removeAllMarkers( ) {
		_map.removeAllMarkers( );		
	}
}
