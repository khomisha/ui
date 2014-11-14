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

import java.util.Map;
import org.homedns.mkh.dataservice.client.view.ViewCache;

/**
 * Tracking manager
 *
 */
public interface TrackingMgr extends Map< String, TrackedObject > {
	
	/**
	 * Adds tracked object
	 * 
	 * @param obj
	 *            the object containing tracked object attributes to add
	 * 
	 * @return added tracked object or null
	 */
	public TrackedObject addTrackedObj( Object obj );
	
	/**
	* Refreshes all tracking manager data buffers
	*/
	public void refresh( );

	/**
	* Returns geo map binding with tracking manager
	*/
	public GeoMap getMap( );

	/**
	* Sets geo map binding with tracking manager
	*/
	public void setMap( GeoMap map );

	/**
	* Sets tracked object store
	*/
	public void setTrackedObjCache( ViewCache store );

	/**
	* Returns tracked object store
	*/
	public ViewCache getTrackedObjCache( );

	/**
	* Returns map refresh delay
	*
	* @return map refresh delay
	*/
	public int getRefreshDelay( );

	/**
	 * Sets map refresh delay
	 * 
	 * @param iRefreshDelay
	 *            the refresh delay in millis to set
	 */
	public void setRefreshDelay( int iRefreshDelay );
}
