package com.workert.robotics.base.roboscript.tool;
import com.workert.robotics.base.roboscript.Interpreter;
import com.workert.robotics.base.roboscript.RoboScript;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class RoboScriptRunner {
	private static String sourcePath = "src/main/java/com/workert/robotics/base/roboscript/tool/script.robo";

	private static RoboScript program = new RoboScript(true) {

		@Override
		public void saveVariableExternally(Map.Entry<String, Object> variableMap) {
			RoboScriptRunner.variableMap.put(variableMap.getKey(), variableMap.getValue());
		}

		@Override
		public Map<String, Object> getExternallySavedVariables() {
			return variableMap;
		}
	};

	private static Map<String, Object> variableMap = new HashMap<>();

	public static void main(String[] args) throws IOException {
		program.defineFunction("print", 1, (interpreter, arguments) -> {
			System.out.println(Interpreter.stringify(arguments.get(0)));
			return null;
		});
		runFile();
	}

	private static void runFile() throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get(sourcePath));
		program.runString(new String(bytes, Charset.defaultCharset()));
		System.out.println(program.getConsoleOutput());
	}
}