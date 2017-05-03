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

package org.homedns.mkh.ui.client.form;

import org.homedns.mkh.ui.client.HasState;
import org.homedns.mkh.ui.client.Transition;
import org.homedns.mkh.ui.client.TransitionCommand;

/**
 * Default grid form transition object
 *
 */
public class GridFormTransition extends Transition {

	public GridFormTransition( ) {
		TransitionCommand noCmd = new NoCommand( );
		TransitionCommand wrongCmd = new WrongTransitionCommand( );
		TransitionCommand roCmd = new Transition2ReadOnlyCommand( );
		TransitionCommand addCmd = new Transition2AddCommand( );
		TransitionCommand updateCmd = new Transition2UpdateCommand( );
		TransitionCommand nostateCmd = new Transition2NoStateCommand( );
		TransitionCommand[][] transitionTable = new TransitionCommand[][] {
			{ noCmd, roCmd, addCmd, updateCmd },
			{ nostateCmd, noCmd, addCmd, updateCmd },
			{ nostateCmd, roCmd, noCmd, wrongCmd },
			{ nostateCmd, roCmd, wrongCmd, noCmd }
		};
		setTransitionTable( transitionTable );
	}
	
	protected class Transition2AddCommand implements TransitionCommand {
		/**
		 * @see org.homedns.mkh.ui.client.TransitionCommand#execute(org.homedns.mkh.ui.client.HasState)
		 */
		@Override
		public void execute( HasState context ) {
			if( context instanceof GridForm ) {
				GridForm form = ( GridForm )context;
				form.setVisibleButton( CONSTANTS.save( ), true );
				form.setVisibleButton( CONSTANTS.update( ), false );
				form.setVisibleButton( CONSTANTS.add( ), false );
				form.setVisibleButton( CONSTANTS.del( ), false );
				form.setVisibleButton( CONSTANTS.cancel( ), true );
				form.setDisabledFields( false );
				form.getForm( ).reset( );
				form.initValues( );
				form.getFields( )[ 0 ].focus( );
			}
		}
	}

	protected class Transition2UpdateCommand implements TransitionCommand {
		/**
		 * @see org.homedns.mkh.ui.client.TransitionCommand#execute(org.homedns.mkh.ui.client.HasState)
		 */
		@Override
		public void execute( HasState context ) {
			if( context instanceof GridForm ) {
				GridForm form = ( GridForm )context;
				form.setVisibleButton( CONSTANTS.save( ), true );
				form.setVisibleButton( CONSTANTS.update( ), false );
				form.setVisibleButton( CONSTANTS.add( ), false );
				form.setVisibleButton( CONSTANTS.del( ), false );
				form.setVisibleButton( CONSTANTS.cancel( ), true );
				form.setDisabledFields( false );
				form.getFields( )[ 0 ].focus( );
			}
		}
	}

	protected class Transition2ReadOnlyCommand implements TransitionCommand {
		/**
		 * @see org.homedns.mkh.ui.client.TransitionCommand#execute(org.homedns.mkh.ui.client.HasState)
		 */
		@Override
		public void execute( HasState context ) {
			if( context instanceof GridForm ) {
				GridForm form = ( GridForm )context;
				form.setVisibleButton( CONSTANTS.save( ), false );
				form.setVisibleButton( CONSTANTS.update( ), true );
				form.setVisibleButton( CONSTANTS.add( ), true );
				form.setVisibleButton( CONSTANTS.del( ), true );
				form.setVisibleButton( CONSTANTS.cancel( ), false );
				form.setDisabledFields( true );
				form.refresh( );
			}
		}
	}

	protected class Transition2NoStateCommand implements TransitionCommand {
		/**
		 * @see org.homedns.mkh.ui.client.TransitionCommand#execute(org.homedns.mkh.ui.client.HasState)
		 */
		@Override
		public void execute( HasState context ) {
			if( context instanceof GridForm ) {
				GridForm form = ( GridForm )context;
				form.setVisibleButton( CONSTANTS.save( ), false );
				form.setVisibleButton( CONSTANTS.update( ), false );
				form.setVisibleButton( CONSTANTS.add( ), true );
				form.setVisibleButton( CONSTANTS.del( ), false );
				form.setVisibleButton( CONSTANTS.cancel( ), false );
				form.setDisabledFields( true );
			}
		}
	}
}
