package com.example.recruit.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.recruit.common.lang.Result;
import com.example.recruit.entity.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 项目经验表，记录与求职者简历相关的项目经验信息 前端控制器
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@RestController
@RequestMapping("/project-experience")
public class ProjectExperienceController extends BaseController {

    // 查找所有项目经验
    @GetMapping("/list/{resume_id}")
    public Object list(@PathVariable String resume_id) {
        try {
            List<ProjectExperience> experienceList = projectExperienceService.list(new QueryWrapper<ProjectExperience>().eq("resume_id",resume_id));
            List<Map<String, Object>> projectExperienceList = new ArrayList<>();
            for (ProjectExperience projectExperience: experienceList) {
                Map<String, Object> map = new HashMap<>();
                map.put("project_id", projectExperience.getProjectId());
                map.put("project_name", projectExperience.getProjectName());
                map.put("project_description",projectExperience.getProjectDescription());
                map.put("project_duty",projectExperience.getProjectDuty());
                map.put("duty_description", projectExperience.getDutyDescription());
                map.put("start_date", projectExperience.getStartDate());
                map.put("end_date", projectExperience.getEndDate());
                map.put("project_url", projectExperience.getProjectUrl());
                projectExperienceList.add(map);
            }
            return Result.succ(MapUtil.builder().put("projectExperienceList",projectExperienceList).map());
        } catch(Exception e){
            return Result.fail(404,"数据库查找该条记录失败",e.toString());
        }
    }

    // 插入或修改项目经验
    @PostMapping("/saveOrUpdate")
    public Object saveOrUpdate(@RequestBody ProjectExperience projectExperience) {
        try {
            projectExperienceService.saveOrUpdate(projectExperience);
            return Result.succ("保存成功");
        } catch(Exception e){
            return Result.fail(404,"该条记录插入或修改失败",e.toString());
        }
    }

    //删除项目经验
    @DeleteMapping("/delete")
    public Object delete(@RequestBody ProjectExperience projectExperience) {
        try {
            projectExperienceService.removeById(projectExperience);
            return Result.succ("删除成功");
        } catch(Exception e){
            return Result.fail(404,"该条记录删除失败",e.toString());
        }
    }

}
