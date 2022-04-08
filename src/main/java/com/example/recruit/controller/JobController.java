package com.example.recruit.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.recruit.common.lang.Result;
import com.example.recruit.entity.Company;
import com.example.recruit.entity.Job;
import com.example.recruit.entity.Recruiter;
import com.example.recruit.util.DateUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 职位表，记录各个招聘官名下 发布 / 待发布 的职位信息 前端控制器
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@RestController
@RequestMapping("/job")
public class JobController extends BaseController {

    // 首页返回9条热门职位记录
    @GetMapping("/list")
    public Result list() {
        List<Map> mapList = new ArrayList<>();
        List<Job> jobList = jobService.list(new QueryWrapper<Job>().groupBy("company_id").last("limit 9"));
        for (Job job : jobList){
            if(mapList.size() == 9) break;
            Recruiter recruiter =recruiterService.getOne(new QueryWrapper<Recruiter>().
                    eq("recruiter_id",job.getRecruiterId()));
            Company company = companyService.getOne(new QueryWrapper<Company>().eq("company_id",recruiter.getCompanyId()));
            Map<String, Object> map = new HashMap<>();
            map.put("job_id",job.getJobId());
            map.put("name",job.getJobDuty());
            map.put("experience",job.getJobYear());
            map.put("qualification",job.getEducation());
            map.put("salary",job.getJobSalary());
            map.put("tag",job.getJobTag());
            map.put("company_id",company.getCompanyId());
            map.put("companyIcon",company.getCompanyLogo());
            map.put("companyName",company.getCompanyName());
            map.put("companyTag",company.getCompanyTag());
            map.put("companySize",company.getCompanySize());
            map.put("address",company.getCompanyCity() + "·" + company.getCompanyDistrict());
            mapList.add(map);
        }
        return Result.succ(mapList);
    }

    // 职位页无条件分页
    @GetMapping("/page")
    public Result page() {
        List<Map> mapList = new ArrayList<>();
        Page<Job> jobPage = jobService.page(getPage());
        for (Job job : jobPage.getRecords()) {
            Recruiter recruiter = recruiterService.getOne(new QueryWrapper<Recruiter>().eq("recruiter_id", job.getRecruiterId()));
            Company company = companyService.getOne(new QueryWrapper<Company>().eq("company_id", recruiter.getCompanyId()));
            Map<String, Object> map = new HashMap<>();
            map.put("job_id", job.getJobId());
            map.put("name", job.getJobDuty());
            map.put("address", company.getCompanyCity() + "·" + company.getCompanyDistrict());
            map.put("salary", job.getJobSalary());
            map.put("experience", job.getJobYear());
            map.put("qualification", job.getEducation());
            map.put("interviewer", recruiter.getRecruiterName());
            map.put("interviewerDuty", recruiter.getRecruiterDuty());
            map.put("companyName", company.getCompanyName());
            map.put("companyTag", company.getCompanyTag());
            map.put("companySize", company.getCompanySize());
            map.put("companyIcon", company.getCompanyLogo());
            map.put("tag", job.getJobTag());
            map.put("companyBenefit", job.getBenefitTag());
            mapList.add(map);
        }
        int total = jobService.count();
        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("jobList", mapList);
        return Result.succ(map);
    }

    // 查找职位详情信息
    @GetMapping("/info/{job_id}")
    public Object info(@PathVariable String job_id){
        try {
            Job job = jobService.getById(job_id);
            Map<String, Object> jobDataMap = new HashMap<>();
            jobDataMap.put("job_id", job.getJobId());
            jobDataMap.put("job_duty",job.getJobDuty());
            jobDataMap.put("job_salary",job.getJobSalary());
            jobDataMap.put("recruit_num",job.getRecruitNum());
            jobDataMap.put("job_year",job.getJobYear());
            jobDataMap.put("education",job.getEducation());
            jobDataMap.put("job_type",job.getJobType());
            jobDataMap.put("job_tag",job.getJobTag());
            jobDataMap.put("job_benefit",job.getJobBenefit());
            jobDataMap.put("job_description",job.getJobDescription());
            jobDataMap.put("job_requirement",job.getJobRequirement());
            jobDataMap.put("attached_info",job.getAttachedInfo());
            jobDataMap.put("interview_info",job.getInterviewInfo());
            jobDataMap.put("office_address",job.getOfficeAddress());
            jobDataMap.put("update_date", DateUtil.DateTimeTransform(job.getUpdateDate()));
            Recruiter recruiter = recruiterService.getById(job.getRecruiterId());
            jobDataMap.put("recruiter_name",recruiter.getRecruiterName());
            jobDataMap.put("recruiter_avatar",recruiter.getRecruiterAvatar());
            jobDataMap.put("recruiter_duty",recruiter.getRecruiterDuty());

            Company company = companyService.getById(recruiter.getCompanyId());
            Map<String, Object> companyMap = new HashMap<>();
            companyMap.put("company_name", company.getCompanyName());
            companyMap.put("company_logo", company.getCompanyLogo());
            companyMap.put("company_tag", company.getCompanyTag());
            companyMap.put("company_type", company.getCompanyType());
            companyMap.put("company_size", company.getCompanySize());
            companyMap.put("company_website", company.getCompanyWebsite());
            return Result.succ(MapUtil.builder().put("jobData",jobDataMap).put("company",companyMap).map());
        } catch(Exception e){
            return Result.fail(404,"数据库查找该条记录失败",e.toString());
        }
    };
}
