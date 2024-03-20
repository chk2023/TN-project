package com._3dhs.tnproject.timeline.dao;

import com._3dhs.tnproject.post.dto.PostDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TimelineMapper {
    List<PostDTO> findTrendList();
}
