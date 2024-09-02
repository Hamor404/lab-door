package com.villege.entity.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value="JsonData",description="选项列表")
public class JsonData implements Serializable{

	@ApiModelProperty("选项")
	private String id;
	@ApiModelProperty("选项内容")
	private String name;
	@ApiModelProperty("是否选中")
	private boolean selected;
	public JsonData() {

	}
	public JsonData(String id,String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
