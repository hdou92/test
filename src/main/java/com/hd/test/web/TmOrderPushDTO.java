package com.hd.test.web;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @auther houdu
 * @date 2020/9/10
 */
@Data
public class TmOrderPushDTO implements Serializable {

	/**
	 * 推送类型
	 */
	@NotBlank(message = "订单类型不能为空")
	private String type;

	/**
	 * 推送时间
	 */
	@NotNull(message = "推送时间不能为空")
	private Long pushTimestamp;

	/**
	 * 推送方
	 */
	@NotBlank(message = "appId不能为空")
	private String appId;

	/**
	 * 店铺编码
	 */
	@NotBlank(message = "店铺编码不能为空")
	private String shopCode;

	/**
	 * 推送主体
	 */
	@NotBlank(message = "推送订单信息不能为空")
	private String data;
}
