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
 * $Id: CommandFactory.java 3 2013-07-16 15:46:34Z khomisha $
 */

package org.homedns.mkh.ui.client.command;

import org.homedns.mkh.dataservice.client.Creator;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Command;

/**
 * Command factory
 *
 */
public class CommandFactory {
	
	/**
	 * Command creator interface
	 */
	public interface CommandCreator extends Creator< Command > {
	}
	
	private static CommandCreator _creator;

	/**
	 * Creates command
	 * 
	 * @param type the command class type
	 * @param param the command parameter
	 * 
	 * @return the command
	 */
	@SuppressWarnings( "unchecked" )
	public static < T > Command create( Class< ? > type, T param ) {
		if( _creator == null ) {
			_creator = GWT.create( CommandCreator.class );
		}
		Command cmd = _creator.instantiate( type );
		if( param != null ) {
			if( cmd instanceof GenericCommand ) {
				GenericCommand< T > gc = ( GenericCommand< T > )cmd;
				gc.setParam( param );
			}
		}
		return( cmd );
	}
}
