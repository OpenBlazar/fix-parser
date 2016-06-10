package com.blazarquant.bfp.common;

import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.data.user.UserState;

import java.time.Instant;

/**
 * @author Wojciech Zankowski
 */
public class TestObjectFactory {

    public static UserDetails createUserDetails(UserID userID, String userName) {
        return createUserDetails(userID, userName, "test@test.com");
    }

    public static UserDetails createUserDetails(UserID userID, String userName, String userMail) {
        return new UserDetails(
                userID,
                userName,
                userMail,
                "testpw",
                UserState.ACTIVE,
                Instant.now(),
                Instant.now()
        );
    }

}
