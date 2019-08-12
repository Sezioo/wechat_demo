package com.sezioo.wechat_demo.tm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "rp_transaction_message")
public class RpTransactionMessage implements Serializable {
    /**
     * 主键ID
     */
    @Id
    private String id;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 修改者
     */
    private String editor;

    /**
     * 创建者
     */
    private String creater;

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
     * 消息ID
     */
    @Column(name = "message_id")
    private String messageId;

    /**
     * 消息数据类型
     */
    @Column(name = "message_data_type")
    private String messageDataType;

    /**
     * 消费队列
     */
    @Column(name = "consumer_queue")
    private String consumerQueue;

    /**
     * 消息重发次数
     */
    @Column(name = "message_send_times")
    private int messageSendTimes;

    /**
     * 是否死亡
     */
    @Column(name = "areadly_dead")
    private String areadlyDead;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 扩展字段1
     */
    private String field1;

    /**
     * 扩展字段2
     */
    private String field2;

    /**
     * 扩展字段3
     */
    private String field3;

    /**
     * 消息内容
     */
    @Column(name = "message_body")
    private String messageBody;

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
     * 获取修改者
     *
     * @return editor - 修改者
     */
    public String getEditor() {
        return editor;
    }

    /**
     * 设置修改者
     *
     * @param editor 修改者
     */
    public void setEditor(String editor) {
        this.editor = editor;
    }

    /**
     * 获取创建者
     *
     * @return creater - 创建者
     */
    public String getCreater() {
        return creater;
    }

    /**
     * 设置创建者
     *
     * @param creater 创建者
     */
    public void setCreater(String creater) {
        this.creater = creater;
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
     * 获取消息ID
     *
     * @return message_id - 消息ID
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * 设置消息ID
     *
     * @param messageId 消息ID
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * 获取消息数据类型
     *
     * @return message_data_type - 消息数据类型
     */
    public String getMessageDataType() {
        return messageDataType;
    }

    /**
     * 设置消息数据类型
     *
     * @param messageDataType 消息数据类型
     */
    public void setMessageDataType(String messageDataType) {
        this.messageDataType = messageDataType;
    }

    /**
     * 获取消费队列
     *
     * @return consumer_queue - 消费队列
     */
    public String getConsumerQueue() {
        return consumerQueue;
    }

    /**
     * 设置消费队列
     *
     * @param consumerQueue 消费队列
     */
    public void setConsumerQueue(String consumerQueue) {
        this.consumerQueue = consumerQueue;
    }

    /**
     * 获取消息重发次数
     *
     * @return message_send_times - 消息重发次数
     */
    public int getMessageSendTimes() {
        return messageSendTimes;
    }

    /**
     * 设置消息重发次数
     *
     * @param i 消息重发次数
     */
    public void setMessageSendTimes(int i) {
        this.messageSendTimes = i;
    }

    /**
     * 获取是否死亡
     *
     * @return areadly_dead - 是否死亡
     */
    public String getAreadlyDead() {
        return areadlyDead;
    }

    /**
     * 设置是否死亡
     *
     * @param areadlyDead 是否死亡
     */
    public void setAreadlyDead(String areadlyDead) {
        this.areadlyDead = areadlyDead;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取扩展字段1
     *
     * @return field1 - 扩展字段1
     */
    public String getField1() {
        return field1;
    }

    /**
     * 设置扩展字段1
     *
     * @param field1 扩展字段1
     */
    public void setField1(String field1) {
        this.field1 = field1;
    }

    /**
     * 获取扩展字段2
     *
     * @return field2 - 扩展字段2
     */
    public String getField2() {
        return field2;
    }

    /**
     * 设置扩展字段2
     *
     * @param field2 扩展字段2
     */
    public void setField2(String field2) {
        this.field2 = field2;
    }

    /**
     * 获取扩展字段3
     *
     * @return field3 - 扩展字段3
     */
    public String getField3() {
        return field3;
    }

    /**
     * 设置扩展字段3
     *
     * @param field3 扩展字段3
     */
    public void setField3(String field3) {
        this.field3 = field3;
    }

    /**
     * 获取消息内容
     *
     * @return message_body - 消息内容
     */
    public String getMessageBody() {
        return messageBody;
    }

    /**
     * 设置消息内容
     *
     * @param messageBody 消息内容
     */
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}