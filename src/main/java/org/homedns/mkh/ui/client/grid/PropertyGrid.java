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

import org.homedns.mkh.dataservice.client.event.RegisterViewEvent;
import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.dataservice.client.view.ViewCache;
import org.homedns.mkh.dataservice.client.view.ViewDesc;
import org.homedns.mkh.dataservice.shared.CUDRequest;
import org.homedns.mkh.dataservice.shared.Data;
import org.homedns.mkh.dataservice.shared.Request;
import org.homedns.mkh.dataservice.shared.Response;
import org.homedns.mkh.dataservice.shared.Id;
import org.homedns.mkh.ui.client.HasButton;
import org.homedns.mkh.ui.client.WidgetDesc;
import org.homedns.mkh.ui.client.cache.RecordUtil;
import org.homedns.mkh.ui.client.cache.WidgetStore;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Command;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.Record;
import com.gwtext.client.util.JavaScriptObjectHelper;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.grid.PropertyGridPanel;

/**
 * Property grid
 *
 */
@SuppressWarnings( "unchecked" )
public class PropertyGrid extends PropertyGridPanel implements View, HasButton {
	private PropertyGridImpl impl;
	private Id id;
	private WidgetDesc desc;
	private Class< ? > cacheType = WidgetStore.class;
	private boolean bBatch = false;
	
	/**
	 * @param id
	 *            the identification object
	 * @param impl
	 *            the grid functionality implementation
	 */
	public PropertyGrid( Id id, PropertyGridImpl impl ) {
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
			RecordUtil.getJsonData( new Record[] { impl.getMainRecord( ) } ) 
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
	 * Sets caption for name column
	 * 
	 * @param sCaption
	 *            the name column caption
	 */
	public void setCaption4NameCol( String sCaption ) {
		setColModelAttribute( "nameText", sCaption );
	}

	/**
	 * Sets caption for value column
	 * 
	 * @param sCaption
	 *            the value column caption
	 */
	public void setCaption4ValueCol( String sCaption ) {
		setColModelAttribute( "valueText", sCaption );
	}

	/**
	 * Sets date format
	 * 
	 * @param sFormat
	 *            the date format
	 */
	public void setDateFormat( String sFormat ) {
		setColModelAttribute( "dateFormat", sFormat );
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
	 * @see com.gwtext.client.widgets.grid.GridPanel#clearSortState(boolean)
	 */
	@Override
	public void clearSortState( boolean reload ) {
		if( getStore( ) != null ) {
			super.clearSortState( reload );
		}
	}

	/**
	 * Sets column model attribute
	 * 
	 * @param sName
	 *            the attribute name
	 * @param sValue
	 *            the attribute value
	 */
	private void setColModelAttribute( String sName, String sValue ) {
		JavaScriptObject jso = JavaScriptObjectHelper.getAttributeAsJavaScriptObject( 
			getJsObj( ), 
			"colModel" 
		);				
		JavaScriptObjectHelper.setAttribute( jso, sName, sValue );
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
		this.impl = ( PropertyGridImpl )impl;
	}
}
