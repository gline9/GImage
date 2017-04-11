package gimage.test;

import java.io.File;
import java.io.IOException;

import gfiles.file.VirtualFile;
import gimage.conv.Convolutions;
import gimage.file.ImageFile;
import gimage.file.ImageFormatException;

public class Tester {
	public static void main(String[] args){
		try {
			ImageFile image = new ImageFile(VirtualFile.load(new File("C:/Users/Gavin/Desktop/GZip test/test.png")));
			ImageFile savingMean = new ImageFile(Convolutions.meanBlur().convolute(image.getImage()), "jpg");
			ImageFile savingGaussian = new ImageFile(Convolutions.gaussianBlur().convolute(image.getImage()), "jpg");
			savingMean.save(new File("C:/Users/Gavin/Desktop/GZip test/blurred.jpg"));
			savingGaussian.save(new File("C:/Users/Gavin/Desktop/GZip test/gaussian.jpg"));
		} catch (ImageFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
