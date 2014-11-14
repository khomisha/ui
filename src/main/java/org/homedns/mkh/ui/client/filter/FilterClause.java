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

import java.util.ArrayList;
import java.util.List;
import org.homedns.mkh.dataservice.client.Column;
import com.gwtext.client.core.NameValuePair;

/**
 * Abstract filter clause
 *
 */
public abstract class FilterClause {
	public static final String COMMA		= ",";
	public static final String QUOTATION  	= "'";
	public static final String AND   		= " and ";
	
	private List< NameValuePair > _operators = new ArrayList< NameValuePair >( );

	/**
	 * Returns filter condition for specified column
	 * 
	 * @param col the column
	 * @param values the filter values
	 * 
	 * @return the filter condition
	 */
	public abstract String getCondition( Column col, Object[] values );
	
	/**
	 * Returns filter's comparison operators name-value list 
	 * 
	 * @return the filter's comparison operators list
	 */
	protected List< NameValuePair > getOperators( ) {
		return( _operators );
	}
}
