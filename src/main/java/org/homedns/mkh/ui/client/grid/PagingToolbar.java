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

package org.homedns.mkh.ui.client.grid;

import java.util.HashMap;
import java.util.Map;
import org.homedns.mkh.dataservice.client.AbstractEntryPoint;
import org.homedns.mkh.dataservice.client.Page;
import org.homedns.mkh.dataservice.client.Paging;
import org.homedns.mkh.dataservice.shared.LoadPageRequest;
import org.homedns.mkh.ui.client.UIMessages;
import org.homedns.mkh.ui.client.cache.PagerStore;
import org.homedns.mkh.ui.client.command.CommandFactory;
import org.homedns.mkh.ui.client.command.RetrieveCmd;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.ToolbarTextItem;
import com.gwtext.client.widgets.event.ButtonListener;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.KeyListener;
import com.gwtext.client.widgets.form.NumberField;

/**
 * Paging toolbar
 *
 */
public class PagingToolbar extends Toolbar {
	private static final UIMessages MESSAGES = ( UIMessages )AbstractEntryPoint.getMessages( );
	private static final int BTN_FIRST 		= 1000;
	private static final int BTN_LAST 		= 1001;
	private static final int BTN_PREV 		= 1002;
	private static final int BTN_NEXT 		= 1003;
	private static final int BTN_REFRESH 	= 1004;
	private static final int NUM_FIELD 		= 1005;
	private static final int ITM_AFTER 		= 1006;
	private static final int ITM_INFO 		= 1007;
	
	private Map< Integer, Widget > _pageWidgets = new HashMap< Integer, Widget >( );
	private Page _page;
	private Command _loadCmd;
	private Command _retrieveCmd;
	private Paging _widget;

	/**
	 * @param widget
	 *            the paging widget
	 */
	public PagingToolbar( Paging widget ) {
		_widget = widget;
		_loadCmd = CommandFactory.create( LoadPageRequest.class, widget );
		_retrieveCmd = CommandFactory.create( RetrieveCmd.class, widget );
		_page = widget.getPage( );
		_pageWidgets.put(  
			BTN_FIRST, 
			addButton( 
				"x-tbar-page-first", 
				new ButtonListenerAdapter( ) {
					public void onClick( Button button, EventObject e ) {
						_page.firstPage( );
						execute( );
					}
				} 
			) 
		);
		_pageWidgets.put(  
			BTN_PREV, 
			addButton( 
				"x-tbar-page-prev", 
				new ButtonListenerAdapter( ) {
					public void onClick( Button button, EventObject e ) {
						_page.prevPage( );
						execute( );
					}
				} 
			) 
		);
		addSeparator( );
		addText( MESSAGES.page( ) );
		_pageWidgets.put( NUM_FIELD, addField( "x-tbar-page-number" ) );
		_pageWidgets.put( ITM_AFTER, addItem( MESSAGES.of( _page.getPageNumber( ) ) ) );
		addSeparator( );
		_pageWidgets.put(  
			BTN_NEXT, 
			addButton( 
				"x-tbar-page-next", 
				new ButtonListenerAdapter( ) {
					public void onClick( Button button, EventObject e ) {
						_page.nextPage( );
						execute( );
					}
				} 
			) 
		);
		_pageWidgets.put(  
			BTN_LAST, 
			addButton( 
				"x-tbar-page-last", 
				new ButtonListenerAdapter( ) {
					public void onClick( Button button, EventObject e ) {
						_page.lastPage( );
						execute( );
					}
				} 
			) 
		);
		addSeparator( );
		_pageWidgets.put(  
			BTN_REFRESH, 
			addButton( 
				"x-tbar-loading", 
				new ButtonListenerAdapter( ) {
					public void onClick( Button button, EventObject e ) {
						execute( );
					}
				} 
			) 
		);
		addFill( );
		_pageWidgets.put( ITM_INFO, addItem( MESSAGES.noRecords( ) ) );
	}

