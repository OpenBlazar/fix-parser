package pl.zankowski.fixparser.common;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class MockitoUtils {

    public static Answer getCallbackAnswer() {
        return new Answer<String>() {

            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (String) args[0];
            }
        };
    }

}
