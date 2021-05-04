package com.achilles.wild.server.tool.generate.encrypt;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;


public class MD5Util {

	public static final String SALT = "HXWcjvQWVG1wI4FQBLZpQ3pWj48AV63d";

	public static void main(String args[]) {

		String md5 = get("Achilles");
		System.out.println(md5);
	}

	/**
	 * get
	 *
	 * @param inputStream
	 * @param salt
	 * @return
	 */
	public static String get(InputStream inputStream,String salt) {

		String key;
		try {
			key = DigestUtils.md5Hex(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		String encryption = get(key,salt);

		return encryption;
	}

	/**
	 * get
	 *
	 * @param inputStream
	 * @return
	 */
	public static String get(InputStream inputStream) {

		String encryption;
		try {
			encryption = DigestUtils.md5Hex(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return encryption;
	}

	/**
	 * get md5 key
	 *
	 * @param key
	 * @return
	 */
	public static String get(String key,String salt) {
		String encryption = DigestUtils.md5Hex(key+salt);
		return encryption;
	}

	/**
	 * get md5 key
	 *
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		String encryption = DigestUtils.md5Hex(key);
		return encryption;
	}

}
