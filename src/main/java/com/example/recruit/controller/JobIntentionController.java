package com.example.recruit.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.recruit.common.lang.Result;
import com.example.recruit.entity.Applicant;
import com.example.recruit.entity.JobIntention;
import com.example.recruit.entity.Resume;
import com.example.recruit.service.JobIntentionService;
import com.example.recruit.service.ResumeService;
import com.sun.org.apache.bcel.internal.generic.GETFIELD;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 求职意向表，记录与求职者简历相关的求职意向信息 前端控制器
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@RestController
@RequestMapping("/job-intention")
public class JobIntentionController extends BaseController {

    // 随机查找一条求职意向记录
    @GetMapping("/info/{login_id}")
    public Object info(@PathVariable String login_id) {
        try {
            Applicant applicant = applicantService.getOne(new QueryWrapper<Applicant>().eq("login_id",login_id));
            Resume resume = resumeService.getOne(new QueryWrapper<Resume>().eq("applicant_id",applicant.getApplicantId()));
            JobIntention jobIntention = jobIntentionService.getOne(new QueryWrapper<JobIntention>().eq("resume_id",resume.getResumeId()).last("limit 1"));
            Map<String, Object> map = new HashMap<>();
            map.put("intention_id", jobIntention.getIntentionId());
            map.put("intention_duty", jobIntention.getIntentionDuty());
            map.put("intention_type",jobIntention.getIntentionType());
            map.put("intention_city",jobIntention.getIntentionCity());
            map.put("min_salary",jobIntention.getMinSalary());
            map.put("max_salary",jobIntention.getMaxSalary());
            map.put("arrive_date",jobIntention.getArriveDate());
            return Result.succ(MapUtil.builder().put("jobIntention",map).map());
        } catch(Exception e){
            return Result.fail(404,"数据库查找该条记录失败",e.toString());
        }
    }

    // 查找所有求职意向
    @GetMapping("/list/{resume_id}")
    public Object list(@PathVariable String resume_id) {
        try {
            List<JobIntention> intentionList = jobIntentionService.list(new QueryWrapper<JobIntention>().eq("resume_id",resume_id));
            List<Map<String, Object>> jobIntentionList = new ArrayList<>();
            for (JobIntention jobIntention: intentionList) {
                Map<String, Object> map = new HashMap<>();
                map.put("intention_id", jobIntention.getIntentionId());
                map.put("intention_duty", jobIntention.getIntentionDuty());
                map.put("intention_type",jobIntention.getIntentionType());
                map.put("intention_city",jobIntention.getIntentionCity());
                map.put("min_salary",jobIntention.getMinSalary());
                map.put("max_salary",jobIntention.getMaxSalary());
                map.put("arrive_date",jobIntention.getArriveDate());
                jobIntentionList.add(map);
            }
            return Result.succ(MapUtil.builder().put("jobIntentionList",jobIntentionList).map());
        } catch(Exception e){
            return Result.fail(404,"数据库查找该条记录失败",e.toString());
        }
    }

    // 插入或修改求职意向
    @PostMapping("/saveOrUpdate")
    public Object saveOrUpdate(@RequestBody JobIntention jobIntention) {
        try {
            jobIntentionService.saveOrUpdate(jobIntention);
            return Result.succ("保存成功");
        } catch(Exception e){
            return Result.fail(404,"该条记录插入或修改失败",e.toString());
        }
    }

    //删除求职意向
    @DeleteMapping("/delete")
    public Object delete(@RequestBody JobIntention jobIntention) {
        try {
            jobIntentionService.removeById(jobIntention);
            return Result.succ("删除成功");
        } catch(Exception e){
            return Result.fail(404,"该条记录删除失败",e.toString());
        }
    }
}
