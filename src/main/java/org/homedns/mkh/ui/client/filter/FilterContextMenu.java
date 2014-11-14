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

package org.homedns.mkh.ui.client.filter;

import org.homedns.mkh.dataservice.client.AbstractEntryPoint;
import org.homedns.mkh.ui.client.BaseMenu;
import org.homedns.mkh.ui.client.MenuItem;
import org.homedns.mkh.ui.client.UIConstants;
import org.homedns.mkh.ui.client.command.CommandFactory;
import org.homedns.mkh.ui.client.command.RetrieveCmd;
import com.google.gwt.user.client.Command;
import com.gwtext.client.widgets.menu.CheckItem;
import com.gwtext.client.widgets.menu.Item;

/**
 * View filter context menu
 *
 */
public class FilterContextMenu extends BaseMenu {
	private static final UIConstants CONSTANTS = ( UIConstants )AbstractEntryPoint.getConstants( );

	private boolean _bRemoteFilterExecuted = false;
	
	/**
	 * @param filter
	 *            the filter which is managed via this context menu
	 */
	public FilterContextMenu( final Filter filter ) {
		final Command cmd = CommandFactory.create( RetrieveCmd.class, filter.getView( ) );
		final MenuItem apply = new MenuItem( 
			Item.class, 
			CONSTANTS.applyFilter( ), 
			new Command( ) {
				@Override
				public void execute( ) {
					_bRemoteFilterExecuted = true;
					cmd.execute( );
				}
			}
		);
		apply.getMenuItem( ).disable( );
		final MenuItem remote = new MenuItem( 
			CheckItem.class, 
			CONSTANTS.remoteFilter( ), 
			new Command( ) {
				@Override
				public void execute( ) {
					apply.getMenuItem( ).setDisabled( !apply.getMenuItem( ).isDisabled( ) );
					filter.setLocalFlag( apply.getMenuItem( ).isDisabled( ) );
				}
			}
		);
		remote.getMenuItem( ).setHideOnClick( false );
		MenuItem clear = new MenuItem( 
			Item.class, 
			CONSTANTS.clear( ), 
			new Command( ) {
				@Override
				public void execute( ) {
					filter.clearFilters( );
					if( _bRemoteFilterExecuted ) {
						_bRemoteFilterExecuted = false;
						cmd.execute( );
					}
				} 
			} 
		);
		addItem( remote.getMenuItem( ) );
		addItem( apply.getMenuItem( ) );
		addItem( clear.getMenuItem( ) );		
	}
}
