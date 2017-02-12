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

package org.homedns.mkh.ui.client.cache;

import java.util.Date;
import java.util.logging.Logger;
import org.homedns.mkh.dataservice.client.event.CacheLoadedEvent;
import org.homedns.mkh.dataservice.client.view.ViewCache;
import org.homedns.mkh.dataservice.client.view.ViewDesc;
import org.homedns.mkh.dataservice.shared.Response;
import org.homedns.mkh.dataservice.shared.Id;
import org.homedns.mkh.dataservice.shared.ReturnValue;
import org.homedns.mkh.ui.client.WidgetDesc;
import com.google.gwt.core.client.JavaScriptObject;
import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.BooleanFieldDef;
import com.gwtext.client.data.DataProxy;
import com.gwtext.client.data.DateFieldDef;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.FloatFieldDef;
import com.gwtext.client.data.IntegerFieldDef;
import com.gwtext.client.data.ObjectFieldDef;
import com.gwtext.client.data.Reader;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.data.event.StoreListenerAdapter;
import com.gwtext.client.util.JSON;

/**
 * Stores widget data
 *
 */
public class WidgetStore extends Store implements ViewCache {
	private static final Logger LOG = Logger.getLogger( WidgetStore.class.getName( ) );  

	private int _iRowCount = 0;
	private Response _data;
	private Id _id;
	private Reader _reader;
	private WidgetDesc _desc;

	public WidgetStore( ) {
	}

	/**
	 * Creates custom memory proxy
	 * 
	 * @param data
	 *            the data as javascript object
	 *            
	 * @return custom memory proxy
	 */
	public DataProxy createProxy( JavaScriptObject data ) {
		return( new CustomMemoryProxy( data ) );			
	}
	
