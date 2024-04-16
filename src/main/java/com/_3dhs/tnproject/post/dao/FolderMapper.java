package com._3dhs.tnproject.post.dao;

import com._3dhs.tnproject.post.dto.FolderDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FolderMapper {
    void updateFolders(List<FolderDTO> requestBody);
    List<FolderDTO> findFolderList(int memberCode);
    void insertAddDefaultFolder(List<FolderDTO> addDefaultFolders);
}
