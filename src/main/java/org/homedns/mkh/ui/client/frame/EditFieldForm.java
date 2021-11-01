package org.homedns.mkh.ui.client.frame;

import org.homedns.mkh.dataservice.client.AbstractEntryPoint;
import org.homedns.mkh.dataservice.client.Column;
import org.homedns.mkh.dataservice.client.event.EventBus;
import org.homedns.mkh.dataservice.client.event.HandlerRegistry;
import org.homedns.mkh.dataservice.client.event.HandlerRegistryAdaptee;
import org.homedns.mkh.dataservice.client.event.RPCResponseEvent;
import org.homedns.mkh.dataservice.client.event.RPCResponseEvent.RPCResponseHandler;
import org.homedns.mkh.dataservice.shared.Response;
import org.homedns.mkh.ui.client.CmdTypeItem;
import org.homedns.mkh.ui.client.UIConstants;
import org.homedns.mkh.ui.client.command.CommandFactory;
import org.homedns.mkh.ui.client.command.NullCmd;
import org.homedns.mkh.ui.client.command.UpdateCmd;
import org.homedns.mkh.ui.client.form.BaseForm;
import org.homedns.mkh.ui.client.form.FieldFactory;
import org.homedns.mkh.ui.client.form.FormButton;
import org.homedns.mkh.ui.client.form.FormConfig;
import org.homedns.mkh.ui.client.grid.Grid;
import com.google.gwt.user.client.Command;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.Record;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.event.ComponentListenerAdapter;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.AnchorLayoutData;

/**
 * Edit grid field form
 *
 */
public class EditFieldForm extends BaseForm implements RPCResponseHandler, HandlerRegistry {
	private static final UIConstants CONSTANTS = ( UIConstants )AbstractEntryPoint.getConstants( );

	private Command cmdUpdate;
	private Field field;
	private String sFieldName;
	private Grid grid;
	private String sInitValue;
	private HandlerRegistryAdaptee handlers;

	/**
	 * @param grid
	 *            the source grid
	 * @param sFieldName
	 *            the field name
	 * @param cfg
	 *            the form configuration object
	 * @param bEdit
	 *            the edit flag
	 */
	public EditFieldForm( final Grid grid, final String sFieldName, FormConfig cfg, boolean bEdit ) {
		this.grid = grid;
		this.sFieldName = sFieldName;
		if( bEdit ) {
			cfg.setAttribute( 
				FormConfig.BUTTONS, 
				new CmdTypeItem[] { 
					new CmdTypeItem( CONSTANTS.save( ), NullCmd.class ), 
					new CmdTypeItem( CONSTANTS.close( ), NullCmd.class )
				} 
			);
			cmdUpdate = CommandFactory.create( UpdateCmd.class, grid );
		} else {
			cfg.setAttribute( 
				FormConfig.BUTTONS, 
				new CmdTypeItem[] { 
					new CmdTypeItem( CONSTANTS.close( ), NullCmd.class )
				} 
			);
		}
		cfg.setAttribute( FormConfig.AUTO_WIDTH, false );
		cfg.setAttribute( FormConfig.WIDTH, 500 );
		setFormConfig( cfg );
		config( );
        setHeight( 300 );  
		Column col = grid.getDescription( ).getDataBufferDesc( ).getColumn( sFieldName );
		field = FieldFactory.create( col );
		field.setHideLabel( true );
		if( col.getLimit( ) != 0 ) {
			( ( TextField )field ).setMaxLength( col.getLimit( ) );
		}
		add( field, new AnchorLayoutData( "100% 100%" ) );
		addListener( 
			new ComponentListenerAdapter( ) {
				@Override
				public boolean doBeforeShow( Component component ) {
					sInitValue = grid.getSelectionModel( ).getSelected( ).getAsString( sFieldName );
					sInitValue = ( sInitValue == null ) ? "" : sInitValue;
					field.setValue( sInitValue );
					field.clearInvalid( );
					return super.doBeforeShow( component );
				}				
			}
		);
		addButtons( ( CmdTypeItem[] )cfg.getAttribute( FormConfig.BUTTONS ) );
		handlers = new HandlerRegistryAdaptee( );
		addHandler( 
			EventBus.getInstance( ).addHandlerToSource( 
				RPCResponseEvent.TYPE, grid.getID( ).getUID( ), this 
			) 
		);
	}

	/**
	 * @see org.homedns.mkh.ui.client.form.BaseForm#onButtonClick(org.homedns.mkh.ui.client.form.FormButton, com.gwtext.client.core.EventObject)
	 */
	@Override
	protected void onButtonClick( FormButton button, EventObject e ) {
		if( button.getText( ).equals( CONSTANTS.save( ) ) ) {
			if( !isValid( ) ) {
				return;
			}
			Record record = grid.getSelectionModel( ).getSelected( );
			record.set( sFieldName, field.getValueAsString( ) );
			cmdUpdate.execute( );
		}
		if( button.getText( ).equals( CONSTANTS.close( ) ) ) {
			hide( );
		}
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.event.HandlerRegistry#removeHandlers()
	 */
	@Override
	public void removeHandlers( ) {
		handlers.clear( );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.event.HandlerRegistry#addHandler(com.google.web.bindery.event.shared.HandlerRegistration)
	 */
	@Override
	public boolean addHandler( HandlerRegistration hr ) {
		return( handlers.add( hr ) );		
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.event.RPCResponseEvent.RPCResponseHandler#onRPCResponse(org.homedns.mkh.dataservice.client.event.RPCResponseEvent)
	 */
	@Override
	public void onRPCResponse( RPCResponseEvent event ) {
		if( event.getResponse( ).getResult( ) != Response.FAILURE ) {
			hide( );
		}
	}
}
