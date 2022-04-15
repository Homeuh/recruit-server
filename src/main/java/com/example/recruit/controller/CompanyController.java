package com.example.recruit.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.recruit.common.lang.Result;
import com.example.recruit.entity.*;
import com.example.recruit.service.JobService;
import com.example.recruit.util.DateUtil;
import com.example.recruit.util.UploadUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 公司表，记录网站已注册的公司信息，一个公司注册成功后，系统会返回该公司唯一会员码，该公司名下所有的招聘人员必须通过会员码成为招聘官	 前端控制器
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@RestController
@RequestMapping("/company")
public class CompanyController extends BaseController {

    // 首页返回8个热门公司记录
    @GetMapping("/list")
    public Result list() {
        List<Map> mapList = new ArrayList<>();
        List<Company> companyList = companyService.list(new QueryWrapper<Company>().last("limit 8"));
        for (Company company: companyList){
            int evaluationCount = interviewEvaluationService.count(new QueryWrapper<InterviewEvaluation>()
                    .eq("company_id",company.getCompanyId()));
            int jobCount = jobService.count(new QueryWrapper<Job>().eq("company_id",company.getCompanyId()));
            Map<String, Object> map = new HashMap<>();
            map.put("company_id",company.getCompanyId());
            map.put("icon",company.getCompanyLogo());
            map.put("name",company.getCompanyName());
            map.put("tag",company.getCompanyTag());
            map.put("size",company.getCompanySize());
            map.put("description",company.getCompanyDescription());
            map.put("userComment",evaluationCount);
            map.put("recruit",jobCount);
            map.put("activity",Math.ceil(Math.random()*10 + 85));
            mapList.add(map);
        }
        return Result.succ(mapList);
    }

    // 公司页
    @GetMapping("/page")
    public Result page() {
        List<Map> mapList = new ArrayList<>();
        Page<Company> companyPage = companyService.page(getPage());
        for (Company company : companyPage.getRecords()) {
            int evaluationCount = interviewEvaluationService.count(new QueryWrapper<InterviewEvaluation>()
                    .eq("company_id",company.getCompanyId()));
            int jobCount = jobService.count(new QueryWrapper<Job>().eq("company_id",company.getCompanyId()));
            Map<String, Object> map = new HashMap<>();
            map.put("company_id",company.getCompanyId());
            map.put("company_logo",company.getCompanyLogo());
            map.put("company_name",company.getCompanyName());
            map.put("company_tag",company.getCompanyTag());
            map.put("company_size",company.getCompanySize());
            map.put("company_description",company.getCompanyDescription());
            map.put("userComment",evaluationCount);
            map.put("recruit",jobCount);
            map.put("activity",Math.ceil(Math.random()*10 + 85));
            mapList.add(map);
        }
        int total = companyService.count();
        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("companyList", mapList);
        return Result.succ(map);
    }

    // 查找公司详情信息
    @GetMapping("/info/{company_id}")
    public Object info(@PathVariable String company_id){
        try {
            Company company = companyService.getById(company_id);
            Map<String, Object> companyMap = new HashMap<>();
            companyMap.put("company_id", company.getCompanyId());
            companyMap.put("company_full_name", company.getCompanyFullName());
            companyMap.put("company_name", company.getCompanyName());
            companyMap.put("company_logo", company.getCompanyLogo());
            companyMap.put("company_description",company.getCompanyDescription());
            companyMap.put("company_address",company.getCompanyAddress());
            companyMap.put("company_tag", company.getCompanyTag());
            companyMap.put("company_type", company.getCompanyType());
            companyMap.put("company_size", company.getCompanySize());
            companyMap.put("company_website", company.getCompanyWebsite());
            companyMap.put("company_introduction",company.getCompanyIntroduction());
            List<Recruiter> recruiterList = recruiterService.list(new QueryWrapper<Recruiter>().eq("company_id",company_id));
            int job_total = 0;
            for (Recruiter recruiter: recruiterList) {
                job_total += jobService.count(new QueryWrapper<Job>().eq("recruiter_id",recruiter.getRecruiterId()));
            }
            companyMap.put("job_total",job_total);
            companyMap.put("resume_feedback",Math.ceil(Math.random()*10 + 90));
            companyMap.put("evaluation_total",100);
            companyMap.put("login_date","3天前");
            return Result.succ(MapUtil.builder().put("company",companyMap).map());
        } catch(Exception e){
            return Result.fail(404,"数据库查找该条记录失败",e.toString());
        }
    };

    // 查找公司热门招聘官(3个)
    @GetMapping("/infoHotRecruiter/{company_id}")
    public Object infoHotRecruiter(@PathVariable String company_id){
        try {
            List<Recruiter> recruiterList = recruiterService.list(new QueryWrapper<Recruiter>()
                    .eq("company_id",company_id)
                    .last("limit 3"));
            List<Map<String, Object>> hotRecruiter = new ArrayList<>();
            for (Recruiter recruiter: recruiterList) {
                Map<String, Object> hotRecruiterMap = new HashMap<>();
                hotRecruiterMap.put("recruiter_name", recruiter.getRecruiterName());
                hotRecruiterMap.put("recruiter_avatar", recruiter.getRecruiterAvatar());
                hotRecruiterMap.put("recruiter_duty", recruiter.getRecruiterDuty());
                hotRecruiter.add(hotRecruiterMap);
            }
            return Result.succ(MapUtil.builder().put("hotRecruiter",hotRecruiter).map());
        } catch(Exception e){
            return Result.fail(404,"数据库查找热门招聘官失败",e.toString());
        }
    }

