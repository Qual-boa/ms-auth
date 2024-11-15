package com.qualaboa.msauth.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StandardError {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;
}
