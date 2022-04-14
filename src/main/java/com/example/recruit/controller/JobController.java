package com.example.recruit.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.recruit.common.lang.Result;
import com.example.recruit.entity.Company;
import com.example.recruit.entity.Job;
import com.example.recruit.entity.Login;
import com.example.recruit.entity.Recruiter;
import com.example.recruit.util.DateUtil;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
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

    // 职位页分页：只展示已上线已发布的职位
    @GetMapping("/page")
    public Result page() {
        List<Map> mapList = new ArrayList<>();
        Page<Job> jobPage = jobService.page(getPage(), new QueryWrapper<Job>().eq("job_status","0"));
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
        int total = jobService.count(new QueryWrapper<Job>().eq("job_status","0"));
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
            companyMap.put("company_id",company.getCompanyId());
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

    /**
     * 招聘官职位管理分页（条件：职位状态、发布时间）：
     *    ①根据不同职位状态分页：全部null / 已发布"0" / 草稿箱"1" / 已下线"2"；
     *    ②根据发布时间分页（update_date，因为职位创建后可被修改状态）
     */
    @GetMapping("/jobManagePage")
    public Object jobManagePage(@RequestParam(name="login_id") String login_id,
                                @RequestParam(name="job_status") String job_status,
                                @RequestParam(name="condition") String condition) {
        List<Map> mapList = new ArrayList<>();
        Recruiter recruiter = recruiterService.getOne(new QueryWrapper<Recruiter>().eq("login_id",login_id));
        Page<Job> jobPage = jobService.page(getPage(), new QueryWrapper<Job>()
                .eq("recruiter_id",recruiter.getRecruiterId())
                .eq(StrUtil.isNotBlank(job_status),"job_status",job_status)
                .orderByDesc("update_date"));
        for (Job job : jobPage.getRecords()) {
            if(DateUtil.DateTimeReverse(job.getUpdateDate(),condition)) {
                Map<String, Object> map = new HashMap<>();
                map.put("job_id", job.getJobId());
                map.put("job_duty", job.getJobDuty());
                map.put("job_year", job.getJobYear());
                map.put("education", job.getEducation());
                map.put("job_type", job.getJobType());
                map.put("job_salary", job.getJobSalary());
                map.put("job_industry", job.getJobIndustry());
                map.put("office_city", job.getOfficeCity());
                map.put("office_district", job.getOfficeDistrict());
                map.put("job_status", job.getJobStatus());
                mapList.add(map);
            }
        }
        List<Job> jobList = jobService.list(new QueryWrapper<Job>()
                .eq("recruiter_id",recruiter.getRecruiterId())
                .eq(StrUtil.isNotBlank(job_status),"job_status",job_status));
        int total = 0;
        for (Job job: jobList) {
            if(DateUtil.DateTimeReverse(job.getUpdateDate(),condition)){
                total += 1;
            }
        }
        return Result.succ(MapUtil.builder().put("jobList",mapList).put("total",total).map());
    }

    //分组统计现有工作行业下该招聘官发布职位的数量，如：技术->1，市场->2，销售-><3
    @GetMapping("/countByIndustry/{login_id}")
    public Object countByIndustry(@PathVariable String login_id) {
        List<Map> mapList = new ArrayList<>();
        Recruiter recruiter = recruiterService.getOne(new QueryWrapper<Recruiter>().eq("login_id",login_id));
        // 保存所有该招聘官下的职位所属行业
        List<Job> jobList = jobService.list(new QueryWrapper<Job>()
                .eq("recruiter_id",recruiter.getRecruiterId())
                .groupBy("job_industry"));
        for (Job job: jobList) {
            Map<String, Object> map = new HashMap<>();
            int count = jobService.count(new QueryWrapper<Job>()
                    .eq("recruiter_id",recruiter.getRecruiterId())
                    .eq("job_industry",job.getJobIndustry()));
            map.put("job_industry",job.getJobIndustry());
            map.put("job_num",count);
            mapList.add(map);
        }
        return Result.succ(MapUtil.builder().put("industryList",mapList).map());
    }

    // 职位状态更新：上线 / 下线
    @PostMapping("/updateStatus")
    public Object updateStatus(@RequestBody Map<String, Object> map){
        try {
//            return Result.succ(MapUtil.builder().put("idArray",map.get("idArray")).put("job_status",map.get("job_status")).map());
            for (String job_id: (List<String>) map.get("idArray")) {
                jobService.update(new UpdateWrapper<Job>()
                    .eq("job_id", job_id)
                    .set("job_status", map.get("job_status"))
                    .set("update_date", LocalDateTime.now()));
            }
            return Result.succ("职位状态更新成功");
        } catch (Exception e) {
            return Result.fail("职位状态更新失败");
        }
    }

    //职位编辑：返回编辑页相应信息
    @GetMapping("/edit/{job_id}")
    public Object edit(@PathVariable String job_id) {
        try {
            Job job = jobService.getById(job_id);
            Map<String, Object> map = new HashMap<>();
            map.put("job_id", job.getJobId());
            map.put("job_duty", job.getJobDuty());
            map.put("job_industry", job.getJobIndustry());
            map.put("job_salary", job.getJobSalary());
            map.put("job_tag", job.getJobTag());
            map.put("job_year", job.getJobYear());
            map.put("education", job.getEducation());
            map.put("recruit_num", job.getRecruitNum());
            map.put("job_type", job.getJobType());
            map.put("job_description", job.getJobDescription());
            map.put("job_requirement", job.getJobRequirement());
            map.put("job_benefit", job.getJobBenefit());
            map.put("attached_info", job.getAttachedInfo());
            map.put("interview_info", job.getInterviewInfo());
            map.put("office_city", job.getOfficeCity());
            map.put("office_district", job.getOfficeDistrict());
            map.put("office_address", job.getOfficeAddress());
            return Result.succ(MapUtil.builder().put("jobForm",map).map());
        } catch (Exception e) {
            return Result.fail("获取职位信息失败");
        }
    }

    // 职位删除
    @DeleteMapping("/delete")
    public Object delete(@RequestBody Map<String, Object> map){
        try {
//            return Result.succ(map.get("idArray"));
            for (String job_id: (List<String>) map.get("idArray")) {
                jobService.remove(new QueryWrapper<Job>().eq("job_id", job_id));
            }
            return Result.succ("职位删除成功");
        } catch (Exception e) {
            return Result.fail("职位删除失败");
        }
    }

    //职位插入或修改，根据update_date返回最新记录
    @PostMapping("/saveOrUpdate")
    public Object saveOrUpdate(@RequestBody Job job) {
        try {
            Recruiter recruiter = recruiterService.getOne(new QueryWrapper<Recruiter>().eq("login_id",job.getLoginId()));
            job.setRecruiterId(recruiter.getRecruiterId());
            job.setCompanyId(recruiter.getCompanyId());
            jobService.saveOrUpdate(job);
            Job newJob = jobService.getOne(new QueryWrapper<Job>()
                    .eq("recruiter_id",job.getRecruiterId())
                    .orderByDesc("update_date")
                    .last("limit 1"));
            return Result.succ(MapUtil.builder().put("job_id",newJob.getJobId()).map());
        } catch (Exception e) {
            return Result.succ(400, "职位新增或更新失败",e.toString());
        }
    }

}
