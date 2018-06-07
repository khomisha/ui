/*
* Copyright 2013-2018 Mikhail Khodonov
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.homedns.mkh.dataservice.client.Column;
import org.homedns.mkh.dataservice.client.event.RegisterViewEvent;
import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.dataservice.client.view.ViewCache;
import org.homedns.mkh.dataservice.client.view.ViewDesc;
import org.homedns.mkh.dataservice.shared.Request;
import org.homedns.mkh.dataservice.shared.CUDRequest;
import org.homedns.mkh.dataservice.shared.Response;
import org.homedns.mkh.dataservice.shared.Data;
import org.homedns.mkh.dataservice.shared.Id;
import org.homedns.mkh.dataservice.shared.RetrieveRequest;
import org.homedns.mkh.ui.client.WidgetDesc;
import org.homedns.mkh.ui.client.cache.RecordUtil;
import org.homedns.mkh.ui.client.cache.WidgetStore;
import org.homedns.mkh.ui.client.command.CommandFactory;
import org.homedns.mkh.ui.client.command.CommandScheduler;
import org.homedns.mkh.ui.client.command.RetrieveCmd;
import com.google.gwt.user.client.Command;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.event.StoreListener;
import com.gwtext.client.data.event.StoreListenerAdapter;
import com.gwtext.client.util.DateUtil;
import com.gwtext.client.util.JavaScriptObjectHelper;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.FormLayout;
import org.homedns.mkh.ui.client.CmdTypeItem;

/**
* Bound to particular presenter form
*/
@SuppressWarnings( "unchecked" )
public class BoundForm extends BaseForm implements View {
	private static final Logger LOG = Logger.getLogger( BoundForm.class.getName( ) );  

	private WidgetStore store;
	private Id id;
	private WidgetDesc desc;
	private Record currentRecord;
	private Class< ? > cacheType = WidgetStore.class;
	private Response response;
	private boolean bBatch = false;
	private StoreListener storeListener;
	private Command retrieveCmd;
	private boolean bForcedRetrieve = false;
	private Map< String, String > initValues;

	/**
	 * @param id
	 *            the identification object
	 * @param cfg
	 *            the form configuration object
	 */
	public BoundForm( Id id, FormConfig cfg ) {
		super( cfg );
		setID( id );
		setStoreListener( new CacheListener( ) );
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
		setBatchUpdate( ( Boolean )cfg.getAttribute( FormConfig.BATCH_UPDATE ) );		
		int iDelay = ( Integer )cfg.getAttribute( FormConfig.SCHEDULE_RETRIEVE_CMD );
		if( iDelay > 0 ) {
			CommandScheduler.get( ).addCmd2Schedule( retrieveCmd, iDelay );
		}
		setInitValues( ( Map< String, String > )cfg.getAttribute( FormConfig.INIT_VALUES ) );
		if( initValues == null ) {
			setDefaultInitValues( );
		}
		if( ( Boolean )cfg.getAttribute( FormConfig.READONLY ) ) {
			setDisabledFields( true );
		}
	}

