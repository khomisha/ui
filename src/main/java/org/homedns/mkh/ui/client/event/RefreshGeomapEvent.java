/*
 * Copyright 2013-2014 Mikhail Khodonov
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

package org.homedns.mkh.ui.client.event;

import org.homedns.mkh.dataservice.client.event.GenericEvent;
import org.homedns.mkh.ui.client.geomap.TrackingMgr;
import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event;

/**
 * Geo map event
 *
 */
public class RefreshGeomapEvent extends GenericEvent< RefreshGeomapEvent.RefreshGeomapHandler > {
	/**
	 * Implemented by objects that handle.
	 */
	public interface RefreshGeomapHandler extends EventHandler {
		public void onRefreshGeomap( RefreshGeomapEvent event );
	}
	  
	private TrackingMgr _trackingMgr;
	public static final Event.Type< RefreshGeomapHandler > TYPE = new Event.Type< RefreshGeomapHandler >( );

	/**
	 * Fires geo map refresh event
	 * 
	 * @param trackingMgr the tracking manager
	 */
	public static void fire( TrackingMgr trackingMgr ) {
		RefreshGeomapEvent event = new RefreshGeomapEvent( );
		event.setTrackingMgr( trackingMgr );
		fire( event, null );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.event.GenericEvent#getType()
	 */
	@Override
	protected Event.Type< RefreshGeomapHandler > getType( ) {
		return( TYPE );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.event.GenericEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch( RefreshGeomapHandler handler ) {
		handler.onRefreshGeomap( this );
	}

	/**
	 * Returns the tracking manager
	 * 
	 * @return the tracking manager
	 */
	public TrackingMgr getTrackingMgr( ) {
		return( _trackingMgr );
	}

	/**
	 * Sets the tracking manager
	 * 
	 * @param trackingMgr the tracking manager to set
	 */
	public void setTrackingMgr( TrackingMgr trackingMgr ) {
		_trackingMgr = trackingMgr;
	}
}
