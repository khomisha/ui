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
import org.homedns.mkh.dataservice.shared.DeleteResponse;
import org.homedns.mkh.dataservice.shared.InsertResponse;
import org.homedns.mkh.dataservice.shared.Response;
import org.homedns.mkh.dataservice.shared.Id;
import org.homedns.mkh.dataservice.shared.UpdateResponse;
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
import com.gwtext.client.data.Record;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Container;
import com.gwtext.client.widgets.form.event.FormPanelListenerAdapter;

/**
 * Form for view/edit grid records
 *
 */
@SuppressWarnings( "unchecked" )
public class GridForm extends BoundForm implements SelectRowHandler, HasState, HandlerRegistry {
	private static final UIConstants CONSTANTS = ( UIConstants )AbstractEntryPoint.getConstants( );

	private HandlerRegistryAdaptee handlers;
	private State state = States.READONLY;
	private Transition transition;
	private Command insertCmd, updateCmd;

	/**
	 * {@link org.homedns.mkh.ui.client.form.BoundForm}
	 */
	public GridForm( Id id, GridFormConfig cfg ) {
		super( id, cfg );
		setStoreListener( new GridFormCacheListener( ) );
		handlers = new HandlerRegistryAdaptee( );
		addHandler( 
			EventBus.getInstance( ).addHandlerToSource( SelectRowEvent.TYPE, id.getUID( ), this ) 
		);
	}

	/**
	 * @see org.homedns.mkh.ui.client.form.BoundForm#config()
	 */
	@Override
	protected void config( ) {
		super.config( );
		GridFormConfig cfg = ( GridFormConfig )getFormConfig( );
		transition = ( Transition )cfg.getAttribute( GridFormConfig.TRANSITION );
		changeStateTo( ( State )cfg.getAttribute( GridFormConfig.INIT_STATE ) );
	}

	/**
	 * @see org.homedns.mkh.ui.client.event.SelectRowEvent.SelectRowHandler#onSelectRow(org.homedns.mkh.ui.client.event.SelectRowEvent)
	 */
	@Override
	public void onSelectRow( SelectRowEvent event ) {
		loadRecord( event.getRecord( ) );
		if( getState( ) == States.NO_STATE ) {
			changeStateTo( States.READONLY );
		}
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#init(org.homedns.mkh.dataservice.client.view.ViewDesc)
	 */
	@Override
	public void init( ViewDesc desc ) {
		super.init( desc );
		insertCmd = CommandFactory.create( InsertCmd.class, this );
		updateCmd = CommandFactory.create( UpdateCmd.class, this );
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
		int iSaveType = -1;
		if( CONSTANTS.add( ).equals( button.getText( ) ) ) {
			iSaveType = 0;
			changeStateTo( States.ADD );
		}
		if( CONSTANTS.update( ).equals( button.getText( ) ) ) {
			iSaveType = 1;
			changeStateTo( States.UPDATE );
		}
		if( CONSTANTS.cancel( ).equals( button.getText( ) ) ) {
			changeStateTo( States.READONLY );
		}
		if( iSaveType > -1 ) {
			getButtons( ).get( CONSTANTS.save( ) ).setCommand( iSaveType == 0 ? insertCmd : updateCmd );
		}
		if( CONSTANTS.save( ).equals( button.getText( ) ) ) {
			if( !getForm( ).isValid( ) ) {
				return;
			}
		}
		super.onButtonClick( button, e );
	}

	/**
	 * @see org.homedns.mkh.ui.client.form.BoundForm#onResponse(org.homedns.mkh.dataservice.shared.Response)
	 */
	@Override
	public void onResponse( Response response ) {
		if( response == null ) {
			return;
		}
		setResponse( response );
		if( response instanceof InsertResponse || response instanceof UpdateResponse ) {
			if( response.getResult( ) == Response.SAVE_SUCCESS ) {
				changeStateTo( States.READONLY );
			}			
		}
		if( response instanceof DeleteResponse ) {
			if( response.getResult( ) == Response.SAVE_SUCCESS ) {
				loadEmptyRecord( );
				changeStateTo( States.NO_STATE );
			}
		}
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.event.HandlerRegistry#removeHandlers()
	 */
	@Override
	public void removeHandlers( ) {
		handlers.clear( );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.event.HandlerRegistry#addHandler(com.google.web.bindery.event.shared.HandlerRegistration)
	 */
	@Override
	public boolean addHandler( HandlerRegistration hr ) {
		return( handlers.add( hr ) );		
	}

	/**
	 * @see org.homedns.mkh.ui.client.HasState#getState()
	 */
	@Override
	public State getState( ) {
		return( state );
	}

	/**
	 * @see org.homedns.mkh.ui.client.HasState#setState(org.homedns.mkh.ui.client.State)
	 */
	@Override
	public void setState( State state ) {
		this.state = state;
	}

	/**
	 * @see org.homedns.mkh.ui.client.HasState#changeStateTo(org.homedns.mkh.ui.client.State)
	 */
	@Override
	public void changeStateTo( State newState ) {
		transition.doTransition( newState, this );
	}
	
	protected class GridFormCacheListener extends CacheListener {
		/**
		 * @see org.homedns.mkh.ui.client.form.BoundForm.CacheListener#onLoad(com.gwtext.client.data.Store, com.gwtext.client.data.Record[])
		 */
		@Override
		public void onLoad( Store store, Record[] records ) {
		}

		/**
		 * @see org.homedns.mkh.ui.client.form.BoundForm.CacheListener#onDataChanged(com.gwtext.client.data.Store)
		 */
		@Override
		public void onDataChanged( Store store ) {
			super.onDataChanged( store );
			if( store.getCount( ) < 1 ) {
				changeStateTo( States.NO_STATE );				
			}
		}		
	}
}

	