package com.example.recruit.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.recruit.common.lang.Result;
import com.example.recruit.entity.Applicant;
import com.example.recruit.entity.Resume;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 简历表，记录求职者的简历信息，支持导入pdf格式的简历 前端控制器
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@RestController
@RequestMapping("/resume")
public class ResumeController extends BaseController {

    // 查找简历ID，若无则立即创建
    @GetMapping("/getId/{applicant_id}")
    public Object getId(@PathVariable String applicant_id){
        try {
            // 是否已创建简历，没有则立即创建
            if (resumeService.count(new QueryWrapper<Resume>().eq("applicant_id",applicant_id)) == 0){
                Resume resume = new Resume();
                resume.setApplicantId(applicant_id);
                resumeService.save(resume);
            }
            Resume resume = resumeService.getOne(new QueryWrapper<Resume>().eq("applicant_id",applicant_id));
            return Result.succ(MapUtil.builder().put("resume_id",resume.getResumeId()).map());
        } catch(Exception e){
            return Result.fail(404,"该求职者简历不存在",e.toString());
        }
    }

    // 查找简历个人优势
    @GetMapping("/selfEvaluation/{resume_id}")
    public Object selfEvaluation(@PathVariable String resume_id) {
        try {
            // 返回刚创建或已有的简历信息
            Resume resume = resumeService.getById(resume_id);
            Map<String, Object> map = new HashMap<>();
            map.put("resume_id", resume.getResumeId());
            map.put("applicant_id", resume.getApplicantId());
            map.put("self_evaluation",resume.getSelfEvaluation());
            return Result.succ(MapUtil.builder().put("resume",map).map());
        } catch(Exception e){
            return Result.fail(404,"数据库查找该条记录失败",e.toString());
        }
    }

    // 插入或修改简历个人优势
    @PostMapping("/saveOrUpdate")
    public Object saveOrUpdate(@RequestBody Resume resume){
        try {
            resumeService.saveOrUpdate(resume);
            return Result.succ(MapUtil.builder().put("resume",resume).map());
        } catch(Exception e){
            return Result.fail(404,"该条记录插入或修改失败",e.toString());
        }
    }
}
