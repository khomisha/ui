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

package org.homedns.mkh.ui.client;

import java.util.HashMap;
import java.util.Map;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;

/**
 * Abstract toolbar
 * 
 */
public abstract class AbstractToolbar extends Toolbar {
	private Map< String, ToolbarButton > buttons;
	private HasButton hasButtonView;

	public AbstractToolbar( ) {
		buttons = new HashMap< String, ToolbarButton >( );
	}

	/**
	 * Returns toolbar button by it's key
	 * 
	 * @param sKey
	 *            the button key (label)
	 * 
	 * @return the button
	 */
	public ToolbarButton getButton( String sKey ) {
		return( buttons.get( sKey ) );
	}

	/**
	 * Returns target view, which implements button click action
	 * 
	 * @return the target view
	 */
	public HasButton getHasButtonView( ) {
		return( hasButtonView );
	}

	/**
	 * Sets target view, which implements button click action
	 * 
	 * @param hasButtonView the target view to set
	 */
	public void setHasButtonView( HasButton hasButtonView ) {
		this.hasButtonView = hasButtonView;
	}

	/**
	 * Adds toolbar button
	 * 
	 * @param sLabel
	 *            the label
	 * @param sStyle
	 *            the style
	 * @param attributes
	 *            the attributes values array, e.g. attribute format "toggle=true"
	 * 
	 * @return the toolbar button
	 */
	public ToolbarButton addButton( String sLabel, String sStyle, String[] attributes ) {
		ToolbarButton button = new ToolbarButton( );
		if( sLabel != null ) {
			button.setText( sLabel );
		}
		if( sStyle != null && !"".equals( sStyle ) ) {
			button.setIconCls( sStyle );
		}
		button.addListener( new ButtonClickListener( hasButtonView ) );
		buttons.put( ( sLabel == null ) ? sStyle : sLabel, button );
		if( attributes != null ) {
			for( String sAttribute : attributes ) {
				String[] as = sAttribute.split( "=" );
				if( "visible".equals( as[ 0 ] ) ) {
					button.setVisible( Boolean.valueOf( as[ 1 ] ) );
				} else if( "disabled".equals( as[ 0 ] ) ) {
					button.setDisabled( Boolean.valueOf( as[ 1 ] ) );
				} else if( "toggle".equals( as[ 0 ] ) ) {
					button.setEnableToggle( Boolean.valueOf( as[ 1 ] ) );
				} else if( "pressed".equals( as[ 0 ] ) ) {
					button.setPressed( Boolean.valueOf( as[ 1 ] ) );					
				} else if( "tooltip".equals( as[ 0 ] ) ) {
					button.setTooltip( as[ 1 ] );
				}
			}
		}
		onAddButton( button );
		addButton( button );
		return( button );
	}

	/**
	 * Before add button custom action
	 * 
	 * @param button the button to add
	 */
	protected void onAddButton( ToolbarButton button ) {	
	}
}
