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

package org.homedns.mkh.ui.client.grid;

import java.util.List;
import org.homedns.mkh.dataservice.client.Paging;
import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.dataservice.client.view.ViewCache;
import org.homedns.mkh.dataservice.shared.LoadPageRequest;
import org.homedns.mkh.dataservice.shared.Request;
import org.homedns.mkh.ui.client.CmdTypeItem;
import org.homedns.mkh.ui.client.BaseMenu;
import org.homedns.mkh.ui.client.event.SelectRowEvent;
import org.homedns.mkh.ui.client.filter.Filter;
import org.homedns.mkh.ui.client.filter.FilterToolbar;
import com.google.gwt.user.client.Command;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.event.StoreListenerAdapter;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.RowSelectionModel;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;
import com.gwtext.client.widgets.grid.event.RowSelectionListenerAdapter;

/**
 * Implements grid functionality
 *
 */
public class GridImpl extends AbstractGridImpl {
	private Filter _filter;
	private BaseMenu _menu;
	
	/**
	 * @param cfg
	 *            the grid configuration object
	 */
	public GridImpl( GridConfig cfg ) {
		super( cfg );
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.AbstractGridImpl#config()
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	protected void config( ) {
		super.config( );
		final GridPanel grid = getGrid( );
		GridConfig cfg = getConfiguration( );
//		grid.stripeRows( true );
		if( 
			( Boolean )cfg.getAttribute( GridConfig.FILTER ) || 
			( Boolean )cfg.getAttribute( GridConfig.REMOTE_FILTER ) 
		) {
			_filter = new Filter( ( ( View )grid ) );
			grid.addPlugin( _filter );
		}
		if( ( Boolean )cfg.getAttribute( GridConfig.REMOTE_FILTER ) ) {
			grid.setTopToolbar( new FilterToolbar( _filter ) );
//			final FilterContextMenu menu = new FilterContextMenu( _filter );
//			grid.addGridHeaderListener(
//				new GridHeaderListenerAdapter( ) {
//					public void onHeaderContextMenu( 
//						GridPanel grid,
//		                int colIndex,
//		                EventObject e
//		            ) {
//						e.stopEvent( );
//						menu.showAt( e.getXY( ) );
//					}
//				}
//			);
		}
		if( grid instanceof Paging ) {
			Paging pagingGrid = ( Paging )grid;
			if( pagingGrid.getPageSize( ) > 0 ) {
				final PagingToolbar pt = new PagingToolbar( pagingGrid );
				grid.setBottomToolbar( pt );
				pt.updateInfo( );
				grid.getStore( ).addStoreListener( 
					new StoreListenerAdapter( ) {
						public void onLoad( Store store, Record[] records ) {
							pt.updateInfo( );
						}
					}
				);
			}
		}
		grid.setEnableColumnResize( ( Boolean )cfg.getAttribute( GridConfig.COL_RESIZE ) );
		grid.getView( ).setAutoFill( ( Boolean )cfg.getAttribute( GridConfig.COL_AUTO_EXPAND ) );
		setContextMenu( ( List< CmdTypeItem > )cfg.getAttribute( GridConfig.CONTEXT_MENU ) );
		grid.addListener(
			new PanelListenerAdapter( ) {
				public void onRender( Component component ) {
					selectRow( 0 );
 				}
			}
		);
		grid.getSelectionModel( ).addListener(
			new RowSelectionListenerAdapter( ) {
				public void onRowSelect( RowSelectionModel sm, int rowIndex, Record record ) {
					SelectRowEvent.fire( ( ( View )grid ).getID( ), record );
				}
			}
		);
	}

	/**
	 * Returns context menu
	 * 
	 * @return the context menu
	 */
	public BaseMenu getContextMenu( ) {
		return( _menu );
	}
	
	/**
	 * Sets context menu
	 * 
	 * @param items
	 *            the menu items names
	 */
	protected void setContextMenu( List< CmdTypeItem > items ) {
		if( items == null || items.isEmpty( ) ) {
			return;
		}
		_menu = new BaseMenu( items, ( View )getGrid( ) );
		getGrid( ).addGridRowListener(
			new GridRowListenerAdapter( ) {
				public void onRowContextMenu( 
					final GridPanel grid, 
					final int rowIndex, 
					final EventObject e 
				) {
					e.stopEvent( );
					_menu.showAt( e.getXY( ) );
				}
			}
		);
	}
	
	/**
	 * Selects row in grid
	 * 
	 * @param iRow
	 *            the row index
	 */
	protected void selectRow( int iRow ) {
		getGrid( ).getSelectionModel( ).selectRow( iRow );
	}
	
	/**
	 * @see org.homedns.mkh.ui.client.filter.Filterable#getCondition()
	 */
	@Override
	protected String getCondition( ) {
		String sCondition = null;
		if( _filter != null ) {
			sCondition = _filter.getCondition( );
		}
		return( sCondition );
	}
	
	/**
	 * @see org.homedns.mkh.ui.client.grid.AbstractGridImpl#onSend(org.homedns.mkh.dataservice.shared.Request)
	 */
	@Override
	protected void onSend( Request request ) {
		super.onSend( request );
		if( request instanceof LoadPageRequest ) {
			LoadPageRequest req = ( LoadPageRequest )request;
			req.setLoadingPageNum( ( ( Paging )getGrid( ) ).getPage( ).getPageNumber( ) );
		}
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.AbstractGridImpl#init()
	 */
	@Override
	protected void init( ) {
		super.init( );
		Command cmd = getAfterInitCommand( );
		if( cmd != null ) {
			cmd.execute( );
		}
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.AbstractGridImpl#setCache(org.homedns.mkh.dataservice.client.view.ViewCache)
	 */
	@Override
	protected void setCache( ViewCache cache ) {
		super.setCache( cache );
		getGrid( ).setStore( ( Store )cache );
	}
}
