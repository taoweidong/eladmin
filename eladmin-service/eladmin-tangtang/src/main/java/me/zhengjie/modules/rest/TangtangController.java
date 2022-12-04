/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.modules.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.domain.Tangtang;
import me.zhengjie.modules.service.TangtangService;
import me.zhengjie.modules.service.dto.TangtangQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author TangTang
* @date 2022-07-10
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "service管理")
@RequestMapping("/api/tangtang")
public class TangtangController {

    private final TangtangService tangtangService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tangtang:list')")
    public void exportTangtang(HttpServletResponse response, TangtangQueryCriteria criteria) throws IOException {
        tangtangService.download(tangtangService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询service")
    @ApiOperation("查询service")
    @PreAuthorize("@el.check('tangtang:list')")
    public ResponseEntity<Object> queryTangtang(TangtangQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(tangtangService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增service")
    @ApiOperation("新增service")
    @PreAuthorize("@el.check('tangtang:add')")
    public ResponseEntity<Object> createTangtang(@Validated @RequestBody Tangtang resources){
        return new ResponseEntity<>(tangtangService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改service")
    @ApiOperation("修改service")
    @PreAuthorize("@el.check('tangtang:edit')")
    public ResponseEntity<Object> updateTangtang(@Validated @RequestBody Tangtang resources){
        tangtangService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除service")
    @ApiOperation("删除service")
    @PreAuthorize("@el.check('tangtang:del')")
    public ResponseEntity<Object> deleteTangtang(@RequestBody String[] ids) {
        tangtangService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}