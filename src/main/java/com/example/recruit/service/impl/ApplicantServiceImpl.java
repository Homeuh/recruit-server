package com.example.recruit.service.impl;

import com.example.recruit.entity.Applicant;
import com.example.recruit.mapper.ApplicantMapper;
import com.example.recruit.service.ApplicantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 求职者用户表，记录求职者的一些基本信息 服务实现类
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Service
public class ApplicantServiceImpl extends ServiceImpl<ApplicantMapper, Applicant> implements ApplicantService {

}
