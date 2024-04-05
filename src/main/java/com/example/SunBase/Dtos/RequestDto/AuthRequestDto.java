package com.example.SunBase.Dtos.RequestDto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AuthRequestDto {

    private String username;
    private String password;
}
