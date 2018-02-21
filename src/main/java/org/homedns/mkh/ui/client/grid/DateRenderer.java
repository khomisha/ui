/*
 * Copyright 2011-2014 Mikhail Khodonov
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

import java.util.Date;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.grid.CellMetadata;
import com.gwtext.client.widgets.grid.Renderer;
import org.homedns.mkh.dataservice.client.Column;
import org.homedns.mkh.dataservice.client.DateFormatter;

/**
 * Date renderer
 *
 */
public class DateRenderer implements Renderer {
	private DateTimeFormat dateFormatter;

	/**
	 * @param sStyle
	 *            the date time column style
	 */
	public DateRenderer( String sStyle ) {
		if( sStyle.equals( Column.EDIT_DATE ) ) {
			dateFormatter = DateFormatter.DATE.getDateTimeFormat( );
		} else if( sStyle.equals( Column.EDIT_TIME ) ) {
			dateFormatter = DateFormatter.TIME.getDateTimeFormat( );
		} else if( sStyle.equals( Column.EDIT_TS ) ) {
			dateFormatter = DateFormatter.TIMESTAMP.getDateTimeFormat( );
		}
	}

	/**
	 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object, com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
	 */
	@Override
	public String render(
		Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum, Store store
	) {
		return( ( value != null ) ? dateFormatter.format( ( Date )value ) : "" );
	}
}
