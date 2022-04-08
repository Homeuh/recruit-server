package com.example.recruit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.recruit.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @Autowired
    HttpServletRequest req;

    @Autowired
    AppealService appealService;

    @Autowired
    ApplicantService applicantService;

    @Autowired
    ApplyService applyService;

    @Autowired
    CompanyService companyService;

    @Autowired
    EducationService educationService;

    @Autowired
    InterviewService interviewService;

    @Autowired
    InterviewEvaluationService interviewEvaluationService;

    @Autowired
    JobCollectService jobCollectService;

    @Autowired
    JobService jobService;

    @Autowired
    JobExperienceService jobExperienceService;

    @Autowired
    JobHistoryService jobHistoryService;

    @Autowired
    JobIntentionService jobIntentionService;

    @Autowired
    LoginService loginService;

    @Autowired
    ProjectExperienceService projectExperienceService;

    @Autowired
    RecruiterService recruiterService;

    @Autowired
    ReportService reportService;

    @Autowired
    ResumeService resumeService;

    @Autowired
    SkillService skillService;

    public Page getPage() {
        int currentPage = ServletRequestUtils.getIntParameter(req, "currentPage", 1);
        int pageSize = ServletRequestUtils.getIntParameter(req, "pageSize", 10);

        return new Page(currentPage, pageSize);
    }

}
