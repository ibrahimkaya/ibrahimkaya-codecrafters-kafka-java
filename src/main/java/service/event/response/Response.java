package service.event.response;

import service.event.CorrelationId;
import service.event.ErrorCode;
import service.event.MessageSize;

public record Response(MessageSize messageSize, CorrelationId correlationId, ErrorCode errorCode) {
}
