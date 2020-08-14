package com.yuyiyun.YYdata.modular.newsweb.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.util.ToolsUtil;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebChannel;
import com.yuyiyun.YYdata.modular.newsweb.model.param.DataWebChannelParam;
import com.yuyiyun.YYdata.modular.newsweb.service.DataWebChannelService;

import cn.stylefeng.roses.core.reqres.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@Controller
@Api(value = "频道权限controller", tags = { "频道权限操作接口" })
@RequestMapping("/channel")
public class DataChannelController {
	@Autowired
	private DataWebChannelService channelService ;



	/**
	 * 分页查询
	 * 
	 * 
	 * */
	@ApiOperation(value = "分页查询", notes = "按网站uuid获取频道数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "网站uuid", required = true, paramType = "query", dataType = "Long"),
			@ApiImplicitParam(name = "page", value = "页数", required = true, paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "limit", value = "条数", required = true, paramType = "query", dataType = "int") })
	@PostMapping("/SelectPageByApi")
	@ResponseBody
	public ResponseData selectPageByApi(int page,int limit,Long id) {
		if(ToolsUtil.isEmpty(id)) {
			return ResponseData.error("缺少uuid请求参数"); 
		} else if (limit < 1 || page < 0) {
			return ResponseData.error("请求参数page、limit异常！");
		}else {
			LayuiPageInfo LayuiPageInfo = channelService.selectPageByApi(page, limit,id);
			return ResponseData.success(LayuiPageInfo);
		}
	}



	/**
	 * 批量更新
	 * 
	 * 
	 * */
	@ApiOperation(value = "批量更新状态接口", notes = "更新频道状态")
	@ApiImplicitParam(name = "uuids", value = "频道uuid数组",allowMultiple = true, required = true, paramType = "query", dataType = "Long")
	@ResponseBody
	@PutMapping(value = "/ApiUpdateBacth")
	public ResponseData dataUpdateBacth(Long[] uuids) {
		int i = channelService.updateBacth(uuids);
		if(i>0) {
			return ResponseData.success();
		}else {
			return ResponseData.error("更新失败");
		}
	}
	
	/**
	 * 批量删除
	 * 
	 * 
	 * */
	@ApiOperation(value = "批量删除接口", notes = "批量删除频道")
	@ApiImplicitParam(name = "uuids", value = "频道uuid数组",allowMultiple = true, required = true, paramType = "query", dataType = "Long")
	@ResponseBody
	@DeleteMapping(value = "/ApiDeleteBacth")
	public ResponseData deleteBacth(Long[] uuids) {
		int i = channelService.deleteBacth(uuids);
		if(i>0) {
			return ResponseData.success();
		}else {
			return ResponseData.error("删除失败");
		}
	}

	/**
	 * 修改
	 * 
	 * 
	 * */
	@ApiOperation(value = "频道修改信息", notes = "修改频道后返回对象")
	@ApiImplicitParam(name = "param", value = "修改参数", required = false, paramType = "body", dataType = "DataWebChannelParam")
	@ResponseBody
	@PutMapping(value = "/ApiUpdate")
	public ResponseData update(@RequestBody() DataWebChannelParam param) {
		DataWebChannel data = channelService.updateByApi(param);
		if(data!=null) {
			return ResponseData.success(data);
		}else {
			return ResponseData.error("修改失败");
		}
	}
	
	/**
	 * 删除接口
	 * 
	 * 
	 * */
	@ApiOperation(value = "删除频道", notes = "删除频道")
	@ApiImplicitParam(name = "uuid", value = "频道uuid", required = false, paramType = "query", dataType = "Long")
	@ResponseBody
	@DeleteMapping(value = "/DeleteByApi")
	public ResponseData delete(Long uuid) {
		if (ToolsUtil.isNotEmpty(uuid)) {
			channelService.deleteChannel(uuid);
			return ResponseData.success();
		} else {
			return ResponseData.error("频道删除异常！");
		}
	}


	/**
	 * 新增接口
	 * 
	 * 
	 * */
	@ApiOperation(value = "频道新增信息", notes = "新增频道后返回对象")
	@ApiImplicitParam(name = "dataWebChannelParam", value = "新增参数", required = false, paramType = "body", dataType = "DataWebChannelParam")
	@ResponseBody
	@PostMapping(value = "/InsertByApi")
	public ResponseData insert(@RequestBody() DataWebChannelParam dataWebChannelParam) {
		DataWebChannel insert = channelService.insert(dataWebChannelParam);
		if(insert!=null) {
			return ResponseData.success();
		}else {
			return ResponseData.error("新增失败");
		}
	}
	
	
	/**
	 * 查看详情接口
	 * 
	 * 
	 * */
	@ApiOperation(value = "获取频道信息", notes = "根据uuid查询频道信息")
	@ApiImplicitParam(name = "uuid", value = "频道uuid", required = true, paramType = "query", dataType = "Long")
	@GetMapping(value = "/SelectByApi")
	@ResponseBody
	public ResponseData selectById(Long uuid) {
		DataWebChannel entity = channelService.getById(uuid);
		return ResponseData.success(entity);
	}

}
