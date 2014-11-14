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
 * $Id$
 */

package org.homedns.mkh.ui.client.geomap;

import com.gwtext.client.widgets.map.Marker;

/**
 * Geo marker
 *
 */
public class GeoMarker extends Marker {
	/**
	 * Marker's icon
	 */
	public enum Icon {
		GREEN( "http://maps.google.com/mapfiles/ms/micons/green-dot.png" ), 
		RED( "http://maps.google.com/mapfiles/ms/micons/red-dot.png");
		
		private String _sURL;
		
		/**
		 * @param sURL
		 *            the icon url
		 */
		private Icon( String sURL ) {
			_sURL = sURL;
		}
		
		/**
		* Returns icon url
		* 
		* @return the icon url
		*/
		public String getURL( ) {
			return( _sURL );
		}
	}
	
	private String _sIconURL;
	private String _sId;
	
	/**
	 * @param point
	 *            the geo point
	 * @param sId
	 *            the marker identifier
	 * @param icon
	 *            the marker icon
	 */
	public GeoMarker( GeoPoint point, String sId, Icon icon ) {
		super( point );
		_sId = sId;
		setIcon( icon.getURL( ) );
		setLabel( sId );
	}

	/**
	 * Sets icon's size.
	 * 
	 * @param iconSize
	 *            the icon size array iconSize[ 0 ] - width, 
	 *            iconSize[ 1 ] - height
	 */
    public native void setIconSize( int[] iconSize ) /*-{
    	var marker = this.@com.gwtext.client.core.JsObject::getJsObj()();
    	marker.setIconSize(iconSize);
	}-*/;
    
	/**
	 * Changes icon url
	 * 
	 * @param sIconURL
	 *            the icon url to change
	 */
	public void changeIcon( String sIconURL ) {
		_sIconURL = sIconURL;
		changeIconURL( sIconURL );
	}  

	/**
	 * Changes icon
	 * 
	 * @param icon
	 *            the icon to change
	 */
	public void changeIcon( Icon icon ) {
		changeIcon( icon.getURL( ) );
	}  
	
	/**
	 * Changes icon url.
	 * 
	 * @param sIconURL
	 *            the icon url
	 */
    public native void changeIconURL( String sIconURL ) /*-{
    	var marker = this.@com.gwtext.client.core.JsObject::getJsObj()();
    	marker.changeIcon(sIconURL);
	}-*/;

	/**
	 * Sets icon.
	 * 
	 * @param sIconURL
	 *            the icon url
	 * @param iconSize
	 *            the icon size array iconSize[ 0 ] - width, 
	 *            iconSize[ 1 ] - height
	 */
    public native void setIcon(String sIconURL, int[] iconSize ) /*-{
       var marker = this.@com.gwtext.client.core.JsObject::getJsObj()();
       marker.setIcon(sIconURL, iconSize);
    }-*/;

	/**
	* Returns icon url
	* 
	* @return the icon url
	*/
	public String getIcon( ) {
		return( _sIconURL );
	}

	/**
	 * Sets icon url
	 * 
	 * @param sIconURL
	 *            the icon url to set
	 */
	public void setIcon( String sIconURL ) {
		_sIconURL = sIconURL;
		super.setIcon( sIconURL );
	}
	
	/**
	* Returns marker identifier
	* 
	* @return the marker identifier
	*/
	public String getId( ) {
		return( _sId );
	}
}
