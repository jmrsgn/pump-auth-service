package com.johnmartin.auth.dto.response;

public record SocialUserResponse(String id,
                                 String firstName,
                                 String lastName,
                                 String email,
                                 String profileImageUrl,
                                 String bio,
                                 long followersNo,
                                 long followingNo,
                                 boolean isFollowing) {
}
