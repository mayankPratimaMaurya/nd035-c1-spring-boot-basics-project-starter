package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NotesMapper {

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Notes getNotes(String noteId);

    @Insert("INSERT INTO NOTES(notetitle,notedescription,userid) VALUES(#{notetitle},#{notedescription},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insertNotes(Notes notes);

    @Update("UPDATE NOTES SET notetitle=#{notetitle}, notedescription=#{notedescription} WHERE noteid='#{noteid}")
    int updateNotes(Notes notes);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    int deleteNotes(String noteId);
}
