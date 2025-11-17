package com.coingshuttle.projects.airbnbApp.Dto;

import com.coingshuttle.projects.airbnbApp.Entity.User;
import com.coingshuttle.projects.airbnbApp.Entity.enums.Gender;
import lombok.Data;

@Data
public class GuestDto {

    private Long id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;
}
