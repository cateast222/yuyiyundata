package  com.yuyiyun.YYdata.modular.perfoapp.service;
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

}
