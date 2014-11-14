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

import com.gwtext.client.widgets.map.LatLonPoint;

/**
 * GeoPoint is a point containing a latitude and longitude. 
 *
 */
public class GeoPoint extends LatLonPoint {

	/**
	 * @param dbLat
	 *            the latitude
	 * @param dbLon
	 *            the longitude
	 */
	public GeoPoint( double dbLat, double dbLon ) {
		super( dbLat, dbLon );
	}

	/**
	 * @param sLat
	 *            the latitude
	 * @param sLon
	 *            the longitude
	 * @param asFormat
	 *            the latitude/longitude format string
	 *            {@link org.homedns.mkh.ui.client.geomap.GeoUtil#LAT_FMT_1}
	 *            {@link org.homedns.mkh.ui.client.geomap.GeoUtil#LON_FMT_1}
	 *            {@link org.homedns.mkh.ui.client.geomap.GeoUtil#LAT_FMT_2}
	 *            {@link org.homedns.mkh.ui.client.geomap.GeoUtil#LON_FMT_2}
	 */
	public GeoPoint( String sLat, String sLon, String[] asFormat ) {
		this( GeoUtil.convert( sLat, asFormat[ 0 ] ), GeoUtil.convert( sLon, asFormat[ 1 ] ) );
	}
}
