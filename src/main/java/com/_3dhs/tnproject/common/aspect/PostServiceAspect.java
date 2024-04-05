package com._3dhs.tnproject.common.aspect;

import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.post.service.LikeService;
import com._3dhs.tnproject.post.service.PostService;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import java.util.List;

@Aspect
@Component
public class PostServiceAspect {

    private final LikeService likeService;
    private final PostService postService;

    @Autowired
    public PostServiceAspect(LikeService likeService, PostService postService) {
        this.likeService = likeService;
        this.postService = postService;
    }

    @Pointcut("execution(* com._3dhs.tnproject.post.service.PostService.*(..)) && !execution(* com._3dhs.tnproject.post.service.PostService.findAllPostListForDoc(..))\"")
    public void postServiceMethods() {
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
                }
            }
        }
    }
}
