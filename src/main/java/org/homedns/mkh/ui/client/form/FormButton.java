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

package org.homedns.mkh.ui.client.form;

import com.google.gwt.user.client.Command;
import com.gwtext.client.widgets.Button;

/**
 * Form button
 *
 */
public class FormButton extends Button {
	private Command _cmd;

	/**
	 * @param sText
	 *            the button label
	 * @param cmd
	 *            the command will be executed when click button
	 */
	public FormButton( String sText, Command cmd ) {
		super( sText );
		setCommand( cmd );
	}

	/**
	 * Returns command
	 * 
	 * @return the the command
	 */
	public Command getCommand( ) {
		return( _cmd );
	}

	/**
	 * Sets command 
	 * 
	 * @param cmd the command to set
	 */
	public void setCommand( Command cmd ) {
		_cmd = cmd;
	}
}
