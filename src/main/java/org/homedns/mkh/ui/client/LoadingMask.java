/*
 * Copyright 2012-2014 Mikhail Khodonov
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
 * $Id: LoadingMask.java 61 2012-09-08 18:08:56Z khomisha $
 */

package org.homedns.mkh.ui.client;

import java.util.HashMap;
import java.util.Map;
import org.homedns.mkh.dataservice.client.AbstractEntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtext.client.core.Ext;
import com.gwtext.client.core.ExtElement;

/**
 * It masks GUI objects while long time operation occurs 
 *
 */
public class LoadingMask {
	private static final UIMessages MESSAGES = ( UIMessages )AbstractEntryPoint.getMessages( );

	private Map< String, Count > _counts = new HashMap< String, Count >( );

	/**
	 * Masks root panel.
	 * 
	 * @param bMask
	 *            the mask flag
	 * @param sCss
	 *            the css class name to apply to the element
	 * @param sMsg
	 *            the message to display in the mask
	 */
	public void mask( boolean bMask, String sCss, String sMsg ) {
		mask( Ext.get( RootPanel.getBodyElement( ) ), bMask, sCss, sMsg );
	}

	/**
	 * Masks root panel with default css class.
	 * 
	 * @param bMask
	 *            the mask flag
	 */
	public void mask( boolean bMask ) {
		mask( Ext.get( RootPanel.getBodyElement( ) ), bMask, null, MESSAGES.load( ) );
	}

	/**
	 * Masks specified element.
	 * 
	 * @param element
	 *            the element to mask
	 * @param bMask
	 *            the mask flag
	 * @param sCss
	 *            a css class name to apply to the element, if null then uses
	 *            default class 'x-mask-loading'
	 * @param sMsg
	 *            the message to display in the mask
	 */
	public void mask( ExtElement element, boolean bMask, String sCss, String sMsg ) {
		Count count = getMaskCount( element );
		if( bMask ) {
			if( count.getValue( ) == 0 ) {
				if( sCss == null ) {
					element.mask( sMsg );
				} else {
					element.mask( sMsg, sCss );
				}
			}
			count.inc( );
		} else {
			if( count.getValue( ) == 0 ) {
				return;
			}
			count.dec( );
			if( count.getValue( ) == 0 ) {
				element.unmask( );
			}
		}
	}
	
	/**
	 * Returns element's mask count
	 * 
	 * @param element the masked element
	 *  
	 * @return the element's mask count
	 */
	private Count getMaskCount( ExtElement element ) {
		String sId = element.getDOM( ).getId( );
		Count c = _counts.get( sId );
		if( c == null ) {
			c = new Count( );
			_counts.put( sId, c );
		}
		return( c );
	}

	private class Count {
		private int iValue = 0;
		
		public void inc( ) {
			iValue++;
		}
		
		public void dec( ) {
			iValue--;
		}
		
		public int getValue( ) {
			return( iValue );
		}
	}
}