	/**
	* Creates dummy record
	*
	* @return dummy record
	*/
	public Record createRecord( ) {
		RecordDef rd = _reader.getRecordDef( );
		return( rd.createRecord( new Object[ rd.getFields( ).length ] ) );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.ViewCache#getData()
	 */
	@Override
	public Response getData( ) {
		return( _data );
	}
	
	/**
	 * Returns store content as json formatted string
	 * 
	 * @return the json data string
	 */
	public String getJsonData( ) {
		return( RecordUtil.getJsonData( getRecords( ) ) );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.ViewCache#getID()
	 */
	@Override
	public Id getID( ) {
		return( _id );
	}

	/**
	 * Returns record field value
	 * 
	 * @param sField
	 *            the field name
	 * @param record
	 *            the record
	 * 
	 * @return the record field value
	 */
	public Object getFieldValue( String sField, Record record ) {
		FieldDef fieldDef = _desc.getFieldDef( sField );
		Object value = null;
		if( fieldDef instanceof StringFieldDef ) {
			value = record.getAsString( sField );
		} else if( fieldDef instanceof BooleanFieldDef ) {
			value = record.getAsBoolean( sField );
		} else if( fieldDef instanceof DateFieldDef ) {
			value = record.getAsDate( sField );
		} else if( fieldDef instanceof FloatFieldDef ) {
			value = record.getAsFloat( sField );
		} else if( fieldDef instanceof IntegerFieldDef ) {
			value = record.getAsInteger( sField );
		} else if( fieldDef instanceof ObjectFieldDef ) {
			value = record.getAsObject( sField );
		}
		return( value );
	}	
	
	/**
	 * Sets value for specified field in the specified record
	 * 
	 * @param record
	 *            the target record
	 * @param sField
	 *            the target field
	 * @param value
	 *            the value to set
	 */
	public void setFieldValue( Record record, String sField, Object value ) {
		FieldDef fieldDef = _desc.getFieldDef( sField );
    	if( fieldDef instanceof StringFieldDef ) {
    		record.set( sField, String.valueOf( value ) );
    	} else if( fieldDef instanceof BooleanFieldDef ) {
    		record.set( sField, ( ( Boolean )value ).booleanValue( ) );
    	} else if( fieldDef instanceof DateFieldDef ) {
    		record.set( sField, ( Date )value );
    	} else if( fieldDef instanceof FloatFieldDef ) {
    		record.set( sField, ( ( Float )value ).floatValue( ) );
    	} else if( fieldDef instanceof IntegerFieldDef ) {
    		record.set( sField, ( ( Integer )value ).intValue( ) );
    	} else if( fieldDef instanceof ObjectFieldDef ) {
    		record.set( sField, value );
    	}
	}
	
	/**
	 * Returns reader 
	 * @see com.gwtext.client.data.Reader
	 * 
	 * @return the reader
	 */
	public Reader getReader( ) {
		return( _reader );
	}
	
	/**
	 * Returns total rows count
	 * 
	 * @return the total rows count
	 */
	public int getRowCount( ) {
		return( _iRowCount );
	}

	/**
	 * Sets row count
	 * 
	 * @param iRowCount the row count to set
	 */
	public void setRowCount( Integer iRowCount ) {
		_iRowCount = iRowCount;
	}
	
	/**
	 * Reloads the Record cache from the configured Proxy using the configured
	 * Reader and the options from the last load operation performed.
	 * 
	 * @param data
	 *            the data as javascript object to load
	 */
	protected void reload( JavaScriptObject data ) {
		setDataProxy( createProxy( data ) );
		load( );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.ViewCache#setData(org.homedns.mkh.dataservice.shared.Response)
	 */
	@Override
	public void setData( Response data ) {
		_data = data;
		setRowCount( data.getRowCount( ) );
		String sData = data.getJsonData( );
		LOG.config( "setData: " + _desc.getDataBufferDesc( ).getName( ) + ": " + sData );
		reload( JSON.decode( sData ) );
	}
	
	/**
	 * @see org.homedns.mkh.dataservice.client.view.ViewCache#setID(org.homedns.mkh.dataservice.shared.Id)
	 */
	@Override
	public void setID( Id id ) {
		_id = id;
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.ViewCache#getViewDesc()
	 */
	@Override
	public ViewDesc getViewDesc( ) {
		return( _desc );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.ViewCache#setViewDesc(org.homedns.mkh.dataservice.client.view.ViewDesc)
	 */
	@Override
	public void setViewDesc( ViewDesc desc ) {
		if( desc instanceof WidgetDesc ) {
			_desc = ( WidgetDesc )desc;
		}
		if( _desc != null ) {
			init( );
		}
	}
	
	/**
	 * Returns record as string at specified index
	 * 
	 * @param iIndex the record index
	 * 
	 * @return the record as string
	 */
	public String record2String( int iIndex ) {
		return( RecordUtil.record2String( getAt( iIndex ) ) );
	}
	
	/**
	 * Finds record index by primary key value (surrogate key)
	 * 
	 * @param pkValue
	 *            the return value object contains primary key value
	 * 
	 * @return the record index or -1
	 */
	public int getRecordByPK( ReturnValue pkValue ) {
		if( pkValue.isEmpty( ) ) {
			return( -1 );
		}
		return( getRecordByPK( pkValue.get( 0 ) ) );
	}
	
	/**
	 * Finds record index by primary key value (surrogate key)
	 * 
	 * @param sPKValue
	 *            the primary key value
	 * 
	 * @return the record index or -1
	 */
	public int getRecordByPK( String sPKValue ) {
		if( "".equals( sPKValue ) || sPKValue == null ) {
			return( -1 );
		}
		String pk = _desc.getDataBufferDesc( ).getTable( ).getKey( );
		int iIndex = find( pk, sPKValue, 0, true, false );
		return( iIndex );
	}

	/**
	 * Inits widget cache implementation specific objects. View description
	 * object is already defined
	 */
	protected void init( ) {
		_reader = new ArrayReader( new RecordDef( _desc.getFieldDefs( ) ) );
		setReader( _reader );
		addStoreListener(
			new StoreListenerAdapter( ) {
				public void onLoad( Store store, Record[] records ) {
					CacheLoadedEvent.fire( getID( ) );
			    }
			}
		);
	}
}
