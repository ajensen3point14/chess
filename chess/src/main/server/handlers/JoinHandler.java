package server.handlers;

import models.Game;
import server.Server;
import server.ServerResponse;
import requests.JoinRequest;
import server.services.JoinService;

/**
 * Handles join requests
 */
public class JoinHandler extends Handler{
    /**
     * Handle a join request, returning empty
     * @param input string input in JSON format
     * @param authToken string authToken for the user. This may be null in many cases
     * @return empty, per the project specs
     */
    @Override
    public ServerResponse handleRequest(String input, String authToken) {
        JoinRequest request = gson.fromJson(input, JoinRequest.class);
        request.setAuthToken(authToken);

        return handleRequest(request);
    }

    public ServerResponse handleRequest(JoinRequest request) {
        JoinService joinService = new JoinService();
        Game game = joinService.join(request);

        ServerResponse response = new ServerResponse("{}");
        response.setGame(game);

        return response;
    }

    public ServerResponse handleLeaveRequest(JoinRequest request) {
        JoinService joinService = new JoinService();
        joinService.unjoin(request);

        return new ServerResponse("{}");
    }
}
