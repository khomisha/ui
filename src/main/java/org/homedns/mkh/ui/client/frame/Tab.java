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
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Container;
import com.gwtext.client.widgets.DefaultsHandler;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.PanelListenerAdapter;

/**
 * Tab panel
 */
@SuppressWarnings( "unchecked" )
public class Tab extends Panel implements ViewAccess {
	private String _sTag;
	private Access _access;
	private TabsPanel _parent; 
	private boolean _bHiding = false;
	private DefaultsHandler _defaultsHandler;


	public Tab( ) {
	}

	/**
	 * @param sTitle
	 *            the panel title
	 */
	public Tab( String sTitle ) {
		this( );
		setTitle( sTitle );
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
	
	/**
	 * Sets parent tab panel
	 * 
	 * @param parent
	 *            the parent tab panel to set
	 */
	public void setParent( TabsPanel parent ) {
		_parent = parent;
		_parent.addListener( 
			new PanelListenerAdapter( ) {
				@Override
				public void onAfterLayout( Container self ) {
					if( _bHiding ) {
						_bHiding = false;
						_parent.hideTabStripItem( Tab.this );
					}
				}
			}
		);
	}
	
	/**
	 * Invokes on hide tab
	 */
	public void onHide( ) {
		_bHiding = true;
		for( int iTab = 0; iTab < _parent.getComponents( ).length; iTab++ ) {
			if( !_parent.getItem( String.valueOf( iTab ) ).isHidden( ) ) {
				_parent.setActiveTab( iTab );
				break;
			}
		}
	}

	/**
	 * @see com.gwtext.client.widgets.Panel#addButton(com.gwtext.client.widgets.Button)
	 */
	@Override
	public void addButton( Button button ) {
        if( _defaultsHandler != null ) {
        	_defaultsHandler.apply( button );
        }
		super.addButton( button );
	}

	/**
	 * @see com.gwtext.client.widgets.Container#setDefaults(com.gwtext.client.widgets.DefaultsHandler)
	 */
	@Override
	public void setDefaults( DefaultsHandler defaultsHandler ) {
		_defaultsHandler = defaultsHandler;
		super.setDefaults( defaultsHandler );
	}
}
