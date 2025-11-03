package org.zerock.apiserver.global.common.exception.type;

import org.zerock.apiserver.global.common.exception.enums.BadStatusCode;

public class NotFoundException extends CustomException {
    public NotFoundException(BadStatusCode badStatusCode) {
        super(badStatusCode);
    }
}
