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

package org.homedns.mkh.ui.client.event;

import org.homedns.mkh.dataservice.client.event.GenericEvent;
import org.homedns.mkh.dataservice.shared.Id;
import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event;
import com.gwtext.client.data.Record;

/**
 * Select row event
 *
 */
public class SelectRowEvent extends GenericEvent< SelectRowEvent.SelectRowHandler > {
			
	/**
	 * Implemented by objects that handle.
	 */
	public interface SelectRowHandler extends EventHandler {
		public void onSelectRow( SelectRowEvent event );
	}
	  
	private Record _record;
	public static final Event.Type< SelectRowHandler > TYPE = new Event.Type< SelectRowHandler >( );

	/**
	 * Fires select row event
	 * 
	 * @param id the event source - identification object
	 * @param record the selected record
	 */
	public static void fire( Id id, Record record ) {
		SelectRowEvent event = new SelectRowEvent( );
		event.setRecord( record );
		fire( event, id.hashCode( ) );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.event.GenericEvent#getType()
	 */
	@Override
	protected Event.Type< SelectRowHandler > getType( ) {
		return( TYPE );
	}

	/**
	 * @see org.homedns.mkh.dataservice.client.event.GenericEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch( SelectRowHandler handler ) {
		handler.onSelectRow( this );
	}

	/**
	 * Returns record
	 * 
	 * @return the record
	 */
	public Record getRecord( ) {
		return( _record );
	}

	/**
	 * Sets record
	 * 
	 * @param record the record to set
	 */
	public void setRecord( Record record ) {
		_record = record;
	}
}
