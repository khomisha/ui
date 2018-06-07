/*
 * Copyright 2017 Mikhail Khodonov
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

package org.homedns.mkh.ui.client;

import java.util.logging.Logger;
import com.google.gwt.core.client.JavaScriptObject;
import com.gwtext.client.util.JavaScriptObjectHelper;

/**
 * UI related helper methods.
 *
 */
public class Util {
	private static final Logger LOG = Logger.getLogger( Util.class.getName( ) );  

	/**
	 * Recursively outputs javascript object attributes values
	 * 
	 * @param jso the javascript object
	 * @param sShift the format string for output with shift, it should be empty string for root object
	 */
	public static void outputJSOAttribute( JavaScriptObject jso, String sShift ) {
		String[] as = JavaScriptObjectHelper.getProperties( jso );
		for( String sProperty : as ) {
			String sValue = JavaScriptObjectHelper.getAttribute( jso, sProperty );
			LOG.info( sShift + sProperty + ": " + sValue );
			JavaScriptObject jsoChild = JavaScriptObjectHelper.getAttributeAsJavaScriptObject( 
				jso, sProperty 
			);
			if( jsoChild != null ) {
				sShift += "\t";
				outputJSOAttribute( jsoChild, sShift );
			}
		}
	}
}
