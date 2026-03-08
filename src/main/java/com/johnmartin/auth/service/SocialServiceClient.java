package com.johnmartin.auth.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<Void> createUser(String authorizationHeader,
                                           String requestId,
                                           CreateSocialUserRequest request) {
        try {
            return socialWebClient.post()
                                  .uri(ApiConstants.PumpSocialService.API_CREATE_USER)
                                  .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                                  .header(SecurityConstants.REQUEST_ID, requestId)
                                  .body(request)
                                  .retrieve()
                                  .toBodilessEntity();
        } catch (Exception ex) {
            throw new RuntimeException(ApiErrorMessages.FAILED_TO_CREATE_SOCIAL_USER, ex);
        }
    }
}
