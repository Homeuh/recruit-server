package com.example.recruit.service.impl;

import com.example.recruit.entity.Job;
import com.example.recruit.mapper.JobMapper;
import com.example.recruit.service.JobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 职位表，记录各个招聘官名下 发布 / 待发布 的职位信息 服务实现类
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {

}
