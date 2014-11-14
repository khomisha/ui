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

import java.util.HashMap;
import java.util.Map;
import org.homedns.mkh.dataservice.client.Type;
import org.homedns.mkh.dataservice.shared.Id;
import org.homedns.mkh.ui.client.WidgetDesc;
import com.gwtext.client.data.Record;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.event.EditorGridListenerAdapter;

/**
 * Cell editor grid supporting cell edit different data types 
 *
 */
public class CellEditorGrid extends EditorGrid {
	private Map< Type, CellEditor > _editors = new HashMap< Type, CellEditor >( );

	/**
	 * @see org.homedns.mkh.ui.client.grid.EditorGrid
	 */
	public CellEditorGrid( Id id, EditorGridImpl impl ) {
		super( id, impl );
		createEditors( );
		addEditorGridListener( 
			new EditorGridListenerAdapter( ) {
				@Override
				public boolean doBeforeEdit( 
					GridPanel grid,
					Record record, 
					String field, 
					Object value,
					int rowIndex, 
					int colIndex 
				) {
					return( getColumnModel( ).isCellEditable( colIndex, rowIndex ) );
				}
				
			}
		);
	}

	/**
	 * Sets grid editor for column depending on specified data type, thus grid
	 * editor can be changed at runtime for specified grid cell, e.g. onCellClick event
	 * 
	 * @param iCol
	 *            the column index
	 * @param cellEditor
	 *            the cell grid editor @see org.homedns.mkh.ui.client.grid.CellEditor
	 */
	public void setEditor( int iCol, CellEditor cellEditor ) {
		WidgetDesc desc = ( WidgetDesc )getDescription( );
		ColumnConfig colConfig = ( ColumnConfig )desc.getColConfigs( )[ iCol ];
		colConfig.setEditor( cellEditor.getGridEditor( ) );
		colConfig.setRenderer( cellEditor.getRenderer( ) );
	}
	
	/**
	 * Returns cell editor corresponding to specified data type
	 * 
	 * @param sType
	 *            the data type
	 * 
	 * @return the cell editor
	 */
	public CellEditor getCellEditor( String sType ) {
		return( _editors.get( Enum.valueOf( Type.class, sType ) ) );
	}
	
	/**
	 * Creates cell editors
	 */
	private void createEditors( ) {
		_editors.put( Type.STRING, new CellEditor( Type.STRING ) );
		CellEditor ceInt = new CellEditor( Type.INT );
		_editors.put( Type.INT, ceInt );
		_editors.put( Type.LONG, ceInt );
		_editors.put( Type.SHORT, ceInt );
		_editors.put( Type.BYTE, ceInt );
		_editors.put( Type.FLOAT, ceInt );
		_editors.put( Type.DOUBLE, ceInt );
		_editors.put( Type.TIMESTAMP, new CellEditor( Type.TIMESTAMP ) );
		_editors.put( Type.BOOLEAN, new CellEditor( Type.BOOLEAN ) );
	}
}
