package event;

public record RequestApiVersion(byte[] version) implements EventPart<Integer> {

    @Override
    public String getName() {
        return "request_api_version";
    }

    @Override
    public int length() {
        return 2;
    }
}
