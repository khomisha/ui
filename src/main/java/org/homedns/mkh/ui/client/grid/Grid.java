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

import org.homedns.mkh.dataservice.client.Page;
import org.homedns.mkh.dataservice.client.Paging;
import org.homedns.mkh.dataservice.client.event.RegisterViewEvent;
import org.homedns.mkh.dataservice.client.view.ViewCache;
import org.homedns.mkh.dataservice.client.view.ViewDesc;
import org.homedns.mkh.dataservice.shared.CUDRequest;
import org.homedns.mkh.dataservice.shared.Data;
import org.homedns.mkh.dataservice.shared.Request;
import org.homedns.mkh.dataservice.shared.Response;
import org.homedns.mkh.dataservice.shared.Id;
import org.homedns.mkh.ui.client.BaseMenu;
import org.homedns.mkh.ui.client.WidgetDesc;
import org.homedns.mkh.ui.client.cache.Pager;
import org.homedns.mkh.ui.client.cache.PagerStore;
import org.homedns.mkh.ui.client.cache.RecordUtil;
import com.google.gwt.user.client.Command;
import com.gwtext.client.widgets.grid.GridPanel;

/**
 * Grid
 *
 */
@SuppressWarnings( "unchecked" )
public class Grid extends GridPanel implements Paging {	
	private GridImpl _impl;
	private Id _id;
	private WidgetDesc _desc;
	private Class< ? > _cacheType = PagerStore.class;
	
	/**
	 * @param id
	 *            the identification object
	 * @param impl
	 *            the grid functionality implementation
	 */
	public Grid( Id id, GridImpl impl ) {
		setID( id );
		_impl = impl;
		_impl.setGrid( this );
		RegisterViewEvent.fire( this );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setID(org.homedns.mkh.dataservice.shared.Id)
	 */
	@Override
	public void setID( Id id ) {
		_id = id;
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getID()
	 */
	@Override
	public Id getID( ) {
		return( _id );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#init(org.homedns.mkh.dataservice.client.view.ViewDesc)
	 */
	@Override
	public void init( ViewDesc desc ) {
		_desc = ( WidgetDesc )desc;
		_impl.init( );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getDescription()
	 */
	@Override
	public ViewDesc getDescription( ) {
		return( _desc );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getSavingData(org.homedns.mkh.dataservice.shared.Request)
	 */
	@Override
	public void getSavingData( Request request ) {
		( ( CUDRequest )request ).setData( 
			RecordUtil.getJsonData( getSelectionModel( ).getSelections( ) ) 
		);
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#refresh(org.homedns.mkh.dataservice.shared.Response)
	 */
	@Override
	public void refresh( Response data ) {
		_impl.refresh( data );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getCache()
	 */
	@Override
	public ViewCache getCache( ) {
		return( _impl.getCache( ) );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#onInit()
	 * {@link org.homedns.mkh.ui.client.grid.AbstractGridImpl#onInit()}
	 */
	@Override
	public Request onInit( ) {
		return( _impl.onInit( ) );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setCache(org.homedns.mkh.dataservice.client.view.ViewCache)
	 */
	@Override
	public void setCache( ViewCache cache ) {
		_impl.setCache( cache );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.Paging#getPage()
	 */
	@Override
	public Page getPage( ) {
		ViewCache cache = getCache( );
		if( cache instanceof Pager ) {
			return( ( ( Pager )cache ).getPage( )  );
		} else {
			throw new IllegalArgumentException( cache.getClass( ).getName( ) );
		}
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.Paging#getPageSize()
	 */
	@Override
	public int getPageSize( ) {
		return( _desc.getDataBufferDesc( ).getTable( ).getPageSize( ) );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#isInit()
	 */
	@Override
	public boolean isInit( ) {
		return( getDescription( ) != null );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#onSend(org.homedns.mkh.dataservice.shared.Request)
	 */
	@Override
	public void onSend( Request request ) {
		_impl.onSend( request );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getArgs()
	 */
	@Override
	public Data getArgs( ) {
		return( _impl.getArgs( ) );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setArgs(org.homedns.mkh.dataservice.shared.Data)
	 */
	@Override
	public void setArgs( Data args ) {
		_impl.setArgs( args );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.Paging#isServerPaging()
	 */
	@Override
	public boolean isServerPaging( ) {
		return( getDescription( ).getDataBufferDesc( ).getTable( ).isServerPaging( ) );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getCacheType()
	 */
	@Override
	public Class< ? > getCacheType( ) {
		return( _cacheType );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setCacheType(java.lang.Class)
	 */
	@Override
	public void setCacheType( Class< ? > cacheType ) {
		_cacheType = cacheType;		
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setAfterInitCommand(com.google.gwt.user.client.Command)
	 */
	@Override
	public void setAfterInitCommand( Command cmd ) {
		_impl.setAfterInitCommand( cmd );
	}
	
	/**
	 * Returns context menu
	 * 
	 * @return the context menu or null
	 */
	public BaseMenu getContextMenu( ) {
		return( _impl.getContextMenu( ) );
	}
}
