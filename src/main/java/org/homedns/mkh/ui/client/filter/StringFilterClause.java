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

import org.homedns.mkh.dataservice.client.Column;

/**
 * String filter clause
 *
 */
public class StringFilterClause extends FilterClause {

	/**
	 * @see org.homedns.mkh.ui.client.filter.FilterClause#getCondition(org.homedns.mkh.dataservice.client.Column, java.lang.Object[])
	 */
	@Override
	public String getCondition( Column col, Object[] values ) {
		String sCondition = "";
		String sValue = ( String )values[ 0 ];
		if( sValue != null && !"".equals( sValue ) ) {
			sCondition = col.getDbName( ) + " like " + QUOTATION + "%" + sValue + "%" + QUOTATION;
		}
		return( sCondition );
	}
}
