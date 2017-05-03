/*
 * Copyright 2017 Mikhail Khodonov
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

package org.homedns.mkh.ui.client.command;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.user.client.Command;

/**
 * Command scheduler executes commands on periodical basis
 */
public class CommandScheduler {
	private static CommandScheduler scheduler;

	private CommandScheduler( ) {
	}

	/**
	 * Returns command scheduler instance
	 * 
	 * @return the command scheduler instance
	 */
	public static CommandScheduler get( ) {
		if( scheduler == null ) {
			scheduler = new CommandScheduler( );
		}
		return( scheduler );
	}
	
	/**
	 * Adds command to the schedule. Command will be executed repeatedly with
	 * specified delay.
	 * {@link com.google.gwt.core.client.Scheduler#scheduleFixedDelay(RepeatingCommand, int)}
	 * 
	 * @param cmd
	 *            the command to execute
	 * @param iDelayMs
	 *            the delay in millis
	 */
	public void addCmd2Schedule( Command cmd,  int iDelayMs ) {
		Scheduler.get( ).scheduleFixedDelay( new ScheduleCommand( cmd ), iDelayMs );
	}
	
	private class ScheduleCommand implements RepeatingCommand {
		private Command cmd;

		/**
		 * @param cmd the command to execute
		 */
		public ScheduleCommand( Command cmd ) {
			this.cmd = cmd;
		}

		/**
		 * @see com.google.gwt.core.client.Scheduler.RepeatingCommand#execute()
		 */
		@Override
		public boolean execute( ) {
			cmd.execute( );
			return( true );
		}
	}
}
