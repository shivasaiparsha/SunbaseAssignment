package com.example.SunBase.Dtos.RequestDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SunbaseRemoteApiAuthDto {
    private String login_id;
    private String password;
}
