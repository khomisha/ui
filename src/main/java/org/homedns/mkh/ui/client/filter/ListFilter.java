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
import com.gwtext.client.data.Store;
import com.gwtextux.client.widgets.grid.plugins.GridListFilter;

/**
 * Filter for combo box grid columns
 *
 */
public class ListFilter extends GridListFilter {
	private Column _col;
	private ListFilterClause _clause;

	/**
	 * @param col
	 *            the column for which filter will be defined
	 * @param store
	 *            the store
	 * @param clause
	 *            the filter clause
	 */
	public ListFilter( Column col, Store store, ListFilterClause clause ) {
		super( col.getName( ), store, col.getCaption( ) );
		_col = col;
		_clause = clause;
	}

	/**
	 * @see com.gwtextux.client.widgets.grid.plugins.GridFilter#getCondition()
	 */
	@Override
	public String getCondition( ) {
		return( _clause.getCondition( _col, getValue( ) ) );
	}
}
