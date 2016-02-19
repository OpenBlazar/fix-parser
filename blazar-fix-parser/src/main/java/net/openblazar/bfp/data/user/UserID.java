package net.openblazar.bfp.data.user;

/**
 * @author Wojciech Zankowski
 */
public class UserID {

	private final long id;

	public UserID(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserID userID = (UserID) o;

		return id == userID.id;

	}

	@Override
	public int hashCode() {
		return (int) (id ^ (id >>> 32));
	}

	@Override
	public String toString() {
		return "UserID{" +
				"id=" + id +
				'}';
	}

}
