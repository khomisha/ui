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

import org.homedns.mkh.dataservice.client.view.ViewAccess.Access;
import com.google.gwt.core.shared.GWT;
import com.gwtext.client.widgets.DefaultsHandler;

/**
 * Widgets access rights handlers factory. 
 * <p>
 * <pre>
 * 1. The GUI objects list with specified access rights for the particular user
 * are stored in database, every object has unique identification tag. 
 * 2. There are following permissions: no access, read only, read write 
 * 3. Listed GUI object must implement org.homedns.mkh.dataservice.client.view.ViewAccess interface 
 * 4. After user successful login GUI objects list is loaded into the application memory
 * 5. UI component on call setTag fires org.homedns.mkh.dataservice.client.event.SetAccessEvent 
 * to retrieve UI component access right. org.homedns.mkh.dataservice.client.presenter.AccessPresenter 
 * handles event and sets access right if object is listed, otherwise default (read write) access right is applied. 
 * 6. Actually to apply access to the UI objects com.gwtext.client.widgets.Container.setDefaults(DefaultsHandler)
 * should be call from the upper container. {@link org.homedns.mkh.ui.client.frame.ViewAccessHandler} finds all
 * ViewAccess objects in components tree, while {@link org.homedns.mkh.ui.client.frame.SetAccessHandler} 
 * is responsible for the specific access implementation for the given GUI object. 
 * Customized handlers may be used, to do this AccessHandlerFactory implementation 
 * should be included in GWT module via deferred binding.
 * </pre>
 */
public abstract class AccessHandlerFactory {

	private static AccessHandlerFactory _instance;
	
	protected AccessHandlerFactory( ) {
	}

	/**
	 * Returns access rights handlers factory instance
	 * 
	 * @return the access rights handlers factory instance
	 */
	public static AccessHandlerFactory get( ) {
		if( _instance == null ) {
			_instance = GWT.create( AccessHandlerFactory.class );
		}
		return( _instance );
	}
	
	/**
	 * Creates {@link org.homedns.mkh.ui.client.frame.SetAccessHandler} 
	 * 
	 * @param access the access right
	 * 
	 * @return access handler
	 */
	public abstract DefaultsHandler createSetAccessHandler( Access access );

	/**
	 * Creates {@link org.homedns.mkh.ui.client.frame.ViewAccessHandler} 
	 * 
	 * @return access handler
	 */
	public abstract DefaultsHandler createViewAccessHandler( );
}
