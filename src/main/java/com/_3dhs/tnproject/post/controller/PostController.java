package com._3dhs.tnproject.post.controller;

import com._3dhs.tnproject.comments.dto.CommentsDTO;
import com._3dhs.tnproject.comments.service.CommentsService;
import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.member.service.MemberService;
import com._3dhs.tnproject.post.dto.*;
import com._3dhs.tnproject.post.service.LikeService;
import com._3dhs.tnproject.post.service.PostService;
import com._3dhs.tnproject.post.util.FileUtil;
import com._3dhs.tnproject.purchase.service.PurchaseService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final PurchaseService purchaseService;

    @ModelAttribute
    public void addCommonAttributes(@RequestParam(defaultValue = "1") int page, @ModelAttribute TabSearchDTO tabSearchDTO, @ModelAttribute WriteDTO writeDTO, @AuthenticationPrincipal MemberDTO loginMemberDTO, Model model) {
        boolean isOwner = loginMemberDTO.getMemberCode() == tabSearchDTO.getMemberCode();
        System.out.println("공통 컨트롤러 isOwner 값 : " + isOwner);

        List<FolderDTO> folderList = postService.findFolderList(tabSearchDTO.getMemberCode());
        TabSearchDTO postViewLikeCount = postService.findPostLikeCount(tabSearchDTO.getMemberCode());
        MemberDTO memberDTO = memberService.findMainBlogMemberInfo(tabSearchDTO.getMemberCode());

        if (memberDTO != null) {
            memberDTO.setMemberCode(tabSearchDTO.getMemberCode());
            if (memberDTO.getMemberCode() == 0) {
                memberDTO.setMemberCode(loginMemberDTO.getMemberCode());
            }
        } else {
            System.out.println("공통 memberDTO null 이래!!");
        }

        Map<String, Object> postAllListAndPaging = null;

        if (tabSearchDTO.getPostStatus() != null) {
            if ("DRAFT".equals(tabSearchDTO.getPostStatus())) {
                tabSearchDTO.setMemberCode(loginMemberDTO.getMemberCode());
                postAllListAndPaging = postService.findAllPostList(tabSearchDTO, page, true);
            }
        } else {
            postAllListAndPaging = postService.findAllPostList(tabSearchDTO, page, isOwner);
        }

//        System.out.println("공통어트리뷰트 writeDTO : " + writeDTO);
//        System.out.println("공통어트리뷰트 tabSearchDTO : " + tabSearchDTO);
//        System.out.println("공통어트리뷰트 memberDTO : " + memberDTO);
//        System.out.println("공통어트리뷰트 로그인 memberDTO : " + loginMemberDTO);
        model.addAttribute("member", memberDTO);
        model.addAttribute("folderList", folderList);
        model.addAttribute("paging", postAllListAndPaging.get("paging"));
        model.addAttribute("totalCount", postAllListAndPaging.get("totalCount"));
        model.addAttribute("totalAllCount", postAllListAndPaging.get("totalAllCount"));
        model.addAttribute("postAllList", postAllListAndPaging.get("postAllList"));
        model.addAttribute("postView", postViewLikeCount);
        model.addAttribute("folderCode", tabSearchDTO.getFolderCode());
        model.addAttribute("memberCode", tabSearchDTO.getMemberCode());
//        System.out.println("공통 뷰반환 paging : " + postAllListAndPaging.get("paging"));
//        System.out.println("공통 뷰반환 totalCount : " + postAllListAndPaging.get("totalCount"));
//        System.out.println("공통 뷰반환 totalAllCount : " + postAllListAndPaging.get("totalAllCount"));
//        System.out.println("공통 뷰반환 postAllList : " + postAllListAndPaging.get("postAllList"));
//        System.out.println("공통 뷰반환 postAllList : " + postAllListAndPaging.get("postAllList"));
    }

    @GetMapping("/main")
    public void blogMainPage(@ModelAttribute TabSearchDTO tabSearchDTO, @AuthenticationPrincipal MemberDTO loginMemberDTO, Model model) {
    }

    @Transactional
    @GetMapping("/folder_edit")
    public String folderEditPage(@AuthenticationPrincipal MemberDTO loginMemberDTO, Model model) {
        List<FolderDTO> folderList = postService.findFolderList(loginMemberDTO.getMemberCode());

        if (folderList.isEmpty()) {
            System.out.println("폴더리스트 비어있는디?");// 비어있음 멤버코드로 10개 만들어줘
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
            System.out.println("컨트롤러단의 addDefaultFolders : " + addDefaultFolders);
            postService.addDefaultFolder(addDefaultFolders);
            folderList = postService.findFolderList(loginMemberDTO.getMemberCode());

        } else {
            System.out.println("폴더리스트에 정보 있는디!!!!?");
        }
        model.addAttribute("folderList", folderList);
        return "/post/folder_edit";
    }

    @GetMapping("/write")
    public String blogWritePage(@AuthenticationPrincipal MemberDTO loginMemberDTO, Model model) {
        if (postService.isFixedPost(loginMemberDTO.getMemberCode())) {
            // 거절 메시지를 모델에 추가하고, 거절 뷰 이름 반환
            model.addAttribute("message", true);
        }
        return "/post/write";
    }

    @GetMapping("/temporary_storage/list")
    public void temporaryStorageListPage(@AuthenticationPrincipal MemberDTO loginMemberDTO, Model model) {

    }

    @GetMapping("/list")
    public String blogListPage(@RequestParam(defaultValue = "1") int page, @ModelAttribute TabSearchDTO tabSearchDTO, @AuthenticationPrincipal MemberDTO loginMemberDTO, Model model) {
        return "/post/list";
    }

    @GetMapping("/folder_list")
    public String folderListPage(@RequestParam(defaultValue = "1") int page, @ModelAttribute TabSearchDTO tabSearchDTO, @AuthenticationPrincipal MemberDTO loginMemberDTO, Model model) {
        return "/post/folder_list";
    }

    @GetMapping("/detail")
    public String blogDetailPage(@AuthenticationPrincipal MemberDTO member, Integer postCode, Model model, CommentsDTO commentsDTO) {
        //1. 해당하는 코드의 post정보를 불러오기
        PostDTO targetPost = postService.findPostByPostCode(postCode);
        targetPost.setAttachmentList(postService.findAttListByPostCode(targetPost.getPostCode()));
        targetPost.makeThumbnailPath();
        targetPost.setLiked(likeService.getHasLiked(targetPost.getPostCode(), member.getMemberCode()));
        targetPost.setTagList(postService.getTagsByPostCode(targetPost.getPostCode()));

        //2. post 상태가 비공개라면 열람자가 일치하는지 확인
        if (targetPost.getPostStatus().equals("PRIVATE")) {
            if (member.getMemberCode() != targetPost.getMemberCode()) {
                //열람자가 일치하지 않으면 에러메세지 첨부
                model.addAttribute("errorMessage", accessor.getMessage("post.notEqualMember"));
                return "/post/detail"; //TODO : view에서 errorMessage가 있다면 이전화면으로 돌아가는 로직 작성해주세요
            }
        }


        //3. post가 유료글인지 판단하기
        if (targetPost.getPostPrice() > 0 && !purchaseService.isPostPurchased(member.getMemberCode(), postCode)) {
            model.addAttribute("paidContent", targetPost);
            model.addAttribute("postCode", postCode);
            System.out.println("Controller: postCode = " + postCode);
            return "/purchase/viewPurchasePage";  //TODO: getPaidPostInfo에서 "@ModelAttribute PostDTO paidContent"로 값 받아 사용하기
        }
        /* 댓글 모달에서 댓글 조회 */
        List<CommentsDTO> comments = commentsService.selectCommentsList(commentsDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("postCode", postCode);
        model.addAttribute("memberCode", member.getMemberCode());
        model.addAttribute("postDetail", targetPost);
        return "/post/detail";

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
        List<PostDTO> postList;
        if (tabSearchDTO.getTabMenu().equals("♡")) {
            postList = postService.findLikeListPostByMemberCode(tabSearchDTO);

        } else {
            postList = postService.findPostList(tabSearchDTO);
        }

        return postList;
    }
    @PostMapping("/folder_edit")
    public @ResponseBody String folderEditList(@AuthenticationPrincipal MemberDTO loginMemberDTO, @RequestBody List<FolderDTO> requestBody) {
        for (FolderDTO folderDTO : requestBody) {
            folderDTO.setFMemberCode(loginMemberDTO.getMemberCode());
        }
        postService.updateFolders(requestBody);

        return "redirect:memberCode=" + loginMemberDTO.getMemberCode() + "";
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
                    String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".")); //TODO 사용안되는데 확인바람
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
    public String postWrite(@AuthenticationPrincipal MemberDTO loginMemberDTO, @ModelAttribute WriteDTO writeDTO, Model model) {

        try {
            PostDTO postDTO = writeDTO.getPostDTO();
//            log.info("postStatus? : {}",postDTO.getPostStatus());
//            writeDTO.setAttachmentDTOList(new ArrayList<>());   //TODO 마스터거 일단 주석처리하고 넣음 nullPoint에러로 인해서 추가 개발완료시 지울것
            List<AttachmentDTO> attachments = writeDTO.getAttachmentDTOList();
            List<TagDTO> tags = writeDTO.getTagDTOList();

            postDTO.setMemberCode(loginMemberDTO.getMemberCode());
            if (attachments != null) {
                for (AttachmentDTO attachment : attachments) {
                    attachment.setFilePath("/userUploadFiles/post");
                }
            }

            //TODO 에디터에서 선택한 모든 이미지들이 tbl_attachment 에 insert 됨
            //TODO 1. 버튼 눌렀을때 현재 에디터에 남아있는 이미지들만 insert 되게 해야함.
            //TODO 2. 위작업 완료하면 서버에 있는 값과 비교해서 실제 쓰이지 않는 서버에 저장된 이미지들 삭제 로직 추가해야함
            //TODO 3. 이미지 서버에 저장되면서 새로운 이미지파일명이 기존 base64명 + 오리지널 명칭 인데 오리지널 명칭 삭제하는 로직 수정추가 해야함

            postService.addWritePostWithAttachmentsAndTags(postDTO, attachments, tags);

            return "redirect:/post/list?memberCode=" + loginMemberDTO.getMemberCode();
        } catch (Exception e) {
            log.error("Error while posting", e);
            model.addAttribute("error", "Post submission failed.");
            return "/post/write"; // 오류 시 사용자에게 오류 페이지를 보여줌
        }
    }
}