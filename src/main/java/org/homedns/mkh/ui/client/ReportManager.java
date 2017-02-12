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

package org.homedns.mkh.ui.client;

import org.homedns.mkh.dataservice.client.AbstractEntryPoint;
import org.homedns.mkh.dataservice.client.event.EventBus;
import org.homedns.mkh.dataservice.client.event.HandlerRegistry;
import org.homedns.mkh.dataservice.client.event.HandlerRegistryAdaptee;
import org.homedns.mkh.dataservice.client.event.RPCResponseEvent;
import org.homedns.mkh.dataservice.client.event.RPCResponseEvent.RPCResponseHandler;
import org.homedns.mkh.dataservice.shared.Data;
import org.homedns.mkh.dataservice.shared.Id;
import org.homedns.mkh.dataservice.shared.ReportRequest;
import org.homedns.mkh.dataservice.shared.ReportResponse;
import org.homedns.mkh.dataservice.shared.RequestFactory;
import org.homedns.mkh.dataservice.shared.Response;
import org.homedns.mkh.ui.client.command.CommandFactory;
import org.homedns.mkh.ui.client.command.ReportCmd;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Report manager produces reports
 *
 */
public class ReportManager implements RPCResponseHandler, HandlerRegistry{
	private static final UIConstants CONSTANTS = ( UIConstants )AbstractEntryPoint.getConstants( );

	private ReportRequest request;
	private Command cmd;
	private HandlerRegistryAdaptee handlers;

	public ReportManager( ) {
		request = ( ReportRequest )RequestFactory.create( ReportRequest.class );
		Id id = new Id( "report_id" );
		request.setID( id );
		cmd = CommandFactory.create( ReportCmd.class, request );
		handlers = new HandlerRegistryAdaptee( );
		addHandler( 
			EventBus.getInstance( ).addHandlerToSource( RPCResponseEvent.TYPE, id.getUID( ), this ) 
		);
	}

	/**
	 * Executes report command
	 */
	public void execute( ) {
		cmd.execute( );
	}
	
	/**
	 * Sets report data buffer
	 * 
	 * @param sReportDataBuffer the report data buffer to set
	 */
	public void setReportDataBuffer( String sReportDataBuffer ) {
		request.setDataBufferName( sReportDataBuffer );
	}

	/**
	 * Sets report parameters
	 * 
	 * @param params the report parameters to set
	 */
	public void setParams( Data params) {
		request.setArgs( params );
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
		if( 
			event.getResponse( ).getResult( ) != Response.FAILURE &&
			response instanceof ReportResponse
		) {
			Window.open( 
				AbstractEntryPoint.getDownloadURL( ) + "?type=xls&filename=" + response.getDownloadFileName( ), 
				CONSTANTS.report( ), 
				"" 
			);			
		}
	}
}
