package com._3dhs.tnproject.post.dao;


import com._3dhs.tnproject.post.dto.AttachmentDTO;
import com._3dhs.tnproject.post.dto.LikeListDTO;
import com._3dhs.tnproject.post.dto.PostDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostMapper {

    int addLike(LikeListDTO likeListDTO);
    void cancelLike(@Param("postCode") int postCode, @Param("memberCode") int memberCode);
    boolean hasLiked(@Param("postCode") int postCode, @Param("memberCode") int memberCode);

    List<LikeListDTO> getLikeListByPostCode(@Param("postCode") int postCode);

    List<LikeListDTO> getPrivateLikesByPostCode(@Param("postCode") int postCode);

    LikeListDTO findLikeListByPostAndMemberCode(int postCode, int memberCode);



    List<PostDTO> findListByParam(Map<String ,Integer> params);

    List<LikeListDTO> findLikeListByCode(int postCode);

    List<AttachmentDTO> findAttListByPostCode(int postCode);

    List<PostDTO> findLikeListPostByMemberCode(int memberCode);



}
