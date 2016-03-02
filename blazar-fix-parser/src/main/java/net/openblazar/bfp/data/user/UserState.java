package net.openblazar.bfp.data.user;

import java.io.Serializable;

/**
 * @author Wojciech Zankowski
 */
public enum UserState implements Serializable {

    ACTIVE(1),
    INACTIVE(0),
    UNKNOWN(-1);

    private final int state;

    UserState(int state) {
        this.state = state;
    }

    public static UserState getUserStateFromCode(int code) {
        for (UserState userState : values()) {
            if (userState.getState() == code) {
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
