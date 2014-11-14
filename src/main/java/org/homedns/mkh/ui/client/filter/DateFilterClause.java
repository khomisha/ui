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

package org.homedns.mkh.ui.client.filter;

import java.util.List;
import org.homedns.mkh.dataservice.client.Column;
import com.google.gwt.core.client.JavaScriptObject;
import com.gwtext.client.core.NameValuePair;
import com.gwtext.client.util.JavaScriptObjectHelper;

/**
 * Date filter clause
 *
 */
public class DateFilterClause extends FilterClause {

	public DateFilterClause( ) {
		List< NameValuePair > operators = getOperators( );
		operators.add( new NameValuePair( "before", " < " ) ); 
		operators.add( new NameValuePair( "after", " > " ) ); 
		operators.add( new NameValuePair( "on", " = " ) ); 
	}

	/**
	 * @see org.homedns.mkh.ui.client.filter.FilterClause#getCondition(org.homedns.mkh.dataservice.client.Column, java.lang.Object[])
	 */
	@Override
	public String getCondition( Column col, Object[] values ) {
		StringBuffer sb = new StringBuffer( );
		for( NameValuePair operator : getOperators( ) ) {
			String sValue = JavaScriptObjectHelper.getAttribute( 
				( JavaScriptObject )values[ 0 ], operator.getName( ) 
			);
			if( sValue != null && !"".equals( sValue ) ) {
				if( sb.length( ) > 0 ) {
					sb.append( AND );
				}
				sb.append( col.getDbName( ) );
				sb.append( operator.getValue( ) );
				sb.append( QUOTATION + sValue + QUOTATION );
			}
		}
		return( sb.toString( ) );
	}
}
