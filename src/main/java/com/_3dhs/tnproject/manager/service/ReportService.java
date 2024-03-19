package com._3dhs.tnproject.manager.service;

import com._3dhs.tnproject.manager.dao.ReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportMapper reportMapper;
}
