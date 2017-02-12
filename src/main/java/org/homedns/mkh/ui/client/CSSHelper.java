/*
 * Copyright 2017 Mikhail Khodonov
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

import java.util.Map;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.UIObject;

/**
 * CSS helper class
 *
 */
public class CSSHelper {
	private static CSSInjector cssInjector;

	/**
	 * Returns custom css injector
	 * 
	 * @return the custom css injector
	 */
	public static CSSInjector getCSSInjector( ) {
		return( cssInjector );
	}
	
	/**
	 * Sets custom css injector
	 * 
	 * @param cssInjector the custom css injector to set
	 */
	public static void setCssInjector( CSSInjector cssInjector ) {
		CSSHelper.cssInjector = cssInjector;
	}
	
	/**
	 * Adds secondary css name
	 * 
	 * @param elem
	 *            the target element
	 * @param sCSSName
	 *            the css name
	 */
	public static void addCSSName( Element elem, String sCSSName ) {
		elem.addClassName( sCSSName );
	}

	/**
	 * Adds secondary css name for all child elements specified by css name
	 * 
	 * @param parent
	 *            the parent
	 * @param sChildCssName
	 *            the target child css name
	 * @param sCSSName
	 *            the secondary css name to add
	 */
	public static void addCSSName( UIObject parent, String sChildCssName, String sCSSName ) {
		NodeList< Element > list = parent.getElement( ).getElementsByTagName( "*" );
		for( int iNode = 0; iNode < list.getLength( ); iNode++ ) {
			Element e = list.getItem( iNode );
			if( ( e.getClassName( ) ).contains( sChildCssName ) ) {
				addCSSName( e, sCSSName );
			}
		}		
	}

	/**
	 * Adds secondary css names for all child elements specified by given css
	 * name
	 * 
	 * @param parent
	 *            the parent object
	 * @param customCSS
	 *            the custom secondary styles map to add
	 */
	public static void addCSSName( UIObject parent, Map< String, String > customCSS ) {
		NodeList< Element > list = parent.getElement( ).getElementsByTagName( "*" );
		for( int iNode = 0; iNode < list.getLength( ); iNode++ ) {
			Element e = list.getItem( iNode );
			String[] as = e.getClassName( ).split( " " );
			for( String sClassName : as ) {
				if( customCSS.containsKey( sClassName ) ) {
					addCSSName( e, customCSS.get( sClassName ) );					
				}
			}
		}		
	}

	/**
	 * Returns first matching child element with given css class name
	 * 
	 * @param parent
	 *            the parent object
	 * @param sCssClass
	 *            sCssClass css class name to match
	 * 
	 * @return child element or null
	 */
	public static Element getChild( UIObject parent, String sCssClass ) {
		Element child = null;
		NodeList< Element > list = parent.getElement( ).getElementsByTagName( "*" );
		for( int iNode = 0; iNode < list.getLength( ); iNode++ ) {
			Element e = list.getItem( iNode );
			if( ( e.getClassName( ) ).contains( sCssClass ) ) {
				child = e;
				break;
			}
		}		
		return( child );
	}
}
