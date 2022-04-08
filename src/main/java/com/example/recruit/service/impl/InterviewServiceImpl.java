package com.example.recruit.service.impl;

import com.example.recruit.entity.Interview;
import com.example.recruit.mapper.InterviewMapper;
import com.example.recruit.service.InterviewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 面试表，记录求职者的面试信息 服务实现类
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Service
public class InterviewServiceImpl extends ServiceImpl<InterviewMapper, Interview> implements InterviewService {

}
