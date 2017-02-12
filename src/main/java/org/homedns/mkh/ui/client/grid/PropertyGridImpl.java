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

package org.homedns.mkh.ui.client.grid;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.homedns.mkh.dataservice.client.Type;
import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.dataservice.shared.Util;
import org.homedns.mkh.ui.client.WidgetDesc;
import org.homedns.mkh.ui.client.cache.WidgetStore;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Command;
import com.gwtext.client.core.NameValuePair;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.event.StoreListenerAdapter;
import com.gwtext.client.util.JavaScriptObjectHelper;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.grid.GridEditor;
import com.gwtext.client.widgets.grid.PropertyGridPanel;
import com.gwtext.client.widgets.grid.event.PropertyGridPanelListener;

/**
 * Implements property grid functionality
 *
 */
public class PropertyGridImpl extends AbstractGridImpl {
	private static final Logger LOG = Logger.getLogger( PropertyGridImpl.class.getName( ) );

	private Map< String, GridEditor > _customEditors = new HashMap< String, GridEditor >( );
	private WidgetDesc _desc;
	private Record _mainRecord;
	private JavaScriptObject _label2Name;

	/**
	 * @param cfg
	 *            the grid configuration object
	 */
	public PropertyGridImpl( GridConfig cfg ) {
		super( cfg );
		_label2Name = JavaScriptObjectHelper.createObject( );
	}

	/**
	 * Returns the property grid main record
	 * 
	 * @return the main record
	 */
	public Record getMainRecord( ) {
		return( _mainRecord );
	}
	
	/**
	 * @see org.homedns.mkh.ui.client.grid.AbstractGridImpl#config()
	 */
	@Override
	protected void config( ) {
		super.config( );
		final PropertyGrid propertyGrid = ( PropertyGrid )getGrid( );
		WidgetStore cache = ( WidgetStore )getCache( );
		setSource( cache.getAt( 0 ) );
		cache.addStoreListener(  
			new StoreListenerAdapter( ) {
				public void onLoad( Store store, Record[] records ) {
					if( records.length > 0 ) {
						setSource( records[ 0 ] );
					}
				}
			}
		);
		propertyGrid.setClicksToEdit( 1 );
		propertyGrid.addPropertyGridPanelListener( 
			new PropertyGridPanelListener( ) {
				@Override
				public boolean doBeforePropertyChange(
					PropertyGridPanel source, String recordID, Object value, Object oldValue 
				) {
					return( true );
				}

				@Override
				public void onPropertyChange( 
					PropertyGridPanel source, String recordID, Object value, Object oldValue 
				) {
					try {
						Record record = source.getStore( ).getById( recordID );
						syncChanges( record, _mainRecord, _label2Name );
					}
					catch( Exception e ) {
						LOG.log( Level.SEVERE, "onPropertyChange: ", Util.unwrap( e ) );  						
					}
				}
			}
		);
	}

	/**
	 * Sets the record containing the property data to display in the grid, and
	 * this data will automatically be loaded into the grid's store. If the grid
	 * already contains data, this method will replace any existing data.
	 * 
	 * @param record
	 *            the source record
	 */
	protected void setSource( Record record ) {
		_mainRecord = record;
		_desc = ( WidgetDesc )( ( View )getGrid( ) ).getDescription( );
        Field[] fields = _desc.getFields( );
        NameValuePair[] data = new NameValuePair[ fields.length ];
        boolean bNoEditors = _customEditors.isEmpty( );
        for( int iField = 0; iField < fields.length; iField++ ) {
        	String sLabel = fields[ iField ].getFieldLabel( );
        	String sName = fields[ iField ].getName( );
        	put2Map( _label2Name, sLabel, sName );
         	Type type = _desc.getFieldType( sName );
        	if( type == Type.STRING ) {
            	data[ iField ] = new NameValuePair( sLabel, record.getAsString( sName ) );        		
        	} else if( type == Type.BOOLEAN ) {
            	data[ iField ] = new NameValuePair( sLabel, record.getAsBoolean( sName ) );        		
        	} else if( type == Type.TIMESTAMP ) {
            	data[ iField ] = new NameValuePair( sLabel, record.getAsDate( sName ) );        		
        	} else if( type == Type.FLOAT ) {
            	data[ iField ] = new NameValuePair( sLabel, record.getAsFloat( sName ) );        		
        	} else if( type == Type.INT ) {
            	data[ iField ] = new NameValuePair( sLabel, record.getAsInteger( sName ) );        		        		
        	}
        	if( bNoEditors ) {
        		_customEditors.put( sLabel, new GridEditor( fields[ iField ] ) );
        	}
        }
		PropertyGrid grid = ( PropertyGrid )getGrid( );
		grid.setCustomEditors( _customEditors );
        grid.setSource( data );
	}
	
	/**
	 * @see org.homedns.mkh.ui.client.grid.AbstractGridImpl#init()
	 */
	@Override
	protected void init( ) {
		config( );
		Command cmd = getAfterInitCommand( );
		if( cmd != null ) {
			cmd.execute( );
		}
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.AbstractGridImpl#getCondition()
	 */
	@Override
	protected String getCondition( ) {
		return null;
	}
	
	/**
	 * @see org.homedns.mkh.ui.client.grid.AbstractGridImpl#refresh()
	 */
	@Override
	protected void refresh( ) {
		getGrid( ).getView( ).refresh( );
	}

	/**
	 * Synchronized property changes with data in main record
	 * 
	 * @param src
	 *            the source record (property grid record)
	 * @param dst
	 *            the destination record (main record)
	 */
	private native void syncChanges( Record src, Record dst, JavaScriptObject label2Name ) /*-{
		var srcJS = src.@com.gwtext.client.core.JsObject::getJsObj()();
		var dstJS = dst.@com.gwtext.client.core.JsObject::getJsObj()();
		var field = label2Name[srcJS.get("name")];
		var value = srcJS.get("value");
		dstJS.set(field, value);
	}-*/;
	
	/**
	 * Put to map value for specified key
	 * 
	 * @param map
	 *            the target map
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	private native void put2Map( JavaScriptObject map, String key, String value ) /*-{
		map[key] = value;
	}-*/;

	/**
	 * @see org.homedns.mkh.ui.client.grid.AbstractGridImpl#selectRow(int)
	 */
	@Override
	protected void selectRow( int iRow ) {
	}	
}
