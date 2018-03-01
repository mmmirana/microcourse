package com.mirana.frame.utils;

import java.util.Random;
import java.util.UUID;

public class RandomUtils {
	/**
	 * 获取32位随机字符串
	 *
	 * @return
	 */
	public static String generateString () {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

	/**
	 * 获取固定位数的随机字符
	 *
	 * @param length
	 * @return
	 */
	public static String generateString (int length) {
		Assert.greatThan(length, 0);
		return generateString().substring(0, length);
	}

	/**
	 * 获取min-max之间的随机数
	 *
	 * @param min
	 * @param max
	 * @return
	 */
	public static int generateInt (int min, int max) {
		Assert.isTrue(max > min);
		return new Random().nextInt(max - min) + min;
	}

	public static void main (String[] args) {
		System.out.println(generateInt(1, 100));
	}
}
