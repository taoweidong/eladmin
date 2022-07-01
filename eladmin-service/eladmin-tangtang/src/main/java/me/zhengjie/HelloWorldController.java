package me.zhengjie;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Taoweidong
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "测试模块开发功能：Hello")
@RequestMapping("/hello/world")
public class HelloWorldController {

    @Log("测试新增模块查询功能记录日志")
    @ApiOperation(value = "查询功能")
    @GetMapping
    //  @PreAuthorize("@el.check('app:list')")
    @AnonymousAccess
    public ResponseEntity<Object> queryApp() {
        return new ResponseEntity<>("Hello world tangtang.........", HttpStatus.OK);
    }
}
