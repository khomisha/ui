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

import java.util.Arrays;
import java.util.List;
import org.homedns.mkh.dataservice.client.event.RegisterViewEvent;
import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.dataservice.client.view.ViewCache;
import org.homedns.mkh.dataservice.client.view.ViewDesc;
import org.homedns.mkh.dataservice.shared.Request;
import org.homedns.mkh.dataservice.shared.CUDRequest;
import org.homedns.mkh.dataservice.shared.RequestFactory;
import org.homedns.mkh.dataservice.shared.Response;
import org.homedns.mkh.dataservice.shared.Data;
import org.homedns.mkh.dataservice.shared.Id;
import org.homedns.mkh.dataservice.shared.RetrieveRequest;
import org.homedns.mkh.ui.client.WidgetDesc;
import org.homedns.mkh.ui.client.cache.RecordUtil;
import org.homedns.mkh.ui.client.cache.WidgetStore;
import com.google.gwt.user.client.Command;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.event.StoreListenerAdapter;
import com.gwtext.client.widgets.form.Field;
import org.homedns.mkh.ui.client.CmdTypeItem;

/**
* Bound to particular presenter form
*/
@SuppressWarnings( "unchecked" )
public class BoundForm extends BaseForm implements View {
	private WidgetStore _store;
	private Id _id;
	private WidgetDesc _desc;
	private Record _currentRecord;
	private Data _args;
	private Class< ? > _cacheType = WidgetStore.class;

	/**
	 * @param id
	 *            the identification object
	 * @param cfg
	 *            the form configuration object
	 */
	public BoundForm( Id id, FormConfig cfg ) {
		super( cfg );
		setID( id );
		RegisterViewEvent.fire( this );
	}

	/**
	 * @see org.homedns.mkh.ui.client.form.BaseForm#config()
	 */
	@Override
	protected void config( ) {
		super.config( );
		FormConfig cfg = getFormConfig( );
		addFields( ( String[] )cfg.getAttribute( FormConfig.FIELD_LIST ) );
		addButtons( ( CmdTypeItem[] )cfg.getAttribute( FormConfig.BUTTONS ) );
	}

	/**
	 * Adds fields to the form
	 * 
	 * @param asField
	 *            the fields names array to add
	 */
	public void addFields( String[] asField ) {
		Field[] fields = _desc.getFields( );
		if( asField == null || asField.length == 0 ) { 
			for( Field field : fields ) {
				addField( field );
			}
		} else {
			List< String > fieldNames = Arrays.asList( _store.getFields( ) );
			for( String sField : asField ) {
				int iField = fieldNames.indexOf( sField );
				if( iField > -1 ) {
					addField( fields[ iField ] );
				}
			}
		}
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getCache()
	 */
	@Override
	public ViewCache getCache( ) {
		return( _store );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getDescription()
	 */
	@Override
	public ViewDesc getDescription( ) {
		return( _desc );
	}

	/**
	 * Sets view description
	 * 
	 * @param desc
	 *            the view description to set
	 */
	public void setDescription( ViewDesc desc ) {
		_desc = ( WidgetDesc )desc;
	}
	
	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getID()
	 */
	@Override
	public Id getID( ) {
		return( _id );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getSavingData(org.homedns.mkh.dataservice.shared.Request)
	 */
	@Override
	public void getSavingData( Request request ) {
		Record record = _currentRecord.copy( );
		// puts form data to this record
		if( updateRecord( record ) ) {
			( ( CUDRequest )request ).setData( 
				RecordUtil.getJsonData( new Record[] { record } ) 
			);
		}
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#init(org.homedns.mkh.dataservice.client.view.ViewDesc)
	 */
	@Override
	public void init( ViewDesc desc ) {
		setDescription( desc );
		config( );
		setReader( _store.getReader( ) );
		_store.addStoreListener(
			new StoreListenerAdapter( ) {
				public void onLoad( Store store, Record[] records ) {
					if( records.length > 0 ) {
						loadRecord( records[ 0 ] );
					}
				}
			}
		);
		Command cmd = getAfterInitCommand( );
		if( cmd != null ) {
			cmd.execute( );
		}
	}

	/**
	* Loads record to the form
	* 
	* @param record to load
	*/
	public void loadRecord( Record record ) {
		_currentRecord = record;
		getForm( ).loadRecord( record );		
	}

	/**
	 * Loads empty record to the form
	 */
	public void loadEmptyRecord( ) {
		loadRecord( _store.createRecord( ) );
	}

	/**
	 * Default implementation sets retrieval arguments and filtering condition
	 * if any isn't null
	 * 
	 * @see org.homedns.mkh.dataservice.client.view.View#onInit()
	 */
	@Override
	public Request onInit( ) {
		RetrieveRequest request = ( RetrieveRequest )RequestFactory.create( 
			RetrieveRequest.class 
		);
		Data args = ( Data )getFormConfig( ).getAttribute( FormConfig.ARGS );
		if( args != null ) {
			request.setArgs( args );
		}
		return( request );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#refresh(org.homedns.mkh.dataservice.shared.Response)
	 */
	@Override
	public void refresh( Response data ) {
		refresh( );
	}

	/**
	 * Refreshes form: reset form, restore previous data (if any have been),
	 * focus on first field
	 */
	public void refresh( ) {
		getForm( ).reset( );
		if( _currentRecord != null ) {
			loadRecord( _currentRecord );
		}
		getFields( )[ 0 ].focus( );		
	}
	
	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setCache(org.homedns.mkh.dataservice.client.view.ViewCache)
	 */
	@Override
	public void setCache( ViewCache cache ) {
		_store = ( WidgetStore )cache;		
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setID(org.homedns.mkh.dataservice.shared.Id)
	 */
	@Override
	public void setID( Id id ) {
		_id = id;
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#isInit()
	 */
	@Override
	public boolean isInit( ) {
		return( getDescription( ) != null );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#onSend(org.homedns.mkh.dataservice.shared.Request)
	 */
	@Override
	public void onSend( Request request ) {
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getArgs()
	 */
	@Override
	public Data getArgs( ) {
		return( _args );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setArgs(org.homedns.mkh.dataservice.shared.Data)
	 */
	@Override
	public void setArgs( Data args ) {
		_args = args;
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getCacheType()
	 */
	@Override
	public Class< ? > getCacheType( ) {
		return( _cacheType );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setCacheType(java.lang.Class)
	 */
	@Override
	public void setCacheType( Class< ? > cacheType ) {
		_cacheType = cacheType;		
	}
}