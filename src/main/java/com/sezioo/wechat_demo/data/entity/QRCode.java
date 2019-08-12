package com.sezioo.wechat_demo.data.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "qr_code")
public class QRCode implements Serializable {
    /**
     * 二维码ID
     */
    @Id
    private Integer id;

    /**
     * 1：临时；2：永久；3：其他
     */
    private Integer type;

    /**
     * 提取二维码ticket
     */
    private String ticket;

    /**
     * 有效期
     */
    private Date validity;

    /**
     * 1：有效；2：冻结
     */
    private Integer status;

    /**
     * 分享次数
     */
    @Column(name = "share_times")
    private Integer shareTimes;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 用户微信ID
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 二维码图片地址
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 创建人ID
     */
    @Column(name = "create_by")
    private Integer createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 更新人ID
     */
    @Column(name = "update_by")
    private Integer updateBy;

    /**
     * 更新时间
     */
    @Column(name = "update_date")
    private Date updateDate;

    private static final long serialVersionUID = 1L;

    /**
     * 获取二维码ID
     *
     * @return id - 二维码ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置二维码ID
     *
     * @param id 二维码ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取1：临时；2：永久；3：其他
     *
     * @return type - 1：临时；2：永久；3：其他
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置1：临时；2：永久；3：其他
     *
     * @param type 1：临时；2：永久；3：其他
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取提取二维码ticket
     *
     * @return ticket - 提取二维码ticket
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * 设置提取二维码ticket
     *
     * @param ticket 提取二维码ticket
     */
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    /**
     * 获取有效期
     *
     * @return validity - 有效期
     */
    public Date getValidity() {
        return validity;
    }

    /**
     * 设置有效期
     *
     * @param validity 有效期
     */
    public void setValidity(Date validity) {
        this.validity = validity;
    }

    /**
     * 获取1：有效；2：冻结
     *
     * @return status - 1：有效；2：冻结
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置1：有效；2：冻结
     *
     * @param status 1：有效；2：冻结
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取分享次数
     *
     * @return share_times - 分享次数
     */
    public Integer getShareTimes() {
        return shareTimes;
    }

    /**
     * 设置分享次数
     *
     * @param shareTimes 分享次数
     */
    public void setShareTimes(Integer shareTimes) {
        this.shareTimes = shareTimes;
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取用户微信ID
     *
     * @return open_id - 用户微信ID
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置用户微信ID
     *
     * @param openId 用户微信ID
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * 获取二维码图片地址
     *
     * @return image_url - 二维码图片地址
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置二维码图片地址
     *
     * @param imageUrl 二维码图片地址
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * 获取创建人ID
     *
     * @return create_by - 创建人ID
     */
    public Integer getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人ID
     *
     * @param createBy 创建人ID
     */
    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取更新人ID
     *
     * @return update_by - 更新人ID
     */
    public Integer getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置更新人ID
     *
     * @param updateBy 更新人ID
     */
    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取更新时间
     *
     * @return update_date - 更新时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置更新时间
     *
     * @param updateDate 更新时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}