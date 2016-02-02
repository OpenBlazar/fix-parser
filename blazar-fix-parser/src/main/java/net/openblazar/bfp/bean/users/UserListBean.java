package net.openblazar.bfp.bean.users;

import net.openblazar.bfp.bean.AbstractBean;
import net.openblazar.bfp.common.users.UserDetails;
import net.openblazar.bfp.services.UserService;

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

	private UserService userService;

	private List<UserDetails> userDetailsList;

	@Inject
	public void setUserService(UserService userService) {
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

}
