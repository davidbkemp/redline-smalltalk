/*
Redline Smalltalk is licensed under the MIT License

Redline Smalltalk Copyright (c) 2010 James C. Ladd

Permission is hereby granted, free of charge, to any person obtaining a copy of this software
and associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package st.redline.smalltalk;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Environment {

	private final Map<String, Object> contents;
	private final CommandLine commandLine;
	private final PrintWriter standardOutput;
	private final PrintWriter errorOutput;

	public static Environment with(CommandLine commandLine, PrintWriter standardOutput, PrintWriter errorOutput) {
		if (commandLine == null || standardOutput == null || errorOutput == null)
			throw new MissingArgumentException();
		return new Environment(commandLine, standardOutput, errorOutput);
	}

	private Environment(CommandLine commandLine, PrintWriter standardOutput, PrintWriter errorOutput) {
		this.commandLine = commandLine;
		this.standardOutput = standardOutput;
		this.errorOutput = errorOutput;
		this.contents = new Hashtable<String, Object>();
	}

	public CommandLine commandLine() {
		return commandLine;
	}

	public PrintWriter standardOutput() {
		return standardOutput;
	}

	public PrintWriter errorOutput() {
		return errorOutput;
	}

	public Object put(String key, Object value) {
		return contents.put(key, value);
	}

	public Object get(String key) {
		return contents.get(key);
	}

	public Object remove(String key) {
		return contents.remove(key);
	}

	public boolean containsKey(String key) {
		return contents.containsKey(key);
	}
}
