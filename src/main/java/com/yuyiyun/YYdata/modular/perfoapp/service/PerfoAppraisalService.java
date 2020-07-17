package  com.yuyiyun.YYdata.modular.perfoapp.service;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.yuyiyun.YYdata.modular.perfoapp.vo.PerfoAppVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.modular.perfoapp.entity.PerfoAppraisalEntity;
import com.yuyiyun.YYdata.modular.perfoapp.mapper.PerfoAppraisalMapper;

import cn.hutool.core.util.PageUtil;
import cn.stylefeng.roses.core.util.ToolUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Arrays;
/**
 *
 * 绩效Service接口
 * @author: Duhao
 * @date 2020-07-07 14:47
 */
@Service
public class PerfoAppraisalService extends ServiceImpl<PerfoAppraisalMapper,PerfoAppraisalEntity> {

	@Resource
	private PerfoAppraisalMapper perfoAppraisalMapper;


	/**
	 * 查询出指定用户的历史记录
	 * @param
	 * @return
	 */
	public List<PerfoAppVo> selectSelf(PerfoAppraisalEntity p){

		List<PerfoAppVo> list = perfoAppraisalMapper.selectSelf(p);
		return list;

	}


	/**
	 * 保存绩效自评
	 * @param perfoAppraisalEntity
	 * @return
	 */
	public String insertPer(PerfoAppraisalEntity perfoAppraisalEntity){
		perfoAppraisalMapper.insert(perfoAppraisalEntity);
		return null;
	}


	/**
	 * 自己点击回显数据
	 * @param perfoAppraisalEntity
	 * @return
	 */
	public  List<PerfoAppraisalEntity> selectById(PerfoAppraisalEntity perfoAppraisalEntity){
		List <PerfoAppraisalEntity> list=perfoAppraisalMapper.selectById(perfoAppraisalEntity);
		return list;
	}

	/**
	 * 查询出当前启用的用户
	 * @param
	 * @return
	 */
	public  List<PerfoAppVo> select(){
		List <PerfoAppVo> list=perfoAppraisalMapper.select();
		return list;
	}

	/**
	 * 查询出当前用户
	 * @param p
	 * @return
	 */
	public List<PerfoAppVo> selectUser(PerfoAppVo p){
		List<PerfoAppVo> perfoAppVos = perfoAppraisalMapper.selectUser(p);
		return perfoAppVos;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 查询未审核的人员名单-
	 * @param perfoAppraisalEntity
	 * @return
	 */
	 public List<Map>  unrevisedAll(PerfoAppraisalEntity perfoAppraisalEntity){
       return perfoAppraisalMapper.unrevisedAll(perfoAppraisalEntity);
	}


	/**
	 * 查询历史记录的人员名单-
	 * @param
	 * @return
	 */
	public List<Map>  unrevisedHistory(PerfoAppVo appVo){
		return perfoAppraisalMapper.unrevisedHistory(appVo);
	}



	/**
	 *
	 * @param
	 * @return
	 */
	public int updatePerfo(PerfoAppraisalEntity perfoAppraisalEntity){
		return perfoAppraisalMapper.updatePerfo(perfoAppraisalEntity);
	}

	
	
	
	
	
	
	
	

}
