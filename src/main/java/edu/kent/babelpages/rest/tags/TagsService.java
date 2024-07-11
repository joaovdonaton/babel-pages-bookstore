package edu.kent.babelpages.rest.tags;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TagsService {
    private final TagsDAO tagsDAO;

    public TagsService(TagsDAO tagsDAO) {
        this.tagsDAO = tagsDAO;
    }

    public List<Tag> getAllTags(){
        return tagsDAO.findAll();
    }

    public Tag getTagByName(String name){
        return tagsDAO.findByName(name);
    }
}
