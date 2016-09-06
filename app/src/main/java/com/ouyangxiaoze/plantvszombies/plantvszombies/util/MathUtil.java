package com.ouyangxiaoze.plantvszombies.plantvszombies.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 运算处理
 */
public class MathUtil {
	/**
	 * 在一个区间内获取指定一个的数
	 */
	public static int getRandom(int start, int end) {
		Random random = new Random();
		int result = random.nextInt(end+1);
		if (result < start) {
			result += start;
		}
		return result;
	}
	/**
	 * 获取指定个数的随机数
	 * @param start 起点
	 * @param end   终点
	 * @param size  个数
	 * @return
	 */
	public static List<Integer> getRandoms(int start, int end, int size) {
		List<Integer> result = new ArrayList<Integer>();
		while (result.size() < size) {
			int num = getRandom(start, end);
			if (result.contains(num)) {
				continue;
			}

			result.add(num);
		}
		return result;
	}
}
