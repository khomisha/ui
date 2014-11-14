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
import org.homedns.mkh.dataservice.shared.UpdateResponse;
import org.homedns.mkh.ui.client.WidgetDesc;
import org.homedns.mkh.ui.client.cache.WidgetStore;
import com.google.gwt.user.client.Command;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.grid.BaseColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;

/**
 * Implements abstract grid functionality
 *
 */
public abstract class AbstractGridImpl {
	private GridConfig _cfg;
	private GridPanel _grid;
	private Data _args;
	private Command _cmd;
	private ViewCache _cache;
	
	/**
	 * @param cfg
	 *            the grid configuration object
	 */
	public AbstractGridImpl( GridConfig cfg ) {
		_cfg = cfg;
	}

	/**
	 * Sets grid
	 * 
	 * @param grid
	 *            the grid to set
	 */
	public void setGrid( GridPanel grid ) {
		_grid = grid;
	}

	/**
	 * Returns grid
	 * 
	 * @return the grid
	 */
	public GridPanel getGrid( ) {
		return( _grid );
	}

	/**
	 * Returns grid configuration object
	 * 
	 * @return the grid configuration object
	 */
	protected GridConfig getConfiguration( ) {
		return( _cfg );
	}
	
	/**
	 * @see org.homedns.mkh.dataservice.client.Response#init(org.homedns.mkh.dataservice.client.DataBufferDesc)
	 */
	protected void init( ) {
		_grid.setColumnModel( new ColumnModel( getColConfigs( ) ) );
		config( );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getCache()
	 */
	protected ViewCache getCache( ) {
		return( _cache );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setCache(org.homedns.mkh.dataservice.client.view.ViewCache)
	 */
	protected void setCache( ViewCache cache ) {
		if( cache instanceof Store ) {
			_cache = cache;
		} else {
			throw new IllegalArgumentException( "setCache:" + cache.getClass( ).getName( ) );
		}
	}

	/**
	 * Applies configuration parameters to the parent grid
	 */
	protected void config( ) {
		_grid.setAutoWidth( ( Boolean )_cfg.getAttribute( GridConfig.AUTO_WIDTH ) );
		_grid.setAutoWidth( ( Boolean )_cfg.getAttribute( GridConfig.AUTO_HEIGHT ) );
		_grid.setBorder( ( Boolean )_cfg.getAttribute( GridConfig.BORDER ) );
		int iWidth = ( Integer )_cfg.getAttribute( GridConfig.WIDTH );
		if( iWidth > 0 ) {
			_grid.setWidth( iWidth );
		}
		int iHeight = ( Integer )_cfg.getAttribute( GridConfig.HEIGHT );
		if( iHeight > 0 ) {
			_grid.setHeight( iHeight );
		}
	}
	
	/**
	 * Returns columns configuration array
	 * 
	 * @return the columns configuration array
	 */
	protected BaseColumnConfig[] getColConfigs( ) {
		return( ( ( WidgetDesc )( ( View )_grid ).getDescription( ) ).getColConfigs( ) );
	}
	
	/**
	 * Default implementation sets retrieval arguments and filtering condition
	 * 
	 * @see org.homedns.mkh.dataservice.client.view.View#onInit()
	 */
	protected Request onInit( ) {
		View view = ( View )_grid;
		RetrieveRequest request = ( RetrieveRequest )RequestFactory.create( 
			RetrieveRequest.class 
		);
		request.setID( view.getID( ) );
		setArgs( ( Data )_cfg.getAttribute( GridConfig.ARGS ) );
		request.setArgs( getArgs( ) );
		return( request );
	}
	
	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#refresh(org.homedns.mkh.dataservice.shared.Response)
	 */
	protected void refresh( Response data ) {
		_grid.getView( ).refresh( );
		if( data instanceof InsertResponse || data instanceof UpdateResponse ) {
			int iIndex = ( ( WidgetStore )_cache ).getRecordByPK( data.getPKValue( ) );
			if( iIndex > -1 ) {
				_grid.getSelectionModel( ).selectRow( iIndex );
			}
		}
	}
	
	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getArgs()
	 */
	protected Data getArgs( ) {
		return( _args );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setArgs(org.homedns.mkh.dataservice.shared.Data)
	 */
	protected void setArgs( Data args ) {
		_args = args;
	}

	/**
	 * {@link org.homedns.mkh.ui.client.filter.Filter#getCondition()}
	 */
	protected abstract String getCondition( );

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setAfterInitCommand(Command)
	 */
	protected void setAfterInitCommand( Command cmd ) {
		_cmd = cmd;
	}
	
	/**
	 * Returns after init callback command
	 * 
	 * @return the command
	 */
	protected Command getAfterInitCommand( ) {
		return( _cmd );
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
}
