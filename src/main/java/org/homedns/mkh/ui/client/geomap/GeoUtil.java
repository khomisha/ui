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
 * $Id: GeoUtil.java 53 2012-06-19 14:04:59Z khomisha $
 */

package org.homedns.mkh.ui.client.geomap;

import java.util.HashMap;
import java.util.Map;

/**
 * The geo utility class
 *
 */
public class GeoUtil {
	/**
	 * latitude format 'DGGMMmmmm'
	 */
	public static final String LAT_FMT_1 = "DGGMMmmmm";
	/**
	 * longitude format 'DGGGMMmmmm'
	 */
	public static final String LON_FMT_1 = "DGGGMMmmmm";
	/**
	 * latitude format 'DGGMMSS'
	 */
	public static final String LAT_FMT_2 = "DGGMMSS";
	/**
	 * longitude format 'DGGGMMSS'
	 */
	public static final String LON_FMT_2 = "DGGGMMSS";
	private static final String DELIM = ";";

	private static Map< String, Integer[] > _fmt = new HashMap< String, Integer[] >( );
	
	static {
		_fmt.put( LAT_FMT_1, new Integer[] { 1, 4, 7 } );
		_fmt.put( LON_FMT_1, new Integer[] { 1, 5, 8 } );
		_fmt.put( LAT_FMT_2, new Integer[] { 1, 4, 7 } );
		_fmt.put( LON_FMT_2, new Integer[] { 1, 5, 8 } );
	}
	
	/**
	 * Converts geo coordinates to the double
	 * 
	 * @param sCoordinate
	 *            the geo coordinates
	 * @param sFormat
	 *            the geo coordinates format
	 * 
	 * @return geo the coordinates as double
	 */
	public static double convert( String sCoordinate, String sFormat ) {
		StringBuffer sb = new StringBuffer( sCoordinate );
		for( Integer iDelimPos : _fmt.get( sFormat ) ) {
			sb.insert( iDelimPos, DELIM ); 
		}
		String[] as = sb.toString( ).split( DELIM );
		double db = 0;
		double dbSign = ( "N".equals( as[ 0 ] ) || "E".equals( as[ 0 ] ) ) ? 1 : -1;
		if( as[ 3 ].length( ) == 4 ) {
			db = dbSign * ( 
				Double.valueOf( as[ 1 ] ) + Double.valueOf( as[ 2 ] + "." + as[ 3 ] ) / 60 
			);
		} else {
			db = dbSign * ( 
				Double.valueOf( as[ 1 ] ) +
				( Double.valueOf( as[ 2 ] ) + Double.valueOf( as[ 3 ] ) / 60 ) / 60 
			);			
		}
		return( db );
	}
}
