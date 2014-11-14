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

package org.homedns.mkh.ui.client.form;

import org.homedns.mkh.dataservice.client.AbstractEntryPoint;
import org.homedns.mkh.dataservice.client.event.EventBus;
import org.homedns.mkh.dataservice.client.event.HandlerRegistry;
import org.homedns.mkh.dataservice.client.event.HandlerRegistryAdaptee;
import org.homedns.mkh.dataservice.client.view.ViewDesc;
import org.homedns.mkh.dataservice.shared.InsertResponse;
import org.homedns.mkh.dataservice.shared.Response;
import org.homedns.mkh.dataservice.shared.Id;
import org.homedns.mkh.dataservice.shared.UpdateResponse;
import org.homedns.mkh.ui.client.CmdTypeItem;
import org.homedns.mkh.ui.client.HasState;
import org.homedns.mkh.ui.client.State;
import org.homedns.mkh.ui.client.Transition;
import org.homedns.mkh.ui.client.UIConstants;
import org.homedns.mkh.ui.client.command.CommandFactory;
import org.homedns.mkh.ui.client.command.InsertCmd;
import org.homedns.mkh.ui.client.command.UpdateCmd;
import org.homedns.mkh.ui.client.event.SelectRowEvent;
import org.homedns.mkh.ui.client.event.SelectRowEvent.SelectRowHandler;
import com.google.gwt.user.client.Command;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Container;
import com.gwtext.client.widgets.form.event.FormPanelListenerAdapter;
import org.homedns.mkh.ui.client.cache.WidgetStore;

/**
 * Form for view/edit grid records
 *
 */
@SuppressWarnings( "unchecked" )
public class GridForm extends BoundForm implements SelectRowHandler, HasState, HandlerRegistry {
	private static final UIConstants CONSTANTS = ( UIConstants )AbstractEntryPoint.getConstants( );

	private HandlerRegistryAdaptee _handlers;
	private State _state = States.NO_STATE;
	private Transition _transition;

	/**
	 * {@link org.homedns.mkh.ui.client.form.BoundForm}
	 */
	public GridForm( Id id, GridFormConfig cfg ) {
		super( id, cfg );
		_handlers = new HandlerRegistryAdaptee( );
		addHandler( 
			EventBus.getInstance( ).addHandlerToSource( SelectRowEvent.TYPE, id.hashCode( ), this ) 
		);
	}

	/**
	 * @see org.homedns.mkh.ui.client.form.BoundForm#config()
	 */
	@Override
	protected void config( ) {
		super.config( );
		GridFormConfig cfg = ( GridFormConfig )getFormConfig( );
		_transition = ( Transition )cfg.getAttribute( GridFormConfig.TRANSITION );
		changeStateTo( ( State )cfg.getAttribute( GridFormConfig.INIT_STATE ) );
	}

	/**
	 * @see org.homedns.mkh.ui.client.event.SelectRowEvent.SelectRowHandler#onSelectRow(org.homedns.mkh.ui.client.event.SelectRowEvent)
	 */
	@Override
	public void onSelectRow( SelectRowEvent event ) {
		loadRecord( event.getRecord( ) );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#init(org.homedns.mkh.dataservice.client.view.ViewDesc)
	 */
	@Override
	public void init( ViewDesc desc ) {
		setDescription( desc );
		config( );
		setReader( ( ( WidgetStore )getCache( ) ).getReader( ) );
		Command cmd = getAfterInitCommand( );
		if( cmd != null ) {
			cmd.execute( );
		}
		addListener(
			new FormPanelListenerAdapter( ) {
				@Override
				public void onAfterLayout( Container self ) {
					refresh( );
				}
			}
		);
	}
	
	/**
	 * @see org.homedns.mkh.ui.client.form.BaseForm#onButtonClick(org.homedns.mkh.ui.client.form.FormButton, com.gwtext.client.core.EventObject)
	 */
	@Override
	protected void onButtonClick( FormButton button, EventObject e ) {
		CmdTypeItem item = null;
		if( CONSTANTS.add( ).equals( button.getText( ) ) ) {
			item = new CmdTypeItem( CONSTANTS.add( ), InsertCmd.class );
			changeStateTo( States.ADD );
		}
		if( CONSTANTS.update( ).equals( button.getText( ) ) ) {
			item = new CmdTypeItem( CONSTANTS.update( ), UpdateCmd.class );
			changeStateTo( States.UPDATE );
		}
		if( CONSTANTS.cancel( ).equals( button.getText( ) ) ) {
			changeStateTo( States.READONLY );
		}
		if( item != null ) {
			getButtons( ).get( CONSTANTS.save( ) ).setCommand( 
				CommandFactory.create( item.getCommandType( ), this ) 
			);
		}
		button.getCommand( ).execute( );
	}

	/**
	 * @see org.homedns.mkh.ui.client.form.BoundForm#refresh(org.homedns.mkh.dataservice.shared.Response)
	 */
	@Override
	public void refresh( Response data ) {
		if( data == null ) {
			return;
		}
		if( data instanceof InsertResponse || data instanceof UpdateResponse ) {
			if( data.getResult( ) == Response.SAVE_SUCCESS ) {
				changeStateTo( States.READONLY );
			}			
		}
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.event.HandlerRegistry#removeHandlers()
	 */
	@Override
	public void removeHandlers( ) {
		_handlers.clear( );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.event.HandlerRegistry#addHandler(com.google.web.bindery.event.shared.HandlerRegistration)
	 */
	@Override
	public boolean addHandler( HandlerRegistration hr ) {
		return( _handlers.add( hr ) );		
	}

	/**
	 * @see org.homedns.mkh.ui.client.HasState#getState()
	 */
	@Override
	public State getState( ) {
		return( _state );
	}

	/**
	 * @see org.homedns.mkh.ui.client.HasState#setState(org.homedns.mkh.ui.client.State)
	 */
	@Override
	public void setState( State state ) {
		_state = state;
	}

	/**
	 * @see org.homedns.mkh.ui.client.HasState#changeStateTo(org.homedns.mkh.ui.client.State)
	 */
	@Override
	public void changeStateTo( State newState ) {
		_transition.doTransition( newState, this );
	}
}

	