package service.event.request;

public enum RequestType {
    API_VERSIONS((short)18);

   private final Object value;

    RequestType(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
