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
package me.zhengjie.modules.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import me.zhengjie.modules.domain.Tangtang;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.repository.TangtangRepository;
import me.zhengjie.modules.service.TangtangService;
import me.zhengjie.modules.service.dto.TangtangDto;
import me.zhengjie.modules.service.dto.TangtangQueryCriteria;
import me.zhengjie.modules.service.mapstruct.TangtangMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author TangTang
 * @website https://el-admin.vip
 * @description 服务实现
 * @date 2022-07-10
 **/
@Service
@RequiredArgsConstructor
@DS("slave")
public class TangtangServiceImpl implements TangtangService {

    private final TangtangRepository tangtangRepository;
    private final TangtangMapper tangtangMapper;

    @Override
    public Map<String, Object> queryAll(TangtangQueryCriteria criteria, Pageable pageable) {
        Page<Tangtang> page = tangtangRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(tangtangMapper::toDto));
    }

    @Override
    public List<TangtangDto> queryAll(TangtangQueryCriteria criteria) {
        return tangtangMapper.toDto(tangtangRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Transactional
    public TangtangDto findById(String id) {
        Tangtang tangtang = tangtangRepository.findById(id).orElseGet(Tangtang::new);
        ValidationUtil.isNull(tangtang.getId(), "Tangtang", "id", id);
        return tangtangMapper.toDto(tangtang);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TangtangDto create(Tangtang resources) {
        resources.setId(IdUtil.simpleUUID());
        return tangtangMapper.toDto(tangtangRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Tangtang resources) {
        Tangtang tangtang = tangtangRepository.findById(resources.getId()).orElseGet(Tangtang::new);
        ValidationUtil.isNull(tangtang.getId(), "Tangtang", "id", resources.getId());
        tangtang.copy(resources);
        tangtangRepository.save(tangtang);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            tangtangRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<TangtangDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TangtangDto tangtang : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("名称", tangtang.getUser());
            map.put("日期", tangtang.getUpdate());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}