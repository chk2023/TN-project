package com._3dhs.tnproject.post.controller;

import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.member.service.MemberService;
import com._3dhs.tnproject.post.dto.FolderDTO;
import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.post.dto.TabSearchDTO;
import com._3dhs.tnproject.post.service.LikeService;
import com._3dhs.tnproject.post.model.PostState;
import com._3dhs.tnproject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final MessageSourceAccessor accessor;
    private final MemberService memberService;
    private final LikeService likeService;

    @GetMapping("/main")
    public void blogMainPage(@ModelAttribute TabSearchDTO tabSearchDTO, Model model) {
        List<FolderDTO> folderList = postService.findFolderList(tabSearchDTO.getMemberCode());
        MemberDTO memberDTO = memberService.findMainBlogMemberInfo(tabSearchDTO.getMemberCode());
        PostDTO postViewLikeCount =  postService.findPostLikeCount(tabSearchDTO.getMemberCode());
//        List<PostDTO> postList =  postService.findPostList(tabSearchDTO); //TODO 수정필
        memberDTO.setMemberCode(tabSearchDTO.getMemberCode());

        model.addAttribute("folderList", folderList);
        model.addAttribute("member", memberDTO);
        model.addAttribute("postView", postViewLikeCount);
//        model.addAttribute("postList", postList);
    }
    @Transactional
    @GetMapping("/folder_edit")
    public void folderEditPage(@AuthenticationPrincipal MemberDTO memberDTO, Model model) {
        List<FolderDTO> folderList = postService.findFolderList(memberDTO.getMemberCode());

        if (folderList.isEmpty()) {
            System.out.println("폴더리스트 비어있는디?");// 비어있음 멤버코드로 10개 만들어줘
            List<FolderDTO> addDefaultFolders = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                FolderDTO folderDTO = new FolderDTO();
                folderDTO.setFolderName("NoName");
                folderDTO.setFolderIconPath("/images/icon_folder.png");
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
    public void blogWritePage(@AuthenticationPrincipal MemberDTO memberDTO, Model model) {
        List<FolderDTO> folderList = postService.findFolderList(memberDTO.getMemberCode());
        PostDTO postViewLikeCount = postService.findPostLikeCount(memberDTO.getMemberCode());

        model.addAttribute("folderList", folderList);
        model.addAttribute("postView", postViewLikeCount);
    }

    @GetMapping("/temporary_storage/list")
    public void temporaryStorageListPage() {}
    @GetMapping("/list")
    public void blogListPage(@ModelAttribute TabSearchDTO tabSearchDTO, Model model) {
        List<FolderDTO> folderList = postService.findFolderList(tabSearchDTO.getMemberCode());
        MemberDTO memberDTO = memberService.findMainBlogMemberInfo(tabSearchDTO.getMemberCode());
        PostDTO postViewLikeCount = postService.findPostLikeCount(tabSearchDTO.getMemberCode());
        List<PostDTO> postList = postService.findPostList(tabSearchDTO); //TODO 수정필
        memberDTO.setMemberCode(tabSearchDTO.getMemberCode());

        model.addAttribute("folderList", folderList);
        model.addAttribute("member", memberDTO);
        model.addAttribute("postView", postViewLikeCount);
        model.addAttribute("postList", postList);
    }
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
    @GetMapping("/load")
    public @ResponseBody List<PostDTO> findTabMenuPostList(@ModelAttribute TabSearchDTO tabSearchDTO, @AuthenticationPrincipal MemberDTO member) {
        List<PostDTO> postList =  postService.findPostList(tabSearchDTO);
        postList.forEach(dto -> {
            dto.setLiked(likeService.getHasLiked(dto.getPostCode(),member.getMemberCode()));
        });
        return postList;
    }
    @PostMapping("/folder_edit")
    public @ResponseBody String folderEditList(@AuthenticationPrincipal MemberDTO memberDTO, @RequestBody List<FolderDTO> requestBody) {
        for (FolderDTO folderDTO : requestBody) {
            folderDTO.setFMemberCode(memberDTO.getMemberCode());
        }
        postService.updateFolders(requestBody);

        //return "redirect:/post/main?memberCode="+memberDTO.getMemberCode()+"";
        return "redirect:memberCode=" + memberDTO.getMemberCode() + "";
    }
}
