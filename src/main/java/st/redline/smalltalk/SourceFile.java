package st.redline.smalltalk;

import java.io.File;

public class SourceFile extends File {

	private final Smalltalk smalltalk;

	public SourceFile(String path, Smalltalk smalltalk) {
		super(path);
		this.smalltalk = smalltalk;
	}

	public String contents() {
		return fileReader().read(this);
	}

	public String nameWithoutExtension() {
		String name = getName();
		int period = name.lastIndexOf('.');
		if (period == -1)
			return name;
		return name.substring(0, period);
	}

	public String parentPath() {
		String parent = super.getParent();
		return parent == null ? "" : parent;
	}

	public String parentPathWithoutUserPath() {
		String userPath = userPath();
		String parentPath = parentPath();
		if (parentPath.startsWith(userPath))
			return parentPath.substring(userPath.length() + 1);
		return parentPath;
	}

	private String userPath() {
		return smalltalk.userPath();
	}

	private FileReader fileReader() {
		return smalltalk.fileReader();
	}
}
