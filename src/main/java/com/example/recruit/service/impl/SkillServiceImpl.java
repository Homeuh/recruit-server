package com.example.recruit.service.impl;

import com.example.recruit.entity.Skill;
import com.example.recruit.mapper.SkillMapper;
import com.example.recruit.service.SkillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 专业技能表，记录与求职者简历相关的专业技能信息 服务实现类
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Service
public class SkillServiceImpl extends ServiceImpl<SkillMapper, Skill> implements SkillService {

}
