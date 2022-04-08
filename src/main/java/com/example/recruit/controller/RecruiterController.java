package com.example.recruit.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.recruit.common.lang.Result;
import com.example.recruit.entity.Recruiter;
import com.example.recruit.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * 企业招聘官用户表，记录招聘官的一些基本信息 前端控制器
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@RestController
@RequestMapping("/recruiter")
public class RecruiterController extends BaseController {

    // 查找招聘官信息
    @GetMapping("/info/{login_id}")
    public Object info(@PathVariable String login_id){
        try {
            Recruiter recruiter = recruiterService.getOne(new QueryWrapper<Recruiter>().eq("login_id", login_id));
            return Result.succ(recruiter);
        } catch(Exception e){
            return Result.fail(404,"数据库没有该招聘官记录",e.toString());
        }
    };

}
