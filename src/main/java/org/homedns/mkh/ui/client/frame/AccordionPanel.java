/*
 * Copyright 2014 Mikhail Khodonov
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

import org.homedns.mkh.dataservice.client.view.View;
import com.google.gwt.event.dom.client.KeyCodes;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.grid.event.GridListenerAdapter;
import com.gwtext.client.widgets.layout.AccordionLayout;
import com.gwtext.client.widgets.layout.FitLayout;

/**
* Container with accordion layout contains list panel and edit panel
*/
@SuppressWarnings( "unchecked" )
public class AccordionPanel extends GenericPanel {
	private int iCompletedCount = 0;

	public AccordionPanel( ) {
		setLayout( new AccordionLayout( false ) );
	}
	
	public AccordionPanel( String sTitle ) {
		this( );
		setTitle( sTitle );
	}

	/**
	 * @see org.homedns.mkh.ui.client.frame.GenericPanel#init()
	 */
	@Override
	protected void init( ) {
		Panel listPanel = new Panel( );
		listPanel.setBorder( false );
		listPanel.setLayout( new FitLayout( ) );
		putPanel( "list", listPanel );
		Panel editPanel = new Panel( );
		editPanel.setBorder( false );	
		editPanel.setLayout( new FitLayout( ) );
		putPanel( "edit", editPanel );		
	}

	/**
	* Proceeds key codes 
	*/
	protected void proceedKeyDown( int iKeyCode ) {
		if( iKeyCode == KeyCodes.KEY_ENTER ) {
			expandDetail( );
		}		
	}

	/**
	 * Expands detail panel
	 */
	protected void expandDetail( ) {
		getPanel( "list" ).collapse( );
		getPanel( "edit" ).expand( );
	}
	
	/**
	 * Adds list panel and edit panel then when they are completed 
	 */
	public void addPanels( ) {
		iCompletedCount++;
		if( iCompletedCount == 2 ) {
			add( getPanel( "list" ) );
			add( getPanel( "edit" ) );
			doLayout( );
		}
	}

	/**
	 * @see org.homedns.mkh.ui.client.frame.GenericPanel#onAfterInit(org.homedns.mkh.dataservice.client.view.View)
	 */
	@Override
	protected void onAfterInit( View view ) {
	}
	
	/**
	 * Built in list panel grid listener adapter, performs expand detail action
	 * on grid double click
	 */
	public class ListWidgetListenerAdapter extends GridListenerAdapter {
		public void onKeyDown( final EventObject e ) {							
			proceedKeyDown( e.getCharCode( ) );
		}						
		public void onDblClick( EventObject e ) {
			expandDetail( );
		}		
	}
}
