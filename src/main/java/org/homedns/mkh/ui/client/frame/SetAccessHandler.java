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

import org.homedns.mkh.dataservice.client.view.ViewAccess;
import org.homedns.mkh.dataservice.client.view.ViewAccess.Access;
import org.homedns.mkh.ui.client.BaseMenu;
import org.homedns.mkh.ui.client.grid.Grid;
import com.gwtext.client.data.Record;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Container;
import com.gwtext.client.widgets.DefaultsHandler;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.grid.EditorGridPanel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.PropertyGridPanel;
import com.gwtext.client.widgets.grid.event.EditorGridListenerAdapter;

/**
 * Set access handler sets access rights for UI component on component's
 * addition to the parent container
 * 
 */
public class SetAccessHandler implements DefaultsHandler {
	private Access _access;

	public SetAccessHandler( ) {
	}

	/**
	 * @param access the access right
	 */
	public SetAccessHandler( Access access ) {
		_access = access;
	}

	/**
	 * @see com.gwtext.client.widgets.DefaultsHandler#apply(com.gwtext.client.widgets.Component)
	 */
	@Override
	public void apply( Component component ) {
		setComponentAccess( component );
		if( component instanceof Container ) {
			Container ct = ( Container )component;
			for( Component c : ct.getComponents( ) ) {
				if( !( c instanceof ViewAccess ) ) {
					if( c instanceof Container ) {
						( ( Container )c ).setDefaults( new SetAccessHandler( _access ) );
					}
					apply( c );
				}
			}
		}
	}

	/**
	 * Sets access right
	 * 
	 * @param access the access right to set
	 */
	public void setAccess( Access access ) {
		_access = access;
	}

	/**
	 * Sets access right for specified component 
	 * 
	 * @param component the target component to set access rights
	 */
	private void setComponentAccess( Component component ) {
		if( _access == Access.NO_ACCESS ) {
			hide( component );
		} else if( _access == Access.READ_ONLY ) {
			disable( component );
		}
	}
	
	/**
	 * Disable component
	 * 
	 * @param component the component to disable
	 */
	private void disable( Component component ) {
		if( component instanceof FormPanel ) {
			component.disable( );
		} else if( component instanceof EditorGridPanel ) {
			EditorGridPanel eg = ( EditorGridPanel )component;
			eg.addEditorGridListener( 
				new EditorGridListenerAdapter( ) {
					@Override
					public boolean doBeforeEdit( 
						GridPanel grid,
						Record record, 
						String field, 
						Object value,
						int rowIndex, 
						int colIndex 
					) {
						return( false );
					}
					
				}
			);
		} else if( component instanceof PropertyGridPanel ) {
			Container parent = component.getOwnerContainer( );
			parent.disable( );
		} else if( component instanceof GridPanel ) {
			Grid grid = ( Grid )component;
			BaseMenu menu = grid.getContextMenu( );
			if( menu != null ) {
				menu.hide( );
			}
		} else if( component instanceof Button || component instanceof Field ) {
			component.disable( );
		}
	}
	
	/**
	 * Hide component
	 * 
	 * @param component the component to hide
	 */
	private void hide( Component component ) {
		component.hide( );
		if( component instanceof Tab ) {
			( ( Tab )component ).onHide( );
		}
	}
}
