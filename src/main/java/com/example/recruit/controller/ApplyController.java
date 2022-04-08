package com.example.recruit.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


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

}
