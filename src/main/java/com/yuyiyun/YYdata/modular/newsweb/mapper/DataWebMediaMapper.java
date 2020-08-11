package com.yuyiyun.YYdata.modular.newsweb.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.modular.datasource.entity.DataApiauth;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebMedia;
import com.yuyiyun.YYdata.modular.newsweb.model.param.DataWebMediaParam;
import com.yuyiyun.YYdata.modular.newsweb.vo.Mediavo;
import com.yuyiyun.YYdata.modular.perfoapp.vo.PerfoAppVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * perfo_appraisalDAO接口
 * @author: jt
 * @date 2020-07-07 14:47
 */
public interface DataWebMediaMapper extends BaseMapper<DataWebMedia> {


    /**
     * 根据ID查询出媒体
     * @return
     */
    List<Map> ByidMedia(DataWebMedia mediavo);
    /**
     * 查询所有媒体
     * @return
     */
//    List<Map>  AllMedia(DataWebMedia mediavo);
    List<Map<String, Object>> AllMedia(@Param("page") Page pageContext, @Param("param") DataWebMediaParam param);

    /**
     * 新增媒体
     * @param media
     * @return
     */
    int inSerMedia(DataWebMedia media);


    /**
     * 根据id修改媒体
     * @param dataWebMedia
     * @return
     */
    int updatemedia(DataWebMedia dataWebMedia);


//    /**
//     * 删除媒体
//     * @param dataWebMedia
//     * @return
//     */
//    String delMedia(Mediavo dataWebMedia);
//
//    String delChannel(Mediavo dataWebMedia);

    /**
     * 判断新增媒体是否存在
     * @param dataWebMedia
     * @return
     */
    List<Map> selectMedia(DataWebMedia dataWebMedia);

    /**
     * 判断媒体总数是否存在
     * @param dataWebMedia
     * @return
     */
    List<Map> selectAllMedia(DataWebMedia dataWebMedia);




    
    

}
