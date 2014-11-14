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

import com.google.gwt.core.client.JavaScriptObject;
import com.gwtext.client.data.DataProxy;
import com.gwtext.client.util.JavaScriptObjectHelper;

/**
 * @see com.gwtext.client.data.MemoryProxy
 *
 */
public class CustomMemoryProxy extends DataProxy {

	/**
	 * Creates new memory proxy using the passed data as javascript object.
	 * 
	 * @param data
	 *            the data as javascript object
	 */
	public CustomMemoryProxy( JavaScriptObject data ) {
		jsObj = create( data );
    }

	/**
	 * Creates new memory proxy using the passed array data.
	 * 
	 * @param data
	 *            the array data
	 */
	public CustomMemoryProxy( Object[][] data ) {
		jsObj = create( JavaScriptObjectHelper.convertToJavaScriptArray( data ) );
    }

    protected native JavaScriptObject create( JavaScriptObject data ) /*-{
        return new $wnd.Ext.data.MemoryProxy(data);
    }-*/;
}
