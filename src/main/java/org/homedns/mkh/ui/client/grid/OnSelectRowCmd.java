/*
 * Copyright 2016 Mikhail Khodonov
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

package org.homedns.mkh.ui.client.grid;

import org.homedns.mkh.ui.client.ChildView;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;

/**
 * Performs child view action on select row in master view
 *
 */
public class OnSelectRowCmd implements RepeatingCommand {
	private ChildView view;

	/**
	 * @param view the target child view
	 */
	public OnSelectRowCmd( ChildView view ) {
		this.view = view;
	}

	/**
	 * @see com.google.gwt.core.client.Scheduler.RepeatingCommand#execute()
	 */
	@Override
	public boolean execute( ) {
		boolean bAgain = true;
		if( view.getCache( ) != null ) {
			view.onSelectRowAction( );
			bAgain = false;
		}
		return( bAgain );
	}
}
