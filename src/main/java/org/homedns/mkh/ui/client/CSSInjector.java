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

import com.google.gwt.user.client.ui.UIObject;

/**
 * Custom CSS injector interface
 *
 */
public interface CSSInjector {
	
	/**
	 * Injects preassigned custom css's for the specified object  
	 * 
	 * @param obj the target object
	 */
	public void injectCSS( UIObject obj );

	/**
	 * Sets custom css name for specified object type
	 * 
	 * @param type
	 *            the object type
	 * @param sTargetCSS
	 *            the target css name
	 * @param sAddingCSS
	 *            the adding css name
	 */
	public void setCSS( Class< ? > type, String sTargetCSS, String sAddingCSS );
}
