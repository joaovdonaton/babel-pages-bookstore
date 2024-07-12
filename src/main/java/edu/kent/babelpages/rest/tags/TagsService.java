package edu.kent.babelpages.rest.tags;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public Set<Tag> getTagsByBookId(String id){
        return new HashSet<>(tagsDAO.findAllByBookId(id));
    }
}
