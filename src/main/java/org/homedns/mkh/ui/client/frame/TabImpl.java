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

import com.gwtext.client.widgets.Container;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.PanelListenerAdapter;

/**
 * Implements tab panel interface
 *
 */
public class TabImpl {
	private TabsPanel parent;
	private Tab child;
	private boolean bHiding = false;

	/**
	 * @param child the child tab panel
	 */
	public TabImpl( Tab child ) {
		this.child = child;
	}

	/**
	 * @see org.homedns.mkh.ui.client.frame.Tab#setParent(org.homedns.mkh.ui.client.frame.TabsPanel)
	 */
	public void setParent( final TabsPanel parent ) {
		this.parent = parent;
		parent.addListener( 
			new PanelListenerAdapter( ) {
				@Override
				public void onAfterLayout( Container self ) {
					if( bHiding ) {
						bHiding = false;
						parent.hideTabStripItem( ( Panel )child );
					}
				}
			}
		);
	}

	/**
	 * @see org.homedns.mkh.ui.client.frame.Tab#onHide()
	 */
	public void onHide( ) {
		bHiding = true;
		for( int iTab = 0; iTab < parent.getComponents( ).length; iTab++ ) {
			if( !parent.getItem( String.valueOf( iTab ) ).isHidden( ) ) {
				parent.setActiveTab( iTab );
				break;
			}
		}
	}
}
