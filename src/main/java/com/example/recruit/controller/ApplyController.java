package com.example.recruit.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.recruit.common.lang.Result;
import com.example.recruit.entity.*;
import org.omg.CORBA.MARSHAL;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    public Object page(@RequestParam(name="apply_status") String apply_status) {
        Page<Apply> applyPage = applyService.page(getPage(), new QueryWrapper<Apply>()
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
}
