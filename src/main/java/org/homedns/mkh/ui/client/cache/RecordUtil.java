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

package org.homedns.mkh.ui.client.cache;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.homedns.mkh.dataservice.shared.Util;
import com.google.gwt.core.client.JavaScriptObject;
import com.gwtext.client.data.Record;
import com.gwtext.client.util.JSON;
import com.gwtext.client.util.JavaScriptObjectHelper;

/**
 * Record related helper methods
 *
 */
public class RecordUtil {
	private static final Logger LOG = Logger.getLogger( RecordUtil.class.getName( ) ); 
	
	/**
	 * Encodes to json string record's child javascript object
	 * 
	 * @param record
	 *            the source record
	 * @param sProperty
	 *            the record's child javascript object name, e.g. "data", "json"
	 * 
	 * @return the json string
	 */
	public static String jsonEncode( Record record, String sProperty ) {
		JavaScriptObject jso = JavaScriptObjectHelper.getAttributeAsJavaScriptObject( 
			record.getJsObj( ), 
			sProperty 
		);
		return( JSON.encode( jso ) );
	}

	/**
	 * Returns record as string
	 * 
	 * @param record
	 *            the target record
	 * 
	 * @return the record as string
	 */
	public static String record2String( Record record ) {
		StringBuffer sb = new StringBuffer( );
		for( String sField : record.getFields( ) ) {
			String sValue = record.getAsString( sField );
			if( sValue == null ) {
				sb.append( "null" );					
			} else {
				sb.append( sValue );
			}
			sb.append( ";" );
		}
		return( sb.toString( ) );
	}

	/**
	 * Returns records list as json data string
	 * 
	 * @param records
	 *            the records list
	 * 
	 * @return json data string
	 */
	public static String getJsonData( List< Record > records ) {
		return( getJsonData( records.toArray( new Record[ records.size( ) ] ) ) );
	}

	/**
	 * Returns records array as json data string
	 * 
	 * @param aRecord
	 *            the records array
	 * 
	 * @return json data string
	 */
	public static String getJsonData( Record[] aRecord ) {
		int iRecord = 0;
		StringBuffer sb = new StringBuffer( );
		try {
			sb.append( "[" );
			for( Record rec : aRecord ) {
				String s = getJson( rec.getJsObj( ) );
				LOG.config( LOG.getName( ) + ": getJsonData: json: " + s );
				sb.append( s );
				if( iRecord < aRecord.length - 1 ) {
					sb.append( "," );
				}
				iRecord++;
			}
			sb.append( "]" );
			LOG.config( LOG.getName( ) + ": getJsonData: " + sb.toString( ) );
		}
		catch( Exception e ) {
			LOG.log( 
				Level.SEVERE, 
				"record: " + RecordUtil.record2String( aRecord[ iRecord ] ), 
				Util.unwrap( e ) 
			);  			
		}
		return( sb.toString( ) );
	}

	/**
	 * Returns record data as json string
	 * 
	 * @param record
	 *            the data source record
	 * 
	 * @return the record data as json string
	 */
	private static native String getJson( JavaScriptObject record ) /*-{
	    var pad = function(n) {
	        return n < 10 ? "0" + n : n;
	    };
	    var m = {
	        "\b": '\\b',
	        "\t": '\\t',
	        "\n": '\\n',
	        "\f": '\\f',
	        "\r": '\\r',
	        '"' : '\\"',
	        "\\": '\\\\'
	    };
	    var encodeString = function(s){
	        if (/["\\\x00-\x1f]/.test(s)) {
	            return '"' + s.replace(/([\x00-\x1f\\"])/g, function(a, b) {
	                var c = m[b];
	                if(c){
	                    return c;
	                }
	                c = b.charCodeAt();
	                return "\\u00" +
	                    Math.floor(c / 16).toString(16) +
	                    (c % 16).toString(16);
	            }) + '"';
	        }
	        return '"' + s + '"';
	    };
	    var items = record.fields.items
	    var cnt=items.length;
	    var result = "["
	    for ( var i=0; i<cnt; ++i ){
	    	var value = record.get(items[i].name);
	    	if($wnd.Ext.isDate(value)) {
	    		value = '"' + value.getFullYear() + "-" +
	            pad(value.getMonth() + 1) + "-" +
	            pad(value.getDate()) + " " +
	            pad(value.getHours()) + ":" +
	            pad(value.getMinutes()) + ":" +
	            pad(value.getSeconds()) + '"';
	    	} else if(typeof value == "number"){
	    		value = isFinite(value) ? String(value) : "null";
	    	} else if(typeof value == "boolean"){
	    		value = String(value);
	    	} else if(typeof value == "string"){
	    		value = encodeString(value);
	    	}
	    	result += value;
	    	if ( i<cnt-1 ) {
	    		result += ",";
	    	} else {
	    		result += "]";
	    	}
	    }
	    return result.toString();
	}-*/;
}
