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
import org.homedns.mkh.ui.client.HasButton;
import org.homedns.mkh.ui.client.WidgetDesc;
import org.homedns.mkh.ui.client.cache.Pager;
import org.homedns.mkh.ui.client.cache.PagerStore;
import org.homedns.mkh.ui.client.cache.RecordUtil;

import com.google.gwt.user.client.Command;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.grid.GridPanel;

/**
 * Grid
 *
 */
public class Grid extends GridPanel implements Paging, HasButton {	
	private GridImpl impl;
	private Id id;
	private WidgetDesc desc;
	private Class< ? > cacheType = PagerStore.class;
	private boolean bBatch = false;
	
	/**
	 * @param id
	 *            the identification object
	 * @param impl
	 *            the grid functionality implementation
	 */
	public Grid( Id id, GridImpl impl ) {
		setID( id );
		setImplementation( impl );
		impl.setGrid( this );
		RegisterViewEvent.fire( this );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setID(org.homedns.mkh.dataservice.shared.Id)
	 */
	@Override
	public void setID( Id id ) {
		this.id = id;
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getID()
	 */
	@Override
	public Id getID( ) {
		return( id );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#init(org.homedns.mkh.dataservice.client.view.ViewDesc)
	 */
	@Override
	public void init( ViewDesc desc ) {
		this.desc = ( WidgetDesc )desc;
		impl.init( );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getDescription()
	 */
	@Override
	public ViewDesc getDescription( ) {
		return( desc );
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
	 * @see org.homedns.mkh.dataservice.client.view.View#onResponse(org.homedns.mkh.dataservice.shared.Response)
	 */
	@Override
	public void onResponse( Response response ) {
		impl.onResponse( response );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getCache()
	 */
	@Override
	public ViewCache getCache( ) {
		return( impl.getCache( ) );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#onInit()
	 * {@link org.homedns.mkh.ui.client.grid.AbstractGridImpl#onInit()}
	 */
	@Override
	public Request onInit( ) {
		return( impl.onInit( ) );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setCache(org.homedns.mkh.dataservice.client.view.ViewCache)
	 */
	@Override
	public void setCache( ViewCache cache ) {
		impl.setCache( cache );
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
		return( desc.getDataBufferDesc( ).getTable( ).getPageSize( ) );
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
		impl.onSend( request );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getArgs()
	 */
	@Override
	public Data getArgs( ) {
		return( impl.getArgs( ) );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setArgs(org.homedns.mkh.dataservice.shared.Data)
	 */
	@Override
	public void setArgs( Data args ) {
		impl.setArgs( args );
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
		return( cacheType );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setCacheType(java.lang.Class)
	 */
	@Override
	public void setCacheType( Class< ? > cacheType ) {
		this.cacheType = cacheType;		
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setAfterInitCommand(com.google.gwt.user.client.Command)
	 */
	@Override
	public void setAfterInitCommand( Command cmd ) {
		impl.setAfterInitCommand( cmd );
	}
	
	/**
	 * Returns context menu
	 * 
	 * @return the context menu or null
	 */
	public BaseMenu getContextMenu( ) {
		return( impl.getContextMenu( ) );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#refresh()
	 */
	@Override
	public void refresh( ) {
		impl.refresh( );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getResponse()
	 */
	@Override
	public Response getResponse( ) {
		return( impl.getResponse( ) );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setResponse(org.homedns.mkh.dataservice.shared.Response)
	 */
	@Override
	public void setResponse( Response response ) {
		impl.setResponse( response );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#reload()
	 */
	@Override
	public void reload( ) {
		impl.reload( );		
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setBatchUpdate(boolean)
	 */
	@Override
	public void setBatchUpdate( boolean bBatch ) {
		this.bBatch = bBatch;
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#isBatchUpdate()
	 */
	@Override
	public boolean isBatchUpdate( ) {
		return( bBatch );
	}

	/**
	 * @see org.homedns.mkh.ui.client.HasButton#onButtonClick(com.gwtext.client.widgets.Button, com.gwtext.client.core.EventObject)
	 */
	@Override
	public void onButtonClick( Button button, EventObject e ) {
		impl.onButtonClick( button, e );
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.GridImpl#getCellValue(int, int)
	 */
	public Object getCellValue( int iRow, int iCol ) {
		return( impl.getCellValue( iRow, iCol ) );
	}
	
	/**
	 * @see org.homedns.mkh.ui.client.grid.GridImpl#getCellValue(int, String)
	 */
	public Object getCellValue( int iRow, String sColName ) {
		return( impl.getCellValue( iRow, sColName ) );
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.GridImpl#setCellValue(int, int, Object)
	 */
	public void setCellValue( int iRow, int iCol, Object value ) {
		impl.setCellValue( iRow, iCol, value );
	}
	
	/**
	 * @see org.homedns.mkh.ui.client.grid.GridImpl#setCellValue(int, String, Object)
	 * 
	 */
	public void setCellValue( int iRow, String sColName, Object value ) {
		impl.setCellValue( iRow, sColName, value );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#isForcedRetrieve()
	 */
	@Override
	public boolean isForcedRetrieve( ) {
		return( impl.isForcedRetrieve( ) );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setForcedRetrieve(boolean)
	 */
	@Override
	public void setForcedRetrieve( boolean bForcedRetrieve ) {
		impl.setForcedRetrieve( bForcedRetrieve );
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.GridImpl#getSelectedRow()
	 */
	public int getSelectedRow( ) {
		return( impl.getSelectedRow( ) );
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.GridImpl#setSelectedRow(int)
	 */
	public void setSelectedRow( int iSelectedRow ) {
		impl.setSelectedRow( iSelectedRow );
	}

	/**
	 * Returns implementation object
	 * 
	 * @return the implementation object
	 */
	protected AbstractGridImpl getImplementation( ) {
		return( impl );
	}

	/**
	 * Sets implementation object
	 * 
	 * @param impl the implementation object to set
	 */
	protected void setImplementation( AbstractGridImpl impl ) {
		this.impl = ( GridImpl )impl;
	}
}
