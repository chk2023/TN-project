package com._3dhs.tnproject.post.controller;

import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.post.dto.FolderDTO;
import com._3dhs.tnproject.post.dto.LikeListDTO;
import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.post.model.PostState;
import com._3dhs.tnproject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final MessageSourceAccessor accessor;
    @GetMapping("/main")
    public void blogMainPage(@AuthenticationPrincipal MemberDTO memberDTO, Model model) {
        List<FolderDTO> folderList = postService.findFolderList(memberDTO.getMemberCode());
        model.addAttribute("folderList", folderList);
    }
    @Transactional
    @GetMapping("/folder_edit")
    public void folderEditPage(@AuthenticationPrincipal MemberDTO memberDTO, Model model) {
        List<FolderDTO> folderList = postService.findFolderList(memberDTO.getMemberCode());

        if(folderList.isEmpty()) {
            System.out.println("폴더리스트 비어있는디?");// 비어있음 멤버코드로 10개 만들어줘
            List<FolderDTO> addDefaultFolders = new ArrayList<>();

            for(int i = 0; i < 10; i++) {
                FolderDTO folderDTO = new FolderDTO();
                folderDTO.setFolderName("NoName");
                folderDTO.setFolderIconPath("/image/icon_folder.png");
                folderDTO.setFolderSequence(10);
                folderDTO.setFMemberCode(memberDTO.getMemberCode());
                folderDTO.setFolderStatus("N");
                addDefaultFolders.add(folderDTO);
            }
            System.out.println("컨트롤러단의 addDefaultFolders : " + addDefaultFolders);
            postService.addDefaultFolder(addDefaultFolders);
            folderList = postService.findFolderList(memberDTO.getMemberCode());

        } else {
            System.out.println("폴더리스트에 정보 있는디!!!!?");
        }
        model.addAttribute("folderList", folderList);
    }
    @GetMapping("/write")
    public void blogWritePage() {}
    @GetMapping("/temporary_storage/list")
    public void temporaryStorageListPage() {}
    @GetMapping("/detail")
    public String blogDetailPage(@AuthenticationPrincipal MemberDTO memberDTO, Integer postCode, Model model) {

        //1. 해당하는 코드의 post정보를 불러오기
        PostDTO targetPost = postService.findPostByPostCode(postCode);
        //2. post 상태가 비공개라면 열람자가 일치하는지 확인
        if (targetPost.getPostState() == PostState.PRIVATE) {
            if (memberDTO.getMemberCode() != targetPost.getMemberCode()) {
                //열람자가 일치하지 않으면 에러메세지 첨부
                model.addAttribute("errorMessage", accessor.getMessage("post.notEqualMember"));
                return "/post/detail"; //TODO : view에서 errorMessage가 있다면 이전화면으로 돌아가는 로직 작성해주세요
            }
        }
        //3. post가 유료글인지 판단하기
        if (targetPost.getPostPrice() > 0) {
            //유료글이라면 유료글 처리를 하는 곳으로 전달하기
            model.addAttribute("paidContent", targetPost);
            return "/getPaidPostInfo";  //TODO: getPaidPostInfo에서 "@ModelAttribute PostDTO paidContent"로 값 받아 사용하기
        }
        //4. 모든 조건이 성립한다면 view로 전달
        model.addAttribute("postDetail", targetPost);
        return "/post/detail";
    }
    @GetMapping("/likelist")
    public void blogLikeListPage(int memberCode, Model model) {
        List<PostDTO> likeList = postService.findLikeListPostByMemberCode(memberCode);
        model.addAttribute("likeList", likeList);
    }

    @GetMapping("/list")
    public String blogListPage(Model model, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            MemberDTO member = (MemberDTO) authentication.getPrincipal();
            List<PostDTO> likedPostList = postService.findLikeListPostByMemberCode(member.getMemberCode());

            for (PostDTO post : likedPostList) {
                int postCode = post.getPostCode();
                boolean hasLiked = postService.hasLiked(postCode, member.getMemberCode());
            }

            model.addAttribute("likedPostList",likedPostList);
        }

        return "list";


    }

    @PostMapping("/post/like")
    public ResponseEntity<String> likePost(@RequestBody LikeListDTO likeListDTO) {

        boolean isLiked = postService.hasLiked(likeListDTO.getPostCode(), likeListDTO.getMemberCode());
        if (isLiked) {
            return new ResponseEntity<>("Liked", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unliked", HttpStatus.OK);
        }
    }

    @PostMapping("/folder_edit")
    public @ResponseBody String folderEditList(@AuthenticationPrincipal MemberDTO memberDTO, @RequestBody List<FolderDTO> requestBody){
        for(FolderDTO folderDTO : requestBody) {
            folderDTO.setFMemberCode(memberDTO.getMemberCode());
        }
        postService.updateFolders(requestBody);

        //System.out.println("처리후 requestBody : " + requestBody);
        return "redirect:/post/main";
    }
}
