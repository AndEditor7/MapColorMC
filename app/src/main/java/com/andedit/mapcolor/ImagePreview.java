package com.andedit.mapcolor;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.io.File;
import java.util.TooManyListenersException;
import java.util.function.Consumer;

import javax.swing.SwingUtilities;

public class ImagePreview extends Canvas {
	private static final long serialVersionUID = 1L;

	private volatile Image image;
	private final DropTarget dropTarget;

	private final DropPngListener listener;

	public ImagePreview(Consumer<File> loader) {
		listener = new DropPngListener(loader);
		dropTarget = new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, null);
		image = Res.DND;
		SwingUtilities.invokeLater(() ->
			getGraphics().drawImage(Res.DND, 0, 0, getWidth(), getHeight(), null)
		);
	}

	public void setImage(Image image) {
		this.image = image;
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}

	public void clear() {
		setImage(Res.DND);
	}

	@Override
    public void addNotify() {
        super.addNotify();
        try {
			dropTarget.addDropTargetListener(listener);
		} catch (TooManyListenersException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        dropTarget.removeDropTargetListener(listener);
    }
}
