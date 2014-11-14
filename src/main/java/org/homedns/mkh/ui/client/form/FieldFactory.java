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

import org.homedns.mkh.dataservice.client.Column;
import org.homedns.mkh.dataservice.client.Type;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.NumberField;
import com.gwtext.client.widgets.form.Radio;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.TimeField;

/**
 * Field factory
 *
 */
public class FieldFactory {
	private static final int FIELD_WIDTH = 120;
	
	/**
	 * Creates field
	 * 
	 * @param col the column for which field will be created
	 * 
	 * @return the field
	 */
	public static Field create( Column col ) {
		Field newField = null;
		String sColName = col.getName( );
		String sCaption = col.isRequired( ) ? col.getCaption( ) + "*" : col.getCaption( );
		String sStyle = col.getStyle( );
		String sRule = col.getValidationRule( );
		String sMsg = col.getValidationMsg( );
		if( Column.DDDB.equals( sStyle ) || Column.DDLB.equals( sStyle ) ) {
			newField = new ListBox( col, FIELD_WIDTH );
		} else if( Column.CHECKBOX.equals( sStyle ) ) {
			newField = configCheckBox( new Checkbox( ), sCaption, sColName );
		} else if( Column.RADIOBUTTON.equals( sStyle ) ) {
			newField = configCheckBox( new Radio( ), sCaption, sColName );
		} else if( Column.PWD.equals( sStyle ) ) {
			newField = new TextField( sCaption, sColName, FIELD_WIDTH );
			( ( TextField )newField ).setPassword( true );
			setValidation( ( TextField )newField, sRule, sMsg );
		} else if( Column.EDIT_DATE.equals( sStyle ) ) {
			DateField field = new DateField( sCaption, sColName, FIELD_WIDTH );
			field.setFormat( "Y-m-d" );
			newField = field;
		} else if( Column.EDIT_TIME.equals( sStyle ) ) {
			TimeField field = new TimeField( sCaption, sColName, FIELD_WIDTH );
			field.setFormat( "g:i:s A" );
			newField = field;
		} else if( Column.EDIT_TS.equals( sStyle ) ) {
			DateField field = new DateField( sCaption, sColName, FIELD_WIDTH );
			field.setFormat( "Y-m-d H:i:s" );
			newField = field;
		} else if( "".equals( sStyle ) ) {
			// no style - no field
		} else if( Column.EDIT.equals( sStyle ) ) {
			Type type = col.getColType( );
			if( type == Type.STRING ) {
				if( col.getLimit( ) == 0 ) {
					newField = configTextArea( new TextArea( sCaption, sColName ), sRule, sMsg );
				} else {
					newField = new TextField( sCaption, sColName, FIELD_WIDTH );
					setValidation( ( TextField )newField, sRule, sMsg );
				}
			} else if(
				type == Type.INT ||
				type == Type.LONG ||
				type == Type.SHORT ||
				type == Type.BYTE
			) {
				newField = configNumberField( 
					new NumberField( sCaption, sColName, FIELD_WIDTH ), false, sRule, sMsg 
				);
			} else if( type == Type.FLOAT || type == Type.DOUBLE ) {
				newField = configNumberField( 
					new NumberField( sCaption, sColName, FIELD_WIDTH ), true, sRule, sMsg 
				);
			} 
		} else {
			throw new IllegalArgumentException( "style: " + sStyle );			
		}
		if( newField != null ) {
			if( newField instanceof TextField ) {
				TextField textField = ( TextField )newField;
				textField.setAllowBlank( !col.isRequired( ) );
				textField.setSelectOnFocus( true );
			}
			newField.setValidationEvent( false );
		}
		return( newField );
	}

	/**
	* Configures text area field
	*
	* @param field to configure
	* @param sRule validation rule
	* @param sMsg validation message
	* 
	* @return the configured field
	*/
	private static Field configTextArea( TextArea field, String sRule, String sMsg ) {
		setValidation( field, sRule, sMsg );
		field.setGrow( true );
		field.setPreventScrollbars( true );
		return( field );
	}

	/**
	* Configures number field
	*
	* @param field to configure
	* @param bIsDecimal decimal number flag
	* @param sRule validation rule
	* @param sMsg validation message
	* 
	* @return the configured field
	*/
	private static Field configNumberField( NumberField field, boolean bIsDecimal, String sRule, String sMsg ) {
		setValidation( field, sRule, sMsg );
		field.setAllowDecimals( bIsDecimal );
		return( field );
	}

	/**
	* Configures check box field
	*
	* @param field to configure
	* @param sLabel label
	* @param sName field name
	* 
	* @return the configured field
	*/
	private static Field configCheckBox( Checkbox field, String sLabel, String sName ) {
		field.setName( sName );
		field.setFieldLabel( sLabel );
		return( field );
	}

	/**
	* Sets validation rule and message for field.
	*
	* @param field to add validation rule and message
	* @param sRule validation rule
	* @param sMsg validation message
	*/
	private static void setValidation( TextField field, String sRule, String sMsg ) {
		if( !"".equals( sRule ) ) {
			field.setRegex( sRule );
			if( !"".equals( sMsg ) ) {
				field.setRegexText( sMsg );
			}
		}
	}
	
}
