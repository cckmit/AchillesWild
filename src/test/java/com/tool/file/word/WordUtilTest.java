package com.tool.file.word;

import org.junit.Test;
import com.achilles.wild.server.tool.file.word.WordUtil;

public class WordUtilTest {

	@Test
	public void wordReadTest() {
		String path = "C://Users//Administrator//Desktop//100元红包.docx"; // 
//		String path = "C://Users//Achil//Desktop//1.docx"; // 
//		System.out.println(WordUtil.readDoc(path));
		System.out.println(WordUtil.read(path));

	}
}
