package com.johnmartin.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.johnmartin.auth.constants.SecurityConstants;
import com.johnmartin.auth.constants.api.ApiConstants;
import com.johnmartin.auth.dto.request.CreateSocialUserRequest;
import com.johnmartin.auth.utilities.LoggerUtility;

@Service
public class SocialServiceClient {

    private static final Class<SocialServiceClient> clazz = SocialServiceClient.class;

    private final RestClient socialWebClient;

    public SocialServiceClient(RestClient socialWebClient) {
        this.socialWebClient = socialWebClient;
    }

    public void createUser(String requestId, CreateSocialUserRequest request) {
        LoggerUtility.d(clazz,
                        String.format("Execute method: [createUser] requestId: [%s] request: [%s]",
                                      requestId,
                                      request));
        try {
            socialWebClient.post()
                           .uri(ApiConstants.PumpSocialService.API_CREATE_USER)
                           .header(SecurityConstants.REQUEST_ID, requestId)
                           .body(request)
                           .retrieve()
                           .toBodilessEntity();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create social user", ex);
        }
    }
}
