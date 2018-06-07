/*
 * Copyright 2013 Mikhail Khodonov
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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.dataservice.client.view.ViewCache;
import org.homedns.mkh.dataservice.shared.InsertResponse;
import org.homedns.mkh.dataservice.shared.Request;
import org.homedns.mkh.dataservice.shared.Response;
import org.homedns.mkh.dataservice.shared.Data;
import org.homedns.mkh.dataservice.shared.RetrieveRequest;
import org.homedns.mkh.ui.client.ChildView;
import org.homedns.mkh.ui.client.WidgetDesc;
import org.homedns.mkh.ui.client.cache.WidgetStore;
import org.homedns.mkh.ui.client.command.CommandFactory;
import org.homedns.mkh.ui.client.command.CommandScheduler;
import org.homedns.mkh.ui.client.command.RetrieveCmd;

import com.google.gwt.user.client.Command;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.grid.BaseColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;

/**
 * Implements abstract grid functionality
 *
 */
public abstract class AbstractGridImpl {
	private static final Logger LOG = Logger.getLogger( AbstractGridImpl.class.getName( ) );  
	public static final String RELOAD_BTN_ICON_CLS = "x-tbar-loading";

	private GridConfig cfg;
	private GridPanel grid;
	private Command afterInitCmd, retrieveCmd;
	private ViewCache cache;
	private Response response;
	private int iSelectedRow = 0;
	private String sSelectedRecordId = "0";
	private boolean bForcedRetrieve = false;
	
	/**
	 * @param cfg
	 *            the grid configuration object
	 */
	public AbstractGridImpl( GridConfig cfg ) {
		this.cfg = cfg;
	}

	/**
	 * Sets grid
	 * 
	 * @param grid
	 *            the grid to set
	 */
	public void setGrid( GridPanel grid ) {
		this.grid = grid;
	}

	/**
	 * Returns grid
	 * 
	 * @return the grid
	 */
	public GridPanel getGrid( ) {
		return( grid );
	}

	/**
	 * Returns grid configuration object
	 * 
	 * @return the grid configuration object
	 */
	protected GridConfig getConfiguration( ) {
		return( cfg );
	}
	
