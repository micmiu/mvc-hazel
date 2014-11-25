package com.micmiu.mvc.hazel.core.action.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public class CaptchaException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public CaptchaException() {

		super();

	}

	public CaptchaException(String message, Throwable cause) {

		super(message, cause);

	}

	public CaptchaException(String message) {

		super(message);

	}

	public CaptchaException(Throwable cause) {

		super(cause);

	}

}
