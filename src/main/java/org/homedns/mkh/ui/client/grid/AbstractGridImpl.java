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

import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.dataservice.client.view.ViewCache;
import org.homedns.mkh.dataservice.shared.InsertResponse;
import org.homedns.mkh.dataservice.shared.Request;
import org.homedns.mkh.dataservice.shared.RequestFactory;
import org.homedns.mkh.dataservice.shared.Response;
import org.homedns.mkh.dataservice.shared.Data;
import org.homedns.mkh.dataservice.shared.RetrieveRequest;
import org.homedns.mkh.ui.client.ChildView;
import org.homedns.mkh.ui.client.WidgetDesc;
import org.homedns.mkh.ui.client.cache.WidgetStore;
import org.homedns.mkh.ui.client.command.CommandFactory;
import org.homedns.mkh.ui.client.command.RetrieveCmd;
import com.google.gwt.user.client.Command;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.grid.BaseColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import org.homedns.mkh.ui.client.CSSHelper;

/**
 * Implements abstract grid functionality
 *
 */
public abstract class AbstractGridImpl {
	protected static final String RELOAD_BTN_ICON_CLS = "x-tbar-loading";

	private GridConfig cfg;
	private GridPanel grid;
	private Data args;
	private Command cmd;
	private ViewCache cache;
	private Response response;
	private int iSelectedRow = 0;
	
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
	 * @see org.homedns.mkh.dataservice.client.Response#init(org.homedns.mkh.dataservice.client.DataBufferDesc)
	 */
	protected void init( ) {
		grid.setColumnModel( new ColumnModel( getColConfigs( ) ) );
		config( );
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
		grid.setAutoWidth( ( Boolean )cfg.getAttribute( GridConfig.AUTO_WIDTH ) );
		grid.setAutoHeight( ( Boolean )cfg.getAttribute( GridConfig.AUTO_HEIGHT ) );
		grid.setBorder( ( Boolean )cfg.getAttribute( GridConfig.BORDER ) );
		grid.setStripeRows( ( Boolean )cfg.getAttribute( GridConfig.STRIPE_ROWS ) );		
		grid.setAutoScroll( ( Boolean )cfg.getAttribute( GridConfig.AUTO_SCROLL ) );
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
		if( ( Boolean )cfg.getAttribute( GridConfig.CUSTOM_CSS ) ) {
			CSSHelper.getCSSInjector( ).injectCSS( grid );
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
		View view = ( View )grid;
		RetrieveRequest request = ( RetrieveRequest )RequestFactory.create( 
			RetrieveRequest.class 
		);
		request.setID( view.getID( ) );
		setArgs( ( Data )cfg.getAttribute( GridConfig.ARGS ) );
		request.setArgs( getArgs( ) );
		return( request );
	}
	
	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#refresh()
	 */
	protected void refresh( ) {
		if( grid instanceof ChildView ) {
			( ( ChildView )grid ).onSelectRowAction( );
		}
		grid.getView( ).refresh( );
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
	 * @see org.homedns.mkh.dataservice.client.view.View#reload()
	 */
	protected void reload( ) {
		Command cmd = CommandFactory.create( RetrieveCmd.class, grid );
		cmd.execute( );
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
		return( args );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setArgs(org.homedns.mkh.dataservice.shared.Data)
	 */
	protected void setArgs( Data args ) {
		this.args = args;
	}

	/**
	 * {@link org.homedns.mkh.ui.client.filter.Filter#getCondition()}
	 */
	protected abstract String getCondition( );

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setAfterInitCommand(Command)
	 */
	protected void setAfterInitCommand( Command cmd ) {
		this.cmd = cmd;
	}
	
	/**
	 * Returns after init callback command
	 * 
	 * @return the command
	 */
	protected Command getAfterInitCommand( ) {
		return( cmd );
	}
	
	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#onSend(org.homedns.mkh.dataservice.shared.Request)
	 */
	protected void onSend( Request request ) {
		if( request instanceof RetrieveRequest ) {
			RetrieveRequest req = ( RetrieveRequest )request;
			req.setCondition( getCondition( ) );
			req.setArgs( getArgs( ) );			
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
			reload( );
		}
	}
}
