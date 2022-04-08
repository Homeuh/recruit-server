package com.example.recruit.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.recruit.common.lang.Result;
import com.example.recruit.entity.Education;
import com.example.recruit.entity.ProjectExperience;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 教育经历表，记录与求职者简历相关的教育经历信息 前端控制器
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@RestController
@RequestMapping("/education")
public class EducationController extends BaseController {

    // 查找所有教育经历
    @GetMapping("/list/{resume_id}")
    public Object list(@PathVariable String resume_id) {
        try {
            List<Education> educationList = educationService.list(new QueryWrapper<Education>().eq("resume_id",resume_id));
            List<Map<String, Object>> mapList = new ArrayList<>();
            for (Education education: educationList) {
                Map<String, Object> map = new HashMap<>();
                map.put("education_id", education.getEducationId());
                map.put("school_name", education.getSchoolName());
                map.put("education",education.getEducation());
                map.put("start_date", education.getStartDate());
                map.put("end_date", education.getEndDate());
                map.put("major",education.getMajor());
                map.put("examination_flag", education.getExaminationFlag());
                map.put("fulltime_flag", education.getFulltimeFlag());
                map.put("honor",education.getHonor());
                map.put("certificate", education.getCertificate());
                map.put("attached_info", education.getAttachedInfo());
                mapList.add(map);
            }
            return Result.succ(MapUtil.builder().put("educationList",mapList).map());
        } catch(Exception e){
            return Result.fail(404,"数据库查找该条记录失败",e.toString());
        }
    }

    // 插入或修改教育经历
    @PostMapping("/saveOrUpdate")
    public Object saveOrUpdate(@RequestBody Education education) {
        try {
            educationService.saveOrUpdate(education);
            return Result.succ("保存成功");
        } catch(Exception e){
            return Result.fail(404,"该条记录插入或修改失败",e.toString());
        }
    }

    //删除教育经历
    @DeleteMapping("/delete")
    public Object delete(@RequestBody Education education) {
        try {
            educationService.removeById(education);
            return Result.succ("删除成功");
        } catch(Exception e){
            return Result.fail(404,"该条记录删除失败",e.toString());
        }
    }

}
