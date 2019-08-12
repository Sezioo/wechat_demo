package com.sezioo.wechat_demo.tm.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.sezioo.wechat_demo.exceptions.MessageBizException;
import com.sezioo.wechat_demo.exceptions.NotifyBizException;
import com.sezioo.wechat_demo.tm.dao.RpNotifyRecordLogMapper;
import com.sezioo.wechat_demo.tm.dao.RpNotifyRecordMapper;
import com.sezioo.wechat_demo.tm.entity.RpNotifyRecord;
import com.sezioo.wechat_demo.tm.entity.RpNotifyRecordLog;
import com.sezioo.wechat_demo.tm.entity.RpTransactionMessage;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
/**
 * 消息通知服务实现
 * @author qinpeng
 *
 */
public class RpNotifyServiceImpl implements RpNotifyService {
	
	@Autowired
	private RpNotifyRecordMapper rpNotifyRecordMapper;
	
	@Autowired
	private RpNotifyRecordLogMapper rpNotifyRecordLogMapper;

	@Override
	public int createNotifyRecord(RpNotifyRecord notifyRecord) throws NotifyBizException {
		if(notifyRecord == null) {
			log.info("插入的通知记录为空");
			throw new NotifyBizException("插入的通知记录为空");
		}
		int insertSelective = rpNotifyRecordMapper.insertSelective(notifyRecord);
		log.info("插入通知记录成功:{}",notifyRecord);
		return insertSelective;
	}

	@Override
	public void updateNotifyRecord(RpNotifyRecord notifyRecord) throws NotifyBizException {
		rpNotifyRecordMapper.updateByPrimaryKeySelective(notifyRecord);
	}

	@Override
	public int createNotifyRecordLog(RpNotifyRecordLog rpNotifyRecordLog) throws NotifyBizException {
		if(rpNotifyRecordLog == null) {
			log.info("插入的通知日志为空");
			throw new NotifyBizException("插入的通知日志为空");
		}
		return rpNotifyRecordLogMapper.insertSelective(rpNotifyRecordLog);
	}

	@Override
	public void notifySend(String notifyUrl, String merchantOrderNo, String merchantNo) throws NotifyBizException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RpNotifyRecord getNotifyRecordById(String id) throws NotifyBizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RpNotifyRecord getNotifyRecordByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo,
			String merchantOrderNo, String notifyType) throws NotifyBizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageInfo<RpNotifyRecord> listNotifyRecordByPage(int pageNum, int pageSize, Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
