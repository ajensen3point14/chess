package server.handlers;

import server.ServerResponse;
import server.services.ClearService;

/**
 * Handles clear requests by calling the respective service.
 */
public class ClearHandler extends Handler{
    /**
     * Create the server response generated by the handle request
     * @param input string body input
     * @param authToken the token associated with the user
     * @return empty, per the project specs
     */
    @Override
    public ServerResponse handleRequest(String input, String authToken) {
        ClearService clearService = new ClearService();
        clearService.clear();

        return new ServerResponse("{}");
    }
}
