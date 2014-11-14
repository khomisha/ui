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

import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.HTMLPanel;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.layout.FormLayout;
import com.gwtext.client.core.EventObject;

/**
 * Modal simple message box
 *
 */
public class MsgBox extends BaseDialog {
	private HTMLPanel _content;
	
	public MsgBox( ) {
		super( );
		Panel panel = new Panel( );
		panel.setLayout( new FormLayout( ) );
		_content = new HTMLPanel( "", 15, 30, 30, 15 );
		panel.add( _content );
		Button button = new Button( 
			"OK", 
			new ButtonListenerAdapter( ) {
				public void onClick( Button button, EventObject e ) {
					MsgBox.this.hide( );
				}
			}
		);
	    panel.addButton( button );
	    add( panel );
	}
	
	/**
	 * @param sTitle
	 *            the title
	 * @param sContent
	 *            the message box content
	 */
	public MsgBox( String sTitle, String sContent ) {
		this( );
		setText( sTitle );
		setContent( sContent );
	}

	/**
	 * Sets message box content
	 * 
	 * @param sContent the content to set
	 */
	public void setContent( String sContent ) {
		_content.setHtml( sContent );
	}
}
