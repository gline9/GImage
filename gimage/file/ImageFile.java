package gimage.file;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import gfiles.file.VirtualFile;

public class ImageFile extends VirtualFile {

	private BufferedImage image;

	public ImageFile(byte[] data) throws ImageFormatException {
		super(data);
		init();
	}

	public ImageFile(InputStream in) throws IOException {
		super(in);
		init();
	}

	public ImageFile(VirtualFile vf) throws ImageFormatException {
		super(vf);
		init();
	}

	public ImageFile(BufferedImage bf, String format) {
		super();
		this.image = bf;
		try {
			ImageIO.write(bf, format, getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() throws ImageFormatException {
		try {
			image = ImageIO.read(getInputStream());
		} catch (IOException e) {
			throw new ImageFormatException();
		}
	}

	public BufferedImage getImage() {
		return image;
	}
}
