package edu.kent.babelpages.rest.tags;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Tag {
    private UUID id;
    private String name;
    private String type;
}
