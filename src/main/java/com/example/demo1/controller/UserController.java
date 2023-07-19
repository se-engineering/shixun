package com.example.demo1.controller;

import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponseBody;
import com.example.demo1.dto.UserDTO;
import com.example.demo1.entity.User;
import com.example.demo1.mapper.UserMapper;
import com.example.demo1.service.UserService;
import com.example.demo1.utils.SMSUtil;
import com.example.demo1.utils.StringUtil;
import com.example.demo1.vo.Result;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@RestController //标记作用 标记当前为控制层
@CrossOrigin(exposedHeaders = "key,token")

public class UserController {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/test")
    public String test(){
        return "hello word";
    }
    @GetMapping("/sendSMS")
    public ResponseEntity<Result>sendSMS(String phone) {
//生成一个四位数的随机字符串
        String code = StringUtil.randomNumber(4);
//将验证码存入redis当中去
        String key = StringUtil.uuid();
        System.out.println(key);
        redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);
//phone 有了 code
        User user1 = userMapper.selectUserByPhone(phone);//查询用户手机号是否注册
        if(user1!=null){
            return ResponseEntity.status(200).body(Result.error("该手机号已经注册"));
        }//如果已经注册了，那么显示注册完毕，且不发送验证码
        try {
            SendSmsResponseBody sendSmsResponseBody = SMSUtil.sendSMS(phone,code);

            if ("OK".equals(sendSmsResponseBody.getCode())) {
                return ResponseEntity.status(200).header("key",key).body(Result.ok("短信发送成功"));
            } else {
                return ResponseEntity.status(200).body(Result.error("短信发送失败了"));
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
            return ResponseEntity.status(200).body(Result.error("短信发送失败2"));
        } catch (InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(200).body(Result.error("短信发送失败3"));
        }
    }
    @PostMapping("reg")
    public ResponseEntity<Result>reg(@Validated @RequestBody UserDTO userDTO, @RequestHeader("key")String key){
//首先先取验证码 o就是真正的验证码
        String o = (String)redisTemplate.opsForValue().get(key);
//判断验证码是否过期
        String phone= userDTO.getPhone();

        if (o==null){
            return ResponseEntity.status(200).body(Result.error("验证码过期"));
        }
//验证码是否正确
        if (!o.equals(userDTO.getCode())){
            return ResponseEntity.status(200).body(Result.error("验证码错误"));
        }

        boolean success = userService.reg(userDTO);
        return success ? ResponseEntity.status(200).body(Result.ok("注册成功")) : ResponseEntity.status(200).body(Result.error("注册失败"));
    }
    @GetMapping("imageCode")
    public ResponseEntity<Result>imageCode(){
        // 获取图形验证码的内容
        String text = defaultKaptcha.createText();
        System.out.println(text);
        //保存下来的 text 放入 redis里面，便于登录的时候进行对比
        String key = StringUtil.uuid();
        System.out.println(key);
        //存入redis
        redisTemplate.opsForValue().set(key,text,5,TimeUnit.MINUTES);
        //生成一张图片
        BufferedImage image = defaultKaptcha.createImage(text);
        //将图片转换成base64加密的字符串
        String s = StringUtil.changeImage2String(image);
        return  ResponseEntity.status(200).header("key",key).body(Result.ok("成功",s));
    }
    @PostMapping("login")
    public ResponseEntity<Result>login(@Validated @RequestBody UserDTO userDTO, @RequestHeader("key")String key){
        //获取图形验证码  通过key
        String o = (String)redisTemplate.opsForValue().get(key);
        if (o==null){
            return ResponseEntity.status(200).body(Result.error("验证码过期"));
        }
        if (!o.equals(userDTO.getCode())){
            return ResponseEntity.status(200).body(Result.error("验证码输入错误"));
        }
        String login = userService.login(userDTO);

        return ResponseEntity.status(200).body(Result.ok("登陆成功"));
    }

}
