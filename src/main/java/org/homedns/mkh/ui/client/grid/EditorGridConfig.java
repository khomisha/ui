/*
 * Copyright 2015 Mikhail Khodonov
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

/**
 * Editable grid configuration object
 *
 */

@SuppressWarnings( "serial" )
public class EditorGridConfig extends GridConfig {
	/**
	* Read only columns list, default is null
	*/
	public static final Integer READONLY_COLS = 100;

	public EditorGridConfig() {
		super( );
		setAttribute( READONLY_COLS, null );
	}
}
