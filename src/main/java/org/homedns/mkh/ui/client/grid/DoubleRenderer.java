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

package org.homedns.mkh.ui.client.grid;

import java.util.logging.Logger;
import org.homedns.mkh.dataservice.client.AbstractEntryPoint;
import org.homedns.mkh.ui.client.UIMessages;
import com.google.gwt.i18n.client.NumberFormat;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.grid.CellMetadata;
import com.gwtext.client.widgets.grid.Renderer;

/**
 * Double renderer
 */
public class DoubleRenderer implements Renderer {
	private static final Logger LOG = Logger.getLogger( DoubleRenderer.class.getName( ) );  
	private static final UIMessages MESSAGES = ( UIMessages )AbstractEntryPoint.getMessages( );

	private NumberFormat nf;

	/**
	 * @param sPattern the number format pattern
	 */
	public DoubleRenderer( String sPattern ) {
		setFormat( sPattern );
	}

	/**
	 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object, com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
	 */
	@Override
	public String render( 
		Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum, Store store 
	) {
        return nf.format( ( ( Number )value ).doubleValue( ) );
	}
	
	/**
	 * Sets the number format
	 * 
	 * @param sPattern the number format pattern
	 */
	public void setFormat( String sPattern ) { 
		try {
			nf = NumberFormat.getFormat( sPattern );
		}
		catch( Exception e ) {
			nf = NumberFormat.getDecimalFormat( );
			LOG.warning( MESSAGES.invalidPattern( sPattern ) );
		}
	}
}
