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

import com.google.gwt.user.client.Command;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

/**
 * Toolbar button executing specified command
 *
 */
public class ToolbarCmdButton extends ToolbarButton {

	/**
	 * @param sLabel the button label
	 * @param cmd the command to execute
	 */
	public ToolbarCmdButton( String sLabel, final Command cmd ) {
		super( sLabel );
		addListener( 
			new ButtonListenerAdapter( ) {
				@Override
				public void onClick( Button button, EventObject e ) {
					cmd.execute( );
				}
			}
		);
	}
}
