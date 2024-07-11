package edu.kent.babelpages.rest.tags;

import edu.kent.babelpages.rest.tags.DTO.TagResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagsController {
    private final TagsService tagsService;

    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @GetMapping("/")
    @Operation(
            summary = "Lists all tags and their respective type"
    )
    @Tag(name = "Tags")
    public List<TagResultDTO> listTags() {
        return tagsService.getAllTags().stream().map(TagResultDTO::new).toList();
    }
}
