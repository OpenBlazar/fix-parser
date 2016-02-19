package net.openblazar.bfp.services;

import net.openblazar.bfp.data.fix.FixMessage;

import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public interface ParserService {

    List<FixMessage> parseInput(String input);

    void addMessages(List<FixMessage> messages);
}
