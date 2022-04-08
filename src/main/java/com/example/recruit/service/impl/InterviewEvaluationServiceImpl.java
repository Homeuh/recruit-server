package com.example.recruit.service.impl;

import com.example.recruit.entity.InterviewEvaluation;
import com.example.recruit.mapper.InterviewEvaluationMapper;
import com.example.recruit.service.InterviewEvaluationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 面试评价表，记录求职者应聘后的反馈信息，对职位描述、面试官印象、公司环境进行打分 服务实现类
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Service
public class InterviewEvaluationServiceImpl extends ServiceImpl<InterviewEvaluationMapper, InterviewEvaluation> implements InterviewEvaluationService {

}
