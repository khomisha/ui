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

import org.homedns.mkh.ui.client.form.BoundForm;
import org.homedns.mkh.ui.client.form.ChildBoundForm;
import org.homedns.mkh.ui.client.grid.ChildEditorGrid;
import org.homedns.mkh.ui.client.grid.ChildGrid;
import org.homedns.mkh.ui.client.grid.EditorGrid;
import org.homedns.mkh.ui.client.grid.PropertyGrid;

/**
 * Panel with border layout, which contains two stand alone views 
 * 
 */
public class ViewsPanel extends BorderLayoutPanel {

	/**
	 * @param iChildSize the child panel initial size
	 */
	public ViewsPanel( int iChildSize ) {
		super( iChildSize );
	}

	/**
	 * @param sTitle
	 *            the panel's title
	 * @param iChildSize the edit panel initial height
	 */
	public ViewsPanel( String sTitle, int iChildSize ) {
		super( sTitle, iChildSize );
	}
	
	/**
	 * @see org.homedns.mkh.ui.client.frame.BorderLayoutPanel#init()
	 */
	@Override
	protected void init( ) {
		super.init( );
		int iCount = 0;
		for( ViewTag tag : getViewTag( ) ) {
			iCount++;
			String sPanelName = ( iCount == 1 ) ? BorderLayoutPanel.MAIN_PANEL : BorderLayoutPanel.CHILD_PANEL;
			if( getParentId( ) == null ) {
				createView( tag, sPanelName );
			} else {
				if( tag.getType( ) == ChildGrid.class ) {
					createChildView( 
						ChildGrid.class, tag.getName( ), 
						sPanelName, getParentId( ), getParentPK( ) 
					);
				} else if( tag.getType( ) == ChildEditorGrid.class ) {
					createChildView( 
						ChildEditorGrid.class, tag.getName( ), 
						sPanelName, getParentId( ), getParentPK( ) 
					);
				} else if( tag.getType( ) == ChildBoundForm.class ) {
					createChildView( 
						ChildBoundForm.class, tag.getName( ), 
						sPanelName, getParentId( ), getParentPK( ) 
					);				
				} else {
					// in panel may be independent views
					createView( tag, sPanelName );					
				}
			}
		}
	}
	
	/**
	 * Creates views
	 * 
	 * @param tag the view tag
	 * @param sPanelName the wrapped panel name
	 */
	private void createView( ViewTag tag, String sPanelName ) {
		if( tag.getType( ) == org.homedns.mkh.ui.client.grid.Grid.class ) {
			createView( 
				org.homedns.mkh.ui.client.grid.Grid.class, 
				tag.getName( ), 
				sPanelName 
			);
		} else if( tag.getType( ) == EditorGrid.class ) {
			createView( EditorGrid.class, tag.getName( ), sPanelName );
		} else if( tag.getType( ) == PropertyGrid.class ) {
			createView( PropertyGrid.class, tag.getName( ), sPanelName );
		} else if( tag.getType( ) == BoundForm.class ) {
			createView( BoundForm.class, tag.getName( ), sPanelName );				
		}		
	}
}
