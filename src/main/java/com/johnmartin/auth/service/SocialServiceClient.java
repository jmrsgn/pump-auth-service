package com.johnmartin.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.johnmartin.auth.constants.SecurityConstants;
import com.johnmartin.auth.constants.api.ApiConstants;
import com.johnmartin.auth.constants.api.ApiErrorMessages;
import com.johnmartin.auth.dto.request.CreateSocialUserRequest;

@Service
public class SocialServiceClient {

    private final RestClient socialWebClient;

    public SocialServiceClient(RestClient socialWebClient) {
        this.socialWebClient = socialWebClient;
    }

    public void createUser(String requestId, CreateSocialUserRequest request) {
        try {
            socialWebClient.post()
                           .uri(ApiConstants.PumpSocialService.API_CREATE_USER)
                           .header(SecurityConstants.REQUEST_ID, requestId)
                           .body(request)
                           .retrieve()
                           .toBodilessEntity();
        } catch (Exception ex) {
            throw new RuntimeException(ApiErrorMessages.FAILED_TO_CREATE_SOCIAL_USER, ex);
        }
    }
}
