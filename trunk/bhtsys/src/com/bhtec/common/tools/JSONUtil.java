/**
 *功能说明：
 * @author jacobliang
 * @time @Dec 22, 2010 @8:33:39 PM
 */
package com.bhtec.common.tools;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

public class JSONUtil {
	/**
	 * 
	 * <p>Title: </p>
	 * <p>Description: 从一个JSON 对象字符格式中得到一个java对象</p>
	 * @author jacobliang
	 * @param jsonString
	 * @param pojoCalss
	 * @return
	 */
	public static Object getObject4JsonString(String jsonString, Class pojoCalss) {
		if(UtilTools.isNullOrEmpty(jsonString))return null;
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		pojo = JSONObject.toBean(jsonObject, pojoCalss);
		return pojo;
	}

	/**
	 * 
	 * <p>Title: getJsonStr2List</p>
	 * <p>Description: </p>
	 * @author jacobliang
	 * @param jsonString
	 * @param pojoCalss
	 * @return
	 */
	public static List getJsonStr2List(String jsonString, Class pojoCalss) {
		List result = new ArrayList();
		Object[] dtoArray = JSONUtil.getObjectArray4Json(jsonString);
		//warehouseService.delWarehouseMaterial(warehosueId[0]);// 添加前先删除原有数据
		if (dtoArray != null && dtoArray.length > 0) {
			for (int i = 0; i < dtoArray.length; i++) {
				try {
					Class c = Class.forName(pojoCalss.getName());
					Object o = c.newInstance();
					o = JSONUtil.getObject4JsonString(dtoArray[i].toString(),
							pojoCalss);
					result.add(o);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

			}
		}

		return result;
	}

	/**
	 * 
	 * <p>Title: getList2JsonStr</p>
	 * <p>Description: </p>
	 * @author jacobliang
	 * @param data
	 * @param pojoCalss
	 * @return
	 */
	public static String getList2JsonStr(List data, Class pojoCalss) {
		StringBuffer result = new StringBuffer();
		result.append("[");
		if (data != null && data.size() > 0) {
			for (int i = 0; i < data.size(); i++) {
				try {
					result.append("{");

					Class c = Class.forName(pojoCalss.getName());
					Object o = c.newInstance();
					o = data.get(i);
					Field[] fields = c.getDeclaredFields();

					Method[] methods = c.getDeclaredMethods();
					if (fields != null && fields.length > 0) {

						for (int j = 0; j < fields.length; j++) {
							result.append(fields[j].getName() + ":");
							for (int t = 0; t < methods.length; t++) {
								String temp = "get"
										+ fields[j].getName().toUpperCase()
												.substring(0, 1)
										+ fields[j].getName().substring(1);
								System.out.println(" ####################"
										+ temp);
								if (methods[t].getName().equals(temp)) {
									result.append("'");
									Object invResult = methods[t].invoke(o,
											null);
									if (null != invResult) {
										result.append(invResult.toString());
									}
									result.append("'");
									if (j < fields.length - 1)
										result.append(",");
								}
							}
						}

					}

					result.append("} ");

					if (i < data.size() - 1)
						result.append(",");

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

			}
		}

		result.append("] ");
		System.out.println(result.toString());
		return result.toString();
	}

	/**
	 * 
	 * <p>Title: getList2JsonStr</p>
	 * <p>Description: </p>
	 * @author jacobliang
	 * @param data
	 * @param pojoCalss
	 * @param idField
	 * @param nameField
	 * @return
	 */
	public static String getList2JsonStr(List data, Class pojoCalss,
			String idField, String nameField) {
		StringBuffer result = new StringBuffer();
		result.append("[");
		if (data != null && data.size() > 0) {
			for (int i = 0; i < data.size(); i++) {
				try {
					result.append("[");

					Class c = Class.forName(pojoCalss.getName());
					Object o = c.newInstance();
					o = data.get(i);
					Method[] methods = c.getDeclaredMethods();

					String idMethod = "get"
							+ idField.toUpperCase().substring(0, 1)
							+ idField.substring(1);

					String nameMethod = "get"
							+ nameField.toUpperCase().substring(0, 1)
							+ nameField.substring(1);

					String idValue = "";
					String nameValue = "";

					for (int t = 0; t < methods.length; t++) {
						if (methods[t].getName().equals(idMethod)) {
							Object invResult = methods[t].invoke(o, null);
							if (null != invResult) {
								idValue = invResult.toString();
							}
						}
						if (methods[t].getName().equals(nameMethod)) {
							Object invResult = methods[t].invoke(o, null);
							if (null != invResult) {
								nameValue = "'" + invResult.toString() + "'";
							}
						}
					}

					result.append(idValue);
					result.append(",");
					result.append(nameValue);
					result.append("]");

					if (i < data.size() - 1)
						result.append(",");

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

			}
		}

		result.append("] ");
		System.out.println(result.toString());
		return result.toString();
	}

	/**
	 * 
	 * <p>Title: </p>
	 * <p>Description: 从json HASH表达式中获取一个map，改map支持嵌套功能</p>
	 * @author jacobliang
	 * @param jsonString
	 * @return
	 */
	public static Map getMap4Json(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map valueMap = new HashMap();

		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}

		return valueMap;
	}

	/** 
	 * 
	 * <p>Title: </p>
	 * <p>Description: 从json数组中得到相应java数组</p>
	 * @author jacobliang
	 * @param jsonString
	 * @return
	 */
	public static Object[] getObjectArray4Json(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray.toArray();

	}

	/** 
	 * 
	 * <p>Title: 表</p>
	 * <p>Description: 从json对象集合表达式中得到一个java对象列</p>
	 * @author jacobliang
	 * @param jsonString
	 * @param pojoClass
	 * @return
	 */
	public static List getList4Json(String jsonString, Class pojoClass) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object pojoValue;

		List list = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {

			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = JSONObject.toBean(jsonObject, pojoClass);
			list.add(pojoValue);

		}
		return list;

	}

	/** 
	 * 
	 * <p>Title: </p>
	 * <p>Description:从json数组中解析出java字符串数组 </p>
	 * @author jacobliang
	 * @param jsonString
	 * @return
	 */
	public static String[] getStringArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		String[] stringArray = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			stringArray[i] = jsonArray.getString(i);

		}

