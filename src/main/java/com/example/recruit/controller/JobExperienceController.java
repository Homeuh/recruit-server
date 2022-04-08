package com.example.recruit.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.recruit.common.lang.Result;
import com.example.recruit.entity.Applicant;
import com.example.recruit.entity.JobExperience;
import com.example.recruit.entity.JobIntention;
import com.example.recruit.entity.Resume;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 工作经验表，记录与求职者简历相关的工作经验信息 前端控制器
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@RestController
@RequestMapping("/job-experience")
public class JobExperienceController extends BaseController {

    // 查找所有工作经验
    @GetMapping("/list/{resume_id}")
    public Object list(@PathVariable String resume_id) {
        try {
            List<JobExperience> experienceList = jobExperienceService.list(new QueryWrapper<JobExperience>().eq("resume_id",resume_id));
            List<Map<String, Object>> jobExperienceList = new ArrayList<>();
            for (JobExperience jobExperience: experienceList) {
                Map<String, Object> map = new HashMap<>();
                map.put("experience_id", jobExperience.getExperienceId());
                map.put("company_name", jobExperience.getCompanyName());
                map.put("job_duty",jobExperience.getJobDuty());
                map.put("job_description",jobExperience.getJobDescription());
                map.put("start_date", jobExperience.getStartDate());
                map.put("end_date", jobExperience.getEndDate());
                jobExperienceList.add(map);
            }
            return Result.succ(MapUtil.builder().put("jobExperienceList",jobExperienceList).map());
        } catch(Exception e){
            return Result.fail(404,"数据库查找该条记录失败",e.toString());
        }
    }

    // 插入或修改工作经验
    @PostMapping("/saveOrUpdate")
    public Object saveOrUpdate(@RequestBody JobExperience jobExperience) {
        try {
            jobExperienceService.saveOrUpdate(jobExperience);
            return Result.succ("保存成功");
        } catch(Exception e){
            return Result.fail(404,"该条记录插入或修改失败",e.toString());
        }
    }

    //删除工作经验
    @DeleteMapping("/delete")
    public Object delete(@RequestBody JobExperience jobExperience) {
        try {
            jobExperienceService.removeById(jobExperience);
            return Result.succ("删除成功");
        } catch(Exception e){
            return Result.fail(404,"该条记录删除失败",e.toString());
        }
    }

}
