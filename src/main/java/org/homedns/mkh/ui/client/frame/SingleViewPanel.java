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

import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.ui.client.form.BoundForm;
import org.homedns.mkh.ui.client.form.ChildBoundForm;
import org.homedns.mkh.ui.client.grid.ChildEditorGrid;
import org.homedns.mkh.ui.client.grid.ChildGrid;
import org.homedns.mkh.ui.client.grid.EditorGrid;
import org.homedns.mkh.ui.client.grid.Grid;
import org.homedns.mkh.ui.client.grid.PropertyGrid;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Container;
import com.gwtext.client.widgets.event.ContainerListenerAdapter;
import com.gwtext.client.widgets.layout.FitLayout;

/**
 * Single editable grid panel
 *
 */
@SuppressWarnings( "unchecked" )
public class SingleViewPanel extends GenericPanel {

	public SingleViewPanel( ) {
		setLayout( new FitLayout( ) );
		addListener( 
			new ContainerListenerAdapter( ){
				@Override
				public void onAdd( Container self, Component component, int index ) {
					self.doLayout( );
				}
			}
		);
	}
	
	/**
	 * @param sTitle the panel title
	 */
	public SingleViewPanel( String sTitle ) {
		this( );
		setTitle( sTitle );
	}

	/**
	 * @see org.homedns.mkh.ui.client.frame.GenericPanel#init()
	 */
	@Override
	protected void init( ) {
		putPanel( "main", this );
		ViewTag tag = getViewTag( ).get( 0 );
		if( getParentId( ) == null ) {
			if( tag.getType( ) == Grid.class ) {
				createView( Grid.class, tag.getName( ), "main" );
			} else if( tag.getType( ) == EditorGrid.class ) {
				createView( EditorGrid.class, tag.getName( ), "main" );
			} else if( tag.getType( ) == PropertyGrid.class ) {
				createView( PropertyGrid.class, tag.getName( ), "main" );
			} else if( tag.getType( ) == BoundForm.class ) {
				createView( BoundForm.class, tag.getName( ), "main" );				
			}
		} else {
			if( tag.getType( ) == ChildGrid.class ) {
				createChildView( 
					ChildGrid.class, tag.getName( ), 
					"main", getParentId( ), getParentPK( ) 
				);
			} else if( tag.getType( ) == ChildEditorGrid.class ) {
				createChildView( 
					ChildEditorGrid.class, tag.getName( ), 
					"main", getParentId( ), getParentPK( ) 
				);
			} else if( tag.getType( ) == ChildBoundForm.class ) {
				createChildView( 
					ChildBoundForm.class, tag.getName( ), 
					"main", getParentId( ), getParentPK( ) 
				);				
			}			
		}
	}

	/**
	 * @see org.homedns.mkh.ui.client.frame.GenericPanel#onAfterInit(org.homedns.mkh.dataservice.client.view.View)
	 */
	@Override
	protected void onAfterInit( View view ) {
		super.onAfterInit( view );
	}

	/**
	 * @see org.homedns.mkh.ui.client.frame.GenericPanel#addPanels()
	 */
	@Override
	public void addPanels( ) {
		super.addPanels( );
	}
}
