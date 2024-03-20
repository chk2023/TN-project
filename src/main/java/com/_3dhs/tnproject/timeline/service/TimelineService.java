package com._3dhs.tnproject.timeline.service;

import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.timeline.dao.TimelineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimelineService {

    private final TimelineMapper mapper;
    public List<PostDTO> findTrendList() {
        return mapper.findTrendList();
    }
}
