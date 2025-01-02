package service.event;

public record Event(MessageSize messageSize, RequestApiKey requestApiKey, RequestApiVersion requestApiVersion,
                    CorrelationId correlationId) {
}
