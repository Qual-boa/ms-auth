package com.qualaboa.msauth.dataContract.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSessionDTO {
    private UUID userId;
    private UUID establishmentId;
}
