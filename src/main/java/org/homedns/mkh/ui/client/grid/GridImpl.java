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
import java.util.logging.Logger;

import org.homedns.mkh.dataservice.client.AbstractEntryPoint;
import org.homedns.mkh.dataservice.client.Paging;
import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.dataservice.client.view.ViewCache;
import org.homedns.mkh.dataservice.shared.LoadPageRequest;
import org.homedns.mkh.dataservice.shared.Request;
import org.homedns.mkh.ui.client.CmdTypeItem;
import org.homedns.mkh.ui.client.BaseMenu;
import org.homedns.mkh.ui.client.HasButton;
import org.homedns.mkh.ui.client.UIConstants;
import org.homedns.mkh.ui.client.ViewToolbar;
import org.homedns.mkh.ui.client.cache.WidgetStore;
import org.homedns.mkh.ui.client.event.SelectRowEvent;
import org.homedns.mkh.ui.client.filter.Filter;

import com.google.gwt.user.client.Command;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.SortDir;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.SortState;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.GridView;
import com.gwtext.client.widgets.grid.RowSelectionModel;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;
import com.gwtext.client.widgets.grid.event.RowSelectionListenerAdapter;

/**
 * Implements grid functionality
 *
 */
public class GridImpl extends AbstractGridImpl {
	private static final UIConstants CONSTANTS = ( UIConstants )AbstractEntryPoint.getConstants( );
	private static final Logger LOG = Logger.getLogger( GridImpl.class.getName( ) );  

	private Filter filter;
	private BaseMenu menu;
	boolean bRemoteFilter;
	
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
		final GridPanel grid = getGrid( ) ;
		GridConfig cfg = getConfiguration( );
		grid.stripeRows( true );
		bRemoteFilter = ( Boolean )cfg.getAttribute( GridConfig.REMOTE_FILTER );
		if( 
			( Boolean )cfg.getAttribute( GridConfig.FILTER ) || bRemoteFilter 
		) {
			filter = new Filter( ( ( View )grid ) );
			grid.addPlugin( filter );
		}
//		setSort( );
		if( isPaging( ) ) {
			Paging pagingGrid = ( Paging )grid;
			PagingTBar ptbar = new PagingTBar( pagingGrid );
			if( bRemoteFilter ) {
				ptbar.addSeparator( );
				ptbar.addButton( CONSTANTS.clear( ), null, null );
				ptbar.addSeparator( );
				ptbar.addButton( CONSTANTS.cacheFilter( ), null, new String[] { "toggle=true" } );				
			}
			grid.setBottomToolbar( ptbar );
		} else {
			if( !( Boolean )cfg.getAttribute( GridConfig.NO_TOOLBAR ) ) {
				ViewToolbar vtbar = new ViewToolbar( ( HasButton )grid );
				vtbar.addButton( 
					null, 
					RELOAD_BTN_ICON_CLS,
					new String[] { "tooltip=" + CONSTANTS.refresh( ) } 
				);
				if( bRemoteFilter ) {
					vtbar.addSeparator( );
					vtbar.addButton( CONSTANTS.clear( ), null, null );
					vtbar.addSeparator( );
					vtbar.addButton( CONSTANTS.cacheFilter( ), null, new String[] { "toggle=true" } );				
				}
				grid.setBottomToolbar( vtbar );
			}
		}
		grid.setEnableColumnResize( ( Boolean )cfg.getAttribute( GridConfig.COL_RESIZE ) );
		grid.getView( ).setAutoFill( ( Boolean )cfg.getAttribute( GridConfig.COL_AUTO_EXPAND ) );
		setContextMenu( ( List< CmdTypeItem > )cfg.getAttribute( GridConfig.CONTEXT_MENU ) );
		grid.addListener(
			new PanelListenerAdapter( ) {
				public void onRender( Component component ) {
					LOG.config( ( ( View )grid ).getID( ).getName( ) + ": onRender: selectRow( 0 )" );
					selectRow( 0 );
 				}
			}
		);
		grid.getSelectionModel( ).addListener(
			new RowSelectionListenerAdapter( ) {
				public void onRowSelect( RowSelectionModel sm, int rowIndex, Record record ) {
					LOG.config( ( ( View )grid ).getID( ).getName( ) + ": onRowSelect: selectRow(" + String.valueOf( rowIndex ) + ")" );
					setSelectedRow( rowIndex );
					GridView gridView = grid.getView( );
					if( gridView != null ) {
						gridView.focusRow( rowIndex );
					}
					SelectRowEvent.fire( ( ( View )grid ).getID( ), record );
				}
			}
		);
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.AbstractGridImpl#onButtonClick(com.gwtext.client.widgets.Button, com.gwtext.client.core.EventObject)
	 */
	@Override
	protected void onButtonClick( Button button, EventObject e ) {
		super.onButtonClick( button, e );
		if( CONSTANTS.clear( ).equals( button.getText( ) ) ) {
			filter.clearFilters( );
			reload( );
		}
		if( CONSTANTS.cacheFilter( ).equals( button.getText( ) ) ) {
			filter.setLocalFlag( !button.isPressed( ) );					
			button.setText( 
				button.isPressed( ) ? CONSTANTS.remoteFilter( ) : CONSTANTS.cacheFilter( ) 
			);				
		}
	}

