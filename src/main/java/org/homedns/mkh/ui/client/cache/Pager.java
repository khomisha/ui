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

import org.homedns.mkh.dataservice.client.Page;

/**
 * Specify store with data pages
 *
 */
public interface Pager {
	/**
	 * Loads specified page into widget store from memory cache or server
	 * 
	 * @param page
	 *            the page object {@link org.homedns.mkh.dataservice.client.Page}
	 */
	public void loadPage( Page page );

	/**
	 * Returns page object
	 * 
	 * @return the page
	 */
	public Page getPage( );
}
