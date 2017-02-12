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

import java.util.HashMap;
import java.util.Map;
import org.homedns.mkh.ui.client.CSSHelper;
import org.homedns.mkh.ui.client.CSSInjector;
import com.google.gwt.user.client.ui.UIObject;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.grid.GridPanel;

/**
 * Base CSS injector implementation @see org.homedns.mkh.ui.client.CSSInjector
 *
 */
public class BaseCSSInjector implements CSSInjector {
	private Map< Class< ? >, Map< String, String> > styles;

	public BaseCSSInjector( ) {
		styles = new HashMap< Class< ? >, Map< String, String > >( );
	}

	/**
	 * @see org.homedns.mkh.ui.client.CSSInjector#injectCSS(com.google.gwt.user.client.ui.UIObject)
	 */
	@Override
	public void injectCSS( UIObject obj ) {
		Map< String, String > typeStyle = null;
		if( obj instanceof GridPanel ) {
			typeStyle = styles.get( GridPanel.class );
		}
		if( obj instanceof FormPanel ) {
			typeStyle = styles.get( FormPanel.class );
		}
		if( typeStyle != null ) {
			CSSHelper.addCSSName( obj, typeStyle );
		}
	}
	
	/**
	 * @see org.homedns.mkh.ui.client.CSSInjector#setCSS(java.lang.Class, java.lang.String, java.lang.String)
	 */
	public void setCSS( Class< ? > type, String sTargetCSS, String sAddingCSS ) {
		if( styles.containsKey( type ) ) {
			styles.get( type ).put( sTargetCSS, sAddingCSS );
		} else {
			Map< String, String > typeStyle = new HashMap< String, String >( );
			typeStyle.put( sTargetCSS, sAddingCSS );
			styles.put( type, typeStyle );
		}
	}
}
