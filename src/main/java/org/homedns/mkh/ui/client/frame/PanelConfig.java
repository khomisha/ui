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

package org.homedns.mkh.ui.client.frame;

import java.util.ArrayList;
import java.util.List;
import com.gwtext.client.widgets.layout.ContainerLayout;

/**
 * Panel configuration object
 *
 */
public class PanelConfig {
	private ContainerLayout _layout;
	private List< ChildContainer > _childs;
	
	public PanelConfig( ) {
		setChilds( new ArrayList< ChildContainer >( ) );		
	}

	/**
	 * @param layout
	 *            the panel layout
	 */
	public PanelConfig( ContainerLayout layout ) {
		this( );
		setLayout( layout );
	}
	
	/**
	 * Adds child panel
	 * 
	 * @param child
	 *            the child panel to add
	 * 
	 * @return true if addition successful and false otherwise
	 * @see java.util.Collection#add(Object)
	 */
	public boolean add( ChildContainer child ) {
		return( _childs.add( child ) );
	}
	
	/**
	 * Adds child panel
	 * 
	 * @param iIndex
	 *            the index at which the specified element is to be added
	 * @param child
	 *            the child panel to add
	 */
	public void add( int iIndex, ChildContainer child ) {
		_childs.add( iIndex, child );
	}

	/**
	 * Returns panel layout
	 * 
	 * @return the panel layout
	 */
	public ContainerLayout getLayout( ) {
		return( _layout );
	}
	
	/**
	 * Returns child panels list
	 * 
	 * @return the child panels
	 */
	public List< ChildContainer > getChilds( ) {
		return( _childs );
	}
	
	/**
	 * Sets panel layout
	 * 
	 * @param layout
	 *            the panel layout to set
	 */
	public void setLayout( ContainerLayout layout ) {
		_layout = layout;
	}
	
	/**
	 * Sets child panels
	 * 
	 * @param childs
	 *            the child panels to set
	 */
	public void setChilds( List< ChildContainer > childs ) {
		_childs = childs;
	}
}
