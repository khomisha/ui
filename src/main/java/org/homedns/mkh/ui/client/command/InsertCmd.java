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

import org.homedns.mkh.dataservice.client.sender.SendBinder;
import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.dataservice.shared.InsertRequest;
import org.homedns.mkh.dataservice.shared.RequestFactory;

/**
 * Insert command
 *
 */
public class InsertCmd extends GenericCommand< View > {

	public InsertCmd( ) {
	}
	
	/**
	 * @see org.homedns.mkh.ui.client.command.GenericCommand#execute()
	 */
	@Override
	public void execute( ) {
		InsertRequest request = ( InsertRequest )RequestFactory.create( 
			InsertRequest.class 
		);
		request.setID( getParam( ).getID( ) );
		getParam( ).getSavingData( request );
		SendBinder.bind( request ).send( );
	}
}
