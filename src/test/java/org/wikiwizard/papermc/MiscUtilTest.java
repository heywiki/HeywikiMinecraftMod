package org.wikiwizard.papermc;

import org.json.simple.JSONObject;
import org.junit.Test;

public class MiscUtilTest {

	@Test
	public void testPrettyPrint() {	
		JSONObject obj = new JSONObject();
		obj.put("id", "123");
		obj.put("name", "abc");
		
		System.out.println(obj);
		System.out.println("-----");
		System.out.println(MiscUtil.prettyPrint(obj));
		
		System.out.println(MiscUtil.prettyPrint(null));
	}
}
