package org.zerock.apiserver.global.common.exception.type;

import org.zerock.apiserver.global.common.exception.enums.BadStatusCode;

public class ServerErrorException extends CustomException {
    public ServerErrorException(BadStatusCode badStatusCode) {
        super(badStatusCode);
    }
}
