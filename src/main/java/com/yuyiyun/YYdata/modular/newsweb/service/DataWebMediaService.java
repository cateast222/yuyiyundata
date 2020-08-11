package com.yuyiyun.YYdata.modular.newsweb.service;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebMedia;
import com.yuyiyun.YYdata.modular.newsweb.mapper.DataWebMediaMapper;
import com.yuyiyun.YYdata.modular.newsweb.model.param.DataWebMediaParam;
import com.yuyiyun.YYdata.modular.newsweb.vo.Mediavo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
@Service
public class DataWebMediaService extends ServiceImpl<DataWebMediaMapper, DataWebMedia> {


    @Autowired
    private  DataWebMediaMapper dataWebMediaMapper;
    
    @Autowired
    private DataWebSiteService dataWebMediaService;

    @Autowired
    private  DataWebChannelService dataWebChannelService;



    /**
     * 全部媒体的媒体信息
     * @param
     * @param
     * @return
     */
//    public List<Map>  AllMedia(DataWebMedia mediavo){
//        List<Map> maps = dataWebMediaMapper.AllMedia(mediavo);
//        return maps;
//    }
    public List<Map<String, Object>> AllMedia(Page page, DataWebMediaParam param) {
        return this.dataWebMediaMapper.AllMedia(page, param);
    }


    /**
     * 根据ID查询
     * @param
     * @param mediavo
     * @return
     */
    public List<Map> ByidMedia(DataWebMedia mediavo){
        List<Map> maps = dataWebMediaMapper.ByidMedia(mediavo);
        return maps;
    }




    /**
     * 删除媒体信息的媒体信息
     * @param
     * @return
     */
    @Transactional
    public boolean delMedia(DataWebMedia dataWebMedia){
        boolean b = this.removeById(dataWebMedia.getUuid());
        dataWebMediaService.deleteByMediaId(dataWebMedia.getUuid());
        dataWebChannelService.deleteByMediaId(dataWebMedia.getUuid());
        return b;
    }
    /**
     * 新增媒体信息
     * @param
     * @return
     */
    public int inSerMedia(DataWebMedia dataWebMedia){
        int i = dataWebMediaMapper.inSerMedia(dataWebMedia);

        return i;
    }

    /**
     * 根据ID查询
     * @param p
     * @return
     */
    public int updatemedia(DataWebMedia p){
        int updatemedia = dataWebMediaMapper.updatemedia(p);
        return updatemedia;
    }

    /**
     * 判断新增媒体是否存在
     * @param p
     * @return
     */
    public List<Map> selectMedia(DataWebMedia p){
        List<Map> s = dataWebMediaMapper.selectMedia(p);
        return s;
    }
    /**
     * 判断新增媒体是否存在
     * @param p
     * @return
     */
    public List<Map> selectAllMedia(DataWebMedia p){
        List<Map> s = dataWebMediaMapper.selectAllMedia(p);
        return s;
    }




}
