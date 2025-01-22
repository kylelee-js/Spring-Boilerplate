package com.example.boilerplate.data.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class DataDto {
    @NotNull
    private String name;

    @NotNull
    private int age;

    @Email
    private String email;

    @Max(100)
    private String address;

    @Size(min = 10, max = 11)
    private String phoneNumber;
}

