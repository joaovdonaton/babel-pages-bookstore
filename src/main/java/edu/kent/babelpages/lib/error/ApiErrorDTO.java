package edu.kent.babelpages.lib.error;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApiErrorDTO {
    private int code;
    private String message;
    private List<String> errors;
    private String timeStamp;
    private String path;
}
