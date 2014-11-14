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
 * $Id$
 */

package org.homedns.mkh.ui.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Anchor;

/**
 * Hyper link style menu item
 *
 */
public class HyperLink extends Anchor {

	/**
	 * @param sLabel the hyper link item label
	 * @param cmd the hyper link item command
	 */
	public HyperLink( String sLabel, final Command cmd ) {
		super( sLabel );
	    addClickHandler( 
			new ClickHandler( ) {
				public void onClick( ClickEvent event ) {
					onItemClick( );
					cmd.execute( );
				}
			}
		);
	}
	
	/**
	 * On item click action
	 */
	protected void onItemClick( ) {
	}
}
