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

import org.homedns.mkh.dataservice.client.view.ViewCache;
import org.homedns.mkh.dataservice.client.view.ViewCacheFactory.ViewCacheCreator;

/**
 * Store creator
 *
 */
public class StoreCreator implements ViewCacheCreator {

	/**
	 * @see org.homedns.mkh.dataservice.client.Creator#instantiate(java.lang.Class)
	 */
	@Override
	public ViewCache instantiate( Class< ? > type ) {
		ViewCache cache = null;
		if( type == WidgetStore.class ) {
			cache = new WidgetStore( );
		} else if( type == PagerStore.class ) {
			cache = new PagerStore( );
		} else {
			throw new IllegalArgumentException( type.getName( ) );			
		}
		return( cache );
	}
}
