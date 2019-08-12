package com.sezioo.wechat_demo.tm.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "rp_notify_record_log")
public class RpNotifyRecordLog implements Serializable {
    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 最后修改时间
     */
    @Column(name = "edit_time")
    private Date editTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 通知记录ID
     */
    @Column(name = "notify_id")
    private String notifyId;

    /**
     * 请求内容
     */
    private String request;

    /**
     * 响应内容
     */
    private String response;

    /**
     * 商户编号
     */
    @Column(name = "merchant_no")
    private String merchantNo;

    /**
     * 商户订单号
     */
    @Column(name = "merchant_order_no")
    private String merchantOrderNo;

    /**
     * HTTP状态
     */
    @Column(name = "http_status")
    private String httpStatus;

    private static final long serialVersionUID = 1L;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取版本号
     *
     * @return version - 版本号
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 设置版本号
     *
     * @param version 版本号
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 获取最后修改时间
     *
     * @return edit_time - 最后修改时间
     */
    public Date getEditTime() {
        return editTime;
    }

    /**
     * 设置最后修改时间
     *
     * @param editTime 最后修改时间
     */
    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取通知记录ID
     *
     * @return notify_id - 通知记录ID
     */
    public String getNotifyId() {
        return notifyId;
    }

    /**
     * 设置通知记录ID
     *
     * @param notifyId 通知记录ID
     */
    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }

    /**
     * 获取请求内容
     *
     * @return request - 请求内容
     */
    public String getRequest() {
        return request;
    }

    /**
     * 设置请求内容
     *
     * @param request 请求内容
     */
    public void setRequest(String request) {
        this.request = request;
    }

    /**
     * 获取响应内容
     *
     * @return response - 响应内容
     */
    public String getResponse() {
        return response;
    }

    /**
     * 设置响应内容
     *
     * @param response 响应内容
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * 获取商户编号
     *
     * @return merchant_no - 商户编号
     */
    public String getMerchantNo() {
        return merchantNo;
    }

    /**
     * 设置商户编号
     *
     * @param merchantNo 商户编号
     */
    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    /**
     * 获取商户订单号
     *
     * @return merchant_order_no - 商户订单号
     */
    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    /**
     * 设置商户订单号
     *
     * @param merchantOrderNo 商户订单号
     */
    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    /**
     * 获取HTTP状态
     *
     * @return http_status - HTTP状态
     */
    public String getHttpStatus() {
        return httpStatus;
    }

    /**
     * 设置HTTP状态
     *
     * @param httpStatus HTTP状态
     */
    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }
}