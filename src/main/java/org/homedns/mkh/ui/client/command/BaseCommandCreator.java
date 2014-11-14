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

import org.homedns.mkh.ui.client.command.CommandFactory.CommandCreator;
import com.google.gwt.user.client.Command;

/**
 * Base command creator
 *
 */
public class BaseCommandCreator implements CommandCreator {
	
	/**
	 * @see org.homedns.mkh.dataservice.client.Creator#instantiate(java.lang.Class)
	 */
	@Override
	public Command instantiate( Class< ? > type ) {
		Command cmd = null;
		if( type == ChangePassCmd.class ) {
			cmd = new ChangePassCmd( );
		} else if( type == DeleteCmd.class ) {
			cmd = new DeleteCmd( );
		} else if( type == ExitCmd.class ) {
			cmd = new ExitCmd( );
		} else if( type == InsertCmd.class ) {
			cmd = new InsertCmd( );
		} else if( type == HelpCmd.class ) {
			cmd = new HelpCmd( );
		} else if( type == LoginCmd.class ) {
			cmd = new LoginCmd( );
		} else if( type == NavigateCmd.class ) {
			cmd = new NavigateCmd( );
		} else if( type == NullCmd.class ) {
			cmd = new NullCmd( );
		} else if( type == ReportCmd.class ) {
			cmd = new ReportCmd( );
		} else if( type == RetrieveCmd.class ) {
			cmd = new RetrieveCmd( );
		} else if( type == UpdateCmd.class ) {
			cmd = new UpdateCmd( );
		}
		return( cmd );
	}
}
