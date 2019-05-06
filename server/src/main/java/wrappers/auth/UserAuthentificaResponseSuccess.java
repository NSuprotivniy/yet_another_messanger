package wrappers.auth;

import wrappers.JsonRPCRequestWrapper;

public class UserAuthentificaResponseSuccess extends JsonRPCRequestWrapper
{
    private UserAuthentificaResponseParams  params;

    public UserAuthentificaResponseSuccess(String uuid) {
        this.params = new UserAuthentificaResponseParams(uuid);
    }

    public UserAuthentificaResponseParams getParams() {
        return params;
    }

    public class UserAuthentificaResponseParams {
        private String uuid;

        public UserAuthentificaResponseParams(String uuid) {
            this.uuid = uuid;
        }

        public String getUuid() {
            return uuid;
        }
    }
}
