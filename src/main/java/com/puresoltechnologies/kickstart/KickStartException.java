/***************************************************************************
 *
 * Copyright 2009-2010 PureSol Technologies 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 *
 ***************************************************************************/

package com.puresoltechnologies.kickstart;

/**
 * This exception is thrown in events of KickStart failures.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class KickStartException extends Exception {

	private static final long serialVersionUID = 7801837046640248848L;

	public KickStartException(String message) {
		super(message);
	}

}