	/**
	 * Inits grid 
	 */
	protected void init( ) {
		try {
			config( );
		}
		catch( Exception e ) {
			LOG.log( Level.SEVERE, e.getMessage( ), e );
		}
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getResponse()
	 */
	protected Response getResponse( ) {
		return( response );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setResponse(org.homedns.mkh.dataservice.shared.Response)
	 */
	protected void setResponse( Response response ) {
		this.response = response;
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getCache()
	 */
	protected ViewCache getCache( ) {
		return( cache );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setCache(org.homedns.mkh.dataservice.client.view.ViewCache)
	 */
	protected void setCache( ViewCache cache ) {
		if( cache instanceof Store ) {
			this.cache = cache;
		} else {
			throw new IllegalArgumentException( "setCache:" + cache.getClass( ).getName( ) );
		}
	}

	/**
	 * Applies configuration parameters to the parent grid
	 */
	protected void config( ) {
		ColumnModel cm = new ColumnModel( getColConfigs( ) );
		cm.setDefaultSortable( ( Boolean )cfg.getAttribute( GridConfig.IS_SORTABLE ) );
		grid.setColumnModel( cm );
		grid.setAutoWidth( ( Boolean )cfg.getAttribute( GridConfig.AUTO_WIDTH ) );
		grid.setAutoHeight( ( Boolean )cfg.getAttribute( GridConfig.AUTO_HEIGHT ) );
		grid.setBorder( ( Boolean )cfg.getAttribute( GridConfig.BORDER ) );
		grid.setStripeRows( ( Boolean )cfg.getAttribute( GridConfig.STRIPE_ROWS ) );		
		grid.setAutoScroll( ( Boolean )cfg.getAttribute( GridConfig.AUTO_SCROLL ) );
		grid.setEnableHdMenu( ( Boolean )cfg.getAttribute( GridConfig.HEADER_MENU ) );
		( ( View )grid ).setBatchUpdate( ( Boolean )cfg.getAttribute( GridConfig.BATCH_UPDATE ) );		
		int iWidth = ( Integer )cfg.getAttribute( GridConfig.WIDTH );
		if( iWidth > 0 ) {
			grid.setWidth( iWidth );
		}
		int iHeight = ( Integer )cfg.getAttribute( GridConfig.HEIGHT );
		if( iHeight > 0 ) {
			grid.setHeight( iHeight );
		}
		String sTitle = ( String )cfg.getAttribute( GridConfig.TITLE );
		if( sTitle != null ) {
			grid.setTitle( sTitle );
		}
		int iDelay = ( Integer )cfg.getAttribute( GridConfig.SCHEDULE_RETRIEVE_CMD );
		if( iDelay > 0 ) {
			CommandScheduler.get( ).addCmd2Schedule( retrieveCmd, iDelay );
		}
	}
	
	/**
	 * Returns selected row
	 * 
	 * @return the selected row
	 */
	protected int getSelectedRow( ) {
		return( iSelectedRow );
	}

	/**
	 * Sets selected row
	 * 
	 * @param iSelectedRow the selected row to set
	 */
	protected void setSelectedRow( int iSelectedRow ) {
		this.iSelectedRow = iSelectedRow;
		this.sSelectedRecordId = grid.getStore( ).getAt( iSelectedRow ).getId( );
	}

	/**
	 * Returns selected record id
	 * 
	 * @return the selected record id
	 */
	public String getSelectedRecordId( ) {
		return( sSelectedRecordId );
	}

	/**
	 * Returns columns configuration array
	 * 
	 * @return the columns configuration array
	 */
	protected BaseColumnConfig[] getColConfigs( ) {
		return( ( ( WidgetDesc )( ( View )grid ).getDescription( ) ).getColConfigs( ) );
	}
	
	/**
	 * Default implementation sets retrieval arguments and filtering condition
	 * 
	 * @see org.homedns.mkh.dataservice.client.view.View#onInit()
	 */
	protected Request onInit( ) {
		RetrieveCmd rc = ( RetrieveCmd )CommandFactory.create( RetrieveCmd.class, grid );
		retrieveCmd = rc;
		setArgs( ( Data )cfg.getAttribute( GridConfig.ARGS ) );
		return( rc.getRequest( ) );
	}
	
	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#refresh()
	 */
	protected void refresh( ) {
		if( grid instanceof ChildView ) {
			( ( ChildView )grid ).onSelectRowAction( );
		}
//		grid.getView( ).refresh( );
		Response response = getResponse( );
		WidgetStore store = ( WidgetStore )cache;
		if( response instanceof InsertResponse ) {
			int iIndex = store.getRecordByPK( response.getPKValue( ) );
			if( iIndex > -1 ) {
				selectRow( iIndex );
			}
		} else {
			int iRowCount = store.getRecords( ).length;
			if( getSelectedRow( ) > iRowCount - 1 ) {
				if( iRowCount > 0 ) {
					selectRow( iRowCount - 1 );					
				}
			} else {
				selectRow( getSelectedRow( ) );
			}
		}
	}
	
	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#isForcedRetrieve()
	 */
	protected boolean isForcedRetrieve( ) {
		return( bForcedRetrieve );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setForcedRetrieve(boolean)
	 */
	protected void setForcedRetrieve( boolean bForcedRetrieve ) {
		this.bForcedRetrieve = bForcedRetrieve;
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#reload()
	 */
	protected void reload( ) {
		LOG.config( ( ( View )grid ).getID( ).getName( ) + ": reload" );
		retrieveCmd.execute( );
	}
	
	/**
	 * Selects row in grid
	 * 
	 * @param iRow
	 *            the row index
	 */
	protected abstract void selectRow( int iRow );
	
	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getArgs()
	 */
	protected Data getArgs( ) {
		RetrieveRequest request = ( RetrieveRequest )( ( RetrieveCmd )retrieveCmd ).getRequest( );
		return( request.getArgs( ) );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setArgs(org.homedns.mkh.dataservice.shared.Data)
	 */
	protected void setArgs( Data args ) {
		RetrieveRequest request = ( RetrieveRequest )( ( RetrieveCmd )retrieveCmd ).getRequest( );
		request.setArgs( args );
	}

	/**
	 * {@link org.homedns.mkh.ui.client.filter.Filter#getCondition()}
	 */
	protected abstract String getCondition( );

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setAfterInitCommand(Command)
	 */
	protected void setAfterInitCommand( Command afterInitCmd ) {
		this.afterInitCmd = afterInitCmd;
	}
	
	/**
	 * Returns after init callback command
	 * 
	 * @return the command
	 */
	protected Command getAfterInitCommand( ) {
		return( afterInitCmd );
	}
	
	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#onSend(org.homedns.mkh.dataservice.shared.Request)
	 */
	protected void onSend( Request request ) {
		if( request instanceof RetrieveRequest ) {
			RetrieveRequest req = ( RetrieveRequest )request;
			req.setCondition( getCondition( ) );
		}
	}
	
	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#onResponse(org.homedns.mkh.dataservice.shared.Response)
	 */
	protected void onResponse( Response response ) {
		setResponse( response );
		if( response.getResult( ) != Response.FAILURE ) {
			if( grid.isRendered( ) ) {
				refresh( );
			}
		}
	}

	/**
	 * @see org.homedns.mkh.ui.client.HasButton#onButtonClick(com.gwtext.client.widgets.Button, com.gwtext.client.core.EventObject)
	 */
	protected void onButtonClick( Button button, EventObject e ) {
		if( RELOAD_BTN_ICON_CLS.equals( button.getIconCls( ) ) ) {
			setForcedRetrieve( true );
			reload( );
			setForcedRetrieve( false );
		}
	}
}
