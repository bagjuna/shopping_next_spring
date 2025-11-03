package org.zerock.apiserver.global.common.exception.type;

import org.zerock.apiserver.global.common.exception.enums.BadStatusCode;

public class UnAuthorizedException extends CustomException {
    public UnAuthorizedException(BadStatusCode badStatusCode) {
        super(badStatusCode);
    }
}
