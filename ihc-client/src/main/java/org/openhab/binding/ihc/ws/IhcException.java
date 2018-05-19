/**
 * Copyright (c) 2010-2014, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.ihc.ws;

/**
 * Exception for handling communication errors to controller.
 * 
 * @author Pauli Anttila
 * @since 1.5.0
 */
public class IhcException extends Exception {

	private static final long serialVersionUID = -8048415193494625295L;

	public IhcException() {
		super();
	}

	public IhcException(String message) {
		super(message);
	}

	public IhcException(String message, Throwable cause) {
		super(message, cause);
	}

	public IhcException(Throwable cause) {
		super(cause);
	}

}
