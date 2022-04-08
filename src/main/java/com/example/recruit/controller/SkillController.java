package com.example.recruit.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.recruit.common.lang.Result;
import com.example.recruit.entity.Education;
import com.example.recruit.entity.Skill;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 专业技能表，记录与求职者简历相关的专业技能信息 前端控制器
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@RestController
@RequestMapping("/skill")
public class SkillController extends BaseController {

    // 查找所有专业技能
    @GetMapping("/list/{resume_id}")
    public Object list(@PathVariable String resume_id) {
        try {
            List<Skill> skillList = skillService.list(new QueryWrapper<Skill>().eq("resume_id",resume_id));
            List<Map<String, Object>> mapList = new ArrayList<>();
            for (Skill skill: skillList) {
                Map<String, Object> map = new HashMap<>();
                map.put("skill_id", skill.getSkillId());
                map.put("skill_name", skill.getSkillName());
                map.put("mastery_degree",skill.getMasteryDegree());
                mapList.add(map);
            }
            return Result.succ(MapUtil.builder().put("skillList",mapList).map());
        } catch(Exception e){
            return Result.fail(404,"数据库查找该条记录失败",e.toString());
        }
    }

    // 插入或修改专业技能
    @PostMapping("/saveOrUpdate")
    public Object saveOrUpdate(@RequestBody Skill skill) {
        try {
            skillService.saveOrUpdate(skill);
            return Result.succ("保存成功");
        } catch(Exception e){
            return Result.fail(404,"该条记录插入或修改失败",e.toString());
        }
    }

    //删除专业技能
    @DeleteMapping("/delete")
    public Object delete(@RequestBody Skill skill) {
        try {
            skillService.removeById(skill);
            return Result.succ("删除成功");
        } catch(Exception e){
            return Result.fail(404,"该条记录删除失败",e.toString());
        }
    }

}
