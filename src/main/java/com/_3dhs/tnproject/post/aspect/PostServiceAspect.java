package com._3dhs.tnproject.post.aspect;

import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.like.service.LikeService;
import com._3dhs.tnproject.post.service.PostService;
import com._3dhs.tnproject.search.controller.SearchController;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Pointcut;
import org.jsoup.Jsoup;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import java.io.IOException;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class PostServiceAspect {

    private final LikeService likeService;
    private final PostService postService;
    private final SearchController searchController;



    @Pointcut("execution(* com._3dhs.tnproject.post.service.PostService.*(..)) " +
            "&& !execution(* com._3dhs.tnproject.post.service.PostService.findAllPostListForDoc(..))\" " +
            "&& !execution(* com._3dhs.tnproject.post.service.PostService.findPostByPostCode(..))\"")
    public void postServiceMethods() {
    }

    @Pointcut("execution(* com._3dhs.tnproject.post.service.PostService.addWritePostWithAttachmentsAndTags(..))")
    public void writPostMethod() {
    }

    @AfterReturning(pointcut = "postServiceMethods()", returning = "result")
    public void processReturningPosts(Object result) {
        MemberDTO member = new MemberDTO();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            if (principal instanceof UserDetails) {
                member = (MemberDTO) principal;
            }
            if (result instanceof List && !((List<?>) result).isEmpty() && ((List<?>) result).get(0) instanceof PostDTO) {
                List<PostDTO> posts = (List<PostDTO>) result;
                for (int i = 0; i < posts.size(); i++) {
                    posts.get(i).setAttachmentList(postService.findAttListByPostCode(posts.get(i).getPostCode()));
                    posts.get(i).makeThumbnailPath();
                    posts.get(i).setLiked(likeService.getHasLiked(posts.get(i).getPostCode(), member.getMemberCode()));
                    posts.get(i).setPostText(Jsoup.parse(posts.get(i).getPostText()).text());
                }
            }
        }
    }

    @AfterReturning(pointcut = "writPostMethod()")
    public void writProcess() {

        PostDTO dto = postService.findLastInsertPost();
        System.out.println(dto.getPostTitle());
        try {
            searchController.insertNewPost(dto);
        } catch (IOException e) {
            throw new RuntimeException("게시물 색인 추가 중 오류 발생", e);
        }
    }
}
