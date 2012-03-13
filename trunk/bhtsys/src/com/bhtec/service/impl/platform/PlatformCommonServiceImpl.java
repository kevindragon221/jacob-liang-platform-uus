/**
 *功能说明：
 * @author jacobliang
 * @time @Nov 17, 2010 @9:49:27 AM
 */
package com.bhtec.service.impl.platform;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bhtec.domain.pojo.platform.SysplDicSmallType;
import com.bhtec.domain.pojo.platform.SysplDistrict;
import com.bhtec.domain.pojo.platform.SysplModOptRef;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.domain.pojo.platform.SysplSysParameter;
import com.bhtec.service.iface.platform.PlatformCommonService;
import com.bhtec.service.iface.platform.TypeDictionary.TypeDictionaryService;
import com.bhtec.service.iface.platform.code.CodeService;
import com.bhtec.service.iface.platform.district.DistrictService;
import com.bhtec.service.iface.platform.module.ModuleService;
import com.bhtec.service.iface.platform.systemparameter.SystemParameterService;
import com.bhtec.service.impl.BaseServiceImpl;

public class PlatformCommonServiceImpl extends BaseServiceImpl implements
		PlatformCommonService {
	private ModuleService moduleService;
	private SystemParameterService systemParameterService;
	private TypeDictionaryService typeDictionaryService;
	private DistrictService districtService;
	private CodeService codeService;
	/**
	 * 功能说明：过滤模块一二三级
	 * 源自：ModuleService 应用：LoginServiceImpl
	 * @author jacobliang
	 * @param	list 	需要过滤的模块列表
	 * @return Map 1:list 2:map 3:map
	 * @time Aug 20, 2010 8:37:50 PM
	 */
	public Map findFilterModuleMenu(List<SysplModuleMemu> list) {
		return moduleService.findFilterModuleMenu(list);
	}
	/**
	 * 功能说明：查询所有模块信息
	 * 源自：ModuleService 应用：LoginServiceImpl
	 * @author jacobliang
	 * @return List<SysplModuleMemu>	所有模块信息
	 * @time Jul 26, 2010 9:28:04 PM
	 */
	public List<SysplModuleMemu> findAllModule() {
		return moduleService.findAllModule();
	}
	/**
	 * 功能说明：查询所有模块操作关系记录
	 * @author jacobliang
	 * @return	List<SysplModOptRef>	所有模块操作关系记录
	 * @time Nov 1, 2010 10:01:57 AM
	 */
	public List<SysplModOptRef> findAllSysplModOptRef() {
		return moduleService.findAllSysplModOptRef();
	}
	
	/**
	 * 功能说明：根据参数名称查找系统参数
	 * @author jacobliang
	 * @return
	 * @time Dec 10, 2010 8:39:15 PM
	 */
	public String findSystemParaByParaName(String paraKeyName){
		SysplSysParameter sysplSysParameter = systemParameterService.findSystemParaByParaName(paraKeyName);
		return sysplSysParameter==null?"":sysplSysParameter.getParaValue();
	}
	
	/**
	 * 功能说明：根据大类代码查询所有小类
	 * @author jacobliang
	 * @param bigTypeCode	大类代码
	 * @return
	 * @time Dec 21, 2010 8:39:15 PM
	 */
	public List<SysplDicSmallType> findSmallTypeDicByBigTypeCode(String bigTypeCode){
		return typeDictionaryService.findSmallTypeDicByBigTypeCode(bigTypeCode);
	}
	
	/**
	 * 功能说明：根据地区ID查询某个地区
	 * @author jacobliang
	 * @param 	districtId		地区ID
	 * @return	sysplDistrict	地区对象
	 * @throws 
	 * @time Dec 29, 2010 9:32:47 AM
	 */
	public SysplDistrict findSysplDistrictByDistrictId(Long districtId){
		return districtService.findSysplDistrictByDistrictId(districtId);
	}
	
	/**
	 * 功能说明：根据编码英文名称查询编码
	 * @author jacobliang
	 * @param	codeEngName	编码英文名称
	 * @return
	 * @time Jan 12, 2011 10:56:51 AM
	 */
	public String obtainSysplCodeByCodeEngName(String codeEngName,HttpServletRequest request){
		return codeService.obtainSysplCodeByCodeEngName(codeEngName, request);
	}
	
	/**
	 * 功能说明：根据模块关系表ID查询所有模块操作关系
	 * @author jacobliang
	 * @param modOptRefId
	 * @return
	 * @time Feb 7, 2012
	 */
	public List<SysplModOptRef> findSysplModOptRefByModOptRefIds(String modOptRefId){
		return moduleService.findSysplModOptRefByModOptRefIds(modOptRefId);
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}
	
	public void setSystemParameterService(
			SystemParameterService systemParameterService) {
		this.systemParameterService = systemParameterService;
	}
	public void setTypeDictionaryService(TypeDictionaryService typeDictionaryService) {
		this.typeDictionaryService = typeDictionaryService;
	}
	public void setDistrictService(DistrictService districtService) {
		this.districtService = districtService;
	}
	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

}
