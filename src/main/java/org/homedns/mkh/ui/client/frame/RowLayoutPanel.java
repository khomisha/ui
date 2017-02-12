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
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.layout.RowLayout;
import com.gwtext.client.widgets.layout.RowLayoutData;

/**
* Container with border layout contains center panel and second panel
*/
@SuppressWarnings( "unchecked" )
public class RowLayoutPanel extends GenericPanel {

	private int iCompletedCount = 0;
	private RowLayoutData firstData, secondData;
	private String sSecondPanelHeight;

	public RowLayoutPanel( ) {
		setLayout( new RowLayout( ) );
	}

	/**
	 * @param iSouthPanelHeight the second panel initial height
	 */
	public RowLayoutPanel( String sSecondPanelHeight ) {
		this( );		
		setSecondPanelHeight( sSecondPanelHeight );
	}

	/**
	 * @param sTitle
	 *            the panel title
	 * @param sSecondPanelHeight the second panel initial height
	 */
	public RowLayoutPanel( String sTitle, String sSecondPanelHeight ) {
		this( sSecondPanelHeight );
		setTitle( sTitle );
	}

	/**
	 * Returns the second panel initial height
	 * 
	 * @return the second panel height
	 */
	public String getSecondPanelHeight( ) {
		return( sSecondPanelHeight );
	}

	/**
	 * Sets the second panel initial height
	 * 
	 * @param sSecondPanelHeight the second panel initial height to set
	 */
	public void setSecondPanelHeight( String sSecondPanelHeight ) {
		this.sSecondPanelHeight = sSecondPanelHeight;
	}

	/**
	 * Adds list panel and edit panel then when they are completed 
	 */
	public void addPanels( ) {
		iCompletedCount++;
		if( iCompletedCount == 2 ) {
			add( getPanel( "first" ), firstData );
			add( getPanel( "second" ), secondData );
			doLayout( );
		}
	}

	/**
	 * @see org.homedns.mkh.ui.client.frame.GenericPanel#init()
	 */
	@Override
	protected void init( ) {
		Panel firstPanel = new Panel( );
		firstPanel.setLayout( new FitLayout( ) );
		firstPanel.setBorder( false );
		firstPanel.setCollapsible( true );
		firstData = new RowLayoutData( "60%" );
		putPanel( "first", firstPanel );
		Panel secondPanel = new Panel( );
		secondPanel.setLayout( new FitLayout( ) );
		secondPanel.setBorder( false );
		secondPanel.setCollapsible( true );
        secondData = new RowLayoutData( sSecondPanelHeight );  
		putPanel( "second", secondPanel );
	}

	/**
	 * @see org.homedns.mkh.ui.client.frame.GenericPanel#onAfterInit(org.homedns.mkh.dataservice.client.view.View)
	 */
	@Override
	protected void onAfterInit( View view ) {
	}
}
