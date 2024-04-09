package com._3dhs.tnproject.timeline.controller;

import com._3dhs.tnproject.post.dto.LikeListDTO;
import com._3dhs.tnproject.post.service.LikeService;
import com._3dhs.tnproject.post.service.PostService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class Recommend {

    private final LikeService likeService;
    private Map<Integer, Set<Integer>> likedContents = new HashMap<>();
    static double threshold = 0.5;

    @PostConstruct
    public void setMemberCollaborativeFiltering() {
        likedContents.clear();
        List<LikeListDTO> likeLists = likeService.getAllList();

        for (LikeListDTO dto : likeLists) {
            int memberCode = dto.getMemberCode();
            int postCode = dto.getPostCode();

            Set<Integer> posts = likedContents.get(memberCode);
            if (posts == null) {
                posts = new HashSet<>();
                likedContents.put(memberCode, posts);
            }
            posts.add(postCode);
        }
    }

    //두 사용자간의 유사도를 계산하는 메소드
    public double calculateSimilarity(int member1, int member2) {
        Set<Integer> likes1 = likedContents.getOrDefault(member1, Collections.emptySet());
        Set<Integer> likes2 = likedContents.getOrDefault(member2, Collections.emptySet());

        Set<Integer> commonLikes = new HashSet<>(likes1);
        commonLikes.retainAll(likes2);
        System.out.println(member1 + "이 좋아하느 글의 갯수 : " + likes1.size());
        System.out.println(member2 + "이 좋아하느 글의 갯수 : " + likes2.size());
        System.out.println("같이 좋아하는 글의 갯수 : " + commonLikes.size());
        System.out.println("유사도 계산" + (double) commonLikes.size() / Math.sqrt(likes1.size() * likes2.size()));
        return (double) commonLikes.size() / Math.sqrt(likes1.size() * likes2.size());
    }
    // 현재 사용자에게 추천할 글을 선정하는 메소드
    public Set<Integer> recommendPosts(int currentUser) {
        Set<Integer> recommendedPosts = new HashSet<>();
        log.info("유저코드{}의 유사도 검사 시작",currentUser);
        for (int member : likedContents.keySet()) {
            if (member != currentUser) {
                double similarity = calculateSimilarity(currentUser, member);
                log.info("{}과의 검사...유사도 {}",member,similarity);
                if (similarity >= threshold) {
                    log.info("{}이상이므로 리스트에 추가",threshold);
                    Set<Integer> memberLikes = likedContents.get(member);
                    memberLikes.removeAll(likedContents.getOrDefault(currentUser, Collections.emptySet()));
                    recommendedPosts.addAll(memberLikes);
                }
            }
        }
        setMemberCollaborativeFiltering();
        return recommendedPosts;
    }

}
