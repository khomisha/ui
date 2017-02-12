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

package org.homedns.mkh.ui.client.frame;

import org.homedns.mkh.dataservice.client.event.SetAccessEvent;
import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.dataservice.client.view.ViewAccess;
import org.homedns.mkh.dataservice.shared.Id;
import org.homedns.mkh.ui.client.ButtonClickHandler;
import org.homedns.mkh.ui.client.Config;
import org.homedns.mkh.ui.client.HasButton;
import org.homedns.mkh.ui.client.HasGWTButton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.Command;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Abstract panel
 *
 */
@SuppressWarnings( "unchecked" )
public abstract class GenericPanel extends Panel implements ViewAccess, HasButton, HasGWTButton {
	private static final Logger LOG = Logger.getLogger( GenericPanel.class.getName( ) );  

	private String sTag;	
	private Access access;
	private ComponentBuilder builder;
	private String sViewName;
	private Map< String, Panel > registry;
	private List< ViewTag > tagList;
	private Map< String, View > viewRegistry;
	private Id parentId;
	private String sParentPK;

	public GenericPanel( ) {
		registry = new HashMap< String, Panel >( );
		tagList = new ArrayList< ViewTag >( );
		viewRegistry = new HashMap< String, View >( );
	}

	/**
	 * Returns nested panel by it's key
	 * 
	 * @return the panel
	 */
	protected Panel getPanel( String sKey ) {
		return( registry.get( sKey ) );
	}

	/**
	 * Puts nested panel to the panel's registry
	 * 
	 * @param sKey
	 *            the key
	 * @param panel
	 *            the nested panel to put
	 */
	protected void putPanel( final String sKey, Panel panel ) {
		registry.put( sKey, panel );
	}

	/**
	 * Inits panel
	 */
	protected void init( ) {
	}
	
	/**
	 * Gives possibility to make some actions descendants on view init
	 * 
	 * @param sViewName
	 *            the view name
	 * @param cfg
	 *            the configuration object
	 */
	public void onInit( String sViewName, Config cfg ) {
	}

	/**
	 * Returns parent identification object
	 * 
	 * @return the parent identification object
	 */
	public Id getParentId( ) {
		return parentId;
	}

	/**
	 * Sets parent identification object
	 * 
	 * @param parentId the parent identification object to set
	 */
	public void setParentId( Id parentId ) {
		this.parentId = parentId;
	}

	/**
	 * Returns parent primary key name
	 * 
	 * @return the parent primary key name
	 */
	public String getParentPK( ) {
		return sParentPK;
	}

	/**
	 * Sets parent primary key name
	 * 
	 * @param sParentPK the parent primary key name to set
	 */
	public void setParentPK( String sParentPK ) {
		this.sParentPK = sParentPK;
	}

	/**
	 * Executes after view initialization
	 * 
	 * @param view the view which has been initialized
	 */
	protected void onAfterInit( View view ) {
	}

	/**
	 * Returns components builder
	 * 
	 * @return the builder
	 */
	public ComponentBuilder getBuilder( ) {
		return( builder );
	}

	/**
	 * Sets component builder
	 * 
	 * @param builder the component builder to set
	 */
	public void setBuilder( ComponentBuilder builder ) {
		this.builder = builder;
	}

	/**
	 * Returns view name
	 * 
	 * @return the view name
	 */
	public String getViewName( ) {
		return( sViewName );
	}

	/**
	 * Sets view name
	 * 
	 * @param sViewName the view name to set
	 */
	public void setViewName( String sViewName ) {
		this.sViewName = sViewName;
	}

	/**
	 * Returns views tags list
	 * 
	 * @return the views tags list
	 */
	public List< ViewTag > getViewTag( ) {
		return( tagList );
	}

	/**
	 * Adds view tag
	 * 
	 * @param tag the view tag to set
	 */
	public void addViewTag( ViewTag tag ) {
		tagList.add( tag );
	}

	/**
	 * Adds view tag
	 * 
	 * @param sName
	 *            the tag name
	 * @param type
	 *            the tag class type
	 */
	public void addViewTag( String sName, Class< ? > type ) {
		tagList.add( new ViewTag( sName, type ) );
	}

	/**
	 * Creates view
	 * 
	 * @param type
	 *            the view class type
	 * @param sName
	 *            the view name
	 * @param sPanelName
	 *            the panel name in which view will be added
	 */
	public < T extends Panel & View > T createView(
		Class< T > type,
		String sName,
		final String sPanelName
	) {
		final T view = ( T )getBuilder( ).getComponent( type, new Id( sName ) );
		putView( type, view );
		view.setAfterInitCommand(
			new Command( ) {
				public void execute( ) {
					onAfterInit( view );
					getPanel( sPanelName ).add( view );
					LOG.config( "adds " + view.getClass( ).getName( ) + " to the " + sPanelName );
					addPanels( );
				}
			}
		);
		return( view );
	}

