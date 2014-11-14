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

import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.layout.LayoutData;

/**
 * Panel content interface
 *
 */
public interface Content {

	/**
	 * Add a widget to the Content
	 * 
	 * @param widget
	 *            the widget to add
	 */
	public void add( Widget widget );
	
	/**
	 * Add a widget to the Content
	 * 
	 * @param widget
	 *            the widget to add
	 * @param layoutData
	 *            the widget layout 
	 */ 
	public void add( Widget widget, LayoutData layoutData );
}
