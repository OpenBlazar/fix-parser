package net.openblazar.bfp.data.user;

/**
 * @author Wojciech Zankowski
 */
public enum UserState {

	ACTIVE(1),
	INACTIVE(0),
	UNKNOWN(-1);

	private final int state;

	UserState(int state) {
		this.state = state;
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
