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

import org.homedns.mkh.dataservice.client.Column;
import org.homedns.mkh.ui.client.form.ListBox;
import com.gwtext.client.widgets.form.Field;

/**
 * Column configuration factory
 *
 */
public class ColumnConfigFactory {
	
	/**
	 * Creates column configuration
	 * 
	 * @param col
	 *            the column for which configuration will be created
	 * @param field
	 *            the field object
	 * 
	 * @return the column configuration
	 */
	public static ColConfig create( Column col, Field field ) {
		ColConfig colConfig = new ColConfig( );
		colConfig.setField( field );
		String sStyle = col.getStyle( );
		colConfig.setDataIndex( col.getName( ) );
		if( "".equals( sStyle ) ) {
			colConfig.setHidden( true );
		} else {
			colConfig.setSortable( true );
			colConfig.setHeader( col.getCaption( ) );
			if( Column.CHECKBOX.equals( sStyle ) ) {
				// shows checkbox in grid
				colConfig.setRenderer( new CheckboxRenderer( ) );
			} else if( Column.DDDB.equals( sStyle ) || Column.DDLB.equals( sStyle ) ) {
				colConfig.setRenderer( new DDDBRenderer( ( ListBox )field ) );				
			} else if( 
				Column.EDIT_DATE.equals( sStyle ) ||
				Column.EDIT_TIME.equals( sStyle ) ||
				Column.EDIT_TS.equals( sStyle )
			) {
				colConfig.setRenderer( new DateRenderer( sStyle ) );
			}
		}
		return( colConfig );
	}
}
