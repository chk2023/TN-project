package com._3dhs.tnproject.post.dao;


import com._3dhs.tnproject.post.dto.AttachmentDTO;
import com._3dhs.tnproject.post.dto.LikeListDTO;
import com._3dhs.tnproject.post.dto.PostDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    List<PostDTO> findListWithLike();

    List<LikeListDTO> findLikeListByCode(int postCode);

    int findLikeCountByPostCode(int postCode);

    List<AttachmentDTO> findAttListByPostCode(int postCode);
}
