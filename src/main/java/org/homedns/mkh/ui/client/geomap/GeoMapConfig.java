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

import org.homedns.mkh.dataservice.shared.AttributeMap;


/**
* Geo map configuration object
*/
@SuppressWarnings( "serial" )
public class GeoMapConfig extends AttributeMap< Integer, Object > {
	/**
	* Default map provider, default google
	*/
	public static final int PROVIDER = 8000;
	/**
	* Longitude input format, default no format, i.e. geo data input as double and don't need conversion 
	*/
	public static final int LON_FMT = 8001;
	/**
	* Latitude input format, default no format, i.e. geo data input as double and don't need conversion 
	*/
	public static final int LAT_FMT = 8002;
	/**
	* Default zoom, ignored if AUTO_CENTER_ZOOM set ON   
	*/
	public static final int DEFAULT_ZOOM = 8003;
	/**
	* If on center and zoom of the map to the smallest bounding box containing all markers,
	* default ON  
	*/
	public static final int AUTO_CENTER_ZOOM = 8004;
	
	/**
	* Geo start point
	*/
	public static final int GEO_CENTER_POINT = 8005;

	/**
	 * Default geo map configuration settings 
	 */
	public GeoMapConfig( ) {
		setAttribute( PROVIDER, GeoMap.GOOGLE );
		setAttribute( LON_FMT, "" );
		setAttribute( LAT_FMT, "" );
		setAttribute( 
			GEO_CENTER_POINT, 
			new GeoPoint( 
				"N554502", "E0373709", new String[] { GeoUtil.LAT_FMT_2, GeoUtil.LON_FMT_2 } 
			)
		);
		setAttribute( DEFAULT_ZOOM, 12 );
		setAttribute( AUTO_CENTER_ZOOM, true );
	}
}
