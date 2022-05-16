package com.andedit.mapcolor;

import java.awt.Image;
import java.awt.Toolkit;

public class Res {
	public static final Image DND = getImage("DragAndDrop.png");
	
	private static ClassLoader getClassLoader() {
		return ImagePreview.class.getClassLoader();
	}
	
	private static Image getImage(String path) {
		return Toolkit.getDefaultToolkit().getImage(getClassLoader().getResource(path));
	}
}
