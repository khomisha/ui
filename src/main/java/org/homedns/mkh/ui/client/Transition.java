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

package org.homedns.mkh.ui.client;

import org.homedns.mkh.dataservice.client.AbstractEntryPoint;

/**
 * State transition object
 *
 */
public class Transition {
	/**
	 * locale-sensitive text constants
	 */
	protected static final UIConstants CONSTANTS = ( UIConstants )AbstractEntryPoint.getConstants( );

	private TransitionCommand[][] _transitionTable;

	public Transition( ) {
	}

	/**
	 * @param transitionTable the state transition table
	 */
	public Transition( TransitionCommand[][] transitionTable ) {
		_transitionTable = transitionTable;
	}
	
	/**
	 * Do transition to the new state
	 * 
	 * @param newState
	 *            the context object new state
	 * @param context
	 *            the state transition target object
	 */
	public void doTransition( State newState, HasState context ) {
		TransitionCommand tc = getTransitionCommand( newState, context.getState( ) );
		tc.execute( context );
		context.setState( newState );
	}
	
	/**
	 * Sets state transition table
	 * 
	 * @param transitionTable the state transition table to set
	 */
	public void setTransitionTable( TransitionCommand[][] transitionTable ) {
		_transitionTable = transitionTable;
	}

	/**
	 * Returns transition command for specified current state and new
	 * state
	 * 
	 * @param newState
	 *            the new state
	 * @param currentState
	 *            the current state
	 * 
	 * @return the transition command
	 */
	protected TransitionCommand getTransitionCommand( State newState, State currentState ) {
		return( _transitionTable[ currentState.getOrdinal( ) ][ newState.getOrdinal( ) ] );
	}
	
	protected class NoCommand implements TransitionCommand {
		
		public NoCommand( ) {
		}

		@Override
		public void execute( HasState context ) {
		}
	}
	
	protected class WrongTransitionCommand implements TransitionCommand {
		
		public WrongTransitionCommand( ) {
		}

		@Override
		public void execute( HasState context ) {
			throw new UnsupportedOperationException( CONSTANTS.wrongTransition( ) );
		}	
	}
}
