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

import org.homedns.mkh.dataservice.client.AbstractEntryPoint;
import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.ui.client.CmdTypeItem;
import org.homedns.mkh.ui.client.Config;
import org.homedns.mkh.ui.client.UIConstants;
import org.homedns.mkh.ui.client.command.DeleteCmd;
import org.homedns.mkh.ui.client.command.NullCmd;
import org.homedns.mkh.ui.client.form.GridForm;
import org.homedns.mkh.ui.client.form.GridFormConfig;
import org.homedns.mkh.ui.client.grid.ChildEditorGrid;
import org.homedns.mkh.ui.client.grid.ChildGrid;
import org.homedns.mkh.ui.client.grid.EditorGrid;
import org.homedns.mkh.ui.client.grid.Grid;

/**
 * Panel with border layout, which contains grid to navigate data 
 * and form to edit grid selected record
 * 
 */
public class BorderContentPanel extends BorderLayoutPanel {
	private static final UIConstants CONSTANTS = ( UIConstants )AbstractEntryPoint.getConstants( );

	/**
	 * @param iFormSize the form panel initial size
	 */
	public BorderContentPanel( int iFormSize ) {
		super( iFormSize );
	}

	/**
	 * @param sTitle
	 *            the panel's title
	 * @param iFormSize the form panel initial size
	 */
	public BorderContentPanel( String sTitle, int iFormSize ) {
		super( sTitle, iFormSize );
	}
	
	/**
	 * @see org.homedns.mkh.ui.client.frame.BorderLayoutPanel#init()
	 */
	@Override
	protected void init( ) {
		super.init( );
		View view = null;
		ViewTag tag = getViewTag( ).get( 0 );
		if( getParentId( ) == null ) {
			if( tag.getType( ) == Grid.class ) {
				view = createView( Grid.class, tag.getName( ), MAIN_PANEL );
			}
			if( tag.getType( ) == EditorGrid.class ) {
				view = createView( EditorGrid.class, tag.getName( ), MAIN_PANEL );
			}
		} else {
			if( tag.getType( ) == ChildGrid.class ) {
				view = createChildView( 
					ChildGrid.class, tag.getName( ), MAIN_PANEL, getParentId( ), getParentPK( ) 
				);
			}
			if( tag.getType( ) == ChildEditorGrid.class ) {
				view = createChildView( 
					ChildEditorGrid.class, tag.getName( ), MAIN_PANEL, getParentId( ), getParentPK( ) 
				);
			}
		}
		createView( GridForm.class, view.getID( ), CHILD_PANEL );
	}

	/**
	 * @see org.homedns.mkh.ui.client.frame.GenericPanel#onInit(java.lang.String, org.homedns.mkh.ui.client.Config)
	 */
	@Override
	public void onInit( String sViewName, Config cfg ) {
		super.onInit( sViewName, cfg );
		if( cfg instanceof GridFormConfig ) {
			CmdTypeItem[] items = new CmdTypeItem[] {
				new CmdTypeItem( CONSTANTS.save( ), NullCmd.class ),
				new CmdTypeItem( CONSTANTS.update( ), NullCmd.class ),
				new CmdTypeItem( CONSTANTS.add( ), NullCmd.class ),
				new CmdTypeItem( CONSTANTS.del( ), DeleteCmd.class ),
				new CmdTypeItem( CONSTANTS.cancel( ), NullCmd.class )
			};
			cfg.setAttribute( GridFormConfig.BUTTONS, items );
		}
	}
}
