package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credentials> getAllCredentialsForUser(int userid);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credentials getCredentialsWithCredentialId(int credentialId);

    @Insert("INSERT INTO CREDENTIALS(userid,url,username,key,password) VALUES(#{userid},#{url},#{username},#{key},#{password})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    int addNewCredentials(Credentials credentials);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username} , password=#{password}, key=#{key} WHERE credentialid = #{credentialid}")
    int updateCredentials(Credentials credentials);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    int removeCredentials(int credentialid);

}
