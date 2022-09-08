package cn.chun.projs.myweb.controller;

import cn.chun.projs.myweb.mapper.UserMapper;
import cn.chun.projs.myweb.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author test@email

 * @date 2022/8/25
 */
@Slf4j
@RestController
public class UserController {

    @Resource
    private UserMapper userMapper;

    @RequestMapping("/user/getOne")
    public User getOne(@RequestParam(value = "id") Long id){
        log.info("id: " + Long.toString(id));
        return userMapper.findById(id);
    }

    @RequestMapping("/user/getAll")
    public List<User> getAll(){
        return userMapper.findAll();
    }
}
