package com.achilles.wild.server.tool.file.word;


import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作word文档
 * @author Achil
 *
 */
public class WordUtil {
	
	
	public static String read(String path) {
		if (path.lastIndexOf(".docx")!=-1) {
			return readDocx(path);
		}
		return readDoc(path);
	}

	public static List<String> readWord(String filePath){

		List<String> linList = new ArrayList<String>();
		String buffer = "";
		try {
			if (filePath.endsWith(".doc")) {
				InputStream is = new FileInputStream(new File(filePath));
				WordExtractor ex = new WordExtractor(is);
				buffer = ex.getText();
				ex.close();

				if(buffer.length() > 0){
					//使用回车换行符分割字符串
					String [] arry = buffer.split("\\r\\n");
					for (String string : arry) {
						linList.add(string.trim());
					}
				}
			} else if (filePath.endsWith(".docx")) {
				OPCPackage opcPackage = POIXMLDocument.openPackage(filePath);
				POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
				buffer = extractor.getText();
				extractor.close();

				if(buffer.length() > 0){
					//使用换行符分割字符串
					String [] arry = buffer.split("\\n");
					for (String string : arry) {
						linList.add(string.trim());
					}
				}
			} else {
				return null;
			}

			return linList;
		} catch (Exception e) {
			System.out.print("error---->"+filePath);
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 读docx  2007
	 * @param path
	 * @return
	 */
	public static String readDocx(String path) {
		OPCPackage opcPackage = null;
		try {
			opcPackage = POIXMLDocument.openPackage(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		POIXMLTextExtractor extractor = null;
		try {
			extractor = new XWPFWordExtractor(opcPackage);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (OpenXML4JException e) {
			e.printStackTrace();
		}
		return extractor.getText();
	}
	
	/**
	 * 读doc  2003
	 * @param path
	 * @return
	 */	
	public static String readDoc(String path) {
		InputStream is = null;
		try {
			is = new FileInputStream(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		WordExtractor ex = null;
		try {
			ex = new WordExtractor(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ex.getText();
	}
	

}
