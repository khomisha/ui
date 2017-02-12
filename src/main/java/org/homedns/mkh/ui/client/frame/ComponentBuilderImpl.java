/*
 * Copyright 2016 Mikhail Khodonov
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

import org.homedns.mkh.dataservice.client.view.View;
import org.homedns.mkh.dataservice.shared.Id;
import org.homedns.mkh.ui.client.form.BoundForm;
import org.homedns.mkh.ui.client.form.ChildBoundForm;
import org.homedns.mkh.ui.client.form.FormConfig;
import org.homedns.mkh.ui.client.form.GridForm;
import org.homedns.mkh.ui.client.form.GridFormConfig;
import org.homedns.mkh.ui.client.grid.ChildEditorGrid;
import org.homedns.mkh.ui.client.grid.ChildGrid;
import org.homedns.mkh.ui.client.grid.EditorGrid;
import org.homedns.mkh.ui.client.grid.EditorGridConfig;
import org.homedns.mkh.ui.client.grid.EditorGridImpl;
import org.homedns.mkh.ui.client.grid.Grid;
import org.homedns.mkh.ui.client.grid.GridConfig;
import org.homedns.mkh.ui.client.grid.GridImpl;
import org.homedns.mkh.ui.client.grid.PropertyGrid;
import org.homedns.mkh.ui.client.grid.PropertyGridImpl;

/**
 * Component builder implementation {@link org.homedns.mkh.ui.client.frame.ComponentBuilder} 
 *
 */
public class ComponentBuilderImpl implements ComponentBuilder {
	private GenericPanel panel;

	public ComponentBuilderImpl( ) {
	}

	public ComponentBuilderImpl( GenericPanel panel ) {
		this.panel = panel;
	}

	/**
	 * Returns parent panel
	 * 
	 * @return the parent panel
	 */
	public GenericPanel getPanel( ) {
		return( panel );
	}

	/**
	 * Sets parent panel
	 * 
	 * @param panel the parent panel to set
	 */
	public void setPanel( GenericPanel panel ) {
		this.panel = panel;
	}

	/**
	 * @see org.homedns.mkh.ui.client.frame.ComponentBuilder#getComponent(java.lang.Class, org.homedns.mkh.dataservice.shared.Id)
	 */
	@Override
	public View getComponent( Class< ? > type, Id id ) {
		View c = null;
		if( type == org.homedns.mkh.ui.client.grid.Grid.class ) {
			c = getGrid( id );
		} else if( type == EditorGrid.class ) {
			c = getEditorGrid( id );			
		} else if( type == PropertyGrid.class ) {
			c = getPropertyGrid( id );						
		} else if( type == GridForm.class ) {
			c = getGridForm( id );
		} else if( type == BoundForm.class ) {
			c = getForm( id );
		} else {
		}
		return( c );
	}

	/**
	 * @see org.homedns.mkh.ui.client.frame.ComponentBuilder#getChildComponent(java.lang.Class, org.homedns.mkh.dataservice.shared.Id, org.homedns.mkh.dataservice.shared.Id, java.lang.String)
	 */
	@Override
	public View getChildComponent( 
		Class< ? > type, 
		Id id, 
		Id masterId,
		String sMasterPK 
	) {
		View c = null;
		if( type == ChildGrid.class ) {
			c = getChildGrid( id, masterId, sMasterPK );
		} else if( type == ChildEditorGrid.class ) {
			c = getChildEditorGrid( id, masterId, sMasterPK );
		} else if( type == ChildBoundForm.class ) {
			c = getChildForm( id, masterId, sMasterPK );
		} else {
		}
		return( c );
	}

	/**
	 * Returns grid
	 * 
	 * @param id
	 *            the identification object
	 * 
	 * @return the grid
	 */
	protected Grid getGrid( Id id ) {
		GridConfig cfg = defineGridConfig( );
		if( panel != null ) {
			panel.onInit( id.getName( ), cfg );
		}
		Grid grid = new Grid( id, new GridImpl( cfg ) );
		return( grid );
	}

	/**
	 * Defines grid configuration object
	 * 
	 * @return the grid configuration object
	 */
	protected GridConfig defineGridConfig( ) {
		return( new GridConfig( ) );
	}

	/**
	 * Returns editor grid
	 * 
	 * @param id
	 *            the identification object
	 * 
	 * @return the editor grid
	 */
	protected EditorGrid getEditorGrid( Id id ) {
		EditorGridConfig cfg = defineEditorGridConfig( );
		if( panel != null ) {
			panel.onInit( id.getName( ), cfg );
		}
		EditorGrid grid = new EditorGrid( id, new EditorGridImpl( cfg ) );
		return( grid );
	}

