/**
 *功能说明：系统编码管理
 * @author jacobliang
 * @time @Jul 26, 2010 @5:30:57 PM
 */
package com.bhtec.service.impl.platform.code;

import static com.bhtec.common.constant.Common.DISABLE;
import static com.bhtec.common.constant.Common.ENABLE;
import static com.bhtec.common.constant.Common.LOG_LEVEL_FIRST;
import static com.bhtec.common.constant.Common.LOG_LEVEL_SECOND;
import static com.bhtec.common.constant.Common.MODIFY_OPT;
import static com.bhtec.common.constant.Common.ORGAN_NAME;
import static com.bhtec.common.constant.Common.ROLE_NAME;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.Common.SPLIT;
import static com.bhtec.common.constant.Common.USER_CODE;
import static com.bhtec.common.constant.ServiceVariable.DISABLE_DIS;
import static com.bhtec.common.constant.ServiceVariable.ENABLE_DIS;
import static com.bhtec.common.constant.ServiceVariable.SYS_CODE;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.bhtec.dao.iface.platform.code.CodeDao;
import com.bhtec.domain.pojo.platform.SysplCode;
import com.bhtec.domain.pojo.platform.SysplModuleCode;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.platform.code.CodeService;
import com.bhtec.service.impl.BaseServiceImpl;

public class CodeServiceImpl extends BaseServiceImpl implements CodeService {
	Logger log = Logger.getLogger(this.getClass());
	private CodeDao codeDao;
	
	/**
	 * 功能说明：保存编码
	 * @author jacobliang
	 * @param sysplCode	编码编码
	 * @throws ApplicationException
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public void saveSysplCode(SysplCode sysplCode) throws ApplicationException{
		codeDao.save(sysplCode);
		StringBuffer optContent = new StringBuffer();
		optContent.append("编码英文名称："+sysplCode.getCodeEngName()+SPLIT)
					.append("编码中文名称:"+sysplCode.getCodeName()+SPLIT)
					.append("编码所属模块名称:"+sysplCode.getCodeName()+SPLIT)
					.append("编码效果:"+sysplCode.getCodeEffect()+SPLIT);
		this.saveLog(LOG_LEVEL_FIRST, SYS_CODE, SAVE_OPT, optContent.toString(),
				sysplCode.getCodeId().toString());
	}
	
	/**
	 * 功能说明：根据编码ID查询某个编码
	 * @author jacobliang
	 * @param  codeId		编码ID
	 * @return sysplCode	编码对象
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public SysplCode findSysplCodeByCodeId(Long codeId){
		SysplCode sysplCode = (SysplCode)codeDao.getPojoById("com.bhtec.domain.pojo.platform.SysplCode", codeId);
		List<String> partList = new ArrayList<String>();
		partList.add(sysplCode.getPart1());
		partList.add(sysplCode.getPart2());
		partList.add(sysplCode.getPart3());
		partList.add(sysplCode.getPart4());
		List<String> partConList = new ArrayList<String>();
		partConList.add(sysplCode.getPart1Con());
		partConList.add(sysplCode.getPart2Con());
		partConList.add(sysplCode.getPart3Con());
		partConList.add(sysplCode.getPart4Con());
		sysplCode.setPartList(partList);
		sysplCode.setPartConList(partConList);
		return sysplCode;
	}
	/**
	 * 功能说明：修改编码
	 * @author jacobliang
	 * @param  sysplCode	编码对象
	 * @return
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public void modifySysplCode(SysplCode sysplCode)throws ApplicationException{
		this.codeDao.update(sysplCode);
		StringBuffer optContent = new StringBuffer();
		optContent.append("编码英文名称："+sysplCode.getCodeEngName()+SPLIT)
		.append("编码中文名称:"+sysplCode.getCodeName())
		.append("编码所属模块名称:"+sysplCode.getCodeName())
		.append("编码效果:"+sysplCode.getCodeEffect());
		this.saveLog(LOG_LEVEL_SECOND, SYS_CODE, MODIFY_OPT, optContent.toString(),
				sysplCode.getCodeId().toString());
	}
	/**
	 * 功能说明：查询编码
	 * @author jacobliang
	 * @param start			开始记录
	 * @param limit			每页记录数
	 * @param codeEngName	英文编码名称
	 * @param codeName		中文编码名称
	 * @param moduleName	模块名称
	 * @return
	 * @time Jan 11, 2011 4:48:48 PM
	 */
	public Map findCodeByCon(int start,int limit,String codeEngName,String codeName,String moduleName){
		return this.codeDao.findCodeByCon(start, limit, codeEngName, codeName, moduleName);
	}
	/**
	 * 功能说明：停用启用编码
	 * @author jacobliang
	 * @param   disEnableFlag	停用启用标志
	 * @param 	codeId		    编码ID
	 * @return  boolean			true为停用启用成功，false不能停用
	 * @throws 
	 * @time Sep 22, 2010 1:29:34 PM
	 */
	public boolean disEnableCode(long codeId,String disEnableFlag)throws ApplicationException{
		if(DISABLE.equals(disEnableFlag)){
			SysplCode sysplCode = this.findSysplCodeByCodeId(codeId);
			sysplCode.setStatus(DISABLE);
			this.modifySysplCode(sysplCode);
			StringBuffer optContent = new StringBuffer();
			optContent.append("编码停用：编码英文名称："+sysplCode.getCodeEngName()+SPLIT)
						.append("编码中文名称:"+sysplCode.getCodeName())
						.append("编码所属模块名称:"+sysplCode.getCodeName())
						.append("编码效果:"+sysplCode.getCodeEffect());
			this.saveLog(LOG_LEVEL_SECOND, SYS_CODE, DISABLE_DIS, optContent.toString(),
					sysplCode.getCodeId().toString());
			return true;
		}else{
			SysplCode sysplCode = this.findSysplCodeByCodeId(codeId);
			sysplCode.setStatus(ENABLE);
			this.modifySysplCode(sysplCode);
			StringBuffer optContent = new StringBuffer();
			optContent.append("编码启用：编码英文名称："+sysplCode.getCodeEngName()+SPLIT)
						.append("编码中文名称:"+sysplCode.getCodeName())
						.append("编码所属模块名称:"+sysplCode.getCodeName())
						.append("编码效果:"+sysplCode.getCodeEffect());
			this.saveLog(LOG_LEVEL_SECOND, SYS_CODE, ENABLE_DIS, optContent.toString(),
					sysplCode.getCodeId().toString());
			return true;
		}
	}
	
