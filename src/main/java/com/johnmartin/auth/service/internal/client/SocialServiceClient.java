package com.johnmartin.auth.service.internal.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.johnmartin.auth.constants.SecurityConstants;
import com.johnmartin.auth.constants.api.ExternalServiceConstants;
import com.johnmartin.auth.constants.error.ExternalServiceErrorConstants;
import com.johnmartin.auth.dto.common.Result;
import com.johnmartin.auth.dto.request.internal.CreateSocialUserRequest;
import com.johnmartin.auth.dto.response.internal.SocialUserResponse;
import com.johnmartin.auth.utilities.LoggerUtility;

@Service
public class SocialServiceClient {

    private static final Class<SocialServiceClient> clazz = SocialServiceClient.class;

    private final RestClient socialServiceRestClient;

    public SocialServiceClient(RestClient socialServiceRestClient) {
        this.socialServiceRestClient = socialServiceRestClient;
    }

    public SocialUserResponse createUser(String requestId, CreateSocialUserRequest request) {
        LoggerUtility.d(clazz,
                        String.format("Execute method: [createUser] requestId: [%s] request: [%s]",
                                      requestId,
                                      request));

        try {
            Result<SocialUserResponse> result = socialServiceRestClient.post()
                                                                       .uri(ExternalServiceConstants.PumpSocialService.API_CREATE_USER)
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
            Result<SocialUserResponse> result = socialServiceRestClient.get()
                                                                       .uri(ExternalServiceConstants.PumpSocialService.API_USERS
                                                                            + "/" + userId)
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
