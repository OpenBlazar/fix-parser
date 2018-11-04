package pl.zankowski.fixparser.web.bean.user;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.zankowski.fixparser.user.api.UserDetailsTO;
import pl.zankowski.fixparser.user.spi.UserService;
import pl.zankowski.fixparser.web.bean.AbstractBean;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.util.List;

@Named("userListBean")
@ViewScoped
public class UserListBean extends AbstractBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserListBean.class);

    private UserService userService;

    private List<UserDetailsTO> userDetailsList;

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

    public List<UserDetailsTO> getUserDetailsList() {
        return userDetailsList;
    }

}
