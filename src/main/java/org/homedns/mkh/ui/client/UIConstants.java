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

package org.homedns.mkh.ui.client;

/**
 * Interface to represent the locale-sensitive, compile-time binding of
 * constant values supplied from property file:
 * UIConstants.properties.
 */
public interface UIConstants extends com.google.gwt.i18n.client.Constants {

	/**
	* Translated "Apply Filter".
	*
	* @return translated "Apply Filter"
	*/
	@DefaultStringValue( "Apply Filter" )
	@Key( "applyFilter" )
	String applyFilter( );

	/**
	* Translated "Cache Filter".
	*
	* @return translated "Cache Filter"
	*/
	@DefaultStringValue( "Cache Filter" )
	@Key( "cacheFilter" )
	String cacheFilter( );

	/**
	* Translated "Clear".
	*
	* @return translated "Clear"
	*/
	@DefaultStringValue( "Clear" )
	@Key( "clear" )
	String clear( );

	/**
	* Translated "Remote Filter".
	*
	* @return translated "Remote Filter"
	*/
	@DefaultStringValue( "Remote Filter" )
	@Key( "remoteFilter" )
	String remoteFilter( );

	/**
	* Translated "no".
	*
	* @return translated "no"
	*/
	@DefaultStringValue( "no" )
	@Key( "no" )
	String no( );

	/**
	* Translated "yes".
	*
	* @return translated "yes"
	*/
	@DefaultStringValue( "yes" )
	@Key( "yes" )
	String yes( );

	/**
	* Translated "Delete".
	*
	* @return translated "Delete"
	*/
	@DefaultStringValue( "Delete" )
	@Key( "del" )
	String del( );

	/**
	* Translated "Edit".
	*
	* @return translated "Edit"
	*/
	@DefaultStringValue( "Edit" )
	@Key( "edit" )
	String edit( );

	/**
	* Translated "Exit".
	*
	* @return translated "Exit"
	*/
	@DefaultStringValue( "Exit" )
	@Key( "exit" )
	String exit( );
	/**
	* Translated "Help".
	*
	* @return translated "Help"
	*/
	@DefaultStringValue( "Help" )
	@Key( "help" )
	String help( );

	/**
	* Translated "Refresh".
	*
	* @return translated "Refresh"
	*/
	@DefaultStringValue( "Refresh" )
	@Key( "refresh" )
	String refresh( );

	/**
	* Translated "Report".
	*
	* @return translated "Report"
	*/
	@DefaultStringValue( "Report" )
	@Key( "report" )
	String report( );

	/**
	* Translated "OK".
	*
	* @return translated "OK"
	*/
	@DefaultStringValue( "OK" )
	@Key( "OK" )
	String ok( );

	/**
	* Translated "Cancel".
	*
	* @return translated "Cancel"
	*/
	@DefaultStringValue( "Cancel" )
	@Key( "cancel" )
	String cancel( );

	/**
	* Translated "New".
	*
	* @return translated "New"
	*/
	@DefaultStringValue( "New" )
	@Key( "new" )
	String add( );

	/**
	* Translated "Update".
	*
	* @return translated "Update"
	*/
	@DefaultStringValue( "Update" )
	@Key( "update" )
	String update( );

	/**
	* Translated "Save".
	*
	* @return translated "Save"
	*/
	@DefaultStringValue( "Save" )
	@Key( "save" )
	String save( );

	/**
	* Translated "Change Password".
	*
	* @return translated "Change Password"
	*/
	@DefaultStringValue( "Change Password" )
	@Key( "chgPass" )
	String chgPass( );

	/**
	* Translated "Confirm Password".
	*
	* @return translated "Confirm Password"
	*/
	@DefaultStringValue( "Confirm Password" )
	@Key( "confirmPass" )
	String confirmPass( );

	/**
	* Translated "Login".
	*
	* @return translated "Login"
	*/
	@DefaultStringValue( "Login" )
	@Key( "login" )
	String login( );

	/**
	* Translated "About".
	*
	* @return translated "About"
	*/
	@DefaultStringValue( "About" )
	@Key( "about" )
	String about( );

	/**
	* Translated "Password".
	*
	* @return translated "Password"
	*/
	@DefaultStringValue( "Password" )
	@Key( "password" )
	String password( );

	/**
	* Translated "wrong state transition".
	*
	* @return translated "wrong state transition"
	*/
	@DefaultStringValue( "wrong state transition" )
	@Key( "wrongTransition" )
	String wrongTransition( );
	
	/**
	* Translated "Password and it's confirmation must agree.".
	*
	* @return translated "Password and it's confirmation must agree."
	*/
	@DefaultStringValue( "Password and it''s confirmation must agree." )
	@Key( "invalidPassInput" )
	String invalidPassInput( );	
}
