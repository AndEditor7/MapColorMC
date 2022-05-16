package com.andedit.mapcolor;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.SwingUtilities;

public class DropPngListener extends DropTargetAdapter {

	private final Consumer<File> loader;

	public DropPngListener(Consumer<File> loader) {
		this.loader = loader;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void drop(DropTargetDropEvent dtde) {
        if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            dtde.acceptDrop(dtde.getDropAction());
            try {
                List<File> files = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                if (files != null && !files.isEmpty()) {
                	final File file = getPngFile(files);
                    if (file != null) {
                    	SwingUtilities.invokeLater(() -> loader.accept(file));
                    	dtde.dropComplete(true);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            dtde.rejectDrop();
        }
	}

	private static File getPngFile(List<File> files) {
		for (File file : files) {
        	if (file.getName().toLowerCase().endsWith(".png")) {
        		return file;
        	}
        }
		return null;
	}
}
