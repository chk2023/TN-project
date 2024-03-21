package com._3dhs.tnproject.post.dao;


import com._3dhs.tnproject.post.dto.LikeListDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {

    void addLike(LikeListDTO likeList);

}
