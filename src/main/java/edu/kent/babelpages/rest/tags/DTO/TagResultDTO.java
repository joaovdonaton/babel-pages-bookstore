package edu.kent.babelpages.rest.tags.DTO;

import edu.kent.babelpages.rest.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagResultDTO {
    private String name;
    private String type;

    public TagResultDTO(Tag tag) {
        this.name = tag.getName();
        this.type = tag.getType();
    }
}
