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
* $Id: BaseDialog.java 63 2012-10-11 06:37:48Z khomisha $
*/

package org.homedns.mkh.ui.client.frame;

import org.homedns.mkh.ui.client.CSSHelper;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.DialogBox;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ComponentListenerAdapter;
import com.gwtext.client.widgets.form.FormPanel;

/**
* Base dialog.
*/
public class BaseDialog extends DialogBox {
	private Panel _panel;
	
	public BaseDialog( ) {
		super( false, true );
		config( );
	}
	
	/**
	 * Base dialog with form or editable grid
	 * 
	 * @param sTitle
	 *            the dialog title
	 * @param panel
	 *            the panel to add
	 */
	public BaseDialog( String sTitle, Panel panel ) {
		this( );
		setText( sTitle );
		_panel = panel;
		panel.addListener( 
			new ComponentListenerAdapter( ) {
				@Override
				public void onHide( Component component ) {
					if( _panel instanceof FormPanel ) {
						( ( FormPanel )_panel ).getForm( ).reset( );
					}
					BaseDialog.this.hide( );
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
		if( _panel != null ) {
			_panel.show( );
			if( _panel instanceof FormPanel ) {
				FormPanel form = ( FormPanel )_panel;
				form.getFields( )[ 0 ].focus( );
			}
		}
	}

	/**
	* Sets dialog configuration
	*/
	protected void config( ) {
		setAnimationEnabled( true );
		setGlassEnabled( true );
		addStyleName( "x-panel-tc" );
		addStyleName( "x-panel-bc" );
		addStyleName( "x-panel-mc" );
		Element child = CSSHelper.getChild( this, "Caption" );
		if( child != null ) {
			setStyleName( child , "x-panel-header" );
		}
	}
}