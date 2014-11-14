/*
 * Copyright 2013 Mikhail Khodonov
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.homedns.mkh.dataservice.client.Column;
import org.homedns.mkh.dataservice.client.DataBufferDesc;
import org.homedns.mkh.dataservice.client.Type;
import org.homedns.mkh.dataservice.client.view.ViewDesc;
import org.homedns.mkh.ui.client.cache.FieldDefFactory;
import org.homedns.mkh.ui.client.form.FieldFactory;
import org.homedns.mkh.ui.client.grid.ColumnConfigFactory;
import com.gwtext.client.data.BooleanFieldDef;
import com.gwtext.client.data.DateFieldDef;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.FloatFieldDef;
import com.gwtext.client.data.IntegerFieldDef;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.grid.BaseColumnConfig;
import com.gwtext.client.widgets.grid.RowNumberingColumnConfig;

/**
 * Widget description object
 *
 */
public class WidgetDesc implements ViewDesc {
	private DataBufferDesc _desc;
	private Field[] _fields;
	private BaseColumnConfig[] _colConfigs;
	private FieldDef[] _fieldDefs;
	private Map< Column, Field > _col2Field = new HashMap< Column, Field >( );
	
	public WidgetDesc( ) {
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.ViewDesc#getDataBufferDesc()
	 */
	@Override
	public DataBufferDesc getDataBufferDesc( ) {
		return( _desc );
	}

	/**
	 * Returns fields
	 * 
	 * @return the fields
	 */
	public Field[] getFields( ) {
		return( _fields );
	}

	/**
	 * Returns columns configurations
	 * 
	 * @return the columns configurations
	 */
	public BaseColumnConfig[] getColConfigs( ) {
		return( _colConfigs );
	}

	/**
	 * Returns fields definitions
	 * 
	 * @return the fields definitions
	 */
	public FieldDef[] getFieldDefs( ) {
		return( _fieldDefs );
	}
	
	/**
	 * Returns column to field map
	 * 
	 * @return the column to field map
	 */
	public Map< Column, Field > getCol2Field( ) {
		return( _col2Field );
	}
	
	/**
	 * Returns field definition object by column name
	 * 
	 * @param sColName the column name
	 * 
	 * @return the field definition
	 */
	public FieldDef getFieldDef( String sColName ) {
		return( getFieldDefs( )[ getColIndex( sColName ) ] );
	}
	
	/**
	 * {@link org.homedns.mkh.dataservice.client.DataBufferDesc#getColIndex(String)}
	 */
	public int getColIndex( String sColName ) {
		return( _desc.getColIndex( sColName ) );
	}

	/**
	 * Returns record's field type
	 * 
	 * @param sField
	 *            the record field
	 * 
	 * @return the field type
	 */
	public Type getFieldType( String sField ) {
		FieldDef fieldDef = getFieldDef( sField );
		Type type = null;
		if( fieldDef instanceof StringFieldDef ) {
			type = Type.STRING;
		} else if( fieldDef instanceof BooleanFieldDef ) {
			type = Type.BOOLEAN;
		} else if( fieldDef instanceof DateFieldDef ) {
			type = Type.TIMESTAMP;
		} else if( fieldDef instanceof FloatFieldDef ) {
			type = Type.FLOAT;
		} else if( fieldDef instanceof IntegerFieldDef ) {
			type = Type.INT;
		}
		return( type );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.ViewDesc#setDataBufferDesc(org.homedns.mkh.dataservice.client.DataBufferDesc)
	 */
	@Override
	public void setDataBufferDesc( DataBufferDesc desc ) {
		_desc = desc;
		init( );
	}
	
	/**
	 * Inits widget implementation specific objects
	 */
	protected void init( ) {
		List< Field > fields = new ArrayList< Field >( );
		List< BaseColumnConfig > colConfigs = new ArrayList< BaseColumnConfig >( );
		List< FieldDef > fieldDefs = new ArrayList< FieldDef >( );
		colConfigs.add( new RowNumberingColumnConfig( ) );
		for( Column col : _desc.getColumns( ) ) {
			Field field = FieldFactory.create( col );
			if( field != null ) {
				fields.add( field );
				_col2Field.put( col, field );
			}
			colConfigs.add( ColumnConfigFactory.create( col, field ) );
			fieldDefs.add( FieldDefFactory.create( col ) );
		}
		_fields = fields.toArray( new Field[ fields.size( ) ] );
		_colConfigs = colConfigs.toArray( new BaseColumnConfig[ colConfigs.size( ) ] );
		_fieldDefs = fieldDefs.toArray( new FieldDef[ fieldDefs.size( ) ] );		
	}
}
