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

import org.homedns.mkh.dataservice.client.Page;
import com.google.gwt.core.client.JavaScriptObject;
import com.gwtext.client.data.DataProxy;

/**
 * Store supporting data paging
 *
 */
public class PagerStore extends WidgetStore implements Pager {
	private Page page;

	public PagerStore( ) {
		page = new Page( );
	}

	/**
	 * @see org.homedns.mkh.ui.client.cache.Pager#loadPage(org.homedns.mkh.dataservice.client.Page)
	 */
	public void loadPage( Page page ) {
		if( page.getPageSize( ) == 0 ) {
			load( );
		} else {
			load( page.getFirstRow( ), page.getPageSize( ) );
		}
	}
	
	/**
	 * Returns page object
	 * 
	 * @return the page
	 */
	public Page getPage( ) {
		return( page );
	}

	/**
	 * Sets page object
	 * 
	 * @param page the page to set
	 */
	public void setPage( Page page ) {
		this.page = page;
	}

	/**
	 * @see org.homedns.mkh.ui.client.cache.WidgetStore#setRowCount(java.lang.Integer)
	 */
	@Override
	public void setRowCount( Integer iRowCount ) {
		super.setRowCount( iRowCount );
		page.setRowCount( iRowCount );
	}

	/**
	 * @see org.homedns.mkh.ui.client.cache.WidgetStore#reload(com.google.gwt.core.client.JavaScriptObject)
	 */
	@Override
	protected void reload( JavaScriptObject data ) {
		setDataProxy( createProxy( data ) );
//		loadPage( page );
		if( page.getPageSize( ) == 0 ) {
			load( );
		} else {
			load( 0, page.getPageSize( ) );
		}
	}

	/**
	 * @see org.homedns.mkh.ui.client.cache.WidgetStore#init()
	 */
	@Override
	protected void init( ) {
		super.init( );
		page.setPageSize( getViewDesc( ).getDataBufferDesc( ).getTable( ).getPageSize( ) );
		setRemoteSort( true );
	}

	/**
	 * @see org.homedns.mkh.ui.client.cache.WidgetStore#createProxy(com.google.gwt.core.client.JavaScriptObject)
	 */
	@Override
	public DataProxy createProxy( JavaScriptObject data ) {
		return( new CustomPagingMemoryProxy( data ) );
	}
}
