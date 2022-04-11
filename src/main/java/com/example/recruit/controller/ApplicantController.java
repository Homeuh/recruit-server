package com.example.recruit.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.recruit.common.lang.Result;
import com.example.recruit.entity.*;
import com.example.recruit.util.UploadUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 求职者用户表，记录求职者的一些基本信息 前端控制器
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@RestController
@RequestMapping("/applicant")
public class ApplicantController extends BaseController {

    // 登录后求职者身份转发
//    @GetMapping("/getUser/{login_id}/{login_role}")
//    public Result getUser(@PathVariable String login_id, @PathVariable String login_role) {
//        try{
//            Applicant applicant = applicantService.getOne(new QueryWrapper<Applicant>().eq("login_id",login_id));
//            Map<String, Object> map = new HashMap<>();
//            map.put("login_role", login_role);
//            map.put("applicant_name", applicant.getApplicantName());
//            return Result.succ(map);
//        } catch (Exception e) {
//            return Result.fail(404,"数据库未找到该条记录",null);
//        }
//    };

    // 查找求职责基本信息（个人中心侧栏）
    @GetMapping("/info/{login_id}")
    public Object info(@PathVariable String login_id){
        try {
            Applicant applicant = applicantService.getOne(new QueryWrapper<Applicant>().eq("login_id",login_id));
            Map<String, Object> map = new HashMap<>();
            map.put("applicant_id", applicant.getApplicantId());
            map.put("applicant_avatar",applicant.getApplicantAvatar());
            map.put("applicant_name",applicant.getApplicantName());
            map.put("applicant_sex",applicant.getApplicantSex());
            map.put("applicant_age",applicant.getApplicantAge());
            map.put("working_year",applicant.getWorkingYear());
            map.put("education",applicant.getApplicantEducation());
            int resume_percent = 40;
            if (resumeService.count(new QueryWrapper<Resume>().eq("applicant_id",applicant.getApplicantId())) != 0) {
                Resume resume = resumeService.getOne(new QueryWrapper<Resume>()
                        .eq("applicant_id",applicant.getApplicantId()));
                int jobIntentionCount = jobIntentionService.count(new QueryWrapper<JobIntention>()
                        .eq("resume_id",resume.getResumeId()));
                int jobExperienceCount = jobExperienceService.count(new QueryWrapper<JobExperience>()
                        .eq("resume_id",resume.getResumeId()));
                int projectExperienceCount = projectExperienceService.count(new QueryWrapper<ProjectExperience>()
                        .eq("resume_id",resume.getResumeId()));
                int educationCount = educationService.count(new QueryWrapper<Education>()
                        .eq("resume_id",resume.getResumeId()));
                int skillCount = skillService.count(new QueryWrapper<Skill>()
                        .eq("resume_id",resume.getResumeId()));
                if(StrUtil.isNotBlank(resume.getSelfEvaluation())){
                    resume_percent += 10;
                }
                if(jobIntentionCount != 0){
                    resume_percent += 10;
                }
                if(jobExperienceCount != 0){
                    resume_percent += 10;
                }
                if(projectExperienceCount != 0){
                    resume_percent += 10;
                }
                if(educationCount != 0){
                    resume_percent += 10;
                }
                if(skillCount != 0){
                    resume_percent += 10;
                }
            }
            map.put("resume_percent",resume_percent);
            return Result.succ(MapUtil.builder().put("applicant",map).map());
        } catch(Exception e){
            return Result.fail(404,"数据库查找该条记录失败",e.toString());
        }
    };

    // 查找求职责基本信息
    @GetMapping("/baseInfo/{resume_id}")
    public Object baseInfo(@PathVariable String resume_id){
        try {
            Resume resume = resumeService.getById(resume_id);
            Applicant applicant = applicantService.getById(resume.getApplicantId());
            Map<String, Object> map = new HashMap<>();
            map.put("applicant_id", applicant.getApplicantId());
            map.put("applicant_avatar",applicant.getApplicantAvatar());
            map.put("applicant_name",applicant.getApplicantName());
            map.put("applicant_sex",applicant.getApplicantSex());
            map.put("applicant_age",applicant.getApplicantAge());
            map.put("applicant_identity",applicant.getApplicantIdentity());
            map.put("working_year",applicant.getWorkingYear());
            map.put("applicant_education",applicant.getApplicantEducation());
            map.put("applicant_tel",applicant.getApplicantTel());
            map.put("applicant_email",applicant.getApplicantEmail());
            map.put("applicant_city",applicant.getApplicantCity());
            return Result.succ(MapUtil.builder().put("resume",map).map());
        } catch(Exception e){
            return Result.fail(404,"数据库查找该条记录失败",e.toString());
        }
    }

