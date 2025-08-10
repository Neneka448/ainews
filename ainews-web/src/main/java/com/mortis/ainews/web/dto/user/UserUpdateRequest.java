package com.mortis.ainews.web.dto.user;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private Long id;
    private String userName;
}
