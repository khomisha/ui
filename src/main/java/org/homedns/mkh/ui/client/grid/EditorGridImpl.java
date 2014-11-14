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

import java.util.ArrayList;
import java.util.Arrays;
import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.dataservice.shared.CUDRequest;
import org.homedns.mkh.dataservice.shared.DeleteRequest;
import org.homedns.mkh.dataservice.shared.InsertRequest;
import org.homedns.mkh.dataservice.shared.Request;
import org.homedns.mkh.dataservice.shared.Response;
import org.homedns.mkh.dataservice.shared.UpdateRequest;
import org.homedns.mkh.ui.client.cache.RecordUtil;
import org.homedns.mkh.ui.client.event.SelectRowEvent;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.Record.Operation;
import com.gwtext.client.data.event.StoreListenerAdapter;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.grid.BaseColumnConfig;
import com.gwtext.client.widgets.grid.CellSelectionModel;
import com.gwtext.client.widgets.grid.EditorGridPanel;
import com.gwtext.client.widgets.grid.GridEditor;
import com.gwtext.client.widgets.grid.event.CellSelectionModelListenerAdapter;

/**
 * Implements editor grid functionality
 *
 */
public class EditorGridImpl extends GridImpl {
	private ArrayList< Record > _removedRecords;
	private ArrayList< Record > _addedRecords;
	private ArrayList< Record > _updatedRecords;

	/**
	 * @param cfg
	 *            the grid configuration object
	 */
	public EditorGridImpl( GridConfig cfg ) {
		super( cfg );
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.GridImpl#config()
	 */
	@Override
	protected void config( ) {
		super.config( );
		final EditorGridPanel grid = ( EditorGridPanel )getGrid( );
		grid.setClicksToEdit( 1 );
		grid.getCellSelectionModel( ).addListener( 
			new CellSelectionModelListenerAdapter( ) {
				@Override
				public void onSelectionChange( 
					CellSelectionModel sm,
					Record record, 
					int[] rowIndexColIndex 
				) {
					SelectRowEvent.fire( ( ( View )grid ).getID( ), record );
				}
			}
		);
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.GridImpl#selectRow(int)
	 */
	@Override
	protected void selectRow( int iRow ) {
		( ( EditorGrid )getGrid( ) ).getCellSelectionModel( ).select( iRow, 1 );
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.AbstractGridImpl#getColConfigs()
	 */
	@Override
	protected BaseColumnConfig[] getColConfigs( ) {
		for( BaseColumnConfig colConfig : super.getColConfigs( ) ) {
			if( colConfig instanceof ColConfig ) {
				ColConfig cc = ( ColConfig )colConfig;
				if( !cc.getHidden( ) ) {
					Field field = cc.getField( );
					cc.setEditor( new GridEditor( field ) );
				}
			}
		}
		return( super.getColConfigs( ) );
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.GridImpl#init()
	 */
	@Override
	protected void init( ) {
		super.init( );
		Store store = getGrid( ).getStore( );
		store.addStoreListener( 
			new StoreListenerAdapter( ) {
				@Override
				public void onAdd( Store store, Record[] records, int index ) {
					_addedRecords.addAll( Arrays.asList( records ) );
				}

				@Override
				public void onRemove( Store store, Record record, int index ) {
					if( _addedRecords.contains( record ) ) {
						_addedRecords.remove( record );
					} else {
						_removedRecords.add( record );
					}
				}

				@Override
				public void onUpdate( 
					Store store, Record record, Operation operation 
				) {
					if( 
						!_addedRecords.contains( record ) && 
						!_updatedRecords.contains( record ) 
					) {
						_updatedRecords.add( record );
					}					
				}
			}
		);
	}
	
	/**
	 * @see org.homedns.mkh.dataservice.client.Response#getSavingData()
	 */
	protected void getSavingData( Request request ) {
		String s = null;
		if( request instanceof InsertRequest ) {
			s = RecordUtil.getJsonData( _addedRecords );
		} else if( request instanceof DeleteRequest ) {
			s = RecordUtil.getJsonData( _removedRecords );			
		} else if( request instanceof UpdateRequest ) {
			s = RecordUtil.getJsonData( _updatedRecords );
		}
		( ( CUDRequest )request ).setData( s );		
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.AbstractGridImpl#refresh(org.homedns.mkh.dataservice.shared.Response)
	 */
	@Override
	protected void refresh( Response data ) {
		super.refresh( data );
		_addedRecords.clear( );
		_removedRecords.clear( );
		_updatedRecords.clear( );
	}
}
