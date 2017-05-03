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

import org.homedns.mkh.dataservice.shared.Request;
import com.google.gwt.user.client.Command;

/**
 * Generic command
 *
 */
public abstract class GenericCommand< T > implements Command {
	private T param;
	private Request request;
	
	public GenericCommand( ) {
	}
	
	/**
	 * @see com.google.gwt.user.client.Command#execute()
	 */
	@Override
	public abstract void execute( );
	
	/**
	 * Sets command parameter
	 * 
	 * @param param the command parameter to set
	 */
	public void setParam( T param ) {
		this.param = param;
	}
	
	/**
	 * Returns command parameter
	 * 
	 * @return the command parameter
	 */
	public T getParam( ) {
		return( param );
	}

	/**
	 * Returns request
	 * 
	 * @return the request
	 */
	public Request getRequest( ) {
		return( request );
	}

	/**
	 * Sets request
	 * 
	 * @param request the request to set
	 */
	public void setRequest( Request request ) {
		this.request = request;
	}
}
