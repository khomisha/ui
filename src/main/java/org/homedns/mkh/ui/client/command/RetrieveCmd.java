/*
 * Copyright 2014 Mikhail Khodonov
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

package org.homedns.mkh.ui.client.command;

import org.homedns.mkh.dataservice.client.sender.RequestSender;
import org.homedns.mkh.dataservice.client.sender.SendBinder;
import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.dataservice.shared.Request;
import org.homedns.mkh.dataservice.shared.RequestFactory;
import org.homedns.mkh.dataservice.shared.RetrieveRequest;

/**
 * Retrieve command
 *
 */
public class RetrieveCmd extends GenericCommand< View > {

	public RetrieveCmd( ) {
		Request request = RequestFactory.create( RetrieveRequest.class );
		setRequest( request );
	}

	/**
	 * @see org.homedns.mkh.ui.client.command.GenericCommand#execute()
	 */
	@Override
	public void execute( ) {
		RetrieveRequest request = ( RetrieveRequest )getRequest( );
		request.setID( getParam( ).getID( ) );
		request.setForcedRetrieve( getParam( ).isForcedRetrieve( ) );
		RequestSender sender = SendBinder.bind( request );
		sender.setView( getParam( ) );
		sender.send( );
	}
}
