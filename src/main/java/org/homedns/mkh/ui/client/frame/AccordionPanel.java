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

import org.homedns.mkh.dataservice.client.event.SetAccessEvent;
import org.homedns.mkh.dataservice.client.view.ViewAccess;
import com.google.gwt.event.dom.client.KeyCodes;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.grid.event.GridListenerAdapter;
import com.gwtext.client.widgets.layout.AccordionLayout;

/**
* Container with accordion layout contains list panel and edit panel
*/
@SuppressWarnings( "unchecked" )
public class AccordionPanel extends Panel implements ViewAccess {
	private String _sTag;	
	private Panel _listPanel;
	private Panel _editPanel;
	private int _iCompletedCount = 0;
	private Access _access;

	/**
	 * @param sTitle
	 *            the panel title
	 * @param sListTitle
	 *            the list panel title
	 * @param sEditPanelTitle
	 *            the edit panel title
	 */
	public AccordionPanel( String sTitle, String sListTitle, String sEditPanelTitle ) {
		super( sTitle );
		setLayout( new AccordionLayout( false ) );
		_listPanel = new Panel( sListTitle );
		_listPanel.setBorder( false );
		_editPanel = new Panel( sEditPanelTitle );
		_editPanel.setBorder( false );
	}

	/**
	 * @return the list panel
	 */
	public Panel getListPanel( ) {
		return( _listPanel );
	}

	/**
	 * @return the edit panel
	 */
	public Panel getEditPanel( ) {
		return( _editPanel );
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
		_listPanel.collapse( );
		_editPanel.expand( );
	}
	
	/**
	 * Adds list panel and edit panel then when they are completed 
	 */
	protected void addPanels( ) {
		_iCompletedCount++;
		if( _iCompletedCount == 2 ) {
			add( _listPanel );
			add( _editPanel );
			doLayout( );
	}
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

	/**
	 * @see org.homedns.mkh.dataservice.client.view.ViewAccess#setAccess(org.homedns.mkh.dataservice.client.view.ViewAccess.Access)
	 */
	@Override
	public void setAccess( Access access ) {
		_access = access;
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.ViewAccess#getAccess()
	 */
	@Override
	public Access getAccess( ) {
		return( _access );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.ViewAccess#getTag()
	 */
	@Override
	public String getTag( ) {
		return( _sTag );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.ViewAccess#setTag(java.lang.String)
	 */
	@Override
	public void setTag( String sTag ) {
		_sTag = sTag;		
		SetAccessEvent.fire( this );							
	}
}
