package org.zerock.apiserver.global.common.exception.type;

import org.zerock.apiserver.global.common.exception.enums.BadStatusCode;

public class ForbiddenException extends CustomException {
    public ForbiddenException(BadStatusCode badStatusCode) {
        super(badStatusCode);
    }
}
