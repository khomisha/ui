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

package org.homedns.mkh.ui.client.cache;

import com.google.gwt.core.client.JavaScriptObject;
import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;

/**
 * Custom simple store
 *
 */
public class CustomSimpleStore extends Store {

	/**
	 * @param asField
	 *            the array of field names. All fields are treated as data type String
	 * @param data
	 *            the data to store
	 */
	public CustomSimpleStore( String[] asField, JavaScriptObject data ) {
		FieldDef[] fieldDefs = new FieldDef[ asField.length ];
		int iField = 0;
		for( String sField : asField ) {
			fieldDefs[ iField ] = new StringFieldDef( sField );
			iField++;
		}
		setDataProxy( new CustomMemoryProxy( data ) );
		setReader( new ArrayReader( new RecordDef( fieldDefs ) ) );
	}
}
