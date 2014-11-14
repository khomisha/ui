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

import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.layout.LayoutData;

/**
 * Child panel container with it's layout data object
 *
 */
public class ChildContainer {
	private Panel _panel;
	private LayoutData _layoutData;
	
	/**
	 * @param panel the child panel
	 */
	public ChildContainer( Panel panel ) {
		setPanel( panel );
	}
	
	/**
	 * @param panel
	 *            the child panel
	 * @param layoutData
	 *            the child panel layout data
	 */
	public ChildContainer( Panel panel, LayoutData layoutData ) {
		this( panel );
		setLayoutData( layoutData );
	}

	/**
	 * Returns child panel
	 * 
	 * @return the child panel
	 */
	public Panel getPanel( ) {
		return( _panel );
	}
	
	/**
	 * Returns layout data if there is
	 * 
	 * @return the layoutData or null
	 */
	public LayoutData getLayoutData( ) {
		return( _layoutData );
	}
	
	/**
	 * Sets child panel
	 * 
	 * @param panel
	 *            the child panel to set
	 */
	public void setPanel( Panel panel ) {
		_panel = panel;
	}
	
	/**
	 * Sets child panel layout data
	 * 
	 * @param layoutData
	 *            the layout data to set
	 */
	public void setLayoutData( LayoutData layoutData ) {
		_layoutData = layoutData;
	}
}
