package com.jacinthocaio.user_service.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ProfilePostResponse {

    private Long id;

    private String name;

    private String description;
}
