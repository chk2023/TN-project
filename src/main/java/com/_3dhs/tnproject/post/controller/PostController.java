package com._3dhs.tnproject.post.controller;

import com._3dhs.tnproject.comments.dto.CommentsDTO;
import com._3dhs.tnproject.comments.service.CommentsService;
import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.member.service.MemberService;
import com._3dhs.tnproject.post.dto.*;
import com._3dhs.tnproject.post.model.PostState;
import com._3dhs.tnproject.post.service.LikeService;
import com._3dhs.tnproject.post.service.PostService;
import com._3dhs.tnproject.post.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final MessageSourceAccessor accessor;
    private final MemberService memberService;
    private final LikeService likeService;
    private final CommentsService commentsService;

    @ModelAttribute
    public void addCommonAttributes(@ModelAttribute TabSearchDTO tabSearchDTO, Authentication authentication, Model model) {
        int totalCount = postService.findTotalCount(tabSearchDTO);
        List<FolderDTO> folderList = postService.findFolderList(tabSearchDTO.getMemberCode());
        PostDTO postViewLikeCount = postService.findPostLikeCount(tabSearchDTO.getMemberCode());

        model.addAttribute("folderList", folderList);
        model.addAttribute("postTotalCount", totalCount);
        model.addAttribute("postView", postViewLikeCount);
    }

    @GetMapping("/main")
    public void blogMainPage(@ModelAttribute TabSearchDTO tabSearchDTO, Authentication authentication, Model model) {
        MemberDTO memberDTO = memberService.findMainBlogMemberInfo(tabSearchDTO.getMemberCode());
//      List<PostDTO> postList =  postService.findPostList(tabSearchDTO); //TODO 수정필
        memberDTO.setMemberCode(tabSearchDTO.getMemberCode());

        model.addAttribute("member", memberDTO);
//      model.addAttribute("postList", postList);
        MemberDTO member = (MemberDTO) authentication.getPrincipal();
        model.addAttribute("loginMemberCode", member.getMemberCode());
    }

    @Transactional
    @GetMapping("/folder_edit")
    public String folderEditPage(@AuthenticationPrincipal MemberDTO memberDTO, Model model) {
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
        return "/post/folder_edit";
    }

    @GetMapping("/write")
    public String blogWritePage(@AuthenticationPrincipal MemberDTO memberDTO, Model model) {
        if (postService.isFixedPost(memberDTO.getMemberCode())) {
            // 거절 메시지를 모델에 추가하고, 거절 뷰 이름 반환
            model.addAttribute("message", true);
        }
        return "/post/write";
    }

    @GetMapping("/temporary_storage/list")
    public void temporaryStorageListPage() {
    }

    @GetMapping("/list")
    public String blogListPage(@RequestParam(defaultValue = "1") int page, @ModelAttribute TabSearchDTO tabSearchDTO, @AuthenticationPrincipal MemberDTO member, Model model) {
        boolean isOwner = member.getMemberCode() == tabSearchDTO.getMemberCode();
        int totalCount = postService.findTotalCount(tabSearchDTO);
        MemberDTO memberDTO = memberService.findMainBlogMemberInfo(tabSearchDTO.getMemberCode());
        List<PostDTO> postList = postService.findPostList(tabSearchDTO); //TODO 수정필
        memberDTO.setMemberCode(tabSearchDTO.getMemberCode());


        Map<String, Object> postAllListAndPaging = postService.findAllPostList(tabSearchDTO, page, totalCount, isOwner);

        model.addAttribute("member", memberDTO);
        model.addAttribute("postList", postList);
        model.addAttribute("paging", postAllListAndPaging.get("paging"));
        model.addAttribute("postAllList", postAllListAndPaging.get("postAllList"));

        return "/post/list";
    }

    @GetMapping("/folder_list")
    public String folderListPage(@RequestParam(defaultValue = "1") int page, @ModelAttribute TabSearchDTO tabSearchDTO, @AuthenticationPrincipal MemberDTO member, Model model) {
        boolean isOwner = member.getMemberCode() == tabSearchDTO.getMemberCode();
        int totalCount = postService.findTotalCount(tabSearchDTO);

//        Map<String, Object> folderTotalCount = postService.findFolderTotalCount(tabSearchDTO, isOwner);

        MemberDTO memberDTO = memberService.findMainBlogMemberInfo(tabSearchDTO.getMemberCode());
        List<PostDTO> postList = postService.findPostList(tabSearchDTO); //TODO 수정필
        memberDTO.setMemberCode(tabSearchDTO.getMemberCode());


        Map<String, Object> postFolderAllListAndPaging = postService.findAllFolderPostList(tabSearchDTO, page, isOwner);

        model.addAttribute("member", memberDTO);
        model.addAttribute("postList", postList);
        model.addAttribute("paging", postFolderAllListAndPaging.get("paging"));
        model.addAttribute("postAllList", postFolderAllListAndPaging.get("postAllList"));
        model.addAttribute("folderCode", tabSearchDTO.getFolderCode());
//        model.addAttribute("folderCount", folderTotalCount.get("folderCount"));

        return "/post/folder_list";
    }

    @GetMapping("/detail")
    public String blogDetailPage(@AuthenticationPrincipal MemberDTO memberDTO, Integer postCode, Model model, CommentsDTO commentsDTO) {
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

        /* 댓글 모달에서 댓글 조회 */
        List<CommentsDTO> comments = commentsService.selectCommentsList(commentsDTO);
        model.addAttribute("comments", comments);

        return "/post/detail";
    }

    @GetMapping("/likelist")
    public void blogLikeListPage(int memberCode, Model model) {
        List<PostDTO> likeList = postService.findLikeListPostByMemberCode(memberCode);
        model.addAttribute("likeList", likeList);
    }

    @PostMapping("/like")
    @ResponseBody
    public ResponseEntity<String> likePost(@RequestBody LikeListDTO likeListDTO, @AuthenticationPrincipal MemberDTO memberDTO, Model model) {
        int memberCode = memberDTO.getMemberCode();
        try {
            boolean isLiked = postService.toggleLike(likeListDTO.getPostCode(), memberCode);
            String result = isLiked ? "true" : "false";
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    }

    @GetMapping("/load")
    public @ResponseBody List<PostDTO> findTabMenuPostList(@ModelAttribute TabSearchDTO tabSearchDTO, @AuthenticationPrincipal MemberDTO member) {
        List<PostDTO> postList = postService.findPostList(tabSearchDTO);
        postList.forEach(dto -> {
            dto.setPostText(Jsoup.parse(dto.getPostText()).text());
            dto.setLiked(likeService.getHasLiked(dto.getPostCode(), member.getMemberCode()));
        });
        return postList;
    }

    @PostMapping("/folder_edit")
    public @ResponseBody String folderEditList(@AuthenticationPrincipal MemberDTO memberDTO, @RequestBody List<FolderDTO> requestBody) {
        // 사용자 로그인 상태 검증
        if (memberDTO == null) {
            return "redirect:/member/login"; // 로그인 페이지로 리다이렉트
        }

        for (FolderDTO folderDTO : requestBody) {
            folderDTO.setFMemberCode(memberDTO.getMemberCode());
        }
        postService.updateFolders(requestBody);

        return "redirect:memberCode=" + memberDTO.getMemberCode() + "";
    }

    @PostMapping("/upload")
    public ResponseEntity<List<Map<String, String>>> uploadImagesPOST(@RequestParam("uploadFiles") MultipartFile[] uploadFiles) {
        List<Map<String, String>> results = new ArrayList<>();

        if (uploadFiles.length == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String savePath = FileUtil.getFilePath("userUploadFiles" + File.separator + "post");
        File uploadPath = new File(savePath);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        try {
            for (MultipartFile uploadFile : uploadFiles) {
                if (!uploadFile.isEmpty()) {
                    String originalFilename = uploadFile.getOriginalFilename();
                    String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    String uuid = UUID.randomUUID().toString();
                    String newFileName = uuid + "_" + originalFilename;

                    File saveFile = new File(uploadPath, newFileName);
                    uploadFile.transferTo(saveFile);

                    Map<String, String> fileInfo = new HashMap<>();
                    fileInfo.put("originalName", originalFilename);
                    fileInfo.put("newName", newFileName);
                    results.add(fileInfo);
                }
            }
            return ResponseEntity.ok(results);
        } catch (IOException e) {
            log.error("File upload failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> displayImageGET(@RequestParam("fileName") String fileName) {
        String savePath = FileUtil.getFilePath("userUploadFiles" + File.separator + "post");
        File file = new File(savePath + File.separator + fileName);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", Files.probeContentType(file.toPath()));
            return new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
        } catch (NoSuchFileException e) {
            log.error("No Such FileException {}", e.getFile());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IOException e) {
            log.error("Error reading file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/uploadBase64", method = RequestMethod.POST)
    public ResponseEntity<String> handleBase64Upload(@RequestBody String base64Image) {
        try {
            int maxLength = 10;
            String filename = truncateAndAppendTimestamp(base64Image, maxLength) + ".png";
            String savePath = FileUtil.getFilePath(""); // 빈 문자열 전달 시 기본 경로 사용
            String filePath = savePath + filename;

            File uploadPath = new File(savePath);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            File file = new File(filePath);
            Base64.Decoder decoder = Base64.getMimeDecoder();
            byte[] decodedBytes = decoder.decode(base64Image.getBytes());
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write(decodedBytes);
            }

            return ResponseEntity.ok(filename);
        } catch (IOException e) {
            log.error("File upload failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed.");
        }
    }

    public static String truncateAndAppendTimestamp(String base64Image, int maxLength) {
        String specialCharactersRegex = "[^a-zA-Z0-9]";
        String truncatedBase64Image = base64Image.length() > maxLength ? base64Image.substring(base64Image.length() - maxLength) : base64Image;
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")).replaceAll(specialCharactersRegex, "");
        return new StringJoiner("_").add(truncatedBase64Image.replaceAll(specialCharactersRegex, "")).add(timestamp).toString();
    }

    @Transactional
    @PostMapping("/write")
    public String postWrite(@AuthenticationPrincipal MemberDTO memberDTO, @ModelAttribute WriteDTO writeDTO, Model model) {
        // 사용자 로그인 상태 검증
        if (memberDTO == null) {
            return "redirect:/member/login"; // 로그인 페이지로 리다이렉트
        }

        try {
            PostDTO postDTO = writeDTO.getPostDTO();
            List<AttachmentDTO> attachments = writeDTO.getAttachmentDTOList();
            List<TagDTO> tags = writeDTO.getTagDTOList();

            postDTO.setMemberCode(memberDTO.getMemberCode());
            for (AttachmentDTO attachment : attachments) {
                attachment.setFilePath("/userUploadFiles/post");
            }

            //TODO 에디터에서 선택한 모든 이미지들이 tbl_attachment 에 insert 됨
            //TODO 1. 버튼 눌렀을때 현재 에디터에 남아있는 이미지들만 insert 되게 해야함.
            //TODO 2. 위작업 완료하면 서버에 있는 값과 비교해서 실제 쓰이지 않는 서버에 저장된 이미지들 삭제 로직 추가해야함
            //TODO 3. 이미지 서버에 저장되면서 새로운 이미지파일명이 기존 base64명 + 오리지널 명칭 인데 오리지널 명칭 삭제하는 로직 수정추가 해야함

            postService.addWritePostWithAttachmentsAndTags(postDTO, attachments, tags);
            return "redirect:/post/list?memberCode=" + memberDTO.getMemberCode();
        } catch (Exception e) {
            log.error("Error while posting", e);
            model.addAttribute("error", "Post submission failed.");
            return "/post/write"; // 오류 시 사용자에게 오류 페이지를 보여줌
        }
    }
}