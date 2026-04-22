package com.johnmartin.auth.service.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.johnmartin.auth.constants.SecurityConstants;
import com.johnmartin.auth.constants.api.ApiConstants;
import com.johnmartin.auth.constants.error.ExternalServiceErrorConstants;
import com.johnmartin.auth.dto.request.CreateSocialUserRequest;
import com.johnmartin.auth.dto.response.Result;
import com.johnmartin.auth.dto.response.SocialUserResponse;
import com.johnmartin.auth.utilities.LoggerUtility;

@Service
public class SocialServiceClient {

    private static final Class<SocialServiceClient> clazz = SocialServiceClient.class;

    private final RestClient socialWebClient;

    public SocialServiceClient(RestClient socialWebClient) {
        this.socialWebClient = socialWebClient;
    }

    public SocialUserResponse createUser(String requestId, CreateSocialUserRequest request) {
        LoggerUtility.d(clazz,
                        String.format("Execute method: [createUser] requestId: [%s] request: [%s]",
                                      requestId,
                                      request));

        try {
            Result<SocialUserResponse> result = socialWebClient.post()
                                                               .uri(ApiConstants.PumpSocialService.API_CREATE_USER)
                                                               .header(SecurityConstants.HttpHeaders.REQUEST_ID,
                                                                       requestId)
                                                               .body(request)
                                                               .retrieve()
                                                               .body(new ParameterizedTypeReference<>() {
                                                               });

            if (result == null || result.getData().isEmpty()) {
                throw new RuntimeException(ExternalServiceErrorConstants.FAILED_TO_CREATE_USER);
            }

            return result.getData().get();
        } catch (Exception ex) {
            throw new RuntimeException(ExternalServiceErrorConstants.FAILED_TO_CREATE_USER, ex);
        }
    }

    public SocialUserResponse getSocialUser(String requestId, String userId) {
        LoggerUtility.d(clazz,
                        String.format("Execute method: [getSocialUser] requestId: [%s] userId: [%s]",
                                      requestId,
                                      userId));

        try {
            Result<SocialUserResponse> result = socialWebClient.get()
                                                               .uri(ApiConstants.PumpSocialService.API_GET_USER + "/"
                                                                    + userId)
                                                               .header(SecurityConstants.HttpHeaders.REQUEST_ID,
                                                                       requestId)
                                                               .retrieve()
                                                               .body(new ParameterizedTypeReference<>() {
                                                               });

            if (result == null || result.getData().isEmpty()) {
                throw new RuntimeException(ExternalServiceErrorConstants.SOCIAL_USER_NOT_FOUND);
            }

            return result.getData().get();
        } catch (Exception ex) {
            throw new RuntimeException(ExternalServiceErrorConstants.FAILED_TO_FETCH_SOCIAL_USER, ex);
        }
    }
}
