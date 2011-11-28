package com.bhtec.service.impl.platform.primarykey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bhtec.common.constant.PojoVariable;
import com.bhtec.dao.iface.platform.primarykey.PrimaryKeySeqDao;
import com.bhtec.domain.pojo.platform.SysplPrimarykeySeq;
import com.bhtec.service.iface.platform.primarykey.PrimaryKeySeqService;
import com.bhtec.service.impl.BaseServiceImpl;

/**
 *功能描述：
 *@since   Mar 14, 2010 11:57:44 AM
 *@author  jacobliang
 *@version 1.0
 */
public class PrimaryKeySeqServiceImpl extends BaseServiceImpl implements
		PrimaryKeySeqService {
	private PrimaryKeySeqDao primaryKeySeqDao;
	
	public void deletePrimaryKeySeq(String pojoName) {
		
	}

	public List findAllPrimaryKeySeq() {
		// TODO Auto-generated method stub
		return null;
	}

	public void savePrimaryKeySeq() {
		// TODO Auto-generated method stub
		
	}

	public void updatePrimaryKeySeq() {
		// TODO Auto-generated method stub
		
	}

	public Map getSeqMap() {
		List<SysplPrimarykeySeq> seqList = primaryKeySeqDao.getAll(PojoVariable.SYSPL_PRIMARYKEY_SEQ,"");
		Map<String,Long> map = new HashMap<String,Long>();
		for(int i=0;i<seqList.size();i++){
			SysplPrimarykeySeq sysplPrimarykeySeq = seqList.get(i);
			String pojoName = sysplPrimarykeySeq.getPojoName();
			String primaryKeyName = sysplPrimarykeySeq.getPrimarykeyName();
			Long maxId = primaryKeySeqDao.getMaxIdOfTable(pojoName,primaryKeyName);
			map.put(pojoName, maxId);
		}
		return map;
	}

	public void setPrimaryKeySeqDao(PrimaryKeySeqDao primaryKeySeqDao) {
		this.primaryKeySeqDao = primaryKeySeqDao;
	}

}
