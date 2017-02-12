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

package org.homedns.mkh.ui.client.frame;

import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.dataservice.shared.Id;

/**
 * Panel's component builder interface
 * 
 */
public interface ComponentBuilder {

	/**
	 * Returns panel's view component @see
	 * org.homedns.mkh.dataservice.client.view.View
	 * 
	 * @param type
	 *            the component class type
	 * @param id
	 *            the view identification object to bind
	 * 
	 * @return the view component
	 */
	public View getComponent( Class< ? > type, Id id );

	/**
	 * Returns panel's child view component which depends on master component
	 * 
	 * @param type
	 *            the component class type
	 * @param id
	 *            the view identification object to bind
	 * @param masterId
	 *            the master component identification object
	 * @param sMasterPK
	 *            the master component primary key name
	 * 
	 * @return the child view component
	 */
	public View getChildComponent( 
		Class< ? > type, 
		Id id, 
		Id masterId,
		String sMasterPK 
	);
}
