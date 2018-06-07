/*
 * Copyright 2018 Mikhail Khodonov
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

package org.homedns.mkh.ui.client;

import java.util.ArrayList;
import java.util.List;
import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.ui.client.cache.WidgetStore;
import org.homedns.mkh.ui.client.frame.DummyPanel;
import org.homedns.mkh.dataservice.shared.Util;
import com.google.gwt.i18n.client.NumberFormat;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarTextItem;
import com.gwtext.client.widgets.Panel;

/**
 * Stores view's totals and 
 * if it is specified outputs totals to the view bottom toolbar
 */
public class Total {
	private WidgetStore store;
	private String sTotal = "";
	private List< TotalItem > totals;
	private boolean bToolbarOutput;
	private ToolbarTextItem tbItem;
	private String sBlank;

	/**
	 * @param view the target view
	 * @param asColName the column names array for which the totals will be calculated
	 * @param bToolbarOutput the totals output to the toolbar flag
	 * @param iDecimal the number of decimal places
	 */
	public Total( View view, String[] asColName, boolean bToolbarOutput ) {
		this.bToolbarOutput = bToolbarOutput;
		if( bToolbarOutput ) {
			sBlank = Util.fill( DummyPanel.HTML_SPACE, 18 );
			Toolbar tb = ( ( Panel )view ).getBottomToolbar( );
			if( tb != null ) {
				tb.addSeparator( );
				tbItem = new ToolbarTextItem( sTotal );
				tb.addItem( tbItem );
			}
		} else {
			sBlank = "   ";
		}
		WidgetDesc desc = ( WidgetDesc )view.getDescription( );
		store = ( WidgetStore )view.getCache( );
		totals = new ArrayList< TotalItem >( );
		for( String sColName : asColName ) {
			String sCaption = desc.getDataBufferDesc( ).getColumn( sColName ).getCaption( );
			String sFormat = desc.getDataBufferDesc( ).getColumn( sColName ).getPattern( );
			TotalItem item = new TotalItem( sColName, sCaption, store.sum( sColName ), sFormat );
			totals.add( item );
		}
	}
	
	/**
	 * Refreshes totals
	 */
	public void refresh( ) {
		sTotal = "";
		for( TotalItem item : totals ) {
			item.setTotal( store.sum( item.getColName( ) ) );
			sTotal += item.toString( ) + sBlank;
		}
		if( bToolbarOutput ) {
			tbItem.setText( sTotal );
		}
	}
	
	/**
	 * Returns totals as string
	 * 
	 * @return the totals as string
	 */
	public String getTotal( ) {
		return( sTotal );
	}
	
	/**
	 * The total item
	 */
	private class TotalItem {
		private String sColName;
		private String sColCaption;
		private float fTotal;
		private String sFormat;
		
		/**
		 * @param sColName the column name
		 * @param sColCaption the column caption
		 * @param fTotal the total
		 * @param sFormat the number format
		 */
		public TotalItem( String sColName, String sColCaption, float fTotal, String sFormat ) {
			setFormat( sFormat );
			setColName( sColName );
			setColCaption( sColCaption );
			setTotal( fTotal );
		}
		
		/**
		 * Returns column name
		 * 
		 * @return the column name
		 */
		public String getColName( ) {
			return( sColName );
		}
		
		/**
		 * Sets column name 
		 * 
		 * @param sColName the column name to set
		 */
		public void setColName( String sColName ) {
			this.sColName = sColName;
		}
		
		/**
		 * Sets column caption 
		 * 
		 * @param sColCaption the column caption to set
		 */
		public void setColCaption( String sColCaption ) {
			this.sColCaption = sColCaption;
		}
		
		/**
		 * Sets total 
		 * 
		 * @param fTotal the total to set
		 */
		public void setTotal( float fTotal ) {
			this.fTotal = fTotal;
		}

		/**
		 * @param sFormat the sFormat to set
		 */
		public void setFormat( String sFormat ) {
			if( "".equals( sFormat ) ) {
				sFormat = NumberFormat.getDecimalFormat( ).getPattern( );
			}
			this.sFormat = sFormat;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString( ) {
			return( sColCaption + ": " + NumberFormat.getFormat( sFormat ).format( fTotal ) );
		}
	}
}
