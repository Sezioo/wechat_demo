package com.sezioo.wechat_demo.tm.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.sezioo.wechat_demo.exceptions.NotifyBizException;
import com.sezioo.wechat_demo.tm.entity.RpNotifyRecord;
import com.sezioo.wechat_demo.tm.entity.RpNotifyRecordLog;

/**
 * 消息通知服务
 * @author qinpeng
 *
 */
public interface RpNotifyService {
	
	/**
	 * 创建消息通知
	 * @param notifyRecord
	 * @return int 创建成功条数
	 * @throws NotifyBizException
	 */
	public int createNotifyRecord(RpNotifyRecord notifyRecord) throws NotifyBizException;
	
	/**
	 * 更新消息通知
	 * @param notifyRecord
	 * @throws NotifyBizException
	 */
	public void updateNotifyRecord(RpNotifyRecord notifyRecord) throws NotifyBizException;
	
	/**
	 * 创建消息通知日志
	 * @param rpNotifyRecordLog
	 * @return
	 * @throws NotifyBizException
	 */
	public int createNotifyRecordLog(RpNotifyRecordLog rpNotifyRecordLog) throws NotifyBizException;
	
	/**
	 * 发送消息通知
	 * @param notifyUrl 通知地址
	 * @param merchantOrderNo 商户订单编号
	 * @param merchantNo 商户编号
	 * @throws NotifyBizException
	 */
	public void notifySend(String notifyUrl,String merchantOrderNo,String merchantNo) throws NotifyBizException;
	
	/**
	 * 获取消息通知
	 * @param id
	 * @return
	 * @throws NotifyBizException
	 */
	public RpNotifyRecord getNotifyRecordById(String id) throws NotifyBizException;
	
	/**
	 * 获取消息通知
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @param notifyType
	 * @return
	 * @throws NotifyBizException
	 */
	public RpNotifyRecord getNotifyRecordByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo,
			String merchantOrderNo,String notifyType) throws NotifyBizException;
	
	/**
	 * 获取消息通知分页列表
	 * @param pageNum
	 * @param pageSize
	 * @param param
	 * @return
	 */
	public PageInfo<RpNotifyRecord> listNotifyRecordByPage(int pageNum,int pageSize,Map<String, Object> param);

}