    // 插入或修改求职者基本信息
    @PostMapping("/saveOrUpdate")
    public Object saveOrUpdate(@RequestBody Applicant applicant){
        try {
            applicantService.saveOrUpdate(applicant);
            return Result.succ(MapUtil.builder().put("resume",applicant).map());
        } catch(Exception e){
            return Result.fail(404,"数据库查找该条记录失败",e.toString());
        }
    }

    // 查找求职者ID
    @GetMapping("/getId/{login_id}")
    public Object getId(@PathVariable String login_id){
        try {
            Applicant applicant = applicantService.getOne(new QueryWrapper<Applicant>().eq("login_id",login_id));
            return Result.succ(MapUtil.builder().put("applicant_id",applicant.getApplicantId()).map());
        } catch(Exception e){
            return Result.fail(404,"该求职者简历不存在",e.toString());
        }
    }

    // 求职者上传头像
    @PostMapping("/upload/{login_id}")
    public Object upload(@RequestParam("file") MultipartFile multipartFile, @PathVariable String login_id){
        //调用工具类完成文件上传
        try{
            String imagePath = UploadUtil.upload(multipartFile);
            if (imagePath != null){
                applicantService.update(new UpdateWrapper<Applicant>()
                        .eq("login_id",login_id)
                        .set("applicant_avatar",imagePath));
                //创建一个HashMap用来存放图片路径
                Map<String, Object> map = new HashMap<>();
                map.put("url",imagePath);
                map.put("notice","上传成功");
                return Result.succ(map);
            }else{
                return Result.fail("上传失败");
            }
        }catch (Exception e){
            return Result.fail(e.toString());
        }
    }

    // 求职者上传默认头像
    @PostMapping("/uploadDefault")
    public Object uploadDefault(String avatarName,  String login_id){
        //调用工具类完成文件上传
        try{
            applicantService.update(new UpdateWrapper<Applicant>()
                    .eq("login_id",login_id)
                    .set("applicant_avatar",avatarName));
            //创建一个HashMap用来存放图片路径
            Map<String, Object> map = new HashMap<>();
            map.put("url",avatarName);
            map.put("notice","上传成功");
            return Result.succ(map);
        }catch (Exception e){
            return Result.fail(e.toString());
        }
    }

    // 获取求职者可修改账号信息
    @GetMapping("/setting/{login_id}")
    public Object setting(@PathVariable String login_id) {
        try {
            Login login = loginService.getById(login_id);
            Applicant applicant = applicantService.getOne(new QueryWrapper<Applicant>().eq("login_id",login_id));
            Map<String, Object> map = new HashMap<>();
            map.put("login_phone", login.getLoginPhone());
            map.put("password", login.getPassword());
            map.put("applicant_wechat", applicant.getApplicantWechat());
            map.put("applicant_email", applicant.getApplicantEmail());
            return Result.succ(MapUtil.builder().put("loginForm",map).map());
        } catch (Exception e) {
            return Result.fail(404,"账号信息获取失败",e.toString());
        }
    }

    //修改微信号码
    @PostMapping("/updateWechat")
    public Object updatePhone(@RequestBody Map<String, Object> map) {
        try{
            applicantService.update(new UpdateWrapper<Applicant>()
                    .eq("login_id",map.get("login_id"))
                    .set("applicant_wechat",map.get("newWechat")));
            return Result.succ("微信号修改成功");
        } catch(Exception e) {
            return Result.fail(404,"修改失败",e.toString());
        }
    }

    //修改绑定邮箱
    @PostMapping("/updateEmail")
    public Object updateEmail(@RequestBody Map<String, Object> map) {
        try{
            applicantService.update(new UpdateWrapper<Applicant>()
                    .eq("login_id",map.get("login_id"))
                    .set("applicant_email",map.get("newEmail")));
            return Result.succ("邮箱修改成功");
        } catch(Exception e) {
            return Result.fail(404,"修改失败",e.toString());
        }
    }
}
