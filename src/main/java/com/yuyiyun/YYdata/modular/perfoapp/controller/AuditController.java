package com.yuyiyun.YYdata.modular.perfoapp.controller;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.modular.perfoapp.entity.PerfoAppraisalEntity;
import com.yuyiyun.YYdata.modular.perfoapp.vo.PerfoAppVo;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yuyiyun.YYdata.modular.perfoapp.service.PerfoAppraisalService;

import cn.stylefeng.roses.core.base.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/perfoAppraisal")
public class AuditController extends BaseController{
	

	private String PREFIX = "/modular/perfoapp";
	
	@Autowired
	private PerfoAppraisalService perfoAppraisalService;
	
	   /**
		 * 
		 * 
		 * @author 
		 * @return
		 */
		@GetMapping("/audit")
		public String audit(Model model) {
			return PREFIX + "/audit.html";
		}



		/**
		 * 查询未审核的人员名单-
		 * @param perfoAppraisalEntity
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "/unrevisedAll")
		public ResponseData unrevisedAll(PerfoAppraisalEntity perfoAppraisalEntity){
			Long userId = ShiroKit.getUser().getId();
			perfoAppraisalEntity.setSuperior(String.valueOf(userId));
			List<Map> perfoAppraisalEntities = perfoAppraisalService.unrevisedAll(perfoAppraisalEntity);

			return  ResponseData.success(0,"成功",perfoAppraisalEntities);
 		}

		/**
	 * 查询历史记录的人员名单-
	 * @param
	 * @return
	 */
		@ResponseBody
		@RequestMapping(value = "/unrevisedHistory")
		public ResponseData unrevisedHistory(PerfoAppVo perfoAppVo,HttpServletRequest request){
			Long userId = ShiroKit.getUser().getId();
			 perfoAppVo.setSuperior(String.valueOf(userId));
			 List<Map> perfoAppraisalEntities = perfoAppraisalService.unrevisedHistory(perfoAppVo);
			 return  ResponseData.success(0,"成功",perfoAppraisalEntities);
		}


	   /**
		 * 查询历史记录的人员名单-
		 * @param
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "/updatePerfo")
		public ResponseData updatePerfo(PerfoAppraisalEntity perfoAppraisalEntity){
			int i = perfoAppraisalService.updatePerfo(perfoAppraisalEntity);
			if(i>0){
				return  ResponseData.success(0,"审核成功",i);
			}
			return  ResponseData.success(0,"审核失败",i);


		}



			@RequestMapping("/edidUnrevised")
			public String edidUnrevised(PerfoAppraisalEntity perfoAppraisalEntity){
				return PREFIX + "/edit.html";
			}




	}


