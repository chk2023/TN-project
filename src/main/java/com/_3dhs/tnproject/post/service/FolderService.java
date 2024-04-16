package com._3dhs.tnproject.post.service;

import com._3dhs.tnproject.post.dao.FolderMapper;
import com._3dhs.tnproject.post.dto.FolderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderMapper folderMapper;
    @Transactional()
    public void updateFolders(List<FolderDTO> requestBody) {
        System.out.println("폴더리스트" + requestBody);
        folderMapper.updateFolders(requestBody);
    }
    @Transactional
    public List<FolderDTO> findFolderList(int memberCode) {
        return folderMapper.findFolderList(memberCode);
    }
    @Transactional
    public void addDefaultFolder(List<FolderDTO> addDefaultFolders) {
        folderMapper.insertAddDefaultFolder(addDefaultFolders);
    }

}
