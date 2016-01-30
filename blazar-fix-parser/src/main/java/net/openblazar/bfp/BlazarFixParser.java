package net.openblazar.bfp;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import net.openblazar.bfp.common.users.UserDetails;
import net.openblazar.bfp.database.DatabaseModule;
import net.openblazar.bfp.database.dao.UserDAO;

import java.util.List;
import java.util.Properties;

/**
 * @author Wojciech Zankowski
 */
public class BlazarFixParser {

	private UserDAO userDAO;

	@Inject
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public static void main(String[] args) {
		BlazarFixParser fixParser = new BlazarFixParser();

		Injector injector = Guice.createInjector(
				new DatabaseModule(fixParser.getProperties()));

		UserDAO userDAO = injector.getInstance(UserDAO.class);
		List<UserDetails> users = userDAO.findAllUsers();
		System.out.println(users);
		UserDetails userDetails = userDAO.findUser("admin");
		System.out.println(userDetails);
	}

	public void test() {
		List<UserDetails> users = userDAO.findAllUsers();
		System.out.println(users);
	}

	private Properties getProperties() {
		Properties properties = new Properties();

		properties.setProperty("JDBC.host", "localhost");
		properties.setProperty("JDBC.port", "3306");
		properties.setProperty("JDBC.schema", "blazarfixparser");

		properties.setProperty("JDBC.username", "root");
		properties.setProperty("JDBC.password", "werty56");
		properties.setProperty("JDBC.autoCommit", "false");

		return properties;
	}

}
