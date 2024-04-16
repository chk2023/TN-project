package com._3dhs.tnproject.post.controller;

import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.post.dto.FolderDTO;
import com._3dhs.tnproject.post.service.FolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post")
public class FolderController {

    private final FolderService folderService;
    @Transactional
    @GetMapping("/folder_edit")
    public String folderEditPage(@AuthenticationPrincipal MemberDTO loginMemberDTO, Model model) {
        List<FolderDTO> folderList = folderService.findFolderList(loginMemberDTO.getMemberCode());

        if (folderList.isEmpty()) {
            System.out.println("폴더리스트 비어있는디?");// 비어있음 멤버코드로 10개 만들어줘
            List<FolderDTO> addDefaultFolders = getFolderDTOS(loginMemberDTO);
            System.out.println("컨트롤러단의 addDefaultFolders : " + addDefaultFolders);
            folderService.addDefaultFolder(addDefaultFolders);
            folderList = folderService.findFolderList(loginMemberDTO.getMemberCode());

        } else {
            System.out.println("폴더리스트에 정보 있는디!!!!?");
        }
        model.addAttribute("folderList", folderList);
        return "/post/folder_edit";
    }
    @NotNull
    private List<FolderDTO> getFolderDTOS(MemberDTO loginMemberDTO) {
        List<FolderDTO> addDefaultFolders = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            FolderDTO folderDTO = new FolderDTO();
            folderDTO.setFolderName("NoName");
            folderDTO.setFolderIconPath("/images/icon_folder.png");
            folderDTO.setFolderSequence(10);
            folderDTO.setFMemberCode(loginMemberDTO.getMemberCode());
            folderDTO.setFolderStatus("N");
            addDefaultFolders.add(folderDTO);
        }
        return addDefaultFolders;
    }
    @GetMapping("/folder_list")
    public void folderListPage() {
    }

    @PostMapping("/folder_edit")
    public @ResponseBody String folderEditList(@AuthenticationPrincipal MemberDTO loginMemberDTO, @RequestBody List<FolderDTO> requestBody) {
        for (FolderDTO folderDTO : requestBody) {
            folderDTO.setFMemberCode(loginMemberDTO.getMemberCode());
        }
        folderService.updateFolders(requestBody);

        return "redirect:memberCode=" + loginMemberDTO.getMemberCode();
    }
}
