package pl.zankowski.fixparser.user.entity;

import pl.zankowski.fixparser.core.entity.IEntity;

public enum UserState implements IEntity {

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
