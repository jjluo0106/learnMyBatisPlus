package com.azhe.domain.query;

import lombok.Data;

@Data
public class UserQuery {


    private Integer id;
    private String name;
    private String password;
    private Integer maxAge;
    private Integer minAge;
    private String dept_id;
}
