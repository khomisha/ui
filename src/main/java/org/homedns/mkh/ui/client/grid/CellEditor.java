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

package org.homedns.mkh.ui.client.grid;

import org.homedns.mkh.dataservice.client.Column;
import org.homedns.mkh.dataservice.client.Type;
import org.homedns.mkh.ui.client.form.FieldFactory;
import org.homedns.mkh.ui.client.form.ListBox;

import com.gwtext.client.data.Record;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.NumberField;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.grid.CellMetadata;
import com.gwtext.client.widgets.grid.GridEditor;
import com.gwtext.client.widgets.grid.Renderer;

/**
 * Cell editor with appropriate renderer
 *
 */
public class CellEditor {
	private GridEditor _gridEditor;
	private Renderer _renderer;
	
	/**
	 * @param type
	 *            the data type
	 */
	public CellEditor( Type type ) {
		setGridEditor( type );
		setRenderer( type );
	}

	/**
	 * @param listBox
	 *            the list box for cell editor
	 */
	public CellEditor( ListBox listBox ) {
		_gridEditor = new GridEditor( listBox );
		_renderer = new DefaultRenderer( );
	}

	/**
	 * Returns cell grid editor
	 * 
	 * @return the cell grid editor
	 */
	public GridEditor getGridEditor( ) {
		return( _gridEditor );
	}
	
	/**
	 * Returns cell renderer
	 * 
	 * @return the cell renderer
	 */
	public Renderer getRenderer( ) {
		return( _renderer );
	}
	
	/**
	 * Sets grid editor corresponding to specified data type
	 * 
	 * @param type
	 *            the data type
	 */
	private void setGridEditor( Type type ) {
		if( type == Type.STRING ) {
			_gridEditor = new GridEditor( new TextField( ) );
		} else if(
			type == Type.INT ||
			type == Type.LONG ||
			type == Type.SHORT ||
			type == Type.BYTE ||
			type == Type.FLOAT ||
			type == Type.DOUBLE
		) {
			_gridEditor = new GridEditor( new NumberField( ) );			
		} else if( type == Type.TIMESTAMP ) {
			DateField dt = new DateField( );
			dt.setFormat( FieldFactory.TIMESTAMP_FMT );
			_gridEditor = new GridEditor( dt );
		} else if( type == Type.BOOLEAN ) {
			_gridEditor = new GridEditor( new Checkbox( ) );					
		}
	}
	
	/**
	 * Sets renderer corresponding to specified data type
	 * 
	 * @param type
	 *            the data type
	 */
	private void setRenderer( Type type ) {
		if( type == Type.TIMESTAMP ) {
			_renderer = new DateRenderer( Column.EDIT_TS );
		} else if( type == Type.BOOLEAN ) {
			_renderer = new CheckboxRenderer( );
		} else {
			_renderer = new DefaultRenderer( );			
		}
	}

    /**
     * Default renderer object
     *
     */
    private class DefaultRenderer implements Renderer {
		/**
		 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object, com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
		 */
		@Override
		public String render( 
			Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum, Store store 
		) {
			return( value == null ? "" : String.valueOf( value ) );
		}
    }
}
