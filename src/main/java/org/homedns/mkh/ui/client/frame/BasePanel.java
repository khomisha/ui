/*
* Copyright 2007-2014 Mikhail Khodonov
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

import org.homedns.mkh.dataservice.client.event.RemoveViewEvent;
import org.homedns.mkh.dataservice.client.view.View;
import com.gwtext.client.core.Ext;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Container;
import com.gwtext.client.widgets.Panel;

/**
* Base panel, all panels are used as navigation subject must extend base panel
* @see org.homedns.mkh.ui.client.frame.MainPanel
*/
@SuppressWarnings( "unchecked" )
public abstract class BasePanel extends Panel {
	private Token _token;
	
	public BasePanel( ) {
		setWidth( Ext.getBody( ).getWidth( true ) );
		setHeight( Ext.getBody( ).getHeight( true ) );
		setBorder( false );
	}

	/**
	 * Returns panel's token
	 * 
	 * @return the panel's token
	 */
	public Token getToken( ) {
		return( _token );
	}

	/**
	 * Sets panel's token
	 * 
	 * @param token the token to set
	 */
	public void setToken( Token token ) {
		_token = token;
	}


	/**
	* Called just before this panel is remove.
	*/
	public void onRemove( ) {
		remove( this );
	}

	/**
	* Called just after this panel is shown.
	*/
	public void onShow( ) {
	}

	/**
	 * Removes component, fires
	 * {@link org.homedns.mkh.dataservice.client.event.RemoveViewEvent} for all
	 * {@link org.homedns.mkh.dataservice.client.view.View} components
	 * 
	 * @param component
	 *            the component from which all child should be remove
	 */
	private void remove( Component component ) {
		if( component instanceof View ) {
			RemoveViewEvent.fire( ( View )component );
		}
		if( component instanceof Container ) {
			Component[] childs = ( ( Container )component ).getComponents( );
			for( Component child : childs ) {
				remove( child );
				child.destroy( );
			}
		}
	}
	
	/**
	* Opens panel.
	*/
	protected void open( ) {
	}

	/**
	* Panel's identifier
	*/
	public static abstract class Token {
		private String _sName;
		
		/**
		 * @param sName the token name
		 */
		public Token( String sName ) {
			_sName = sName;
		}

		/**
		 * Creates base panel
		 * 
		 * @return the base panel
		 */
		public abstract BasePanel createPanel( );

		/**
		* Returns panel instance
		*
		* @return the panel instance
		*/
		public BasePanel getPanel( ) {
			BasePanel panel = createPanel( );
			panel.setToken( this );
			return( panel );
		}

		/**
		 * Returns token name
		 * 
		 * @return the token name
		 */
		public String getName( ) {
			return( _sName );
		}
	}
}
