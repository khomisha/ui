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
import com.gwtextux.client.widgets.grid.plugins.GridDateFilter;

/**
 * Filter for grid date columns
 *
 */
public class DateFilter extends GridDateFilter {
	private Column _col;
	private DateFilterClause _clause;

	/**
	 * @param col the column for which filter will be defined
	 */
	public DateFilter( Column col, DateFilterClause clause ) {
		super( col.getName( ) );
		_col = col;
		_clause = clause;
	}

	/**
	 * @see com.gwtextux.client.widgets.grid.plugins.GridFilter#getCondition()
	 */
	@Override
	public String getCondition( ) {
		return( _clause.getCondition( _col, new Object[] { getValue( ) } ) );
	}
}
