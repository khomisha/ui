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

import java.util.List;

import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.dataservice.shared.Id;
import com.google.gwt.user.client.Command;
import com.gwtext.client.widgets.Panel;

/**
 * Master grid with multiple child border layout panel
 *
 */
@SuppressWarnings( "unchecked" )
public class MasterMultiChildPanel extends GenericMasterChildPanel {
	private Panel childsPanel;
	private boolean childsAdded = false;

	/**
	 * @param iChildPanelHeight
	 *            the child panel initial height
	 */
	public MasterMultiChildPanel( int iChildPanelHeight ) {
		super( iChildPanelHeight );
	}

	/**
	 * @param sTitle
	 *            the panel's title
	 * @param iChildPanelHeight
	 *            the child panel initial height
	 */
	public MasterMultiChildPanel( String sTitle, int iChildPanelHeight ) {
		super( sTitle, iChildPanelHeight );
	}

	/**
	 * Returns the child panel
	 * 
	 * @return the the child panel
	 */
	public Panel getChildsPanel( ) {
		return( childsPanel );
	}

	/**
	 * Sets the child panel
	 * 
	 * @param childsPanel the child panel to set
	 */
	public void setChildsPanel( Panel childsPanel ) {
		this.childsPanel = childsPanel;
	}

	/**
	 * @see org.homedns.mkh.ui.client.frame.BorderLayoutPanel#onAfterInit(org.homedns.mkh.dataservice.client.view.View)
	 */
	@Override
	protected void onAfterInit( View view ) {
		super.onAfterInit( view );
		if( view.getID( ).getName( ).equals( getViewName( ) ) ) {
			createChildView( getViewTag( ), view ); 
		} else {
			childsPanel.add( ( Panel )view );					
		}
	}

	/**
	 * Creates child views and binds it to the master grid
	 * 
	 * @param tags the child views tags list
	 * @param masterGrid the master grid
	 */
	@Override
	protected void createChildView( List< ViewTag > tags, View masterGrid ) {
		super.createChildView( tags, masterGrid );
		childsAdded = true;
	}

	/**
	 * @see org.homedns.mkh.ui.client.frame.GenericPanel#createChildView(java.lang.Class, java.lang.String, java.lang.String, org.homedns.mkh.dataservice.shared.Id, java.lang.String)
	 */
	@Override
	public < T extends Panel & View > T createChildView( 
		Class< T > type,
		String sName, 
		final String sPanelName, 
		Id masterId, 
		String sMasterPK 
	) {
		final T view = ( T )getBuilder( ).getChildComponent( 
			type, 
			new Id( sName ), 
			masterId, 
			sMasterPK 
		) ;
		putView( type, view );
		view.setAfterInitCommand(
			new Command( ) {
				public void execute( ) {
					onAfterInit( view );
					if( childsAdded ) {
						getPanel( sPanelName ).add( childsPanel );
						addPanels( );
					}
				}
			}
		);
		return( view );
	}
}
