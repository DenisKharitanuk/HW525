package steps.asertsResponses;

import models.negative_responses.NegativeResponses;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NegativeAssert {
    public static void verifyBodyNegative(NegativeResponses response, String errorCode, String errorMessage) {
        assertEquals(response.getErrorCode(), errorCode, "Incorrect errorCode");
        assertEquals(response.getErrorMessage(), errorMessage, "Incorrect errorMessage");
    }
}
