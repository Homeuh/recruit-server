package com.example.recruit.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.recruit.common.lang.Result;
import com.example.recruit.entity.Applicant;
import com.example.recruit.entity.Company;
import com.example.recruit.entity.Login;
import com.example.recruit.entity.Recruiter;
import com.example.recruit.service.RecruiterService;
import com.example.recruit.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * 企业招聘官用户表，记录招聘官的一些基本信息 前端控制器
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@RestController
@RequestMapping("/recruiter")
public class RecruiterController extends BaseController {

    // 查找招聘官信息
    @GetMapping("/info/{login_id}")
    public Object info(@PathVariable String login_id){
        try {
            Recruiter recruiter = recruiterService.getOne(new QueryWrapper<Recruiter>().eq("login_id", login_id));
            Map<String, Object> map = new HashMap<>();
            map.put("recruiter_id", recruiter.getRecruiterId());
            map.put("recruiter_avatar", recruiter.getRecruiterAvatar());
            map.put("recruiter_name",recruiter.getRecruiterName());
            map.put("recruiter_sex",recruiter.getRecruiterSex());
            map.put("recruiter_duty", recruiter.getRecruiterDuty());
            Company company = companyService.getById(recruiter.getCompanyId());
            map.put("company_logo",company.getCompanyLogo());
            map.put("company_name",company.getCompanyName());
            return Result.succ(MapUtil.builder().put("recruiterForm",map).map());
        } catch(Exception e){
            return Result.fail(404,"数据库没有该招聘官记录",e.toString());
        }
    };

    // 插入或修改招聘官
    @PostMapping("saveOrUpdate")
    public Object saveOrUpdate(@RequestBody Map<String, String> map) {
        try {
            Recruiter recruiter = new Recruiter();
            recruiter.setLoginId(map.get("login_id"));
            recruiter.setRecruiterName(map.get("recruiter_name"));
            recruiter.setRecruiterSex(map.get("recruiter_sex"));
            recruiter.setRecruiterDuty(map.get("recruiter_duty"));
            recruiter.setRecruiterAvatar(map.get("recruiter_avatar"));
            recruiter.setRecruiterTel(map.get("recruiter_tel"));
            recruiter.setRecruiterWechat(map.get("recruiter_wechat"));
            Company company = companyService.getOne(new QueryWrapper<Company>().eq("company_full_name",map.get("company_full_name")));
            recruiter.setCompanyId(company.getCompanyId());
            recruiterService.saveOrUpdate(recruiter);
            return Result.succ("插入或修改招聘官记录成功");
        } catch (Exception e) {
            return Result.fail(400,"插入或修改招聘官记录失败",e.toString());
        }
    }

    // 招聘官上传头像
    @PostMapping("/upload/{login_id}")
    public Object upload(@RequestParam("file") MultipartFile multipartFile, @PathVariable String login_id){
        //调用工具类完成文件上传
        try{
            String imagePath = UploadUtil.upload(multipartFile, "avatar");
            if (imagePath != null){
                recruiterService.update(new UpdateWrapper<Recruiter>()
                        .eq("login_id",login_id)
                        .set("recruiter_avatar",imagePath));
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

    // 招聘官上传默认头像
    @PostMapping("/uploadDefault")
    public Object uploadDefault(String avatarName,  String login_id){
        //调用工具类完成文件上传
        try{
            recruiterService.update(new UpdateWrapper<Recruiter>()
                    .eq("login_id",login_id)
                    .set("recruiter_avatar",avatarName));
            //创建一个HashMap用来存放图片路径
            Map<String, Object> map = new HashMap<>();
            map.put("url",avatarName);
            map.put("notice","上传成功");
            return Result.succ(map);
        }catch (Exception e){
            return Result.fail(e.toString());
        }
    }

    // 获取招聘官可修改账号信息
    @GetMapping("/setting/{login_id}")
    public Object setting(@PathVariable String login_id) {
        try {
            Login login = loginService.getById(login_id);
            Recruiter recruiter = recruiterService.getOne(new QueryWrapper<Recruiter>().eq("login_id",login_id));
            Map<String, Object> map = new HashMap<>();
            map.put("login_phone", login.getLoginPhone());
            map.put("password", login.getPassword());
            map.put("recruiter_tel", recruiter.getRecruiterTel());
            map.put("recruiter_wechat", recruiter.getRecruiterWechat());
            return Result.succ(MapUtil.builder().put("loginForm",map).map());
        } catch (Exception e) {
            return Result.fail(404,"账号信息获取失败",e.toString());
        }
    }

    // 招聘官设置个人展示页信息修改，单独写原因：头像不能修改
    @PostMapping("/update")
    public Object update(@RequestBody Recruiter recruiter){
        try {
            recruiterService.update(new UpdateWrapper<Recruiter>()
                    .eq("recruiter_id",recruiter.getRecruiterId())
                    .set("recruiter_name",recruiter.getRecruiterName())
                    .set("recruiter_sex",recruiter.getRecruiterSex())
                    .set("recruiter_duty",recruiter.getRecruiterDuty()));
            return Result.succ("更新成功");
        } catch (Exception e) {
            return Result.fail("更新失败");
        }
    }

    //修改联系电话
    @PostMapping("/updateTel")
    public Object updateTel(@RequestBody Map<String, Object> map) {
        try{
            recruiterService.update(new UpdateWrapper<Recruiter>()
                    .eq("login_id",map.get("login_id"))
                    .set("recruiter_tel",map.get("newTel")));
            return Result.succ("联系电话修改成功");
        } catch(Exception e) {
            return Result.fail(404,"修改失败",e.toString());
        }
    }

    //修改微信号码
    @PostMapping("/updateWechat")
    public Object updateWechat(@RequestBody Map<String, Object> map) {
        try{
            recruiterService.update(new UpdateWrapper<Recruiter>()
                    .eq("login_id",map.get("login_id"))
                    .set("recruiter_wechat",map.get("newWechat")));
            return Result.succ("微信号修改成功");
        } catch(Exception e) {
            return Result.fail(404,"修改失败",e.toString());
        }
    }

}
