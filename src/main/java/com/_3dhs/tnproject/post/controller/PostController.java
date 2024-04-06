package com._3dhs.tnproject.post.controller;

import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.member.service.MemberService;
import com._3dhs.tnproject.post.dto.FolderDTO;
import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.post.dto.TabSearchDTO;
import com._3dhs.tnproject.post.model.PostState;
import com._3dhs.tnproject.post.service.LikeService;
import com._3dhs.tnproject.post.service.PostService;
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

    @GetMapping("/main")
    public void blogMainPage(@ModelAttribute TabSearchDTO tabSearchDTO, Model model) {
        List<FolderDTO> folderList = postService.findFolderList(tabSearchDTO.getMemberCode());
        MemberDTO memberDTO = memberService.findMainBlogMemberInfo(tabSearchDTO.getMemberCode());
        PostDTO postViewLikeCount = postService.findPostLikeCount(tabSearchDTO.getMemberCode());
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
    public void temporaryStorageListPage() {
    }

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
        List<PostDTO> postList = postService.findPostList(tabSearchDTO);
        postList.forEach(dto -> {
            dto.setLiked(likeService.getHasLiked(dto.getPostCode(), member.getMemberCode()));
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

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImagePOST(@RequestParam("uploadFile") MultipartFile[] uploadFile) {
        String savePath;

        // OS 따라 구분자 분리
        String os = System.getProperty("os.name").toLowerCase();
        savePath = os.contains("win") ? System.getProperty("user.dir") + "\\files\\image" : System.getProperty("user.dir") + "/files/image";

        java.io.File uploadPath = new java.io.File(savePath);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        try {
            for (MultipartFile multipartFile : uploadFile) {
                String uploadFileName = multipartFile.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                uploadFileName = uuid + "_" + uploadFileName;

                java.io.File saveFile = new java.io.File(uploadPath, uploadFileName);
                multipartFile.transferTo(saveFile);
                return ResponseEntity.ok(saveFile.getAbsolutePath());
            }
        } catch (Exception e) {
            log.error("File upload failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No files uploaded");
    }

    /**
     * 에디터 내 사진 파일 첨부
     *
     * @param fileName
     * @return
     */
    @GetMapping("/display")
    public ResponseEntity<byte[]> displayImageGET(@RequestParam("fileName") String fileName) {
        String os = System.getProperty("os.name").toLowerCase();
        String savePath = os.contains("win") ? System.getProperty("user.dir") + "\\files\\image\\" : System.getProperty("user.dir") + "/files/image/";

        java.io.File file = new java.io.File(savePath + fileName);
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
            int maxLength = 20;
            String filename = truncateAndAppendTimestamp(base64Image, maxLength) + ".png";
            String savePath;
            String filePath;

            String os = System.getProperty("os.name").toLowerCase();
            savePath = os.contains("win") ? System.getProperty("user.dir") + "\\files\\image" : System.getProperty("user.dir") + "/files/image";
            filePath = savePath + (os.contains("win") ? "\\" : "/") + filename;

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
}
