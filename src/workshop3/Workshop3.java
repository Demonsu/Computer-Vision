package workshop3;

public class Workshop3 {
	/**
	 * Log transformation. You may make use of this function when solving Task 1.
	 *
	 * @param img the graylevel image (row major representation)
	 */
    private void logTransformation(byte[] img) {
    	double c = 255 / Math.log(256);
		byte[] tmp = new byte[256];
		for (int i=0;i<256;i++)
			tmp[i] = (byte)(c*Math.log(i+1));
		for (int i=0;i<img.length;i++)
			img[i] = tmp[(int)(img[i] & 0xFF)];
	}

		/**
	 * Task 1:
	 * Implement the Fourier transform. Make sure all intensity values are in the range 0 .. 255.
	 *
	 * @param img the graylevel image (row major representation)
	 * @param width of the image
	 * @param height of the image
	 */
    public void fourierTransform(byte[] img, int width, int height) {
    	Complex[] FS = ft(img, width, height);
    	Complex[] F = seperate_ft(img, width, height);
    	for (int i=0;i<width*height;i++){
    		if ((Math.abs(F[i].getReal()-FS[i].getReal())<0.001) && (Math.abs(F[i].getImaginary()-FS[i].getImaginary())<0.001)){
    			
    		}else {
    			System.out.println("different");
				}
    	}
		double max = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < F.length; i++)
			max = Math.max(F[i].getNorm(), max);
		double c = 255.0 / max;
		for (int i = 0; i < img.length; i++)
			img[i] = (byte)(c*F[i].getNorm());
		logTransformation(img);
    }

	private Complex[] ft(byte[] img, int width, int height) {
    	Complex[] F = new Complex[width*height];
		for (int u = 0;u<height;u++) {
			for (int v = 0;v<width;v++) {
				F[u*width + v] = new Complex();
				for (int x = 0;x<height;x++) {
					for (int y = 0;y<width;y++) {
						double a = (double)(img[x*width+y]&0xFF);//*Math.pow(-1, x+y);
						double b = -2.0 * Math.PI * (u*x/(double)height + v*y/(double)width);
						Complex c = Complex.fromPolar(1, b).mul(a);
						F[u*width+v] = F[u*width+v].plus(c);
					}
				}
			}
		}
		return F;
	} 
	
	private Complex[] single_ft(Complex[] f, int height, int width, String type){
		int M = width*height;
		Complex[] F = new Complex[M];
		for (int i=0;i<M;i++){
			int u = i / width;
			int v = i % width;
			F[i] = new Complex();
			if (type=="y"){
				for (int y=0;y<width;y++){
					double b = -2*Math.PI*v*y/(double)width;
					F[i] = Complex.fromPolar(1, b).mul(f[u*width+y]).plus(F[i]);
				}			
			}
			if (type=="x"){
				for (int x=0;x<height;x++){
					double b = -2*Math.PI*u*x/(double)height;
					F[i] = Complex.fromPolar(1, b).mul(f[x*width+v]).plus(F[i]);
				}					
			}
		}
		return F;
	}
	
	private Complex[] seperate_ft(byte[] img, int width, int height){
//		Complex[] FY = new Complex[width*height];
//		for (int x=0;x<height;x++){	
//			for (int v=0;v<width;v++){
//				FY[x*width+v]=new Complex();
//				for (int y=0;y<width;y++){
//					double a = (img[x*width+y]&0xFF)*Math.pow(-1, x+y);
//					double b = -2*Math.PI*v*y/(double)width;
//					FY[x*width+v] = Complex.fromPolar(1, b).mul(a).plus(FY[x*width+v]);
//				}
//			}
//		}
		Complex[] IMG = new Complex[width*height];
		for (int i=0;i<width*height;i++){
			IMG[i] = new Complex((img[i]&0xFF),0);
		}
		Complex[] FY = single_ft(IMG, height, width, "y");
		
		Complex[] F = single_ft(FY, height, width, "x");

//		Complex[] F =new Complex[width*height];
//		for (int u=0;u<height;u++)
//			for (int v=0;v<width;v++){
//				F[u*width+v]=new Complex();
//				for (int x=0;x<height;x++){
//					double b = -2*Math.PI*u*x/(double)height;
//					F[u*width+v] = Complex.fromPolar(1, b).mul(FY[x*width+v]).plus(F[u*width+v]);
//				}
//			}
		return F;
	}

   /**
	 * Task 2:
	 * Change the image to test your Fourier transform implementation. 
	 *
	 * @param img the graylevel image (row major representation)
	 * @param width of the image
	 * @param height of the image
	 */
    public void changeImage(byte[] img, int width, int height) {
		for (int x = 0;x<height;x++) {
			for (int y = 0;y<width;y++) {
				img[x*width+y] = (byte)0;
				if ((Math.abs(x - (height/2)) < 10) && (Math.abs(y - (width/2)) < 5))
					img[x*width+y] = (byte)255;
			}
		}
	}

	private void ift(Complex[] F, byte[] img, int width, int height) {
		for (int x = 0;x<height;x++) {
			for (int y = 0;y<width;y++) {
				Complex c = new Complex();
				for (int u = 0;u<height;u++) {
					for (int v = 0;v<width;v++) {
					 Complex tmp=Complex.fromPolar(1,2.0*Math.PI* (u*x/(double)height + v*y/(double)width));
					 c = c.plus(tmp.mul(F[u*width+v]));
					}
				}
				c = c.div(height*width);
				c = c.mul(Math.pow(-1, (x+y)));
				if (c.getReal() < 0) c.setReal(0.0);
				img[x*width+y] = (byte)(c.getReal());
			}
		}
	} 

   /**
	 * Task 3:
	 * Implement a simple filter. E.g, one that sets the center of the transform to 0. 
	 *
	 * @param img the graylevel image (row major representation)
	 * @param width of the image
	 * @param height of the image
	 */
	public void filtering(byte[] img, int width, int height) {
		Complex[] F = ft(img, width, height);
		F[(height/2)*width + (width/2)] = new Complex();
		ift(F, img, width, height);
	}

}