	/**
	 * Creates view
	 * 
	 * @param type
	 *            the view class type
	 * @param id
	 *            the existing identification object
	 * @param sPanelName
	 *            the panel name in which view will be added
	 */
	public < T extends Panel & View > T createView(
		Class< T > type,
		Id id,
		final String sPanelName
	) {
		final T view = ( T )getBuilder( ).getComponent( type, id );
		putView( type, view );
		view.setAfterInitCommand(
			new Command( ) {
				public void execute( ) {
					onAfterInit( view );
					getPanel( sPanelName ).add( view );
					LOG.config( "adds " + view.getClass( ).getName( ) + " to the " + sPanelName );
					addPanels( );
				}
			}
		);
		return( view );
	}

	/**
	 * Creates child view and binds it to the master grid
	 * 
	 * @param type
	 *            the child view class type
	 * @param sName
	 *            the child view name
	 * @param sPanelName
	 *            the panel name in which view will be added
	 * @param masterId
	 *            the master grid identification object
	 * @param sMasterPK
	 *            the master grid primary key name
	 */
	public < T extends Panel & View > T createChildView(
		Class< T > type,
		String sName, 
		final String sPanelName,
		Id masterId, 
		String sMasterPK 
	) {
		final T view = ( T )getBuilder( ).getChildComponent( 
			type, 
			new Id( sName ), 
			masterId, 
			sMasterPK 
		);
		putView( type, view );
		view.setAfterInitCommand(
			new Command( ) {
				public void execute( ) {
					onAfterInit( view );
					getPanel( sPanelName ).add( view );
					addPanels( );
				}
			}
		);
		return( view );
	}

	/**
	 * Returns view by it's name
	 * 
	 * @param sKey the view name
	 * @param type the view class type 
	 * 
	 * @return the view
	 */
	public View getView( String sKey, Class< ? > type ) {
		sKey = sKey + type.getName( );
		LOG.config( "getView: " + sKey );
		return( viewRegistry.get( sKey ) );
	}
	
	/**
	 * Returns view by key, where key is view name + view class name
	 * 
	 * @param sKey the view key
	 * 
	 * @return the view
	 */
	public View getView( String sKey ) {
		LOG.config( "getView: " + sKey );
		return( viewRegistry.get( sKey ) );
	}

	/**
	 * Puts view to the view registry
	 * 
	 * @param view the view
	 */
	public void putView( Class< ? > type, View view ) {
		String sKey = view.getID( ).getName( ) + type.getName( );
		LOG.config( "putView: " + sKey );
		viewRegistry.put( sKey, view );		
	}

	/**
	 * @see org.homedns.mkh.ui.client.HasButton#onButtonClick(com.gwtext.client.widgets.Button, com.gwtext.client.core.EventObject)
	 */
	public void onButtonClick( Button button, EventObject e ) {
	}

	/**
	 * @see org.homedns.mkh.ui.client.HasGWTButton#onButtonClick(com.google.gwt.user.client.ui.Button, com.google.gwt.event.dom.client.ClickEvent)
	 */
	public void onButtonClick( com.google.gwt.user.client.ui.Button button, ClickEvent e ) {
	}
	
	/**
	 * Adds nested panels to the wrapper panel 
	 */
	public void addPanels( ) {
		onAfterAddPanels( );		//???
	}

	/**
	 * Custom actions after actually panels addition
	 */
	protected void onAfterAddPanels( ) {	
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.ViewAccess#setAccess(org.homedns.mkh.dataservice.client.view.ViewAccess.Access)
	 */
	@Override
	public void setAccess( Access access ) {
		this.access = access;
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.ViewAccess#getAccess()
	 */
	@Override
	public Access getAccess( ) {
		return( access );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.ViewAccess#getTag()
	 */
	@Override
	public String getTag( ) {
		return( sTag );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.ViewAccess#setTag(java.lang.String)
	 */
	@Override
	public void setTag( String sTag ) {
		this.sTag = sTag;		
		SetAccessEvent.fire( this );							
	}
	
	/**
	 * Reloads view
	 * 
	 * @param sName
	 *            the view name
	 * @param type
	 *            the view class type
	 */
	public void reload( String sName, Class< ? > type ) {
		View view = getView( sName, type );
		view.reload( );
	}
	
	/**
	 * Adds GWT button with specified caption to the panel 
	 * 
	 * @param sCaption the button caption
	 */
	public void addButton( String sCaption ) {
		com.google.gwt.user.client.ui.Button btn = new com.google.gwt.user.client.ui.Button( sCaption );
		btn.addClickHandler( new ButtonClickHandler( this, btn ) );
		add( btn );
	}
}
