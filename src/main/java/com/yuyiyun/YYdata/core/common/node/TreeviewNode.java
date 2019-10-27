package com.yuyiyun.YYdata.core.common.node;

import cn.stylefeng.roses.kernel.model.tree.Tree;
import lombok.Data;

import java.util.List;

/**
 * jquery ztree 插件的节点
 *
 * @author duhao
 * @date 2017年2月17日 下午8:25:14
 */
@Data
public class TreeviewNode implements Tree {

    /**
     * 附加信息，一般用于存业务id
     */
    private String tags;

    /**
     * 父级id
     */
    private String parentId;

    /**
     * 节点名称
     */
    private String text;

    /**
     * 子节点
     */
    private List<TreeviewNode> nodes;

    @Override
    public String getNodeId() {
        return tags;
    }

    @Override
    public String getNodeParentId() {
        return parentId;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public void setChildrenNodes(List childrenNodes) {
        this.nodes = childrenNodes;
    }

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<TreeviewNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<TreeviewNode> nodes) {
		this.nodes = nodes;
	}
}
