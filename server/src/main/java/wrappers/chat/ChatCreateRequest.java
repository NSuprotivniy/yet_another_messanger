package wrappers.chat;
import wrappers.JsonRPCRequestWrapper;


public class ChatCreateRequest extends JsonRPCRequestWrapper {
    private ChatCreateParams params;

    public ChatCreateParams getParams() {
        return params;
    }
}
