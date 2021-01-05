package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Notes getNotesForNoteId(int noteid);

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Notes> getNotesForUser(int userid);

    @Insert("INSERT INTO NOTES(notetitle,notedescription,userid) VALUES(#{notetitle},#{notedescription},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int addNotes(Notes notes);

    @Update("UPDATE NOTES SET notetitle=#{notetitle}, notedescription=#{notedescription} WHERE noteid = #{noteid}")
    int updateNotes(Notes notes);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    int deleteNotes(int noteId);
}