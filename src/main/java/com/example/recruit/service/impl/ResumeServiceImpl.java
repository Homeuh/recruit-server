package com.example.recruit.service.impl;

import com.example.recruit.entity.Resume;
import com.example.recruit.mapper.ResumeMapper;
import com.example.recruit.service.ResumeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 简历表，记录求职者的简历信息，支持导入pdf格式的简历 服务实现类
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Service
public class ResumeServiceImpl extends ServiceImpl<ResumeMapper, Resume> implements ResumeService {

}
