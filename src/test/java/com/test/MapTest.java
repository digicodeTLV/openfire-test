package com.test;

import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tymoshenkol on 31-Aug-16.
 */
@Slf4j
public class MapTest {

	@Test
	public void test () {
		int start = 0;
		int end = 25000;
		Map<String, String> occupantsByNickname = new ConcurrentHashMap<>();
		for (int i = start; i <= end; i++) {
			occupantsByNickname.put("test1"+i, "test1"+i);
		}
		log.debug("occupantsByNickname.size: {}", occupantsByNickname.size());
		Assert.assertEquals(25001, occupantsByNickname.size());
	}
}
