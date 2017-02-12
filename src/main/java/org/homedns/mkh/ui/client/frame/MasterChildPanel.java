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

/**
 * Master/child panel
 */
@SuppressWarnings( "unchecked" )
public class MasterChildPanel extends GenericMasterChildPanel {

	/**
	 * @param iChildPanelHeight
	 *            the child panel initial height
	 */
	public MasterChildPanel( int iChildPanelHeight ) {
		super( iChildPanelHeight );
	}

	/**
	 * @param sTitle
	 *            the panel's title
	 * @param iChildPanelHeight
	 *            the child panel initial height
	 */
	public MasterChildPanel( String sTitle, int iChildPanelHeight ) {
		super( sTitle, iChildPanelHeight );
	}

	/**
	 * @see org.homedns.mkh.ui.client.frame.BorderLayoutPanel#onAfterInit(org.homedns.mkh.dataservice.client.view.View)
	 */
	@Override
	protected void onAfterInit( View view ) {
		super.onAfterInit( view );
		if( view.getID( ).getName( ).equals( getViewName( ) ) ) {
			createChildView( getViewTag( ), view ); 
		}
	}
}
