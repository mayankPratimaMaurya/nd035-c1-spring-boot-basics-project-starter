package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES(filename,contenttype,filesize,userid,filedata) values(#{filename},#{contenttype},#{filesize},#{userid},#{filedata})")
    public int insertFile(File file);

    @Select("SELECT filename,fileId FROM FILES WHERE userid = #{userid}")
    public List<File> getFileMetaData(int userid);
}
