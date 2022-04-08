package com.example.recruit.service.impl;

import com.example.recruit.entity.JobIntention;
import com.example.recruit.mapper.JobIntentionMapper;
import com.example.recruit.service.JobIntentionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 求职意向表，记录与求职者简历相关的求职意向信息 服务实现类
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Service
public class JobIntentionServiceImpl extends ServiceImpl<JobIntentionMapper, JobIntention> implements JobIntentionService {

}
