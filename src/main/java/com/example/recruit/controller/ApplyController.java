package com.example.recruit.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.recruit.common.lang.Result;
import com.example.recruit.entity.*;
import com.example.recruit.util.DateUtil;
import org.omg.CORBA.MARSHAL;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * <p>
 * 简历投递表，记录求职者的简历投递信息，简历投递记录不允许被求职者主动删除，因为与interview表关联，只有当求职者 / 招聘官账号注销、或者公司注销时才可被级联删除 前端控制器
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@RestController
@RequestMapping("/apply")
public class ApplyController extends BaseController {

    // 求职者个人中心页返回6条最新投递记录
    @GetMapping("/list/{login_id}")
    public Object list(@PathVariable String login_id) {
        try {
            Applicant applicant = applicantService.getOne(new QueryWrapper<Applicant>().eq("login_id",login_id));
            List<Apply> applyList = applyService.list(new QueryWrapper<Apply>()
                    .eq("applicant_id",applicant.getApplicantId())
                    .orderByDesc("create_date")
                    .last("limit 6"));
            List<Map> mapList = new ArrayList<>();
            for (Apply apply: applyList) {
                Job job = jobService.getById(apply.getJobId());
                Company company = companyService.getById(apply.getCompanyId());
                Map<String, Object> map = new HashMap<>();
                map.put("job_id", job.getJobId());
                map.put("job_duty", job.getJobDuty());
                map.put("job_salary", job.getJobSalary());
                map.put("office_city", job.getOfficeCity());
                map.put("job_year", job.getJobYear());
                map.put("education", job.getEducation());
                map.put("company_id",company.getCompanyId());
                map.put("company_logo", company.getCompanyLogo());
                map.put("company_name", company.getCompanyName());
                map.put("company_tag", company.getCompanyTag());
                map.put("company_type", company.getCompanyType());
                map.put("company_size", company.getCompanySize());
                map.put("create_date", apply.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                map.put("apply_status", apply.getApplyStatus());
                mapList.add(map);
            }
            int apply_num = applyService.count(new QueryWrapper<Apply>().eq("applicant_id",applicant.getApplicantId()));
            return Result.succ(MapUtil.builder().put("applyList",mapList).put("apply_num",apply_num).map());
        } catch (Exception e) {
            return Result.fail(404, "数据库无该求职者投递记录",e.toString());
        }
    }

    // 查询是否有该条投递记录
    @GetMapping("info/{login_id}/{job_id}")
    public Object info(@PathVariable String login_id, @PathVariable String job_id) {
        try {
            Applicant applicant = applicantService.getOne(new QueryWrapper<Applicant>().eq("login_id",login_id));
            int applyCount = applyService.count(new QueryWrapper<Apply>()
                    .eq("applicant_id",applicant.getApplicantId())
                    .eq("job_id",job_id));
            return Result.succ(MapUtil.builder().put("applyCount",applyCount).map());
        } catch (Exception e){
            return Result.fail(404,"数据库没有该条投递记录",e.toString());
        }
    }

    // 分页查找不同投递状态的投递记录
    @GetMapping("/page")
    public Object page(@RequestParam(name="apply_status") String apply_status, @RequestParam(name="login_id") String login_id) {
        Applicant applicant = applicantService.getOne(new QueryWrapper<Applicant>().eq("login_id",login_id));
        Page<Apply> applyPage = applyService.page(getPage(), new QueryWrapper<Apply>()
                .eq("applicant_id",applicant.getApplicantId())
                .eq(StrUtil.isNotBlank(apply_status)
                    ,"apply_status"
                    ,apply_status));
        List<Map> mapList = new ArrayList<>();
        for (Apply apply : applyPage.getRecords()) {
            Job job = jobService.getById(apply.getJobId());
            Company company = companyService.getById(apply.getCompanyId());
            Map<String, Object> map = new HashMap<>();
            map.put("apply_id", apply.getApplyId());
            map.put("job_duty",job.getJobDuty());
            map.put("job_salary",job.getJobSalary());
            map.put("office_city",job.getOfficeCity());
            map.put("job_year",job.getJobYear());
            map.put("education",job.getEducation());
            map.put("company_logo",company.getCompanyLogo());
            map.put("company_name",company.getCompanyName());
            map.put("company_tag",company.getCompanyTag());
            map.put("company_type",company.getCompanyType());
            map.put("company_size",company.getCompanySize());
            map.put("create_date",apply.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            map.put("apply_status",apply.getApplyStatus());
            mapList.add(map);
        }
        int total = applyService.count(new QueryWrapper<Apply>()
                .eq("applicant_id",applicant.getApplicantId())
                .eq(StrUtil.isNotBlank(apply_status)
                    , "apply_status"
                    ,apply_status));
        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("applyList", mapList);
        return Result.succ(map);
    }

    // 插入或修改投递记录
    @PostMapping("/saveOrUpdate")
    public Object saveOrUpdate(@RequestParam("login_id") String login_id, @RequestParam("job_id") String job_id){
        try {
            Applicant applicant = applicantService.getOne(new QueryWrapper<Applicant>().eq("login_id",login_id));
            Resume resume = resumeService.getOne(new QueryWrapper<Resume>().eq("applicant_id",applicant.getApplicantId()));
            Job job = jobService.getById(job_id);
            Apply apply = new Apply();
            apply.setApplicantId(applicant.getApplicantId());
            apply.setResumeId(resume.getResumeId());
            apply.setCompanyId(job.getCompanyId());
            apply.setJobId(job.getJobId());
            apply.setRecruiterId(job.getRecruiterId());
            applyService.saveOrUpdate(apply);
            return Result.succ("投递成功");
        } catch (Exception e){
            return Result.fail(404,"投递记录插入或修改失败",e.toString());
        }
    }

    // 删除投递记录，mybatis-plus有防全表删除插件，省事直接注释了
    @DeleteMapping("/delete")
    public Object delete(@RequestParam("login_id") String login_id, @RequestParam("job_id") String job_id) {
        try {
            Applicant applicant = applicantService.getOne(new QueryWrapper<Applicant>().eq("login_id",login_id));
            applyService.remove(new QueryWrapper<Apply>()
                    .eq("applicant_id",applicant.getApplicantId())
                    .eq("job_id",job_id));
            return Result.succ("删除成功");
        } catch (Exception e){
            return Result.fail(404,"数据库没有该条投递记录",e.toString());
        }
    }

    //分组统计现有工作行业下该招聘官已发布的有收到投递简历的职位数量，如：技术->1，市场->2，销售->3（只筛选投递状态为："1"简历已投递（默认） / "2"简历被查看 / "3"公司感兴趣）
    @GetMapping("/countByIndustry/{login_id}")
    public Object countByIndustry(@PathVariable String login_id) {
        Recruiter recruiter = recruiterService.getOne(new QueryWrapper<Recruiter>().eq("login_id",login_id));
        List<Apply> applyList = applyService.list(new QueryWrapper<Apply>()
                .eq("recruiter_id",recruiter.getRecruiterId())
                .in("apply_status", new String[]{"1", "2", "3"}));
        List<String> jobIds = new ArrayList<>();
        for (Apply apply: applyList) {
            jobIds.add(apply.getJobId());
        }

        List<Map> mapList = new ArrayList<>();
        if(jobIds.size() != 0) {
            List<Job> jobList = jobService.list(new QueryWrapper<Job>()
                    .in("job_id", jobIds)
                    .groupBy("job_industry"));
            for (Job job: jobList) {
                Map<String, Object> map = new HashMap<>();
                int count = jobService.count(new QueryWrapper<Job>()
                        .in("job_id", jobIds)
                        .eq("job_industry",job.getJobIndustry()));
                map.put("job_industry",job.getJobIndustry());
                map.put("job_num",count);
                mapList.add(map);
            }
        }
        return Result.succ(MapUtil.builder().put("industryList",mapList).map());
    }

    /**
     * 招聘官简历处理(投递管理)分页（条件：职位名称、投递时间）
     *    ①根据不同职位名称分页；
     *    ②根据发布时间分页（update_date，因为投递记录创建后可被修改投递状态）
     */
    @GetMapping("/applyManagePage")
    public Object applyManagePage(@RequestParam(name="login_id") String login_id,
                                  @RequestParam(name="job_duty") String job_duty,
                                  @RequestParam(name="job_industry") String job_industry,
                                  @RequestParam(name="condition") String condition) {
        /**
         * 筛选①：职位状态为"1"简历已投递（默认） / "2"简历被查看 / "3"公司感兴趣
         *    ②：参数条件下
         *    职位投递总数total，并保存职位ID列表
         */
        Recruiter recruiter = recruiterService.getOne(new QueryWrapper<Recruiter>().eq("login_id",login_id));
        List<String> jobIds = new ArrayList<>();
        List<Apply> applyList = applyService.list(new QueryWrapper<Apply>()
                .eq("recruiter_id",recruiter.getRecruiterId())
                .in("apply_status", new String[]{"1", "2", "3"}));
        int total = 0;
        for (Apply apply: applyList) {
            Job job = jobService.getById(apply.getJobId());
            // 如果职位名条件不为空，筛选职位名，不通过则跳转下一条投递记录
            if(StrUtil.isNotBlank(job_duty) && !job.getJobDuty().equals(job_duty)) {
                continue;
            }
            // 如果职位行业条件不为空，筛选职位条件，不通过则跳转下一条投递记录
            if(StrUtil.isNotBlank(job_industry) && !job.getJobIndustry().equals(job_industry)) {
                continue;
            }
            if(DateUtil.DateTimeReverse(job.getUpdateDate(),condition)){
                total += 1;
                jobIds.add(job.getJobId());
            }
        }

        // 根据职位ID对该招聘官名下职位投递进行分页，并返回前端所需投递相应信息
        List<Map> mapList = new ArrayList<>();
        if (jobIds.size() != 0){
            Page<Apply> applyPage = applyService.page(getPage(), new QueryWrapper<Apply>()
                    .eq("recruiter_id",recruiter.getRecruiterId())
                    .in("job_id",jobIds)
                    .orderByDesc("update_date"));
            for (Apply apply : applyPage.getRecords()) {
                Job job = jobService.getById(apply.getJobId());
                /*// 如果职位名条件不为空，筛选职位名，不通过则跳转下一条投递记录
                if(StrUtil.isNotBlank(job_duty) && !job.getJobDuty().equals(job_duty)) {
                    continue;
                }
                // 如果职位行业条件不为空，筛选职位条件，不通过则跳转下一条投递记录
                if(StrUtil.isNotBlank(job_industry) && !job.getJobIndustry().equals(job_industry)) {
                    continue;
                }*/
                if(DateUtil.DateTimeReverse(apply.getUpdateDate(),condition)) {
                    Applicant applicant = applicantService.getById(apply.getApplicantId());
                    Education education = new Education();
                    if(educationService.count(new QueryWrapper<Education>().eq("resume_id",apply.getResumeId())) != 0) {
                        education = educationService.getOne(new QueryWrapper<Education>()
                                .eq("resume_id",apply.getResumeId())
                                .eq("education",applicant.getApplicantEducation())
                                .last("limit 1"));
                    }
                    Map<String, Object> map = new HashMap<>();
                    map.put("apply_id", apply.getApplyId());
                    map.put("applicant_id", apply.getApplicantId());
                    map.put("applicant_avatar", applicant.getApplicantAvatar());
                    map.put("applicant_name", applicant.getApplicantName());
                    map.put("applicant_sex", applicant.getApplicantSex());
                    map.put("applicant_age", applicant.getApplicantAge());
                    map.put("working_year", applicant.getWorkingYear());
                    map.put("applicant_education", applicant.getApplicantEducation());
                    map.put("applicant_identity", applicant.getApplicantIdentity());
                    map.put("major", education.getMajor());
                    map.put("school_name", education.getSchoolName());
                    map.put("job_duty", job.getJobDuty());
                    map.put("job_industry", job.getJobIndustry());
                    map.put("create_date", apply.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                    mapList.add(map);
                }
            }
        }

        return Result.succ(MapUtil.builder().put("applyList",mapList).put("total",total).map());
    }

    // 查找所有该招聘官名下发布的职位名称
    @GetMapping("/getJobDuty/{login_id}")
    public Object getJobDuty(@PathVariable String login_id){
        try {
            Recruiter recruiter = recruiterService.getOne(new QueryWrapper<Recruiter>().eq("login_id",login_id));
            List<Job> jobList = jobService.list(new QueryWrapper<Job>().eq("recruiter_id",recruiter.getRecruiterId()));
            List<Map> jobDutyList = new ArrayList<>();
            for (Job job: jobList) {
                Map<String, Object> map = new HashMap<>();
                map.put("job_duty",job.getJobDuty());
                map.put("job_industry",job.getJobIndustry());
                jobDutyList.add(map);
            }
            return Result.succ(MapUtil.builder().put("conditionJob",jobDutyList).map());
        } catch (Exception e) {
            return Result.fail(404,"数据库未查找到相应的职位信息", e.toString());
        }
    }

    // 批量更新投递状态："0"不合适 / "1"简历已投递（默认） / "2"简历被查看 / "3"公司感兴趣 / "4"收到面试邀请 / "5"面试已结束
    @PostMapping("/updateStatus")
    public Object updateStatus(@RequestBody Map<String, Object> map) {
        try {
//            return Result.succ(MapUtil.builder().put("idArray",map.get("idArray")).put("apply_status",map.get("job_status")).map());
            for (String apply_id: (List<String>) map.get("idArray")) {
                applyService.update(new UpdateWrapper<Apply>()
                        .eq("apply_id", apply_id)
                        .set("apply_status", map.get("apply_status"))
                        .set("update_date", LocalDateTime.now()));
            }
            return Result.succ("投递状态更新成功");
        } catch (Exception e) {
            return Result.fail("投递状态更新失败");
        }
    }
}
