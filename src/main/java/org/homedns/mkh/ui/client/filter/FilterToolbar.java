/*
* Copyright 2011-2014 Mikhail Khodonov
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
import org.homedns.mkh.ui.client.UIConstants;
import org.homedns.mkh.ui.client.command.CommandFactory;
import org.homedns.mkh.ui.client.command.RetrieveCmd;
import com.google.gwt.user.client.Command;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

/**
 * Filter toolbar
 *
 */
public class FilterToolbar extends Toolbar {
	private static final UIConstants CONSTANTS = ( UIConstants )AbstractEntryPoint.getConstants( );

	private boolean _bRemoteFilterExecuted = false;
	
	/**
	 * @param filter the filter object
	 */
	public FilterToolbar( final Filter filter ) {
		final Command cmd = CommandFactory.create( RetrieveCmd.class, filter.getView( ) );
		final ToolbarButton apply = new ToolbarButton( 
			CONSTANTS.applyFilter( ), 
			new ButtonListenerAdapter( ) {
				public void onClick( Button button, EventObject e ) {
					_bRemoteFilterExecuted = true;
					cmd.execute( );
				}
			}
		);
    	apply.disable( );
    	
    	ToolbarButton clear = new ToolbarButton( 
    		CONSTANTS.clear( ),
			new ButtonListenerAdapter( ) {
				public void onClick( Button button, EventObject e ) {
					filter.clearFilters( );
					if( _bRemoteFilterExecuted ) {
						_bRemoteFilterExecuted = false;
						cmd.execute( );
					}
				}
			}
		);
    	
    	ToolbarButton remote = new ToolbarButton( 
        	CONSTANTS.cacheFilter( ), 
    		new ButtonListenerAdapter( ) {
				@Override
				public void onClick( Button button, EventObject e ) {
					apply.setDisabled( !button.isPressed( ) );
					filter.setLocalFlag( !button.isPressed( ) );					
					button.setText( 
						button.isPressed( ) ? CONSTANTS.remoteFilter( ) : CONSTANTS.cacheFilter( ) 
					);
				}
    		}
    	);
    	remote.setEnableToggle( true );
    	addSpacer( );
    	addButton( remote );
    	addSeparator( );
    	addButton( apply );
    	addSeparator( );
    	addButton( clear );
    	addFill( );		
	}
}

