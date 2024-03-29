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

package org.homedns.mkh.ui.client.frame;


/**
 * Tabs style content panel
 *
 */
public class ContentPanel extends TabsPanel implements Content {

	public ContentPanel( ) {
		config( );
	}
	
	/**
	 * @param sTitle the panel title
	 */
	public ContentPanel( String sTitle ) {
		this( );
		setTitle( sTitle );
	}
	
	/**
	 * Configures content panel 
	 */
	protected void config( ) {
		setShadow( false );
		setShim( false );
		setPlain( true );
		setBorder( false );
	}
}