	/**
	 * Defines editor grid configuration object
	 * 
	 * @return the editor grid configuration object
	 */
	protected EditorGridConfig defineEditorGridConfig( ) {
		return( new EditorGridConfig( ) );
	}

	/**
	 * Returns property grid
	 * 
	 * @param id
	 *            the identification object
	 * 
	 * @return the property grid
	 */
	protected PropertyGrid getPropertyGrid( Id id ) {
		GridConfig cfg = definePropertyGridConfig( );
		if( panel != null ) {
			panel.onInit( id.getName( ), cfg );
		}
		PropertyGrid grid = new PropertyGrid( id, new PropertyGridImpl( cfg ) );
		return( grid );
	}

	/**
	 * Defines property grid configuration object
	 * 
	 * @return the property grid configuration object
	 */
	protected GridConfig definePropertyGridConfig( ) {
		return( new GridConfig( ) );
	}

	/**
	 * Defines grid form configuration object
	 * 
	 * @return the grid form configuration object
	 */
	protected GridFormConfig defineGridFormConfig( ) {
		return( new GridFormConfig( ) );
	}
	
	/**
	 * Defines grid form
	 * 
	 * @param id
	 *            the view identification object
	 * 
	 * @return the grid form
	 */
	protected GridForm getGridForm( Id id ) {
		GridFormConfig cfg = defineGridFormConfig( );
		if( panel != null ) {
			panel.onInit( id.getName( ), cfg );
		}
		return( new GridForm( id, cfg ) );
	}

	/**
	 * Defines form configuration object
	 * 
	 * @return the form configuration object
	 */
	protected FormConfig defineFormConfig( ) {
		return( new FormConfig( ) );
	}
	
	/**
	 * Defines bound form
	 * 
	 * @param id
	 *            the view identification object
	 * 
	 * @return the bound form
	 */
	protected BoundForm getForm( Id id ) {
		FormConfig cfg = defineFormConfig( );
		if( panel != null ) {
			panel.onInit( id.getName( ), cfg );
		}
		return( new BoundForm( id, cfg ) );
	}

	/**
	 * Returns child grid
	 * 
	 * @param id
	 *            the grid identification object
	 * @param masterId
	 *            the master grid identification object
	 * @param sMasterPK 
	 * 			  the master grid primary key name
	 * 
	 * @return the child grid
	 */
	protected ChildGrid getChildGrid( Id id, Id masterId, String sMasterPK ) {
		GridConfig cfg = defineChildGridConfig( );
		if( panel != null ) {
			panel.onInit( id.getName( ), cfg );
		}
		ChildGrid grid = new ChildGrid( id, masterId, sMasterPK, new GridImpl( cfg ) );
		return( grid );
	}

	/**
	 * Defines child grid configuration object
	 * 
	 * @return the child grid configuration object
	 */
	protected GridConfig defineChildGridConfig( ) {
		return( new GridConfig( ) );
	}

	/**
	 * Returns child editable grid
	 * 
	 * @param id
	 *            the grid identification object
	 * @param masterId
	 *            the master grid identification object
	 * @param sMasterPK 
	 * 			  the master grid primary key name
	 * 
	 * @return the child editable grid
	 */
	protected ChildEditorGrid getChildEditorGrid( Id id, Id masterId, String sMasterPK ) {
		EditorGridConfig cfg = defineChildEditorGridConfig( );
		if( panel != null ) {
			panel.onInit( id.getName( ), cfg );
		}
		ChildEditorGrid grid = new ChildEditorGrid( id, masterId, sMasterPK, new EditorGridImpl( cfg ) );
		return( grid );
	}

	/**
	 * Defines child editor grid configuration object
	 * 
	 * @return the child editor grid configuration object
	 */
	protected EditorGridConfig defineChildEditorGridConfig( ) {
		return( new EditorGridConfig( ) );
	}

	/**
	 * Defines child bound form
	 * 
	 * @param id
	 *            the form identification object
	 * @param masterId
	 *            the master grid identification object
	 * @param sMasterPK 
	 * 			  the master grid primary key name
	 * 
	 * @return the child bound form
	 */
	protected ChildBoundForm getChildForm( Id id, Id masterId, String sMasterPK ) {
		FormConfig cfg = defineChildFormConfig( );
		if( panel != null ) {
			panel.onInit( id.getName( ), cfg );
		}
		return( new ChildBoundForm( id, masterId, sMasterPK, cfg ) );
	}

	/**
	 * Defines child form configuration object
	 * 
	 * @return the child form configuration object
	 */
	protected FormConfig defineChildFormConfig( ) {
		return( new FormConfig( ) );
	}
}
