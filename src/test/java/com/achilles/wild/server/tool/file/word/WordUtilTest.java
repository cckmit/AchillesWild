package com.achilles.wild.server.tool.file.word;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class WordUtilTest {

	@Test
	public void wordReadTest() {
		String path = "C:\\Users\\Achilles\\Desktop\\resume.doc";
//		String path = "C://Users//Achil//Desktop//1.docx"; // 
//		System.out.println(WordUtil.readDoc(path));
		System.out.println(WordUtil.read(path));

	}

	@Test
	public void wordReadTest2() {
		String path = "C:\\Users\\Achilles\\Desktop\\resume.doc";

		File file = new File(path);
		try {
			System.out.println(doc2String(file));
		} catch (IOException e) {
			e.printStackTrace();
		}

		//System.out.println(WordUtil.readWord(path));

	}


	public static String doc2String(File file) throws IOException {
		return doc2String(new FileInputStream(file));
	}


	public static String doc2String(FileInputStream fs) throws IOException {
		StringBuilder result = new StringBuilder();
		WordExtractor re = new WordExtractor(fs);
		result.append(re.getText());
		re.close();
		return result.toString();

	}
}