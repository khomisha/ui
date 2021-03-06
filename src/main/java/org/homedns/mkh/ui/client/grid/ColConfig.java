/*
* Copyright 2007-2014 Mikhail Khodonov
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

import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.form.Field;

/**
* Column configuration object
*/
public class ColConfig extends ColumnConfig {
	private Field _field;

	/**
	 * Sets field
	 * 
	 * @param field
	 *            the field to set
	 */
	public void setField( Field field ) {
		_field = field;
	}

	/**
	* Returns field
	*
	* @return field or null
	*/
	public Field getField( ) {
		return( _field );
	}
}
