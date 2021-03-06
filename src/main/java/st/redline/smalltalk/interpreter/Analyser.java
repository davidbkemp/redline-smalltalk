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
package st.redline.smalltalk.interpreter;

import st.redline.smalltalk.Smalltalk;
import st.redline.smalltalk.SourceFile;

public class Analyser implements NodeVisitor {

	private final Smalltalk smalltalk;
	private final Generator generator;

	public Analyser(Smalltalk smalltalk, Generator generator) {
		this.smalltalk = smalltalk;
		this.generator = generator;
	}

	public byte[] result() {
		return generator.classBytes();
	}

	public void visit(Program program) {
		writeClass(program);
	}

	private void writeClass(Program program) {
		generator.openClass(sourceFileName(), sourceFileParentPathWithoutUserPath());
		program.sequence().accept(this);
		generator.closeClass();
	}

	public void visit(Sequence sequence) {
		sequence.statements().accept(this);
	}

	public void visit(Statements statements) {
		statements.statementList().accept(this);
	}

	public void visit(StatementList statementList) {
		statementList.eachAccept(this);
	}

	public void visit(Expression expression) {
		expression.cascade().accept(this);
	}

	public void visit(Cascade cascade) {
		cascade.messageSend().accept(this);
	}

	public void visit(MessageSend messageSend) {
		messageSend.unaryMessageSend().accept(this);
	}

	public void visit(UnaryMessageSend unaryMessageSend) {
		unaryMessageSend.primary().accept(this);
		unaryMessageSend.eachAccept(this);
	}

	public void visit(Variable variable) {
		if (variable.isClassReference())
			generator.classLookup(variable.name());
	}

	public void visit(UnaryMessage unaryMessage) {
		generator.unarySend(unaryMessage.selector());
	}

	private String sourceFileName() {
		return sourceFile().nameWithoutExtension();
	}

	private String sourceFileParentPathWithoutUserPath() {
		return sourceFile().parentPathWithoutUserPath();
	}

	private SourceFile sourceFile() {
		return smalltalk.currentFile();
	}
}
