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
import java.util.List;

import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.dataservice.shared.CUDRequest;
import org.homedns.mkh.dataservice.shared.DeleteRequest;
import org.homedns.mkh.dataservice.shared.InsertRequest;
import org.homedns.mkh.dataservice.shared.Request;
import org.homedns.mkh.dataservice.shared.UpdateRequest;
import org.homedns.mkh.ui.client.cache.RecordUtil;
import org.homedns.mkh.ui.client.cache.WidgetStore;
import org.homedns.mkh.ui.client.event.SelectRowEvent;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.Record.Operation;
import com.gwtext.client.data.event.StoreListenerAdapter;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.grid.BaseColumnConfig;
import com.gwtext.client.widgets.grid.EditorGridPanel;
import com.gwtext.client.widgets.grid.GridEditor;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.event.EditorGridListenerAdapter;
import com.gwtext.client.widgets.grid.event.GridCellListenerAdapter;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;

/**
 * Implements editor grid functionality
 *
 */
public class EditorGridImpl extends GridImpl {	
	private List< Record > removedRecords;
	private List< Record > addedRecords;
	private List< Record > updatedRecords;
	private List< String > readonlyCols;

	/**
	 * @param cfg
	 *            the grid configuration object
	 */
	public EditorGridImpl( EditorGridConfig cfg ) {
		super( cfg );
		this.removedRecords = new ArrayList< Record >( );
		this.addedRecords = new ArrayList< Record >( );
		this.updatedRecords = new ArrayList< Record >( );
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.GridImpl#config()
	 */
	@Override
	protected void config( ) {
		super.config( );
		final EditorGridPanel grid = ( EditorGridPanel )getGrid( );
		grid.setClicksToEdit( 1 );
		EditorGridConfig cfg = ( EditorGridConfig )getConfiguration( );
		readonlyCols = Arrays.asList( ( String[] )cfg.getAttribute( EditorGridConfig.READONLY_COLS ) );
		grid.addEditorGridListener( 
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
					return( !readonlyCols.contains( field ) );
				}
				
			}
		);
        grid.addGridCellListener(
        	new GridCellListenerAdapter() {
        		public void onCellClick( GridPanel grid, int rowIndex, int colIndex, EventObject e ) {
        			if( e.getTarget( ".checkbox", 1 ) != null ) {
        				Record record = grid.getStore( ).getAt( rowIndex );
        				String sDataIndex = grid.getColumnModel( ).getDataIndex( colIndex );
        				record.set( sDataIndex, !record.getAsBoolean( sDataIndex ) );
        			}
        		}
        	}
        );
        grid.addGridRowListener( 
        	new GridRowListenerAdapter( ) {
				@Override
				public void onRowClick( GridPanel grid, int rowIndex, EventObject e ) {
					setSelectedRow( rowIndex );
					SelectRowEvent.fire( 
						( ( View )grid ).getID( ), 
						grid.getStore( ).getAt( rowIndex ) 
					);
				}
        	}
        );
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.EditorGrid#getRemovedRecords()
	 */
	protected List< Record > getRemovedRecords( ) {
		return removedRecords;
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.EditorGrid#getAddedRecords()
	 */
	protected List< Record > getAddedRecords( ) {
		return addedRecords;
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.EditorGrid#getUpdatedRecords()
	 */
	protected List< Record > getUpdatedRecords( ) {
		return updatedRecords;
	}
	
	/**
	 * @see org.homedns.mkh.ui.client.grid.EditorGrid#setRemovedRecords(java.util.List)
	 */
	public void setRemovedRecords( List< Record > removedRecords ) {
		this.removedRecords = removedRecords;
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.EditorGrid#setAddedRecords(java.util.List)
	 */
	public void setAddedRecords( List< Record > addedRecords ) {
		this.addedRecords = addedRecords;
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.EditorGrid#setUpdatedRecords(java.util.List)
	 */
	public void setUpdatedRecords( List< Record > updatedRecords ) {
		this.updatedRecords = updatedRecords;
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.GridImpl#selectRow(int)
	 */
	@Override
	protected void selectRow( int iRow ) {
		WidgetStore store = ( WidgetStore )getCache( );
		if( store.getRowCount( ) < 1 ) {
			return;
		}
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
					if( !"checkbox".equals( field.getXType( ) ) ) {
						cc.setEditor( new GridEditor( field ) );
					}
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
					addedRecords.addAll( Arrays.asList( records ) );
				}

				@Override
				public void onRemove( Store store, Record record, int index ) {
					if( addedRecords.contains( record ) ) {
						addedRecords.remove( record );
					} else {
						removedRecords.add( record );
					}
				}

				@Override
				public void onUpdate( 
					Store store, Record record, Operation operation 
				) {
					if( 
						!addedRecords.contains( record ) && 
						!updatedRecords.contains( record ) 
					) {
						updatedRecords.add( record );
					}					
				}
			}
		);
	}
	
	/**
	 * Returns true if grid content has been changed and false otherwise
	 * 
	 * @return true or false
	 */
	public boolean isDirty( ) {
		return( !( updatedRecords.isEmpty( ) && removedRecords.isEmpty( ) && addedRecords.isEmpty( ) ) );
	}
	
	/**
	 * @see org.homedns.mkh.dataservice.client.Response#getSavingData(org.homedns.mkh.dataservice.shared.Request)
	 */
	protected void getSavingData( Request request ) {
		String s = null;
		if( request instanceof InsertRequest ) {
			s = RecordUtil.getJsonData( addedRecords );
		} else if( request instanceof DeleteRequest ) {
			s = RecordUtil.getJsonData( removedRecords );			
		} else if( request instanceof UpdateRequest ) {
			s = RecordUtil.getJsonData( updatedRecords );
		}
		( ( CUDRequest )request ).setData( s );		
	}

	/**
	 * @see org.homedns.mkh.ui.client.grid.AbstractGridImpl#refresh()
	 */
	@Override
	protected void refresh( ) {
		super.refresh( );
		this.addedRecords.clear( );
		this.removedRecords.clear( );
		this.updatedRecords.clear( );
	}
	
	/**
	 * {@link org.homedns.mkh.ui.client.grid.EditorGrid}
	 */
	protected void removeFromRecords( int iType, Record record ) {
		if( iType == EditorGrid.ADDED ) {
			addedRecords.remove( record );
		}
		if( iType == EditorGrid.UPDATED ) {
			updatedRecords.remove( record );			
		}
		if( iType == EditorGrid.REMOVED ) {
			removedRecords.remove( record );			
		}
	}
}
