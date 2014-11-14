/*
 * Copyright 2013-2014 Mikhail Khodonov
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

package org.homedns.mkh.ui.client.grid;

import com.google.gwt.core.client.GWT;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.grid.CellMetadata;
import com.gwtext.client.widgets.grid.Renderer;

/**
 * Check box renderer
 *
 */
public class CheckboxRenderer implements Renderer {

	/**
	 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object, com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
	 */
	@Override
	public String render( 
		Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum, Store store 
	) {
		final boolean bChecked = ( "1".equals( String.valueOf( value ) ) );
		return(
			"<img class=\"checkbox\" src=\"" + 
			GWT.getModuleBaseURL( ) + "/js/ext/resources/images/default/menu/" +
			( bChecked ? "checked.gif" : "unchecked.gif" ) + "\"/>"
		);
	}

}
