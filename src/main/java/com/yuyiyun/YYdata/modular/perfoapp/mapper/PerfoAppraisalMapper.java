package com.yuyiyun.YYdata.modular.perfoapp.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuyiyun.YYdata.modular.perfoapp.entity.PerfoAppraisalEntity;
import com.yuyiyun.YYdata.modular.perfoapp.vo.PerfoAppVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * perfo_appraisalDAO接口
 * @author: Duhao
 * @date 2020-07-07 14:47
 */
public interface PerfoAppraisalMapper extends BaseMapper<PerfoAppraisalEntity> {

    /**
     * 查询出指定用户的历史记录
     * @param perfoAppraisalEntity
     * @return
     */
    List<PerfoAppVo> selectSelf(PerfoAppraisalEntity perfoAppraisalEntity);


    /**
     * 保存绩效自评
     * @param perfoAppraisalEntity
     * @return
     */
    String insertPer(PerfoAppraisalEntity perfoAppraisalEntity);


    /**
     * 自己点击回显数据
     * @param perfoAppraisalEntity
     * @return
     */
    List<PerfoAppraisalEntity> selectById(PerfoAppraisalEntity perfoAppraisalEntity);

    /**
     * 查询出当前启用的用户
     * @param
     * @return
     */
    List<PerfoAppVo> select();

    /**
     * 查询出当前用户
     * @return
     */
    List<PerfoAppVo> selectUser(PerfoAppVo perfoAppVo);

}
