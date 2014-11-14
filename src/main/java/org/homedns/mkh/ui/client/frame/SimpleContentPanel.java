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

import org.homedns.mkh.dataservice.shared.Id;
import org.homedns.mkh.ui.client.form.GridForm;
import org.homedns.mkh.ui.client.form.GridFormConfig;
import org.homedns.mkh.ui.client.grid.Grid;
import org.homedns.mkh.ui.client.grid.GridConfig;
import org.homedns.mkh.ui.client.grid.GridImpl;
import com.google.gwt.user.client.Command;
import com.gwtext.client.widgets.layout.FitLayout;

/**
 * Panel with accordion layout, which contains grid with paging and filter
 * options to navigate data and form to edit selected in grid record
 * 
 */
public class SimpleContentPanel extends AccordionPanel {

	/**
	 * @param sGridName
	 *            the grid name e.g. data buffer name
	 * @param sTitle
	 *            the panel's title
	 * @param sGridPanelTitle
	 *            the grid panel title
	 * @param sFormPanelTitle
	 *            the form panel title
	 */
	public SimpleContentPanel( 
		String sGridName, 
		String sTitle, 
		String sGridPanelTitle, 
		String sFormPanelTitle 
	) {
		super( sTitle, sGridPanelTitle, sFormPanelTitle );
		getListPanel( ).setLayout( new FitLayout( ) );
		getEditPanel( ).setLayout( new FitLayout( ) );
		final Grid grid = getGrid( sGridName );
		final GridForm form = defineForm( grid.getID( ), defineFormConfig( ) );
		grid.setAfterInitCommand(
			new Command( ) {
				public void execute( ) {
					getListPanel( ).add( grid );
					addPanels( );
				}
			}
		);
		form.setAfterInitCommand(
			new Command( ) {
				public void execute( ) {
					getEditPanel( ).add( form );
					addPanels( );
				}
			}
		);
	}

	/**
	 * Returns grid
	 * 
	 * @param sGridName
	 *            the grid name
	 * 
	 * @return the grid
	 */
	protected Grid getGrid( String sGridName ) {
		Id id = new Id( );
		id.setName( sGridName );
		Grid grid = new Grid( id, new GridImpl( defineGridConfig( ) ) );
		grid.addGridListener( new ListWidgetListenerAdapter( ) );
		return( grid );
	}

	/**
	 * Defines grid configuration object
	 * 
	 * @return the grid configuration object
	 */
	protected GridConfig defineGridConfig( ) {
		return( new GridConfig( ) );
	}

	/**
	 * Defines grid form configuration object
	 * 
	 * @return the grid form configuration object
	 */
	protected GridFormConfig defineFormConfig( ) {
		return( new GridFormConfig( ) );
	}
	
	/**
	 * Defines grid form
	 * 
	 * @param id
	 *            the view identification object
	 * @param cfg
	 *            the grid form configuration object
	 * 
	 * @return the customized grid form
	 */
	protected GridForm defineForm( Id id, GridFormConfig cfg ) {
		return( new GridForm( id, cfg ) );
	}
}
