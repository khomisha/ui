/*
 * Copyright 2016 Mikhail Khodonov
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
import org.homedns.mkh.ui.client.cache.WidgetStore;
import org.homedns.mkh.ui.client.event.SelectRowEvent;
import org.homedns.mkh.ui.client.event.SelectRowEvent.SelectRowHandler;

import com.google.gwt.core.client.Scheduler;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtext.client.data.Record;

/**
 * Child grid, depends on master grid
 * 
 */
public class ChildGrid extends Grid implements ChildView, SelectRowHandler, RPCResponseHandler, HandlerRegistry {
	private static final Logger LOG = Logger.getLogger( ChildGrid.class.getName( ) );  

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
	 * @param impl
	 *            the grid functionality implementation
	 */
	public ChildGrid( Id id, Id masterId, String sMasterPK, GridImpl impl ) {
		super( id, impl );
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
	 * Returns current parent record
	 * 
	 * @return the parent record
	 */
	public Record getParentRec( ) {
		return( parentRec );
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
	public void onSelectRowAction( ) {
		WidgetStore store = ( WidgetStore )getCache( );
		if( store.getRowCount( ) < 1 ) {
			return;
		}
		LOG.config( getID( ).getName( ) + ": filter: " + sMasterPKValue );
		store.filter( sMasterPK, sMasterPKValue, false );
//		getImplementation( ).selectRow( 0 );
	}
	
	/**
	 * @see org.homedns.mkh.dataservice.client.event.HandlerRegistry#removeHandlers()
	 */
	@Override
	public void removeHandlers( ) {
		handlers.clear( );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.event.HandlerRegistry#addHandler(com.google.web.bindery.event.shared.HandlerRegistration)
	 */
	@Override
	public boolean addHandler( HandlerRegistration hr ) {
		return( handlers.add( hr ) );		
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
//			if( response instanceof RetrieveResponse && isRendered( ) ) {
			if( response instanceof RetrieveResponse ) {
				if( ( ( RetrieveResponse )response ).isForcedRetrieve( ) ) {
					setForcedRetrieve( true );
					reload( );
					setForcedRetrieve( false );
				}
			}
		}
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.GridImpl#getSelectedRow()
	 */
	public int getSelectedRow( ) {
		return( getImplementation( ).getSelectedRow( ) );
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.GridImpl#setSelectedRow(int)
	 */
	public void setSelectedRow( int iSelectedRow ) {
		getImplementation( ).setSelectedRow( iSelectedRow );
	}
}
