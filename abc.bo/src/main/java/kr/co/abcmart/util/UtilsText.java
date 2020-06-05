package kr.co.abcmart.util;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * 문자열을 조작 할 때 쓰이는 유틸 클래스
 *
 * @author zerocooldog@zen9.co.kr
 */
public class UtilsText extends kr.co.abcmart.common.util.UtilsText {
	/**
	 *
	 * @Desc : xml 데이터를 tp명인 태그를 list<map>으로 리턴한다.
	 * @Method Name : parseXmlToMap
	 * @Date : 2019. 2. 18.
	 * @Author : choi
	 * @param xmlString
	 * @param tp
	 * @return
	 */
	public static List<Map<String, Object>> parseXmlToMap(List<Map<String, Object>> resultList, String xmlString,
			String tp) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new ByteArrayInputStream(xmlString.getBytes()));

			doc.getDocumentElement().normalize();

			NodeList resultNode = doc.getElementsByTagName(tp);

			for (int i = 0; i < resultNode.getLength(); i++) {
				NodeList nList = resultNode.item(i).getChildNodes();
				HashMap<String, Object> hm = new HashMap<String, Object>();

				for (int j = 0; j < nList.getLength(); j++) {
					String nodeName = nList.item(j).getNodeName();
					String nodeValue = nList.item(j).getTextContent();

					hm.put(nodeName, nodeValue);
				}
				resultList.add(hm);
			}

//			for(Map data :resultList) {
//				System.out.println("data >>>" + data);
//				System.out.println("dateName data >>>" + data.get("dateName"));
//				System.out.println("locdate data >>>" + data.get("locdate"));
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

}
