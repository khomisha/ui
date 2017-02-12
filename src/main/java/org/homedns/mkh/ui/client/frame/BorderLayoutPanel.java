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
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

/**
* Container with border layout contains main panel and child panel
*/
@SuppressWarnings( "unchecked" )
public class BorderLayoutPanel extends GenericPanel {
	public static final String MAIN_PANEL = "main";
	public static final String CHILD_PANEL = "child";
	
	private int iCompletedCount = 0;
	private BorderLayoutData mainLayoutData, childLayoutData;
	private int iChildSize;
	private String sMainTitle, sChildTitle;
	private RegionPosition childPosition = RegionPosition.SOUTH;

	public BorderLayoutPanel( ) {
		setLayout( new BorderLayout( ) );
		setBorder( false );
	}

	/**
	 * @param iChildSize the child panel initial size
	 */
	public BorderLayoutPanel( int iChildSize ) {
		this( );		
		setChildPanelSize( iChildSize );
	}

	/**
	 * @param sTitle
	 *            the panel title
	 * @param iChildSize the child panel initial size
	 */
	public BorderLayoutPanel( String sTitle, int iChildSize ) {
		this( iChildSize );
		setTitle( sTitle );
	}

	/**
	 * Returns the child panel initial size
	 * 
	 * @return the child panel size
	 */
	public int getChildPanelSize( ) {
		return( iChildSize );
	}

	/**
	 * Sets the child panel initial size
	 * 
	 * @param iChildSize the child panel initial size to set
	 */
	public void setChildPanelSize( int iChildSize ) {
		this.iChildSize = iChildSize;
	}

	/**
	 * Sets title for main panel
	 * 
	 * @param sMainTitle the title for main panel to set
	 */
	public void setMainPanelTitle( String sMainTitle ) {
		this.sMainTitle = sMainTitle;
	}

	/**
	 * Sets title for child panel 
	 * 
	 * @param sChildTitle the title for child panel to set
	 */
	public void setChildPanelTitle( String sChildTitle ) {
		this.sChildTitle = sChildTitle;
	}

	/**
	 * Returns main panel layout data
	 * 
	 * @return the main panel layout data
	 */
	public BorderLayoutData getMainLayoutData( ) {
		return( mainLayoutData );
	}

	/**
	 * Returns child panel layout data
	 * 
	 * @return the child panel layout data
	 */
	public BorderLayoutData getChildLayoutData( ) {
		return( childLayoutData );
	}

	/**
	 * @see org.homedns.mkh.ui.client.frame.GenericPanel#addPanels()
	 */
	@Override
	public void addPanels( ) {
		iCompletedCount++;
		if( iCompletedCount == 2 ) {
			super.addPanels( );
			doLayout( );
		}
	}

	/**
	 * @see org.homedns.mkh.ui.client.frame.GenericPanel#init()
	 */
	@Override
	protected void init( ) {
		Panel mainPanel = new Panel( );
		mainPanel.setLayout( new FitLayout( ) );
		mainPanel.setBorder( false );
		if( sMainTitle != null ) {
			mainPanel.setTitle( sMainTitle );
		}
        mainLayoutData = new BorderLayoutData( RegionPosition.CENTER );  
		putPanel( MAIN_PANEL, mainPanel );
		Panel childPanel = new Panel( );
		childPanel.setLayout( new FitLayout( ) );
		childPanel.setBorder( false );
		if( sChildTitle != null ) {
			childPanel.setTitle( sChildTitle );
		}
		if( RegionPosition.NORTH.equals( childPosition ) || RegionPosition.SOUTH.equals( childPosition ) ) {
			childPanel.setHeight( iChildSize );
		}
		if( RegionPosition.WEST.equals( childPosition ) || RegionPosition.EAST.equals( childPosition ) ) {
			childPanel.setWidth( iChildSize );			
		}
		childPanel.setCollapsible( true );
        childLayoutData = new BorderLayoutData( childPosition );
        childLayoutData.setSplit( true );  
        childLayoutData.setCollapseModeMini( true );
        childPanel.setHeader( false );
		putPanel( CHILD_PANEL, childPanel );
		add( mainPanel, mainLayoutData );
		add( childPanel, childLayoutData );
	}

	/**
	 * @see org.homedns.mkh.ui.client.frame.GenericPanel#onAfterInit(org.homedns.mkh.dataservice.client.view.View)
	 */
	@Override
	protected void onAfterInit( View view ) {
	}

	/**
	 * Returns child panel position
	 * 
	 * @return the child panel position
	 */
	public RegionPosition getChildPosition( ) {
		return( childPosition );
	}

	/**
	 * Sets child panel position
	 * 
	 * @param childPosition the child panel position to set
	 */
	public void setChildPosition( RegionPosition childPosition ) {
		this.childPosition = childPosition;
	}
}
