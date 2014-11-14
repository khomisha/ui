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
import org.homedns.mkh.ui.client.frame.BasePanel;
import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event;

/**
 * Navigation event
 *
 */
public class NavigationEvent extends GenericEvent< NavigationEvent.NavigationHandler > {
	/**
	 * Implemented by objects that handle.
	 */
	public interface NavigationHandler extends EventHandler {
		public void onNavigation( NavigationEvent event );
	}
	  
	private BasePanel.Token _token;
	public static final Event.Type< NavigationHandler > TYPE = new Event.Type< NavigationHandler >( );

	/**
	 * Fires navigation event
	 * 
	 * @param token the panel token
	 */
	public static void fire( BasePanel.Token token ) {
		NavigationEvent event = new NavigationEvent( );
		event.setToken( token );
		fire( event, null );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.event.GenericEvent#getType()
	 */
	@Override
	protected Event.Type< NavigationHandler > getType( ) {
		return( TYPE );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.event.GenericEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch( NavigationHandler handler ) {
		handler.onNavigation( this );
	}

	/**
	 * Returns panel's token
	 * 
	 * @return the token
	 */
	public BasePanel.Token getToken( ) {
		return( _token );
	}

	/**
	 * Sets panel's token
	 * 
	 * @param token the token to set
	 */
	public void setToken( BasePanel.Token token ) {
		_token = token;
	}
}
