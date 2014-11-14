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

package org.homedns.mkh.ui.client.frame;

import com.gwtext.client.widgets.Panel;

/**
 * Panel builder
 *
 */
public class PanelBuilder {

	/**
	 * Builds panel
	 * 
	 * @param parent
	 *            the target panel
	 * @param cfg
	 *            the panel configuration object
	 */
	public static void build( Panel parent, PanelConfig cfg ) {
		parent.setLayout( cfg.getLayout( ) );
		for( ChildContainer child : cfg.getChilds( ) ) {
			if( child.getLayoutData( ) == null ) {
				parent.add( child.getPanel( ) );
			} else {
				parent.add( child.getPanel( ), child.getLayoutData( ) );
			}
		}
		parent.doLayout( );
	}
}
