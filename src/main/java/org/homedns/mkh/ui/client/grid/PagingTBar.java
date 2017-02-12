/*
 * Copyright 2015 Mikhail Khodonov
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

import org.homedns.mkh.dataservice.client.AbstractEntryPoint;
import org.homedns.mkh.dataservice.client.Paging;
import org.homedns.mkh.ui.client.ButtonClickListener;
import org.homedns.mkh.ui.client.HasButton;
import org.homedns.mkh.ui.client.UIConstants;
import org.homedns.mkh.ui.client.UIMessages;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.PagingToolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.ComponentListenerAdapter;

/**
 * Custom paging toolbar
 *
 */
public class PagingTBar extends PagingToolbar {
	private static final UIConstants CONSTANTS = ( UIConstants )AbstractEntryPoint.getConstants( );
	private static final UIMessages MESSAGES = ( UIMessages )AbstractEntryPoint.getMessages( );

	private Paging grid;
	
	/**
	 * @param grid the parent grid
	 */
	public PagingTBar( final Paging grid  ) {
		this.grid = grid;
		final Store store = ( Store )grid.getCache( );
		setStore( store );
		setPageSize( grid.getPageSize( ) );
		setDisplayInfo( true );
		setDisplayMsg( MESSAGES.displaying( ) + " {0} - {1} " + MESSAGES.of( ) + " {2}" );
		setEmptyMsg( MESSAGES.noRecords( ) );
		setAfterPageText( MESSAGES.of( ) + " {0}" );
		setBeforePageText( MESSAGES.page( ) );
		setFirstText( "" );
		setLastText( "" );
		setNextText( "" );
		setPrevText( "" );
		setRefreshText( CONSTANTS.refresh( ) );
		addListener( 
			new ComponentListenerAdapter( ) {
				@Override
				public void onRender( Component component ) {
					Button refresh = getRefreshButton( );
					refresh.purgeListeners( );
					refresh.addListener( 
						new ButtonListenerAdapter( ) {
							@Override
							public void onClick( Button button, EventObject e ) {
								grid.reload( );
							}
						}
					);
					store.load( 0, grid.getPageSize( ) );
				}
			}
		);
	}

	/**
	 * Adds toolbar button
	 * 
	 * @param sLabel
	 *            the label
	 * @param sStyle
	 *            the style
	 * @param attributes
	 *            the attributes values array
	 * 
	 * @return the toolbar button
	 */
	public ToolbarButton addButton( String sLabel, String sStyle, String[] attributes ) {
		ToolbarButton button = new ToolbarButton( );
		if( sLabel != null ) {
			button.setText( sLabel );
		}
		if( sStyle != null && !"".equals( sStyle ) ) {
			button.setIconCls( sStyle );
		}
		button.addListener( new ButtonClickListener( ( HasButton )grid ) );
		if( attributes != null ) {
			for( String sAttribute : attributes ) {
				String[] as = sAttribute.split( "=" );
				if( "visible".equals( as[ 0 ] ) ) {
					button.setVisible( Boolean.valueOf( as[ 1 ] ) );
				} else if( "disabled".equals( as[ 0 ] ) ) {
					button.setDisabled( Boolean.valueOf( as[ 1 ] ) );
				} else if( "toggle".equals( as[ 0 ] ) ) {
					button.setEnableToggle( Boolean.valueOf( as[ 1 ] ) );
				} else if( "pressed".equals( as[ 0 ] ) ) {
					button.setPressed( Boolean.valueOf( as[ 1 ] ) );					
				}
			}
		}
		addButton( button );
		return( button );
	}
}
