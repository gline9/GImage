package gimage.conv;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public abstract class Convolution {

	private final ConvolutionApplier applier;

	private final int radius;

	protected Convolution(int radius, ConvolutionApplier applier) {
		if (radius < 0)
			throw new IllegalArgumentException("Can't have a radius less than 0 (" + radius + ") for a convolution");
		
		this.applier = applier;
		this.radius = radius;

	}

	public final BufferedImage convolute(BufferedImage image) {
		// check if the image is large enough
		int blockLength = 2 * radius + 1;

		if (image.getWidth() < blockLength || image.getHeight() < blockLength)
			throw new IllegalArgumentException("Image is too small to apply this convolution filter");

		int width = image.getWidth();
		int height = image.getHeight();

		int newWidth = width - blockLength;
		int newHeight = height - blockLength;
		
		BufferedImage results = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < newWidth; x++) {
			for (int y = 0; y < newHeight; y++) {
				int[] rgb = image.getRGB(x, y, blockLength, blockLength, null, 0, blockLength);
				int[] r = new int[rgb.length];
				int[] g = new int[rgb.length];
				int[] b = new int[rgb.length];
				for (int i = 0; i < rgb.length; i++) {
					r[i] = (rgb[i] & 0xFF0000) / 0x10000;
					g[i] = (rgb[i] & 0x00FF00) / 0x100;
					b[i] = rgb[i] & 0x0000FF;
				}
				int[] filtered = applier.apply(r, g, b);
				int rgbi = filtered[0] * 0x10000 + filtered[1] * 0x100 + filtered[2];
				results.setRGB(x, y, rgbi);
			}
		}
		return results;
	}

}
