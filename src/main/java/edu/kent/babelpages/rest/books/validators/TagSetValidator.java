package edu.kent.babelpages.rest.books.validators;

import edu.kent.babelpages.rest.tags.TagsService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class TagSetValidator implements ConstraintValidator<TagSetConstraint, Set<String>> {
    private final TagsService tagsService;

    public TagSetValidator(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @Override
    public boolean isValid(Set<String> tagNames, ConstraintValidatorContext constraintValidatorContext) {
        if(tagNames == null || tagNames.isEmpty()) return true; // we accept null tags

        var validTags = tagsService.getAllTags();

        for(String name : tagNames) {
            if(validTags.stream().noneMatch(tag -> tag.getName().equals(name))) {
                return false;
            }
        }

        return true;
    }
}
