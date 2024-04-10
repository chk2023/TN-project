package com._3dhs.tnproject.comments.dao;

import com._3dhs.tnproject.comments.dto.CommentsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentsMapper {
    int findCmtCountByPostCode(int postCode);

    List<CommentsDTO> selectCommentsList(CommentsDTO commentsDTO);

    int writeComments(CommentsDTO comments);

    int updateComments(int cmtCode, String cmtText);

    int deleteComments(int cmtCode);

    CommentsDTO getCommentByCommentsCode(int cmtCode);
}
