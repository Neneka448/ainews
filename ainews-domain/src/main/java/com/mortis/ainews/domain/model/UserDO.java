package com.mortis.ainews.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDO {
    private Long id;
    private String userName;
    private String ac;
    // omit pw for security when exposing DO
    private int deleted;
}