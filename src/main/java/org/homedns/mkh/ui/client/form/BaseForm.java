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

package org.homedns.mkh.ui.client.form;

import java.util.HashMap;
import java.util.Map;
import org.homedns.mkh.ui.client.CmdTypeItem;
import org.homedns.mkh.ui.client.command.CommandFactory;
import com.google.gwt.user.client.Command;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.data.Record;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.Radio;
import com.gwtext.client.widgets.layout.AnchorLayoutData;

/**
 * Base form
 *
 */
public class BaseForm extends FormPanel {
	private FormConfig cfg;
	private Map< String, FormButton > buttons = new HashMap< String, FormButton >( );
	private Command cmd;
	
	public BaseForm( ) {
	}

	/**
	 * @param cfg
	 *            the form configuration object
	 */
	public BaseForm( FormConfig cfg ) {
		this.cfg = cfg;
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.view.View#setAfterInitCommand(Command)
	 */
	public void setAfterInitCommand( Command cmd ) {
		this.cmd = cmd;
	}
	
	/**
	 * Returns after init callback command
	 * 
	 * @return the command
	 */
	protected Command getAfterInitCommand( ) {
		return( cmd );
	}
	
	/**
	 * Adds buttons to the form
	 * 
	 * @param items the command type items array
	 */
	public void addButtons( CmdTypeItem[] items ) {
		for( CmdTypeItem item : items ) {
			final Command cmd = CommandFactory.create( 
				item.getCommandType( ), this 
			);
			final FormButton btn = new FormButton( item.getName( ), cmd );
			btn.addListener(
				new ButtonListenerAdapter( ) {
					public void onClick( Button button, EventObject e ) {
						onButtonClick( btn, e );
					}
				}
			);		
			buttons.put( btn.getText( ), btn );
			addButton( btn );
		}
	}
	
	/**
	 * Returns form configuration object
	 * 
	 * @return the form configuration object
	 */
	public FormConfig getFormConfig( ) {
		return( cfg );
	}
	
	/**
	 * Sets form configuration object
	 * 
	 * @param cfg the form configuration object to set
	 */
	public void setFormConfig( FormConfig cfg ) {
		this.cfg = cfg;
	}

	/**
	 * Returns buttons map
	 * 
	 * @return the buttons map
	 */
	public Map< String, FormButton > getButtons( ) {
		return( buttons );
	}

	/**
	 * Sets specified button visible/invisible
	 * 
	 * @param sName
	 *            the button's text
	 * @param bVisible
	 *            the visible flag
	 */
	public void setVisibleButton( String sName, boolean bVisible ) {
		FormButton btn = getButtons( ).get( sName );
		if( btn != null ) {
			btn.setVisible( bVisible );
		}
	}
	
	/**
	 * Sets specified button disable/enable
	 * 
	 * @param sName
	 *            the button's text
	 * @param bDisabled
	 *            the disable flag
	 */
	public void setDisabledButton( String sName, boolean bDisabled ) {
		FormButton btn = getButtons( ).get( sName );
		if( btn != null ) {
			btn.setDisabled( bDisabled );
		}
	}
	
	/**
	 * Sets specified field visible/invisible
	 * 
	 * @param sName
	 *            the the field ID, dataIndex, name or hiddenName to search for
	 * @param bVisible
	 *            the visible flag
	 */
	public void setVisibleField( String sName, boolean bVisible ) {
		Field field = getForm( ).findField( sName );
		if( field != null ) { 
			field.setVisible( bVisible );
		}
	}
	
	/**
	 * Sets specified field disable/enable
	 * 
	 * @param sName
	 *            the the field ID, dataIndex, name or hiddenName to search for
	 * @param bDisabled
	 *            the disable flag
	 */
	public void setDisabledField( String sName, boolean bDisabled ) {
		Field field = getForm( ).findField( sName );
		if( field != null && field.isVisible( ) ) { 
			field.setDisabled( bDisabled );
		}
	}

	/**
	 * Sets disable/enable all fields
	 * 
	 * @param bDisabled
	 *            the disable flag
	 */
	public void setDisabledFields( boolean bDisabled ) {
		for( Field field : getFields( ) ) {
			field.setDisabled( bDisabled );
		}
	}
	
	/**
	 * Sets visible/invisible all fields
	 * 
	 * @param bVisible
	 *            the visible flag
	 */
	public void setVisibleFields( boolean bVisible ) {
		for( Field field : getFields( ) ) {
			field.setVisible( bVisible );
		}
	}

	/**
	 * Returns true if record update successful @see
	 * com.gwtext.client.widgets.form.Form.updateRecord(Record) otherwise false
	 * 
	 * @param record
	 *            the target record
	 * 
	 * @return true if update success and false otherwise
	 */
	public boolean updateRecord( Record record ) {
		boolean bResult = beforeRecordUpdate( record );
		if( bResult ) {
			getForm( ).updateRecord( record	);
		}
		return( bResult );
	}
	
	/**
	 * Returns true if client-side validation on the form is successful.
	 * 
	 * @return true if validation is successful false otherwise
	 */
	public boolean isValid( ) {
		return( getForm( ).isValid( ) );
	}
	
	/**
	 * Before record update action
	 * 
	 * @param record
	 *            the target record
	 * 
	 * @return true if before update action successful otherwise false which
	 *         breaks record update
	 */
	protected boolean beforeRecordUpdate( Record record ) {
		return( true );
	}

	/**
	 * Configures form
	 */
	protected void config( ) {
		String sTitle = ( String )cfg.getAttribute( FormConfig.TITLE );
		if( !"".equals( sTitle ) ) {
			setTitle( sTitle );
		}	
		if( ( Boolean )cfg.getAttribute( FormConfig.AUTO_WIDTH ) ) {
			setAutoWidth( true );
		} else {
			setWidth( ( Integer )cfg.getAttribute( FormConfig.WIDTH ) );
		}
		setBorder( ( Boolean )cfg.getAttribute( FormConfig.BORDER ) );
		setLabelWidth( ( Integer )cfg.getAttribute( FormConfig.LABEL_WIDTH ) );
		setLabelAlign( ( Position )cfg.getAttribute( FormConfig.LABEL_ALIGN ) );
		setPaddings( ( Integer )cfg.getAttribute( FormConfig.PADDING ) );
		setButtonAlign( ( Position )cfg.getAttribute( FormConfig.BUTTON_ALIGN ) );
		setTrackResetOnLoad( ( Boolean )cfg.getAttribute( FormConfig.TRACK_ON_RESET ) );
		setAutoScroll( ( Boolean )cfg.getAttribute( FormConfig.AUTO_SCROLL ) );		
	}

	/**
	 * On button click reaction
	 * 
	 * @param button
	 *            the button which click
	 * @param e
	 *            the event object
	 */
	protected void onButtonClick( FormButton button, EventObject e ) {
		button.getCommand( ).execute( );		
	}

	/**
	 * Adds field to the form
	 * 
	 * @param field
	 *            the field to add
	 */
	protected void addField( Field field ) {
		if( field instanceof Checkbox || field instanceof Radio ) {
			add( field );
		} else {
			add( field, new AnchorLayoutData( "95%" ) );
		}					
	}
	
	/**
	 * Returns true if all fields are passed validation and false otherwise
	 * 
	 * @return true if pass validation, false otherwise
	 */
	protected boolean validate( ) {
		try {
			for( Field field : getFields( ) ) {
				field.validate( );
			}
			return( true );
		}
		catch( Exception e ) {
			return( false );
		}
	}
}
