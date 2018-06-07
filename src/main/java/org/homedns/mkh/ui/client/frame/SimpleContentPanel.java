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

import org.homedns.mkh.ui.client.form.GridForm;
import org.homedns.mkh.ui.client.grid.Grid;

/**
 * Panel with accordion layout, which contains grid with paging and filter
 * options to navigate data and form to edit selected in grid record
 * 
 */
public class SimpleContentPanel extends AccordionPanel {

	/**
	 * @param sTitle
	 *            the panel's title
	 */
	public SimpleContentPanel( String sTitle ) {
		super( sTitle );
	}

	/**
	 * @see org.homedns.mkh.ui.client.frame.AccordionPanel#init()
	 */
	@Override
	protected void init( ) {
		super.init( );
		Grid grid = createView( Grid.class, getViewName( ), "list" );
		createView( GridForm.class, grid.getID( ), "edit" );
		grid.addGridListener( new ListWidgetListenerAdapter( ) );
	}
}