		return stringArray;
	}

	/** 
	 * 
	 * <p>Title: </p>
	 * <p>Description:从json数组中解析出javaLong型对象数组 </p>
	 * @author jacobliang
	 * @param jsonString
	 * @return
	 */
	public static Long[] getLongArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Long[] longArray = new Long[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			longArray[i] = jsonArray.getLong(i);

		}
		return longArray;
	}

	/** 
	 * 
	 * <p>Title:  </p>
	 * <p>Description:从json数组中解析出java Integer型对象数组 </p>
	 * @author jacobliang
	 * @param jsonString
	 * @return
	 */
	public static Integer[] getIntegerArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Integer[] integerArray = new Integer[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			integerArray[i] = jsonArray.getInt(i);

		}
		return integerArray;
	}

	/** 
	 * 
	 * <p>Title: getDateArray4Json</p>
	 * <p>Description:从json数组中解析出java Date 型对象数组，使用本方法必须保证 </p>
	 * @author jacobliang
	 * @param jsonString
	 * @param DataFormat
	 * @return
	 */
	public static Date[] getDateArray4Json(String jsonString, String DataFormat) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Date[] dateArray = new Date[jsonArray.size()];
		String dateString;
		Date date = null;

		for (int i = 0; i < jsonArray.size(); i++) {
			dateString = jsonArray.getString(i);
			//  date = DateUtil.stringToDate(dateString, DataFormat);
			dateArray[i] = date;

		}
		return dateArray;
	}

	/** 
	 * 
	 * <p>Title: getDoubleArray4Json</p>
	 * <p>Description:从json数组中解析出java Integer型对象数组 </p>
	 * @author jacobliang
	 * @param jsonString
	 * @return
	 */
	public static Double[] getDoubleArray4Json(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Double[] doubleArray = new Double[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			doubleArray[i] = jsonArray.getDouble(i);

		}
		return doubleArray;
	}

	/** 
	 * 
	 * <p>Title: getJsonString4JavaPOJO</p>
	 * <p>Description:将java对象转换成json字符串 </p>
	 * @author jacobliang
	 * @param javaObj
	 * @return
	 */
	public static String getJsonString4JavaPOJO(Object javaObj) {

		JSONObject json;
		json = JSONObject.fromObject(javaObj);
		return json.toString();

	}

	/** 
	 * 
	 * <p>Title: getJsonString4JavaPOJO</p>
	 * <p>Description: 将java对象转换成json字符串,并设定日期格式</p>
	 * @author jacobliang
	 * @param javaObj
	 * @param dataFormat
	 * @return
	 */
	public static String getJsonString4JavaPOJO(Object javaObj,
			String dataFormat) {

		JSONObject json;
		JsonConfig jsonConfig = configJson(dataFormat);
		json = JSONObject.fromObject(javaObj, jsonConfig);
		return json.toString();
	}

	/** 
	 * 
	 * <p>Title: configJson</p>
	 * <p>Description: JSON 时间解析器具</p>
	 * @author jacobliang
	 * @param datePattern
	 * @return
	 */
	public static JsonConfig configJson(String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "" });
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		// jsonConfig.registerJsonValueProcessor(Date.class,   
		// new DateJsonValueProcessor(datePattern));   

		return jsonConfig;
	}

	/**
	 * 
	 * <p>Title: configJson</p>
	 * <p>Description: </p>
	 * @author jacobliang
	 * @param excludes
	 * @param datePattern
	 * @return
	 */
	public static JsonConfig configJson(String[] excludes, String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		//   jsonConfig.registerJsonValueProcessor(Date.class,   
		//   new DateJsonValueProcessor(datePattern));   

		return jsonConfig;
	}

	/**
	 * <p>Title: format4tableCols</p>
	 * <p>Description: 根据表列将字符串数组格式化为json对象</p> 
	 * @param str
	 * @param tableCols
	 * @return
	 */
	public static String formatBytableCols(Object[] obj, String[] tableCols) {
		StringBuffer result = new StringBuffer();
		result.append("{");
		if (obj != null && obj.length > 0) {
			for (int i = 0; i < obj.length; i++) {
				try {
					result.append(tableCols[i]);
					result.append(":'");
					if (obj[i] instanceof String) {
						String s = null;
						if (obj[i].toString() != null) {
							s = obj[i].toString().replace("\"", "\\\"")
									.replace("'", "\\'").replace("\n", "<br/>")
									.replace("\b", "\\b").replace("\f", "\\f")
									.replace("\r", "").replace("\t", "");
						}
						result.append(s);
					} else if (obj[i] instanceof Integer) {
						result.append((Integer) obj[i]);
					} else if (obj[i] instanceof Long) {
						result.append((Long) obj[i]);
					} else if (obj[i] instanceof Double) {
						result.append((Double) obj[i]);
					} else if (obj[i] instanceof java.sql.Date) {
						result.append((java.sql.Date) obj[i]);
					} else if (obj[i] instanceof Float) {
						result.append((Float) obj[i]);
					}
					result.append("'");
					if (i < obj.length - 1) {
						result.append(",");
					}

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}

			}
		}
		result.append("}");
		System.out.println(result.toString());
		return result.toString();
	}

	/**
	 * <p>Title: format4tableCols</p>
	 * <p>Description: 根据表列将集合格式化为json对象</p> 
	 * @param str
	 * @param tableCols
	 * @return
	 */
	public static String formatBytableCols(List<Object[]> list,
			String[] tableCols) {
		StringBuffer result = new StringBuffer();
		result.append("[");
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				result.append(formatBytableCols(obj, tableCols));
				if (i < list.size() - 1)
					result.append(",");
			}

		}
		result.append("]");
		return result.toString();
	}
}