	/**
	 * Adds fields to the form
	 * 
	 * @param asField
	 *            the fields names array to add
	 */
	public void addFields( String[] asField ) {
		Field[] fields = desc.getFields( );
		if( asField == null || asField.length == 0 ) { 
			for( Field field : fields ) {
				addField( field );
			}
		} else {
			List< String > fieldNames = Arrays.asList( store.getFields( ) );
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
		return( store );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getDescription()
	 */
	@Override
	public ViewDesc getDescription( ) {
		return( desc );
	}

	/**
	 * Sets view description
	 * 
	 * @param desc
	 *            the view description to set
	 */
	public void setDescription( ViewDesc desc ) {
		this.desc = ( WidgetDesc )desc;
	}
	
	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getID()
	 */
	@Override
	public Id getID( ) {
		return( id );
	}
	
	/**
	 * Returns field by it's name
	 * 
	 * @param sName
	 *            the field (column) name
	 * 
	 * @return the field
	 */
	public Field getField( String sName ) {
		Column col = desc.getDataBufferDesc( ).getColumn( sName );
		return( desc.getCol2Field( ).get( col ) );
	}

	/**
	 * Fills panel with specified fields

	 * @param sTitle the panel title
	 * @param asField
	 *            fields names array
	 * 
	 * @return the panel with fields
	 */
	public Panel fillPanel( String sTitle, String[] asField ) {
		Panel panel = fillPanel( asField );
		panel.setTitle( sTitle );
		return( panel );
	}

	/**
	 * Fills panel with specified fields
	 * 
	 * @param asField
	 *            fields names array
	 * 
	 * @return the panel with fields
	 */
	public Panel fillPanel( String[] asField ) {
		Panel panel = new Panel( );			
		panel.setLayout( new FormLayout( ) );
		panel.setBorder( false );
		for( String sField : asField ) {
			Field field = getField( sField );
			panel.add( field, new AnchorLayoutData( "95%" ) );
		}
		return( panel );
	}

	/**
	 * Fills specified panel with fields
	 * 
	 * @param panel
	 *            the target panel
	 * @param asField
	 *            fields names array
	 */
	public void fillPanel( Panel panel, String[] asField ) {
		for( String sField : asField ) {
			Field field = getField( sField );
			panel.add( field, new AnchorLayoutData( "95%" ) );
		}
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getSavingData(org.homedns.mkh.dataservice.shared.Request)
	 */
	@Override
	public void getSavingData( Request request ) {
		if( currentRecord == null ) {
			currentRecord = createRecord( );
		}
		Record record = currentRecord.copy( );
		// puts form data to this record
		if( updateRecord( record ) ) {
			( ( CUDRequest )request ).setData( 
				RecordUtil.getJsonData( new Record[] { record } ) 
			);
		}
	}
	
	/**
	 * Returns current record
	 * 
	 * @return the current record
	 */
	public Record getCurrentRecord( ) {
		return( currentRecord );
	}

	/**
	 * Sets current record
	 * 
	 * @param record the record to set
	 */
	public void setCurrentRecord( Record record ) {
		currentRecord = record;
	}
	
	/**
	 * Creates empty record
	 * 
	 * @return the created record
	 */
	public Record createRecord( ) {
		return( store.createRecord( ) );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#init(org.homedns.mkh.dataservice.client.view.ViewDesc)
	 */
	@Override
	public void init( ViewDesc desc ) {
		setDescription( desc );
		config( );
		setReader( store.getReader( ) );
		store.addStoreListener( getStoreListener( ) );
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
		currentRecord = record;
		if( isRendered( ) ) {
			getForm( ).loadRecord( record );
		}
	}

	/**
	 * Loads empty record to the form
	 */
	public void loadEmptyRecord( ) {
		loadRecord( createRecord( ) );
	}

	/**
	 * Default implementation sets retrieval arguments and filtering condition (if necessary)
	 * 
	 * @see org.homedns.mkh.dataservice.client.view.View#onInit()
	 */
	@Override
	public Request onInit( ) {
		RetrieveCmd rc = ( RetrieveCmd )CommandFactory.create( RetrieveCmd.class, this );
		retrieveCmd = rc;
		setArgs( ( Data )getFormConfig( ).getAttribute( FormConfig.ARGS ) );
		return( rc.getRequest( ) );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#onResponse(org.homedns.mkh.dataservice.shared.Response)
	 */
	@Override
	public void onResponse( Response response ) {
		this.response = response;
		if( response.getResult( ) != Response.FAILURE ) {
			WidgetStore store = ( WidgetStore )getCache( );
			if( store != null ) {
				setCurrentRecord( store.getAt( 0 ) );
			}
			if( isRendered( ) ) {
				refresh( );
			}
		}
	}

	/**
	 * Refreshes form: reset form, restore previous data (if any have been),
	 * focus on first field
	 */
	public void refresh( ) {
		getForm( ).reset( );
		if( currentRecord != null ) {
			LOG.config( RecordUtil.record2String( currentRecord ) );
			loadRecord( currentRecord );
		}
		getFields( )[ 0 ].focus( );		
	}
	
	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setCache(org.homedns.mkh.dataservice.client.view.ViewCache)
	 */
	@Override
	public void setCache( ViewCache cache ) {
		store = ( WidgetStore )cache;		
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setID(org.homedns.mkh.dataservice.shared.Id)
	 */
	@Override
	public void setID( Id id ) {
		this.id = id;
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
		RetrieveRequest request = ( RetrieveRequest )( ( RetrieveCmd )retrieveCmd ).getRequest( );
		return( request.getArgs( ) );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setArgs(org.homedns.mkh.dataservice.shared.Data)
	 */
	@Override
	public void setArgs( Data args ) {
		RetrieveRequest request = ( RetrieveRequest )( ( RetrieveCmd )retrieveCmd ).getRequest( );
		request.setArgs( args );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getCacheType()
	 */
	@Override
	public Class< ? > getCacheType( ) {
		return( cacheType );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setCacheType(java.lang.Class)
	 */
	@Override
	public void setCacheType( Class< ? > cacheType ) {
		this.cacheType = cacheType;		
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#getResponse()
	 */
	@Override
	public Response getResponse( ) {
		return( response );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setResponse(org.homedns.mkh.dataservice.shared.Response)
	 */
	@Override
	public void setResponse( Response response ) {
		this.response = response;
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#reload()
	 */
	@Override
	public void reload( ) {
		retrieveCmd.execute( );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setBatchUpdate(boolean)
	 */
	@Override
	public void setBatchUpdate( boolean bBatch ) {
		this.bBatch = bBatch;
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#isBatchUpdate()
	 */
	@Override
	public boolean isBatchUpdate( ) {
		return( bBatch );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#isForcedRetrieve()
	 */
	@Override
	public boolean isForcedRetrieve( ) {
		return( bForcedRetrieve );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setForcedRetrieve(boolean)
	 */
	@Override
	public void setForcedRetrieve( boolean bForcedRetrieve ) {
		this.bForcedRetrieve = bForcedRetrieve;
	}

	/**
	 * Returns store listener object 
	 * 
	 * @return the store listener object
	 */
	public StoreListener getStoreListener( ) {
		return( storeListener  );
	}

	/**
	 * Sets store listener object
	 * 
	 * @param storeListener the store listener object to set
	 */
	public void setStoreListener( StoreListener storeListener ) {
		this.storeListener = storeListener;
	}
	
	/**
	 * Sets initial values
	 * 
	 * @param initValues the initial values
	 */
	public void setInitValues( Map< String, String > initValues ) {
		this.initValues = initValues;
	}
	
	/**
	 * Returns initial values
	 * 
	 * @return the initial values
	 */
	public Map< String, String > getInitValues( ) {
		return( initValues );
	}

	/**
	 * Sets initials values (if exist) for form fields
	 */
	public void initValues( ) {
		for( Field field : getFields( ) ) {
			String sValue = initValues.get( field.getName( ) );
			if( sValue != null ) {
				field.setValue( sValue );					
			}
			field.clearInvalid( );
		}
	}
	
	/**
	 * Sets default initial fields values
	 */
	private void setDefaultInitValues( ) {
		initValues = new HashMap< String, String >( );
		for( Field field : getFields( ) ) { 
			if( field instanceof DateField ) {
				DateField df = ( DateField )field;
				String fmt = JavaScriptObjectHelper.getAttribute( df.getJsObj( ), "format" );
				initValues.put( field.getName( ), DateUtil.format( new Date( ), fmt ) );
			}
		}		
	}
	
	protected class CacheListener extends StoreListenerAdapter {
		/**
		 * @see com.gwtext.client.data.event.StoreListenerAdapter#onLoad(com.gwtext.client.data.Store, com.gwtext.client.data.Record[])
		 */
		@Override
		public void onLoad( Store store, Record[] records ) {
			if( records.length > 0 ) {
				loadRecord( records[ 0 ] );
			}
		}

		/**
		 * @see com.gwtext.client.data.event.StoreListenerAdapter#onDataChanged(com.gwtext.client.data.Store)
		 */
		@Override
		public void onDataChanged( Store store ) {
			if( store.getCount( ) < 1 ) {
				loadEmptyRecord( );
			}
		}		
	}
}