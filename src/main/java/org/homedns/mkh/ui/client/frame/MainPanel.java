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

package org.homedns.mkh.ui.client.frame;

import org.homedns.mkh.dataservice.client.event.EventBus;
import org.homedns.mkh.dataservice.client.event.HandlerRegistryAdaptee;
import org.homedns.mkh.dataservice.client.event.HandlerRegistry;
import org.homedns.mkh.ui.client.event.NavigationEvent;
import org.homedns.mkh.ui.client.event.NavigationEvent.NavigationHandler;
import org.homedns.mkh.ui.client.frame.BasePanel.Token;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtext.client.core.EventCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.layout.FitLayout;

/**
 * The application top level panel
 *
 */
@SuppressWarnings( "unchecked" )
public abstract class MainPanel extends Panel implements NavigationHandler, HandlerRegistry {
	private BasePanel _currentPanel;
	private HandlerRegistryAdaptee _handlers;
	private Timer _logoutTimer;
	private int _iSessionTimeout;
	private int _iRefreshTimeout;

	public MainPanel( ) {
		setLayout( new FitLayout( ) );
		setBorder( false );
		_handlers = new HandlerRegistryAdaptee( );
		addHandler( EventBus.getInstance( ).addHandler( NavigationEvent.TYPE, this ) );
	}

	/**
	 * @see org.homedns.mkh.ui.client.event.NavigationEvent.NavigationHandler#onNavigation(org.homedns.mkh.ui.client.event.NavigationEvent)
	 */
	@Override
	public void onNavigation( NavigationEvent event ) {
		showPanel( getPanel( event.getToken( ) ) );		
	}

	/**
	 * Shows panel.
	 * 
	 * @param panel
	 *            the panel to be show
	 */
	protected void showPanel( BasePanel panel ) {
		if( panel == null ) {
			return;
		}

		if( _currentPanel != null ) {
			if( _currentPanel.getToken( ) == panel.getToken( ) ) {
				// This is the same panel, do nothing
				return;
			}
		}

		// Remove the old panel from the display area.
		if( _currentPanel != null ) {
			_currentPanel.onRemove( );
		  	remove( _currentPanel, true );
		}

	    // Get the new panel instance
	    _currentPanel = panel;

		// Display the new panel.
		add( _currentPanel );
		_currentPanel.onShow( );
		doLayout( );
	}

	/**
	* Returns current active panel
	*
	* @return current panel
	*/
	public BasePanel getCurrentPanel( ) {
		return( _currentPanel );
	}

	/**
	 * Sets logout timer
	 * 
	 * @param logoutTimer
	 *            the logout timer to set
	 */
	public void setLogoutTimer( Timer logoutTimer ) {
		_logoutTimer = logoutTimer;
	}

	/**
	 * Sets session timeout in millis and starts auto logout if session timeout
	 * > 0 {@link org.homedns.mkh.ui.client.frame.MainPanel#runAutoLogout()}
	 * 
	 * @param iSessionTimeout
	 *            the session timeout to set
	 */
	public void setSessionTimeout( int iSessionTimeout ) {
		_iSessionTimeout = iSessionTimeout;
		if( iSessionTimeout > 0 ) {
			runAutoLogout( );
		}
	}

	/**
	 * Returns UI refresh timeout
	 * 
	 * @return the UI refresh timeout
	 */
	public int getRefreshTimeout( ) {
		return( _iRefreshTimeout );
	}

	/**
	 * Sets the UI refresh timeout
	 * 
	 * @param iRefreshTimeout
	 *            the the UI refresh timeout to set
	 */
	public void setRefreshTimeout( int iRefreshTimeout ) {
		_iRefreshTimeout = iRefreshTimeout;
	}

	/**
	 * Returns token by it's name
	 * 
	 * @param sTokenName
	 *            the token name
	 * 
	 * @return the token
	 */
	public abstract Token getToken( String sTokenName );

	/**
	 * Returns base panel or null if it doesn't exist
	 * 
	 * @param token
	 *            the panel's identifier
	 * 
	 * @return panel or null
	 */
	protected BasePanel getPanel( BasePanel.Token token ) {
		if( token != null ) {
			return( token.getPanel( ) );
		}
		return( null );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.event.HandlerRegistry#removeHandlers()
	 */
	@Override
	public void removeHandlers( ) {
		_handlers.clear( );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.event.HandlerRegistry#addHandler(com.google.web.bindery.event.shared.HandlerRegistration)
	 */
	@Override
	public boolean addHandler( HandlerRegistration hr ) {
		return( _handlers.add( hr ) );		
	}
	
	/**
	 * Runs auto logout, i.e. if no keyboard and mouse activity within a
	 * specified period then logout occurs
	 */
	public void runAutoLogout( ) {
		watchEvent( 
			new EventCallback( ) {
				public void execute( EventObject e ) {
					renewLogoutTimer( );
				}				
			}
		);
		renewLogoutTimer( );
	}
	
 	/**
	 * Watches keydown and mouse click events
	 * 
	 * @param callback
	 *            the callback function when event happens
	 */
	protected void watchEvent( EventCallback callback ) {
		ExtElement root = Ext.get( RootPanel.getBodyElement( ) );
		root.addListener( "keydown", callback );
		root.addListener( "click", callback	);
	}

	/**
	 * Re-news logout timer
	 */
	private void renewLogoutTimer( ) {
		if( _logoutTimer != null ) {
			_logoutTimer.schedule( _iSessionTimeout );
		}
	}
}
