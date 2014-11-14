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

import com.gwtext.client.widgets.map.Polyline;

/**
 * Tracked object, object which placement monitored on
 * {@link org.homedns.mkh.ui.client.geomap.GeoMap}
 * 
 */
public interface TrackedObject {
	public static final int MOVING = 1;
	public static final int STOPPED = 2;
	public static final int DELETED = 3;
	public static final int NOT_AVAILABLE = 4;
	
	/**
	* Returns tracked object geo marker.
	* 
	* @return geo marker
	*/
	public GeoMarker getMarker( );
	
	/**
	 * Sets tracked object geo marker in current point.
	 * 
	 * @param point
	 *            the tracked object current point
	 */
	public void setMarker( GeoPoint point );
	
	/**
	* Returns current track polyline increase, if no increase returns null.
	* 
	* @return track polyline increase or null
	*/
	public Polyline getTrack( );
	
	/**
	 * Sets current track polyline increase.
	 * 
	 * @param aPoint
	 *            the track polyline increase points array
	 */
	public void setTrack( GeoPoint[] aPoint );
	
	/**
	* Returns object state {@link org.homedns.mkh.ui.client.geomap.TrackedObject#MOVING},
	* {@link org.homedns.mkh.ui.client.geomap.TrackedObject#STOPPED},
	* {@link org.homedns.mkh.ui.client.geomap.TrackedObject#DELETED},
	* {@link org.homedns.mkh.ui.client.geomap.TrackedObject#NOT_AVAILABLE}.
	* 
	* @return the object state
	*/
	public int getState( );
	
	/**
	 * Sets object state
	 * {@link org.homedns.mkh.ui.client.geomap.TrackedObject#MOVING},
	 * {@link org.homedns.mkh.ui.client.geomap.TrackedObject#STOPPED}.
	 * {@link org.homedns.mkh.ui.client.geomap.TrackedObject#DELETED},
	 * {@link org.homedns.mkh.ui.client.geomap.TrackedObject#NOT_AVAILABLE}.
	 * 
	 * @param iState
	 *            the object state to set
	 */
	public void setState( int iState );

	/**
	* Returns tracked object identifier.
	* 
	* @return tracked object identifier
	*/
	public String getId( );
	
	/**
	* Sets tracked object identifier.
	* 
	* @param sId the object identifier
	*/
	public void setId( String sId );

	/**
	* Refreshes tracking data (current point, tracking line).
	*/
	public void refresh( );
	
	/**
	* Reloads tracking data from data base.
	*/
	public void reload( );
		
	/**
	* Returns tracked object current geo point
	* 
	* @return the current geo point
	*/
	public GeoPoint getPoint( );

	/**
	* Sets tracked object current geo point
	* 
	* @param point the point to set
	*/
	public void setPoint( GeoPoint point );

	/**
	 * Sets tracked flag for object, if true it's mean that object track showed
	 * on map
	 * 
	 * @param bTracked
	 *            the object tracked flag
	 */
	public void setTracked( boolean bTracked );

	/**
	* Returns object tracked flag
	* 
	* @return object tracked flag
	*/
	public boolean isTracked( );
}