	/**
	 * Returns page object
	 * 
	 * @return the page object
	 */
	public Page getPage( ) {
		return( _page );
	}

	/**
	 * Updates toolbar text info and buttons state
	 * 
	 */
	public void updateInfo( ) {
		int iLastPage = _page.getLastPage( );
		int iCurrentPage = _page.getPageNumber( );
		int iPageSize = _page.getPageSize( );
		int iRowCount = _page.getRowCount( );
		if( iRowCount > 0 ) {
			Store store = ( Store )_widget.getCache( );
			( ( ToolbarTextItem )_pageWidgets.get( ITM_AFTER ) ).setText( MESSAGES.of( iLastPage ) );
			( ( ToolbarTextItem )_pageWidgets.get( ITM_INFO ) ).setText(
				MESSAGES.displaying( 
					( iCurrentPage - 1 ) * iPageSize + 1, 
					( iCurrentPage - 1 ) * iPageSize + store.getCount( ) 
				) + " " + MESSAGES.of( iRowCount )
			);
		} else {
			( ( ToolbarTextItem )_pageWidgets.get( ITM_AFTER ) ).setText( MESSAGES.of( iCurrentPage ) );
			( ( ToolbarTextItem )_pageWidgets.get( ITM_INFO ) ).setText( MESSAGES.noRecords( ) );		
		}
		( ( NumberField )_pageWidgets.get( NUM_FIELD ) ).setValue( String.valueOf( iCurrentPage ) );
		( ( Component )_pageWidgets.get( BTN_PREV ) ).setDisabled( iCurrentPage == 1 );
		( ( Component )_pageWidgets.get( BTN_FIRST ) ).setDisabled( iCurrentPage == 1 );
		( ( Component )_pageWidgets.get( BTN_NEXT ) ).setDisabled( iCurrentPage == iLastPage );
		( ( Component )_pageWidgets.get( BTN_LAST ) ).setDisabled( iCurrentPage == iLastPage );		
	}	

	/**
	 * Adds toolbar button
	 * 
	 * @param iButton
	 *            the button identifier
	 * @param sStyle
	 *            the button style
	 * 
	 * @return the component's ID
	 */
	private Widget addButton( String sStyle, ButtonListener listener ) {
		ToolbarButton button = new ToolbarButton( );
		button.addListener( listener );
		button.setIconCls( sStyle );
		addButton( button );
		return( button );
	}

	/**
	 * Adds field
	 * 
	 * @param sStyle
	 *            the field style
	 * 
	 * @return the added field
	 */
	private Widget addField( String sStyle ) {
		final NumberField field = new NumberField( );
		field.setHideLabel( true );
		field.setAllowDecimals( false );
		field.setSelectOnFocus( true );
		field.setFieldClass( sStyle );
		field.addKeyListener( 
			EventObject.ENTER,
            new KeyListener( ) {
				public void onKey( int key, EventObject e ) {
					if( key == EventObject.ENTER ) {
						_page.setPageNumber( Integer.valueOf( field.getRawValue( ) ) );
						execute( );
					}
				}
			}
        );
		addField( field );
		return( field );
	}

	/**
	 * Adds toolbar text item
	 * 
	 * @param sText
	 *            the item text
	 * 
	 * @return the component's ID
	 */
	private Widget addItem( String sText ) {
		ToolbarTextItem item = new ToolbarTextItem( sText );
		addItem( item );
		return( item );
	}
	
	/**
	 * Executes command
	 */
	private void execute( ) {
		if( _widget.isServerPaging( ) ) {
			_loadCmd.execute( );
		} else {
			PagerStore pagerStore = ( PagerStore )_widget.getCache( );
			if( _page.isChanged( ) ) {
				pagerStore.loadPage( _page );
			} else {
				pagerStore.setPage( _page );
				_retrieveCmd.execute( );
			}
		}
	}
}
