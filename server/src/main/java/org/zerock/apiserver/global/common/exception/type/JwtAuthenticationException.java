package org.zerock.apiserver.global.common.exception.type;

import org.zerock.apiserver.global.common.exception.enums.BadStatusCode;

public class JwtAuthenticationException extends CustomException {

	public JwtAuthenticationException(BadStatusCode badStatusCode) {
		super(badStatusCode);
	}

}
