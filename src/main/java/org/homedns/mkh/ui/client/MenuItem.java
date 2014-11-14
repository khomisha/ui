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

package org.homedns.mkh.ui.client;

import com.google.gwt.user.client.Command;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.CheckItem;
import com.gwtext.client.widgets.menu.ColorItem;
import com.gwtext.client.widgets.menu.DateItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;

/**
 * Menu item
 *
 */
public class MenuItem {
	private Item _item;

	/**
	 * @param type
	 *            the menu item type
	 * @param sLabel
	 *            the menu item label
	 * @param cmd
	 *            the menu item command
	 */
	public MenuItem( Class< ? > type, String sLabel, final Command cmd ) {
		if( type == CheckItem.class ) {
			_item = new CheckItem( );
		} else if( type == DateItem.class ) {
			_item = new DateItem( );
		} else if( type == ColorItem.class ) {
			_item = new ColorItem( );
		} else if( type == Item.class ) {
			_item = new Item( );			
		} else {
			throw new IllegalArgumentException( type.getName( ) );
		}
		_item.setText( sLabel );
		_item.addListener(
			new BaseItemListenerAdapter( ) {
				@Override
				public void onClick( BaseItem item, EventObject e ) {
					cmd.execute( );
				}
			}
		);
	}

	/**
	 * Returns menu item
	 * 
	 * @return the menu item
	 */
	public Item getMenuItem( ) {
		return( _item );
	}
}
