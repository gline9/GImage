package gimage.conv;

public final class Convolutions {
	private Convolutions() {}

	public static Convolution create(ConvolutionApplier applier, int radius) {
		return new Convolution(radius, applier) {};
	}

	public static Convolution create(double[][] weights) {
		if (weights.length != weights[0].length)
			throw new IllegalArgumentException("Weight matrix for convolution must be square!");

		if ((weights.length - 1) % 2 != 0)
			throw new IllegalArgumentException(
					"Must have odd width and height for the weight matrix of a convolution!");

		int length = weights.length;

		int squares = length * length;

		double sum = 0;

		for (int x = 0; x < weights.length; x++) {
			for (int y = 0; y < weights.length; y++) {
				sum += weights[x][y];
			}
		}

		double total = sum;

		return create((r, g, b) -> {
			double rSum = 0, gSum = 0, bSum = 0;
			for (int i = 0; i < squares; i++) {
				rSum += r[i] * weights[i % length][i / length];
				gSum += g[i] * weights[i % length][i / length];
				bSum += b[i] * weights[i % length][i / length];
			}

			// return the normalized colors
			return new int[] { (int) (rSum / total), (int) (gSum / total), (int) (bSum / total) };
		} , (weights.length - 1) / 2);
	}

	public static Convolution meanBlur() {
		return create(new double[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } });
	}

	public static Convolution gaussianBlur() {
		return create(new double[][] { { 0.003765, 0.015019, 0.023792, 0.015019, 0.003765 },
				{ 0.015019, 0.059912, 0.094907, 0.059912, 0.015019 },
				{ 0.023792, 0.094907, 0.150342, 0.094907, 0.023792 },
				{ 0.015019, 0.059912, 0.094907, 0.059912, 0.015019 },
				{ 0.003765, 0.015019, 0.023792, 0.015019, 0.003765 } });
	}

}
