package com._3dhs.tnproject.manager.controller;

import com._3dhs.tnproject.manager.service.ReportService;
import com._3dhs.tnproject.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ManagerController {
    private final ReportService reportService;
    private final MemberService memberService;
}
