package com.andedit.mapcolor;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class PngFilter extends FileFilter {
	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) return true;
		return file.getName().toLowerCase().endsWith(".png");
	}

	@Override
	public String getDescription() {
		return "PNG (*.png)";
	}
}
