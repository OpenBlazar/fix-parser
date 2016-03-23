package com.blazarquant.bfp.services.parser;

import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.fix.data.FixMessage;

import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public interface ParserService {

    List<FixMessage> findMessagesByUser(UserDetails userDetails, int lowerLimit, int upperLimit);

    int countUserMessages(UserDetails userDetails);

    List<FixMessage> parseInput(String input);

    void saveMessages(UserDetails userDetails, List<FixMessage> messages);

}
