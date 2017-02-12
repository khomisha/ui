/*
* Copyright 2017 Mikhail Khodonov
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

import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ComponentListenerAdapter;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.layout.FitLayout;

/**
 * A specialized panel intended for use as an application window with specified
 * content panel
 * 
 */
public class BaseWindow extends Window {
	private Panel panel;

	/**
	 * @param sTitle
	 *            the title
	 * @param bModal
	 *            the modal flag true for modal
	 * @param bResize
	 *            the resize flag true for resizable
	 * @param panel
	 *            the content panel
	 */
	public BaseWindow( String sTitle, boolean bModal, boolean bResize, Panel panel ) {
		super( sTitle, bModal, bResize );
		this.panel = panel;
		setBorder( false );
	    setMinWidth( 300 );  
	    setMinHeight( 200 );  
	    setLayout( new FitLayout( ) );  
	    setPaddings( 5 );  
        setCloseAction( Window.HIDE );
        setPlain( true );
		panel.addListener( 
			new ComponentListenerAdapter( ) {
				@Override
				public void onHide( Component component ) {
					BaseWindow.this.hide( );
				}
			}
		);
        add( panel );
	}

	/**
	 * @see com.google.gwt.user.client.ui.DialogBox#show()
	 */
	@Override
	public void show( ) {
		super.show( );
		if( panel != null ) {
			panel.show( );
			if( panel instanceof FormPanel ) {
				FormPanel form = ( FormPanel )panel;
				form.getFields( )[ 0 ].focus( );
			}
		}
	}
}
