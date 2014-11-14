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

import com.gwtext.client.widgets.HTMLPanel;

/**
 * Empty panel
 *
 */
public class DummyPanel extends HTMLPanel {
	public static final String HTML_SPACE = "&nbsp;";

	public DummyPanel( ) {
		super( HTML_SPACE );
	}

	/**
	 * @param paddings the paddings on all sides
	 */
	public DummyPanel( int paddings ) {
		super( HTML_SPACE, paddings );
	}

	/**
	 * @param topPadding the top padding
	 * @param leftPadding the left padding
	 * @param rightPadding the right padding
	 * @param bottomPadding the bottom padding
	 */
	public DummyPanel( int topPadding, int leftPadding, int rightPadding, int bottomPadding ) {
		super( HTML_SPACE, topPadding, leftPadding, rightPadding, bottomPadding );
	}
}
