package com.yuyiyun.YYdata.core.util;

import com.yuyiyun.YYdata.config.properties.YYdataProperties;
import com.yuyiyun.YYdata.core.common.constant.Const;
import com.yuyiyun.YYdata.core.common.node.MenuNode;
import cn.stylefeng.roses.core.util.SpringContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * api接口文档显示过滤
 *
 * @author duhao
 * @date 2017-08-17 16:55
 */
public class ApiMenuFilter extends MenuNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static List<MenuNode> build(List<MenuNode> nodes) {

        //如果关闭了接口文档,则不显示接口文档菜单
    	YYdataProperties gunsProperties = SpringContextHolder.getBean(YYdataProperties.class);
        if (!gunsProperties.getSwaggerOpen()) {
            List<MenuNode> menuNodesCopy = new ArrayList<>();
            for (MenuNode menuNode : nodes) {
                if (Const.API_MENU_NAME.equals(menuNode.getName())) {
                    continue;
                } else {
                    menuNodesCopy.add(menuNode);
                }
            }
            nodes = menuNodesCopy;
        }

        return nodes;
    }
}
