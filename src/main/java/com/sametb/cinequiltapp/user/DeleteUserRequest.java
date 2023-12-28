package com.sametb.cinequiltapp.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Samet Bayat.
 * Date: 28.11.2023 3:50 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Getter
@Setter
@Builder
@JsonDeserialize(builder = DeleteUserRequest.DeleteUserRequestBuilder.class)
public class DeleteUserRequest {
    private String password;

    @JsonPOJOBuilder(withPrefix = "")
    public static class DeleteUserRequestBuilder {
    }
}

