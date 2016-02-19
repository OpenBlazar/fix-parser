package net.openblazar.bfp.data.user;

import java.time.Instant;

/**
 * @author Wojciech Zankowski
 */
public class UserDetails {

	private final UserID userID;
	private final String userName;
	private final String userMail;
	private final String password;
	private final Boolean isActive;
	private final Instant registrationDate;
	private final Instant lastLogin;

	public UserDetails(UserID userID, String userName, String userMail, String password, Boolean isActive,
	                   Instant registrationDate, Instant lastLogin) {
		this.userID = userID;
		this.userName = userName;
		this.userMail = userMail;
		this.password = password;
		this.isActive = isActive;
		this.registrationDate = registrationDate;
		this.lastLogin = lastLogin;
	}

	public UserID getUserID() {
		return userID;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserMail() {
		return userMail;
	}

	public String getPassword() {
		return password;
	}

	public boolean isActive() {
		return isActive;
	}

	public Instant getRegistrationDate() {
		return registrationDate;
	}

	public Instant getLastLogin() {
		return lastLogin;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserDetails that = (UserDetails) o;

		if (getUserID() != null ? !getUserID().equals(that.getUserID()) : that.getUserID() != null) return false;
		if (getUserName() != null ? !getUserName().equals(that.getUserName()) : that.getUserName() != null)
			return false;
		if (getUserMail() != null ? !getUserMail().equals(that.getUserMail()) : that.getUserMail() != null)
			return false;
		if (getPassword() != null ? !getPassword().equals(that.getPassword()) : that.getPassword() != null)
			return false;
		if (isActive != null ? !isActive.equals(that.isActive) : that.isActive != null) return false;
		if (getRegistrationDate() != null ? !getRegistrationDate().equals(that.getRegistrationDate()) : that.getRegistrationDate() != null)
			return false;
		return getLastLogin() != null ? getLastLogin().equals(that.getLastLogin()) : that.getLastLogin() == null;

	}

	@Override
	public int hashCode() {
		int result = getUserID() != null ? getUserID().hashCode() : 0;
		result = 31 * result + (getUserName() != null ? getUserName().hashCode() : 0);
		result = 31 * result + (getUserMail() != null ? getUserMail().hashCode() : 0);
		result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
		result = 31 * result + (isActive != null ? isActive.hashCode() : 0);
		result = 31 * result + (getRegistrationDate() != null ? getRegistrationDate().hashCode() : 0);
		result = 31 * result + (getLastLogin() != null ? getLastLogin().hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "UserDetails{" +
				"userID=" + userID +
				", userName='" + userName + '\'' +
				", userMail='" + userMail + '\'' +
				", password='" + password + '\'' +
				", isActive=" + isActive +
				", registrationDate=" + registrationDate +
				", lastLogin=" + lastLogin +
				'}';
	}

}