	/**
	 * Returns context menu
	 * 
	 * @return the context menu
	 */
	public BaseMenu getContextMenu( ) {
		return( menu );
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
		menu = new BaseMenu( items, ( View )getGrid( ) );
		getGrid( ).addGridRowListener(
			new GridRowListenerAdapter( ) {
				public void onRowContextMenu( 
					final GridPanel grid, 
					final int rowIndex, 
					final EventObject e 
				) {
					e.stopEvent( );
					menu.showAt( e.getXY( ) );
				}
			}
		);
	}
	
	/**
	 * @see org.homedns.mkh.ui.client.grid.AbstractGridImpl#selectRow(int)
	 */
	protected void selectRow( int iRow ) {
		WidgetStore store = ( WidgetStore )getCache( );
		if( store.getRowCount( ) < 1 ) {
			return;
		}
		getGrid( ).getSelectionModel( ).selectRow( iRow );
	}
	
	/**
	 * @see org.homedns.mkh.ui.client.grid.AbstractGridImpl#getCondition()
	 */
	@Override
	protected String getCondition( ) {
		String sCondition = null;
		if( filter != null ) {
			if( bRemoteFilter ) {
				sCondition = filter.getCondition( );
			}
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
			if( getGrid( ) instanceof Paging ) {
				req.setLoadingPageNum( ( ( Paging )getGrid( ) ).getPage( ).getPageNumber( ) );
			}
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
	
	/**
	 * Sets sorts condition
	 */
	protected void setSort( ) {
		GridConfig cfg = getConfiguration( );
		String sSort = ( String )cfg.getAttribute( GridConfig.SORT );
		if( sSort == null ) {
			return;
		}
		String[] as = sSort.split( "," );
		Store store = getGrid( ).getStore( );
		SortDir sd = ( "A".equals( as[ 1 ] ) ) ? SortDir.ASC : SortDir.DESC;
		store.setSortInfo( new SortState( as[ 0 ], sd ) );
	}
	
	/**
	 * Returns specified cell value
	 * 
	 * @param iRow the cell row
	 * @param iCol the cell column
	 * 
	 * @return the value
	 */
	protected Object getCellValue( int iRow, int iCol ) {
		String sColName = getGrid( ).getColumnModel( ).getDataIndex( iCol );
		return( getCellValue( iRow, sColName ) );
	}
	
	/**
	 * Returns specified cell value
	 * 
	 * @param iRow the cell row
	 * @param sColName the cell column name
	 * 
	 * @return the value
	 */
	protected Object getCellValue( int iRow, String sColName ) {
		Object value = null;
		try {
			WidgetStore store = ( WidgetStore )getCache( );
			value = store.getFieldValue( sColName, store.getAt( iRow ) );
		}
		catch( Exception e ) {
			LOG.warning( LOG.getName( ) + ": " + e.getMessage( ) );
		}
		return( value );
	}

	/**
	 * Sets value for specified cell
	 * 
	 * @param iRow the cell row
	 * @param iCol the cell column
	 * @param value the value to set
	 */
	protected void setCellValue( int iRow, int iCol, Object value ) {
		String sColName = getGrid( ).getColumnModel( ).getDataIndex( iCol );
		setCellValue( iRow, sColName, value );
	}
	
	/**
	 * Sets value for specified cell
	 * 
	 * @param iRow the cell row
	 * @param sColName the cell column name
	 * @param value the value to set
	 */
	protected void setCellValue( int iRow, String sColName, Object value ) {
		try {
			WidgetStore store = ( WidgetStore )getCache( );
			store.setFieldValue( store.getAt( iRow ), sColName, value );
		}
		catch( Exception e ) {
			LOG.warning( LOG.getName( ) + ": " + e.getMessage( ) );
		}		
	}

	/**
	 * Returns true if grid paging is on and false otherwise 
	 * 
	 * @return true if grid paging is on and false otherwise 
	 */
	private boolean isPaging( ) {
		boolean bPaging = false;
		if( getGrid( ) instanceof Paging ) {
			bPaging = ( ( ( Paging )getGrid( ) ).getPageSize( ) > 0 );
		}
		return( bPaging );
	}
}
