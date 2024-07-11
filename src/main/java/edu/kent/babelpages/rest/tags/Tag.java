package edu.kent.babelpages.rest.tags;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private int id;
    private String name;
    private String type;
}
