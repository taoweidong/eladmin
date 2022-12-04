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
package me.zhengjie.modules.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author TangTang
* @date 2022-07-10
**/
@Entity
@Data
@Table(name="tb_tangtang")
public class Tangtang implements Serializable {

    @Id
    @Column(name = "`id`")
    @ApiModelProperty(value = "编号")
    private String id;

    @Column(name = "`user`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "名称")
    private String user;

    @Column(name = "`update`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "日期")
    private Timestamp update;

    public void copy(Tangtang source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
