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

package org.homedns.mkh.ui.client.frame;

import org.homedns.mkh.dataservice.client.event.SetAccessEvent;
import org.homedns.mkh.dataservice.client.view.ViewAccess;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Container;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.event.PanelListenerAdapter;

/**
* TabPanel container with view access management.
*/
@SuppressWarnings( "unchecked" )
public class TabsPanel extends TabPanel implements ViewAccess {
	private Access _access;
	private String _sTag;

	public TabsPanel( ) {
		addListener( 
			new PanelListenerAdapter( ) {
				@Override
				public boolean doBeforeAdd( 
					Container self, Component component, int index 
				) {
					if( component instanceof Tab ) {
						( ( Tab )component ).setParent( TabsPanel.this );
					}
					return( true );
				}
			}
		);
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.ViewAccess#setAccess(org.homedns.mkh.dataservice.client.view.ViewAccess.Access)
	 */
	@Override
	public void setAccess( Access access ) {
		_access = access;
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.ViewAccess#getAccess()
	 */
	@Override
	public Access getAccess( ) {
		return( _access );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.ViewAccess#getTag()
	 */
	@Override
	public String getTag( ) {
		return( _sTag );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.ViewAccess#setTag(java.lang.String)
	 */
	@Override
	public void setTag( String sTag ) {
		_sTag = sTag;		
		SetAccessEvent.fire( this );							
	}
}
