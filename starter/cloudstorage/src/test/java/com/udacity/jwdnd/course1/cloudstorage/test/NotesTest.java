package com.udacity.jwdnd.course1.cloudstorage.test;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotesTest {

    @Autowired
    private NotesService notesService;
    private List<Notes> notesList;

    @Test
    public void ifNotesAreNotPresentForTheUser(){
        Assertions.assertEquals(0,notesService.getNotesListForUser(3).stream().count());
    }

    @Test
    public void deleteNoteForUser(){
        long currentNotesCount = notesService.getNotesListForUser(1).stream().count();
        Assertions.assertEquals(1,notesService.deleteNotes(1));
        Assertions.assertEquals(currentNotesCount-1,notesService.getNotesListForUser(1).stream().count());
        Assertions.assertEquals(1,notesService.deleteNotes(2));
        Assertions.assertEquals(currentNotesCount-2,notesService.getNotesListForUser(1).stream().count());
        //Try to delete already deleted note.
        Assertions.assertEquals(0,notesService.deleteNotes(1));
    }

    @Test
    public void addNewNotesValidation(){
        Notes note = new Notes(0,"title","description",1);
        long currentNOtesCount = notesService.getNotesListForUser(1).stream().count();
        Assertions.assertEquals(1,notesService.addNotes(note));
        Assertions.assertEquals(currentNOtesCount+1,notesService.getNotesListForUser(1).stream().count());
    }

    @Test void updatedNotesValidation(){
        Notes note = notesService.getNotesForNoteId(1);
        note.setNotetitle("modified title");
        note.setNotedescription("modified description");
        Assertions.assertEquals(1,notesService.updateNotes(note));
        Assertions.assertEquals("modified title",notesService.getNotesForNoteId(1).getNotetitle());
        Assertions.assertEquals("modified description",notesService.getNotesForNoteId(1).getNotedescription());
    }

    @BeforeEach
    public void populateNotesList(){
        notesList = new ArrayList<>();
        notesList.add(new Notes(0,"title","description",1));
        notesList.add(new Notes(0,"title2","description2",1));
        notesList.add(new Notes(0,"title3","description3",1));
        notesList.add(new Notes(0,"title4","description4",1));

        for (Notes notes:notesList) {
            notesService.addNotes(notes);
        }

    }
}
