/*
* Copyright 2013 Mikhail Khodonov
*
* Licensed under the Apache License, Version 2.0 ( the "License"); you may not
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
 * messages supplied from property file:
 * UIMessages.properties.
 */
public interface UIMessages extends com.google.gwt.i18n.client.Messages {

	/**
	* Translated "DDDB or DDLB is empty.".
	*
	* @return translated "DDDB or DDLB is empty."
	*/
	@DefaultMessage( "DDDB or DDLB is empty." )
	@Key( "ddEmpty" )
	String ddEmpty( );

	/**
	* Translated "Do you really want to delete the selected records?".
	*
	* @return translated "Do you really want to delete the selected records?"
	*/
	@DefaultMessage( "Do you really want to delete the selected records?" )
	@Key( "deleteRecord" )
	String deleteRecord( );

	/**
	* Translated "No records to display.".
	*
	* @return translated "No records to display."
	*/
	@DefaultMessage( "No records to display." )
	@Key( "noRecords" )
	String noRecords( );

	/**
	* Translated "No display value.".
	*
	* @return translated "No display value."
	*/
	@DefaultMessage( "No display value." )
	@Key( "noValue" )
	String noValue( );

	/**
	* Translated "Loading...".
	*
	* @return translated "Loading..."
	*/
	@DefaultMessage( "Loading..." )
	@Key( "load" )
	String load( );

	/**
	* Translated "Invalid login or password".
	*
	* @return translated "Invalid login or password"
	*/
	@DefaultMessage( "Invalid login or password" )
	@Key( "invalidLogin" )
	String invalidLogin( );
	
	/**
	* Translated "Displaying {0} - {1}".
	*
	* @return translated "Displaying {0} - {1}"
	*/
	@DefaultMessage( "Displaying {0} - {1}" )
	@Key( "displaying" )
	String displaying( int iStart, int iEnd );
	
	/**
	* Translated "of {0}".
	*
	* @return translated "of {0}"
	*/
	@DefaultMessage( "of {0}" )
	@Key( "of" )
	String of( int iTotal );

	/**
	* Translated "Page".
	*
	* @return translated "Page"
	*/
	@DefaultMessage( "Page" )
	@Key( "page" )
	String page( );

	/**
	* Translated "Invalid login or password".
	*
	* @return translated "Invalid login or password"
	*/
	@DefaultMessage( "Default locale must explicitly defined" )
	@Key( "defaultLocale" )
	String defaultLocale( );
	
	/**
	* Translated "Access rights did not defined for gui object".
	*
	* @return translated "Access rights did not defined for gui object"
	*/
	@DefaultMessage( "Access rights did not defined for gui object" )
	@Key( "noAccessRights" )
	String noAccessRights( );

	/**
	* Translated "Illegal type: {0}".
	*
	* @return translated "Illegal type: {0}"
	*/
	@DefaultMessage( "Illegal type: {0}" )
	@Key( "noType" )
	String noType( String sType );

	/**
	* Translated "No such panel token name: {0}".
	*
	* @return translated "No such panel token name: {0}"
	*/
	@DefaultMessage( "No such panel token name: {0}" )
	@Key( "noToken" )
	String noToken( String sName );

	/**
	* Translated "Illegal page: {0}".
	*
	* @return translated "Illegal page: {0}"
	*/
	@DefaultMessage( "Illegal page: {0}" )
	@Key( "noPage" )
	String noPage( int iType );

	/**
	* Translated "Version: {0}<br>(c) {1}".
	*
	* @return translated "Version: {0}<br>(c) {1}"
	*/
	@DefaultMessage( "Version: {0}<br>(c) {1}" )
	@Key( "version" )
	String version( String sVersion, String sCopyright );
}
