package com._3dhs.tnproject.post.dao;

import com._3dhs.tnproject.post.dto.LikeListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LikeMapper {

    int addLike(LikeListDTO likeListDTO);
    void cancelLike(@Param("postCode") int postCode, @Param("memberCode") int memberCode);

    List<LikeListDTO> getLikeListByPostCode(@Param("postCode") int postCode);

    List<LikeListDTO> getPrivateLikesByPostCode(@Param("postCode") int postCode);


    boolean getHasLiked(int postCode, int memberCode);
}
