package com.blazarquant.bfp.web.bean.user;

import com.blazarquant.bfp.web.bean.AbstractBean;
import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.services.user.UserService;
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
