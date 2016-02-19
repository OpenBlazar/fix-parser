package net.openblazar.bfp.bean.users;

import net.openblazar.bfp.bean.AbstractBean;
import net.openblazar.bfp.data.user.UserDetails;
import net.openblazar.bfp.services.UserService;
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

	private UserService userService;

	private List<UserDetails> userDetailsList;

	@Inject
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@PostConstruct
	@Override
	public void init() {
		super.init();
		userDetailsList = userService.getUsers();
	}

	public List<UserDetails> getUserDetailsList() {
		return userDetailsList;
	}

}
