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

package org.homedns.mkh.ui.client.form;

import org.homedns.mkh.dataservice.client.AbstractEntryPoint;
import org.homedns.mkh.dataservice.client.Column;
import org.homedns.mkh.dataservice.client.Value;
import org.homedns.mkh.ui.client.UIMessages;
import com.gwtext.client.data.MemoryProxy;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.form.ComboBox;

/**
 * List box control
 *
 */
public class ListBox extends ComboBox {
	private static final UIMessages MESSAGES = ( UIMessages )AbstractEntryPoint.getMessages( );
	
	private String sDisplayCol, sDataCol;
	
	/**
	 * @param col
	 *            the column
	 * @param iWidth
	 *            the field width
	 */
	public ListBox( Column col, int iWidth ) {
		super( 
			col.isRequired( ) ? col.getCaption( ) + "*" : col.getCaption( ), 
			col.getName( ), 
			iWidth 
		);
		config( col );
	}
	
	/**
	 * Returns the display column name
	 * 
	 * @return the display column name
	 */
	public String getDisplayCol( ) {
		return( sDisplayCol );
	}


	/**
	 * Returns the data column name
	 * 
	 * @return the data column name
	 */
	public String getDataCol( ) {
		return( sDataCol );
	}

	/**
	 * Configures list box for specified column 
	 * 
	 * @param col the source column
	 */
	protected void config( final Column col ) {
		sDisplayCol = col.getDddbDisplayCol( );
		sDataCol = col.getName( );
		setDisplayField( sDisplayCol );
		setValueField( sDataCol );
		Store store = createStore( sDisplayCol, sDataCol, col.getValues( ) );
		setStore( store );
		setTypeAhead( true );
		setMode( ComboBox.LOCAL );
		setTriggerAction( ComboBox.ALL );
		setSelectOnFocus( true );
		setEditable( false );
		setForceSelection( true );
		setValueNotFoundText( MESSAGES.noValue( ) );
		setResizable( true );
		setMinListWidth( 400 );
		setListWidth( getWidth( ) );
	}

	/**
	 * Reloads dddb column store with new data.
	 * 
	 * @param aoData
	 *            the 2D array with new data
	 */
	protected void reload( Object[][] aoData ) {
		getStore( ).setDataProxy( new MemoryProxy( aoData ) );
		getStore( ).load( );
	}

	/**
	 * Creates store for column with ddlb or dddb style.
	 * 
	 * @param sDisplayCol
	 *            the display column name
	 * @param sDataCol
	 *            the data column name
	 * @param data
	 *            the data to store
	 * 
	 * @return store
	 */
	private Store createStore( String sDisplayCol, String sDataCol, Value[] data ) {
		// so many amazing things in gwtext, general purpose Store
		// doesn't work with combobox, only SimpleStore. All fields values are treated as String!
		int iRow = 0;
		String[][] a = new String[ data.length ][ 2 ];
		for( Value value : data ) {
			a[ iRow ][ 0 ] = value.getDisplayValue( );
			a[ iRow ][ 1 ] = value.getDataValue( );
			iRow++;
		}
		SimpleStore store = new SimpleStore( new String[] { sDisplayCol, sDataCol }, a );
		store.load( );
		return( store );
	}
}
