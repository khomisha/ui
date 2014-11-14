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
import org.homedns.mkh.dataservice.client.Type;
import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.ui.client.WidgetDesc;
import org.homedns.mkh.ui.client.form.ListBox;
import com.gwtext.client.util.JavaScriptObjectHelper;
import com.gwtextux.client.widgets.grid.plugins.GridFilter;
import com.gwtextux.client.widgets.grid.plugins.GridFilterPlugin;

/**
 * Grid filter
 *
 */
public class Filter extends GridFilterPlugin {
	private List< GridFilter > _filters = new ArrayList< GridFilter >( );
	private View _view;

	/**
	 * @param view the widget for which filter will be applied
	 */
	public Filter( View view ) {
		_view = view;
		setFilters( createFilter( ( WidgetDesc )view.getDescription( ) ) );
		setLocalFlag( true );
		setShowMenu( true );
//		setAutoReload( false );
	}

	/**
	 * Returns widget for which filter is applied
	 * 
	 * @return the widget
	 */
	public View getView( ) {
		return _view;
	}

	/**
	* Returns filter conditions
	* 
	* @return filter conditions
	*/
    public String getCondition( ) {
    	StringBuffer sb = new StringBuffer( );
    	for( GridFilter filter : _filters ) {
			String sCondition = filter.getCondition( );
    		if( JavaScriptObjectHelper.getAttributeAsBoolean( filter.getJsObj( ), "active" ) ) {
				if( sb.length( ) > 0 ) {
					sb.append( FilterClause.AND );
				}
				sb.append( sCondition );
    		}
    	}
    	return( sb.toString( ) );
    }
    
    /**
	* Creates grid data filter
	*
	*/
	protected GridFilter[] createFilter( WidgetDesc desc ) {
		for( Column col : desc.getDataBufferDesc( ).getColumns( ) ) {
			String sStyle = col.getStyle( );
			if( "".equals( sStyle ) ) {
				continue;
			} else if( Column.DDDB.equals( sStyle ) || Column.DDLB.equals( sStyle ) ) {
				ListBox listBox = ( ListBox )desc.getCol2Field( ).get( col );
				_filters.add( new ListFilter( col, listBox.getStore( ), new ListFilterClause( ) ) ); 
			} else {
				Type type = col.getColType( );
				if( type == Type.STRING ) {
					_filters.add( new StringFilter( col, new StringFilterClause( ) ) );
				} else if( type == Type.TIMESTAMP ) {
					_filters.add( new DateFilter( col, new DateFilterClause( ) ) );
				} else if( 
					type == Type.INT ||
					type == Type.LONG ||
					type == Type.SHORT ||
					type == Type.BYTE ||
					type == Type.DOUBLE ||
					type == Type.FLOAT ||
					type == Type.BYTE
				) {
					_filters.add( new NumericFilter( col, new NumericFilterClause( ) ) );					
				}
			}
		}
		return( _filters.toArray( new GridFilter[ _filters.size( ) ] ) );
	}
}
