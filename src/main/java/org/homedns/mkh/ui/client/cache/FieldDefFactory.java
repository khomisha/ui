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

package org.homedns.mkh.ui.client.cache;

import org.homedns.mkh.dataservice.client.Column;
import org.homedns.mkh.dataservice.client.Type;
import com.gwtext.client.data.BooleanFieldDef;
import com.gwtext.client.data.DateFieldDef;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.FloatFieldDef;
import com.gwtext.client.data.IntegerFieldDef;
import com.gwtext.client.data.StringFieldDef;

/**
 * Field definition factory
 *
 */
public class FieldDefFactory {

	/**
	 * Creates field definition
	 * 
	 * @param col the column for which field definition will be created
	 * 
	 * @return the field definition
	 */
	public static FieldDef create( Column col ) {
		FieldDef fieldDef = null;
		Type type = col.getColType( );
		String sColName = col.getName( );
		if( type == Type.BOOLEAN ) {
			fieldDef = new BooleanFieldDef( sColName );
		} else if( 
			type == Type.BYTE || 
			type == Type.INT || 
			type == Type.SHORT || 
			type == Type.LONG 
		) {
			fieldDef = new IntegerFieldDef( sColName );
		} else if( type == Type.FLOAT || type == Type.DOUBLE ) {
			fieldDef = new FloatFieldDef( sColName );
		} else if( type == Type.TIMESTAMP ) {
			fieldDef = new DateFieldDef( sColName, "Y-m-d H:i:s" );
		} else if( type == Type.STRING ) {
			fieldDef = new StringFieldDef( sColName );			
		}
		return( fieldDef );
	}
}
