package net.openblazar.bfp.bean.users;

import net.openblazar.bfp.bean.AbstractBean;
import net.openblazar.bfp.common.users.UserDetails;
import net.openblazar.bfp.services.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean
@ViewScoped
public class UserListBean extends AbstractBean {

	private final static Logger LOGGER = LoggerFactory.getLogger(UserListBean.class);

	private UserServiceImpl userService;

	private List<UserDetails> userDetailsList;

	private String test="testBean";

	@Inject
	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	@PostConstruct
	public void init() {
		super.init();
		userDetailsList = userService.getUsers();
	}

	public List<UserDetails> getUserDetailsList() {
		return userDetailsList;
	}

	public String getTest() {
		return test;
	}
}
