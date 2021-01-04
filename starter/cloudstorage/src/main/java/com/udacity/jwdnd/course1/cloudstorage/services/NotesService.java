package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotesService {

    private NotesMapper notesMapper;

    public NotesService(NotesMapper notesMapper){
        this.notesMapper = notesMapper;
    }

    public List<Notes> getNotesListForUser(int userId){
        return notesMapper.getNotesForUser(userId);
    }

    public int deleteNotes(int noteId){
        return notesMapper.deleteNotes(noteId);
    }

    public int addNotes(Notes notes) {
        return notesMapper.addNotes(notes);
    }

    public Notes getNotesForNoteId(int noteId) {
        return notesMapper.getNotesForNoteId(noteId);
    }

    public int updateNotes(Notes notes) {
        return notesMapper.updateNotes(notes);
    }
}
