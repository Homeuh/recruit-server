package com.example.recruit.service.impl;

import com.example.recruit.entity.JobHistory;
import com.example.recruit.mapper.JobHistoryMapper;
import com.example.recruit.service.JobHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 浏览记录表，记录求职者近期浏览过的职位信息 服务实现类
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Service
public class JobHistoryServiceImpl extends ServiceImpl<JobHistoryMapper, JobHistory> implements JobHistoryService {

}
