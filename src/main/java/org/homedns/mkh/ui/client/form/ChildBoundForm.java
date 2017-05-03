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

package org.homedns.mkh.ui.client.form;

import java.util.logging.Logger;

import org.homedns.mkh.dataservice.client.event.EventBus;
import org.homedns.mkh.dataservice.client.event.HandlerRegistry;
import org.homedns.mkh.dataservice.client.event.HandlerRegistryAdaptee;
import org.homedns.mkh.dataservice.client.event.RPCResponseEvent;
import org.homedns.mkh.dataservice.client.event.RPCResponseEvent.RPCResponseHandler;
import org.homedns.mkh.dataservice.shared.DeleteResponse;
import org.homedns.mkh.dataservice.shared.Id;
import org.homedns.mkh.dataservice.shared.InsertResponse;
import org.homedns.mkh.dataservice.shared.Response;
import org.homedns.mkh.dataservice.shared.RetrieveResponse;
import org.homedns.mkh.ui.client.ChildView;
import org.homedns.mkh.ui.client.cache.RecordUtil;
import org.homedns.mkh.ui.client.cache.WidgetStore;
import org.homedns.mkh.ui.client.event.SelectRowEvent;
import org.homedns.mkh.ui.client.event.SelectRowEvent.SelectRowHandler;
import org.homedns.mkh.ui.client.grid.OnSelectRowCmd;

import com.google.gwt.core.client.Scheduler;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtext.client.data.Record;

/**
 * Child bound form, depends on master grid
 * 
 */
@SuppressWarnings( "unchecked" )
public class ChildBoundForm extends BoundForm implements ChildView, SelectRowHandler, RPCResponseHandler, HandlerRegistry  {
	private static final Logger LOG = Logger.getLogger( ChildBoundForm.class.getName( ) );  

	private HandlerRegistryAdaptee handlers;
	private String sMasterPK;
	private String sMasterPKValue;
	private Record parentRec;

	/**
	 * @param id
	 *            the identification object
	 * @param masterId
	 *            the master grid identification object
	 * @param sMasterPK 
	 * 			  the master grid primary key name
	 * @param cfg
	 *            the bound form configuration object
	 */
	public ChildBoundForm( Id id, Id masterId, String sMasterPK, FormConfig cfg ) {
		super( id, cfg );
		handlers = new HandlerRegistryAdaptee( );
		addHandler( 
			EventBus.getInstance( ).addHandlerToSource( 
				SelectRowEvent.TYPE, masterId.getUID( ), this 
			) 
		);
		addHandler( 
			EventBus.getInstance( ).addHandlerToSource( 
				RPCResponseEvent.TYPE, masterId.getUID( ), this 
			) 
		);
		this.sMasterPK = sMasterPK;
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.event.HandlerRegistry#addHandler(com.google.web.bindery.event.shared.HandlerRegistration)
	 */
	@Override
	public boolean addHandler( HandlerRegistration hr ) {
		return( handlers.add( hr ) );		
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.event.HandlerRegistry#removeHandlers()
	 */
	@Override
	public void removeHandlers( ) {
		handlers.clear( );
	}

	/**
	 * @see org.homedns.mkh.ui.client.event.SelectRowEvent.SelectRowHandler#onSelectRow(org.homedns.mkh.ui.client.event.SelectRowEvent)
	 */
	@Override
	public void onSelectRow( SelectRowEvent event ) {
		parentRec = event.getRecord( );
		sMasterPKValue = parentRec.getAsString( sMasterPK );
		Scheduler.get( ).scheduleIncremental( new OnSelectRowCmd( this ) );
	}

	/**
	 * @see org.homedns.mkh.ui.client.ChildView#onSelectRowAction()
	 */
	@Override
	public void onSelectRowAction( ) {
		WidgetStore store = ( WidgetStore )getCache( );
		store.filter( sMasterPK, sMasterPKValue, false );
		Record rec = store.getAt( 0 );
		LOG.config( 
			LOG.getName( ) + ": " + getID( ).getName( ) + ": onSelectRowAction: " + 
			( ( rec == null ) ? "null record" : RecordUtil.record2String( rec ) ) 
		);
		if( rec == null ) {
			loadEmptyRecord( );
		} else {
			loadRecord( rec );
		}
	}

	/**
	 * @see org.homedns.mkh.ui.client.ChildView#getParentRec()
	 */
	@Override
	public Record getParentRec( ) {
		return( parentRec );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#onResponse(org.homedns.mkh.dataservice.shared.Response)
	 */
	@Override
	public void onResponse( Response response ) {
		setResponse( response );
		if( response.getResult( ) != Response.FAILURE ) {
			if( isRendered( ) ) {
				refresh( );
			}
		}
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.event.RPCResponseEvent.RPCResponseHandler#onRPCResponse(org.homedns.mkh.dataservice.client.event.RPCResponseEvent)
	 */
	@Override
	public void onRPCResponse( RPCResponseEvent event ) {
		Response response = event.getResponse( );
		if( response.getResult( ) != Response.FAILURE ) {
			if( response instanceof InsertResponse || response instanceof DeleteResponse ) {
				reload( );
			}
			if( response instanceof RetrieveResponse && isRendered( ) ) {
				reload( );				
			}
		}
	}
}