    // 查找公司侧栏在招职位(5个)：职位状态为上线
    @GetMapping("/listJob/{company_id}")
    public Object listJob(@PathVariable String company_id){
        try {
            List<Recruiter> recruiterList = recruiterService.list(new QueryWrapper<Recruiter>().eq("company_id",company_id));
            List<String> recruiterIds = new ArrayList<>();
            for (Recruiter recruiter: recruiterList) {
                recruiterIds.add(recruiter.getRecruiterId());
            }
            List<Job> jobList = jobService.list(new QueryWrapper<Job>()
                        .in("recruiter_id",recruiterIds)
                        .eq("job_status", "0")
                        .last("limit 5"));
            List<Map<String, Object>> recruit_job = new ArrayList<>();
            for (Job job: jobList) {
                Map<String, Object> jobMap = new HashMap<>();
                jobMap.put("job_id",job.getJobId());
                jobMap.put("job_duty", job.getJobDuty());
                jobMap.put("job_salary", job.getJobSalary());
                jobMap.put("office_city", job.getOfficeCity());
                jobMap.put("job_year", job.getJobYear());
                jobMap.put("education", job.getEducation());
                recruit_job.add(jobMap);
            }
            return Result.succ(MapUtil.builder().put("recruit_job",recruit_job).map());
        } catch(Exception e){
            return Result.fail(404,"数据库查找在招职位失败",e.toString());
        }
    }

    // 查找公司所有在招职位：职位状态为上线
    @GetMapping("/pageJob/{company_id}")
    public Object pageJob(@PathVariable String company_id){
        try {
            List<Recruiter> recruiterList = recruiterService.list(new QueryWrapper<Recruiter>().eq("company_id",company_id));
            List<String> recruiterIds = new ArrayList<>();
            for (Recruiter recruiter: recruiterList) {
                recruiterIds.add(recruiter.getRecruiterId());
            }
            Page<Job> jobPage = jobService.page(getPage(), new QueryWrapper<Job>()
                    .in("recruiter_id",recruiterIds)
                    .eq("job_status", "0"));
            List<Map<String, Object>> filterJob = new ArrayList<>();
            for (Job job: jobPage.getRecords()){
                Map<String, Object> jobMap = new HashMap<>();
                jobMap.put("job_id",job.getJobId());
                jobMap.put("job_duty", job.getJobDuty());
                jobMap.put("job_salary", job.getJobSalary());
                jobMap.put("office_city", job.getOfficeCity());
                jobMap.put("job_year", job.getJobYear());
                jobMap.put("education", job.getEducation());
                Recruiter recruiter = recruiterService.getById(job.getRecruiterId());
                jobMap.put("recruiter_id",recruiter.getRecruiterId());
                jobMap.put("recruiter_name", recruiter.getRecruiterName());
                jobMap.put("recruiter_avatar", recruiter.getRecruiterAvatar());
                jobMap.put("recruiter_duty", recruiter.getRecruiterDuty());
                filterJob.add(jobMap);
            }
            int total = jobService.count(new QueryWrapper<Job>()
                    .eq("company_id",company_id)
                    .eq("job_status", "0"));
            return Result.succ(MapUtil.builder().put("filterJob",filterJob).put("total",total).map());
        } catch(Exception e){
            return Result.fail(404,"数据库查找在招职位失败",e.toString());
        }
    }

    // 查找公司表是否存在该公司,存在返回公司相应信息
    @GetMapping("/getCompanyInfo")
    public Object getCompanyInfo(@RequestParam(name="company_full_name") String company_full_name) {
        try {
            Company company = companyService.getOne(new QueryWrapper<Company>().eq("company_full_name",company_full_name));
            Map<String, Object> map = new HashMap<>();
            map.put("company_id", company.getCompanyId());
            map.put("company_full_name", company.getCompanyFullName());
            map.put("company_name", company.getCompanyName());
            map.put("company_logo", company.getCompanyLogo());
            map.put("company_tag", company.getCompanyTag());
            map.put("company_type", company.getCompanyType());
            return Result.succ(MapUtil.builder().put("company",map).map());
        } catch (Exception e) {
            return Result.fail(404,"该公司尚未注册",e.toString());
        }
    }

    // 插入或修改公司
    @PostMapping("/saveOrUpdate")
    public Object saveOrUpdate(@RequestBody Company company){
        try {
            companyService.saveOrUpdate(company);
            return Result.succ("插入或修改公司记录成功");
        } catch (Exception e) {
            return Result.fail(400,"插入或修改公司记录失败",e.toString());
        }
    }

    // 公司上传logo
    @PostMapping("/upload/{company_full_name}")
    public Object upload(@RequestParam("file") MultipartFile multipartFile, @PathVariable String company_full_name){
        //调用工具类完成文件上传
        try{
            String imagePath = UploadUtil.upload(multipartFile, "company");
            if (imagePath != null){
                companyService.update(new UpdateWrapper<Company>()
                        .eq("company_full_name",company_full_name)
                        .set("company_logo",imagePath));
                //创建一个HashMap用来存放图片路径
                Map<String, Object> map = new HashMap<>();
                map.put("url",imagePath);
                map.put("notice","上传成功");
                return Result.succ(map);
            }else{
                return Result.fail("上传失败");
            }
        }catch (Exception e){
            return Result.fail(e.toString());
        }
    }

    // 检验公司及其会员码是否与前端传递数据匹配，返回一个boolean结果
    @GetMapping("/getMemberCode")
    public Object getMemberCode(@RequestParam(name="company_id") String company_id, @RequestParam(name="member_code") String member_code) {
        try {
            int companyCount = companyService.count(new QueryWrapper<Company>()
                    .eq("company_id",company_id)
                    .eq("member_code",member_code));
            boolean companyExist = companyCount == 1;
            return Result.succ(MapUtil.builder().put("companyExist",companyExist).map());
        } catch (Exception e) {
            return Result.fail(400,"未知错误",e.toString());
        }
    }
}
