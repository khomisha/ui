/*
 * Copyright 2013-2014 Mikhail Khodonov
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

import java.util.List;
import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.ui.client.command.CommandFactory;
import com.google.gwt.user.client.Command;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.menu.BaseItem;

/**
 * Base menu
 *
 */
public class BaseMenu extends Menu {
	
	public BaseMenu( ) {
	}
	
	/**
	 * @param items
	 *            the menu items
	 */
	public BaseMenu( List< MenuItem > items ) {
		for( MenuItem item : items ) {
			addItem( item.getMenuItem( ) );
		}
	}

	/**
	 * @param items
	 *            the menu item names list
	 * @param view
	 *            the view to which the commands (defined for menu items) will
	 *            be applied
	 */
	public BaseMenu( List< CmdTypeItem > items, View view ) {
		for( CmdTypeItem item : items ) {
			final Command cmd = CommandFactory.create( 
				item.getCommandType( ), view 
			);
			addItem( 
				new Item( 
					item.getName( ), 
					new BaseItemListenerAdapter( ) {
						public void onClick( BaseItem item, EventObject e ) {
							cmd.execute( );
						}
					}
				)
			);
		}
	}
}
