package com.thoughtworks.rslist.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class User {
    @Size(max = 8)
    @NotNull
    private String userName;

    @NotNull
    private String gender;

    @Min(18)
    @Max(100)
    private int age;

    @Email
    private String email;

    @Pattern(regexp = "1\\d{10}")
    private String phone;

    public User(String userName, int age,String gender,  String email, String phone){
        this.userName = userName;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }
}
