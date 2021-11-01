/*
 * Copyright 2015 Mikhail Khodonov
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

import java.util.List;
import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.ui.client.form.ChildBoundForm;
import org.homedns.mkh.ui.client.grid.ChildEditorGrid;
import org.homedns.mkh.ui.client.grid.ChildGrid;
import org.homedns.mkh.ui.client.grid.EditorGrid;
import org.homedns.mkh.ui.client.grid.Grid;

/**
 * Abstract master child panel
 *
 */
public abstract class GenericMasterChildPanel extends BorderLayoutPanel {

	public GenericMasterChildPanel( ) {
	}

	/**
	 * @param iChildPanelHeight the child's panel height
	 */
	public GenericMasterChildPanel( int iChildPanelHeight ) {
		super( iChildPanelHeight );
	}

	/**
	 * @param sTitle the title
	 * @param iChildPanelHeight the child's panel height
	 */
	public GenericMasterChildPanel( String sTitle, int iChildPanelHeight ) {
		super( sTitle, iChildPanelHeight );
	}
	
	/**
	 * @see org.homedns.mkh.ui.client.frame.BorderLayoutPanel#init()
	 */
	@Override
	public void init( ) {
		super.init( );
		View view = null;
		if( getViewType( ) == Grid.class ) {
			view = createView( Grid.class, getViewName( ), MAIN_PANEL );
		} else if( getViewType( ) == EditorGrid.class ) {
			view = createView( EditorGrid.class, getViewName( ), MAIN_PANEL );			
		}
		setParentId( view.getID( ) );
	}

	/**
	 * Creates child views and binds it to the master grid
	 * 
	 * @param tags the child views tags list
	 * @param masterGrid the master grid
	 */
	protected void createChildView( List< ViewTag > tags, View masterGrid ) { 
		String sMasterPK = masterGrid.getDescription( ).getDataBufferDesc( ).getTable( ).getKey( );
		for( ViewTag tag : tags ) {
			if( tag.getType( ) == ChildGrid.class ) {
				createChildView( 
					ChildGrid.class, 
					tag.getName( ), 
					BorderLayoutPanel.CHILD_PANEL, 
					masterGrid.getID( ),
					sMasterPK 
				);
			}
			if( tag.getType( ) == ChildEditorGrid.class ) {
				createChildView( 
					ChildEditorGrid.class, 
					tag.getName( ), 
					BorderLayoutPanel.CHILD_PANEL, 
					masterGrid.getID( ), 
					sMasterPK 
				);
			}
			if( tag.getType( ) == ChildBoundForm.class ) {
				createChildView( 
					ChildBoundForm.class, 
					tag.getName( ), 
					BorderLayoutPanel.CHILD_PANEL, 
					masterGrid.getID( ), 
					sMasterPK 
				);
			}
		}
	}
}
