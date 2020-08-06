package com.yuyiyun.YYdata.modular.news.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.modular.news.entity.DataWebMedia;
import com.yuyiyun.YYdata.modular.news.entity.DataWebSite;
import com.yuyiyun.YYdata.modular.news.mapper.DataWebMediaMapper;
import com.yuyiyun.YYdata.modular.news.mapper.DataWebSiteMapper;
import org.springframework.stereotype.Service;

@Service

public class DataWebMediaService  extends ServiceImpl<DataWebMediaMapper, DataWebMedia> {
}
