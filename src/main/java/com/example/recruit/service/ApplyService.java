package com.example.recruit.service;

import com.example.recruit.entity.Apply;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 简历投递表，记录求职者的简历投递信息，简历投递记录不允许被求职者主动删除，因为与interview表关联，只有当求职者 / 招聘官账号注销、或者公司注销时才可被级联删除 服务类
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
public interface ApplyService extends IService<Apply> {

}
