package com.sezioo.wechat_demo.tm.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "rp_notify_record")
public class RpNotifyRecord implements Serializable {
    /**
     * 主键ID
     */
    @Id
    private String id;

    /**
     * 版本事情
     */
    private Integer version;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 最后修改时间
     */
    @Column(name = "edit_time")
    private Date editTime;

    /**
     * 通知规则（单位:分钟）
     */
    @Column(name = "notify_rule")
    private String notifyRule;

    /**
     * 已通知次数
     */
    @Column(name = "notify_times")
    private Integer notifyTimes;

    /**
     * 最大通知次数限制
     */
    @Column(name = "limit_notify_times")
    private Integer limitNotifyTimes;

    /**
     * 通知请求链接（包含通知内容）
     */
    private String url;

    /**
     * 商户订单号
     */
    @Column(name = "merchant_order_no")
    private String merchantOrderNo;

    /**
     * 商户编号
     */
    @Column(name = "merchant_no")
    private String merchantNo;

    /**
     * 通知状态（对应枚举值）
     */
    private String status;

    /**
     * 通知类型
     */
    @Column(name = "notify_type")
    private String notifyType;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取版本事情
     *
     * @return version - 版本事情
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 设置版本事情
     *
     * @param version 版本事情
     */
    public void setVersion(Integer version) {
        this.version = version;
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
     * 获取通知规则（单位:分钟）
     *
     * @return notify_rule - 通知规则（单位:分钟）
     */
    public String getNotifyRule() {
        return notifyRule;
    }

    /**
     * 设置通知规则（单位:分钟）
     *
     * @param notifyRule 通知规则（单位:分钟）
     */
    public void setNotifyRule(String notifyRule) {
        this.notifyRule = notifyRule;
    }

    /**
     * 获取已通知次数
     *
     * @return notify_times - 已通知次数
     */
    public Integer getNotifyTimes() {
        return notifyTimes;
    }

    /**
     * 设置已通知次数
     *
     * @param notifyTimes 已通知次数
     */
    public void setNotifyTimes(Integer notifyTimes) {
        this.notifyTimes = notifyTimes;
    }

    /**
     * 获取最大通知次数限制
     *
     * @return limit_notify_times - 最大通知次数限制
     */
    public Integer getLimitNotifyTimes() {
        return limitNotifyTimes;
    }

    /**
     * 设置最大通知次数限制
     *
     * @param limitNotifyTimes 最大通知次数限制
     */
    public void setLimitNotifyTimes(Integer limitNotifyTimes) {
        this.limitNotifyTimes = limitNotifyTimes;
    }

    /**
     * 获取通知请求链接（包含通知内容）
     *
     * @return url - 通知请求链接（包含通知内容）
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置通知请求链接（包含通知内容）
     *
     * @param url 通知请求链接（包含通知内容）
     */
    public void setUrl(String url) {
        this.url = url;
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
     * 获取通知状态（对应枚举值）
     *
     * @return status - 通知状态（对应枚举值）
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置通知状态（对应枚举值）
     *
     * @param status 通知状态（对应枚举值）
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取通知类型
     *
     * @return notify_type - 通知类型
     */
    public String getNotifyType() {
        return notifyType;
    }

    /**
     * 设置通知类型
     *
     * @param notifyType 通知类型
     */
    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }
}