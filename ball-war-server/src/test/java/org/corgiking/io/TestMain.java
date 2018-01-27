package org.corgiking.io;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

public class TestMain {

	public static void main(String[] args) {
		Class<TestMain> clazz = TestMain.class;
		boolean instance = clazz.isInstance(null);
		System.out.println(instance);

	}

	public static String test(String... strings ){
		FormattingTuple arrayFormat = MessageFormatter.arrayFormat("test-{},sdfa-{}", strings);
		return arrayFormat.getMessage();
	}
}
