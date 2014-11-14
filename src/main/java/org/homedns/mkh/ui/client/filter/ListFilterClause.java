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

import java.util.Arrays;
import org.homedns.mkh.dataservice.client.Column;
import org.homedns.mkh.dataservice.client.Type;

/**
 * List box filter clause 
 *
 */
public class ListFilterClause extends FilterClause {

	/**
	 * @see org.homedns.mkh.ui.client.filter.FilterClause#getCondition(org.homedns.mkh.dataservice.client.Column, java.lang.Object[])
	 */
	@Override
	public String getCondition( Column col, Object[] values ) {
		StringBuffer sb = new StringBuffer( );
		StringBuffer valueList = getValues( col, values );
		if( valueList.length( ) > 0 ) {
			sb.append( col.getDbName( ) );
			sb.append( " in(" );
			sb.append( valueList );
			sb.append( ")" );
		}
		return( sb.toString( ) );
	}

    /**
	* Returns filter values
	* 
	* @return filter values
	*/
	private StringBuffer getValues( Column col, Object[] values ) {
		StringBuffer sb = new StringBuffer( );
		String sQuote = ( Type.STRING == col.getColType( ) ) ? QUOTATION : "";
		String[] asValue = Arrays.asList( values ).toArray( new String[ values.length ] );
		int iValue = 0;
		for( String sValue : asValue ) {
			if( sValue == null || "".equals( sValue ) ) {
				continue;
			}
			sb.append( sQuote + sValue + sQuote );
			if( iValue < asValue.length - 1 ) {
				sb.append( COMMA );
			}
			iValue++;
		}
		return( sb );
	}
}
