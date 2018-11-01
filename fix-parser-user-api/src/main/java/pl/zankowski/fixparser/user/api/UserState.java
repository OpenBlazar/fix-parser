package pl.zankowski.fixparser.user.api;

import pl.zankowski.fixparser.core.ITransferObject;

public enum UserState implements ITransferObject {

    ACTIVE(1),
    INACTIVE(0),
    UNKNOWN(-1);

    private final int state;

    UserState(int state) {
        this.state = state;
    }

    public static UserState getUserStateFromCode(Integer code) {
        for (UserState userState : values()) {
            if (Integer.valueOf(userState.getState()).equals(code)) {
                return userState;
            }
        }
        return UNKNOWN;
    }

    public int getState() {
        return state;
    }

    @Override
    public String toString() {
        return "UserState{" +
                "state=" + state +
                '}';
    }
}