	/**
	 * 功能说明：根据编码英文名称查询编码是否存在
	 * @author jacobliang
	 * @param	codeEngName	编码英文名称
	 * @return	boolean true 名称已经存在 false名称不存在
	 * @time Jan 12, 2011 10:56:51 AM
	 */
	public boolean findSysplCodeByCodeEngName(String codeEngName){
		int count = codeDao.findSysplCodeByCodeEngName(codeEngName);
		if(count == 0){
			return false;
		}
		return true;
	}
	/**
	 * 功能说明：根据编码英文名称查询编码
	 * @author jacobliang
	 * @param	codeEngName	编码英文名称
	 * @return
	 * @time Jan 12, 2011 10:56:51 AM
	 */
	public synchronized String obtainSysplCodeByCodeEngName(String codeEngName,HttpServletRequest request){
		SysplModuleCode sysplModuleCode = (SysplModuleCode)codeDao.getPojoById("com.bhtec.domain.pojo.platform.SysplModuleCode", codeEngName);
		String currentCode = sysplModuleCode != null?sysplModuleCode.getCodeContent():"";//obtain newwest code from DB.
		SysplCode sysplCode = codeDao.obtainSysplCodeByCodeEngName(codeEngName);
		String part1 = sysplCode.getPart1();
		String part1Con = sysplCode.getPart1Con();
		String part2 = sysplCode.getPart2();
		String part2Con = sysplCode.getPart2Con();
		String part3 = sysplCode.getPart3();
		String part3Con = sysplCode.getPart3Con();
		String part4 = sysplCode.getPart4();
		String part4Con = sysplCode.getPart4Con();
		String delimite = sysplCode.getDelimite();
		
		part1Con = this.getPartContent(part1, part1Con, currentCode, request, 1, delimite);
		part2Con = this.getPartContent(part2, part2Con, currentCode, request, 2, delimite);
		part3Con = this.getPartContent(part3, part3Con, currentCode, request, 3, delimite);
		part4Con = this.getPartContent(part4, part4Con, currentCode, request, 4, delimite);
		int partNum = sysplCode.getPartNum();
		String newCode = "";
		switch(partNum){
			case 1:
				newCode = part1Con;
				break;
			case 2:
				newCode = part1Con+delimite+part2Con;
				break;
			case 3:
				newCode = part1Con+delimite+part2Con+delimite+part3Con;
				break;
			case 4:
				newCode = part1Con+delimite+part2Con+delimite+part3Con+delimite+part4Con;
				break;
		}
		log.info("newCode is : " + newCode);
		if("".equals(currentCode)){
			SysplModuleCode newSysplModuleCode = new SysplModuleCode();
			newSysplModuleCode.setCodeContent(newCode);
			newSysplModuleCode.setCodeEngName(codeEngName);
			codeDao.save(newSysplModuleCode);
		}else{
			sysplModuleCode.setCodeContent(newCode);
			codeDao.update(sysplModuleCode);
		}
		return newCode;
	}
	/**
	 * 功能说明：私有方法获得格式化后的编码
	 * @author jacobliang
	 * @param part		code format part
	 * @param partCon	code content
	 * @param currentCode	newwest code
	 * @param request		
	 * @param partFlag		
	 * @param delimite
	 * @return
	 * @time Jan 13, 2011 10:17:52 AM
	 */
	private String getPartContent(String part,String partCon,
								  String currentCode,HttpServletRequest request,
								  int partFlag,String delimite){
		if(isNullOrEmpty(part) || isNullOrEmpty(partCon))return "";
		if("date".equalsIgnoreCase(part)){
			SimpleDateFormat sf = new SimpleDateFormat(partCon);
			partCon = sf.format(new Date());
		}else if("char".equalsIgnoreCase(part)){
			
		}else if("number".equalsIgnoreCase(part)){
			if(!isNullOrEmpty(currentCode)){
				String[] partArr = currentCode.split(delimite);
				String numStr = partArr[partFlag-1];
				int numLength = numStr.length();
				numStr = (Integer.parseInt(numStr)+1)+"";
				int newNumLength = numStr.length();
				while(numLength > newNumLength){
					numStr = "0"+numStr;
					newNumLength++;
				}
				partCon = numStr;
			}
		}else if("sysPara".equalsIgnoreCase(part)){
			if("userName".equals(partCon)){
				partCon =  (String)request.getSession().getAttribute(USER_CODE);
			}else if("roleUser".equals(partCon)){
				partCon =  (String)request.getSession().getAttribute(ROLE_NAME)+"~"
						  +(String)request.getSession().getAttribute(USER_CODE);
			}else if("organRoleUser".equals(partCon)){
				partCon =  (String)request.getSession().getAttribute(ORGAN_NAME)+"~"
						  +(String)request.getSession().getAttribute(ROLE_NAME)+"~"
						  +(String)request.getSession().getAttribute(USER_CODE);
			}
		}
		return partCon;
	}
	
	public void setCodeDao(CodeDao codeDao) {
		this.codeDao = codeDao;
	}
}
