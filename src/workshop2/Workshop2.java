package workshop2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Workshop2 {
	/**
	 * Task 1: 
     * Implement the thresholding transformation. It will set 
     * all pixels with intensity below the threshold 
     * to black and other pixels to white.
     * @param img
     * @param threshold
     */
    public void thresholdTransformation(byte[] img, int threshold) {
		for (int i=0;i<img.length;i++)
		{
			if (img[i]<threshold) img[i]=(byte)0;
			else img[i]=(byte)0xFF;
		}
    	System.out.println("TODO: implement Task 1");
    }
   /**
     * Task 2: 
	 * Implement the negative transformation.
	 *
	 * @param img
	 */
	public void negativeTransformation(byte[] img) {
		for (int i=0;i<img.length;i++)
		{
			img[i]=(byte)((byte)255-img[i]);
		}
		System.out.println("TODO: implement Task 2");
	}
	/**
	 * Task 3:
     * Implement the log transformation.
     * @param img
     */
    public void logTransformation(byte[] img) {
		//double c = 255 / Math.ln(256);
    	System.out.println("TODO: implement Task 3");
    }
    /**
     * Task 4:
     * Implement the bit-plane slicing
     *
     * @param img
     * @param mask - between 0 to 255 in decimal
     */
	public void bitPlaneSlicing(byte img[], int mask) {
		for (int i=0;i<img.length;i++)
		{
			//if ((mask&img[i])==mask)
				img[i]=(byte)(img[i] & (byte)mask);
			//else
			//	img[i]=(byte)0xFF;
		}
        System.out.println("Using mask: " + Integer.toBinaryString(mask));
     	System.out.println("TODO: implement Task 4");
    }
	/**
	 * Task 5:
	 * Calculate the histogram of the image.
	 * @param img
	 * @return the histogram
	 */
	public int[] histogram (byte[] img) {
		int[] h=new int[256];
		for (int i=0;i<256;i++)
			h[i]=0;
		for (int i=0;i<img.length;i++)
		{
			h[img[i] & 0xFF ]++;
		}
		return h;
	}
	/**
	 * Task 6:
	 * Perform histogram equalization.
	 * @param img
	 */
	public void histogramEqualization(byte img[]) {
		int[] h=histogram(img);
		double[] lookup=new double[256];
		int sum=h[0];
		lookup[0]=0;
		for (int i=0;i<256;i++)
		{
			lookup[i] = 256.0/(img.length*1.0)*(sum*1.0);
			if (i<255)
				sum=sum+h[i+1];
		}
		for (int i=0;i<img.length;i++)
		{
			img[i]=(byte)lookup[img[i] & 0xFF];
		}
		
		System.out.println("TODO: implement Task 6");
	}
	
	//return the value of a point in img, if out of bound, then return 0
	public int inBound(byte[] img, int x, int y, int w, int h)
	{
		if ((x<0) || (x>=h))
			return 0;
		if ((y<0) || (y>=w))
			return 0;
		return (int)img[x*w+y]&0xFF;
	}
	
	/**
	 * Homework 1:
	 * Implement the box smoothing filter.
	 *
	 * @param img the graylevel image (row major representation)
	 * @param w width of the image
	 * @param h height of the image
	 * @param filterSize the size of the filter, which is supplied by the user
	 */
	public void boxSmoothFilter(byte[] img, int w, int h, int filterSize) {
		byte[] originImg = img.clone();
		for (int x=0;x<h;x++){
			for (int y=0;y<w;y++){
				int temp=0;
				for (int i=-filterSize/2;i<filterSize/2+1;i++){
					for (int j=-filterSize/2;j<filterSize/2+1;j++){
						temp+=inBound(originImg, x+i, y+j, w, h);
					}
				}
				img[x*w+y]=(byte)(temp/(filterSize*filterSize));
			}
		}
	}
	
	/**
	 * Homework 2:
	 * Implement the median filter. 
	 * The java function java.util.Arrays.sort(array) can be used to sort an array.
	 *
	 * @param img the graylevel image (row major representation)
	 * @param w width of the image
	 * @param h height of the image
	 * @param filterSize the size of the filter, which is supplied by the user
	 */
	public void medianFilter(byte[] img, int w, int h, int filterSize) {
		byte[] originImg = img.clone();
		for (int x=0;x<h;x++){
			for (int y=0;y<w;y++){
				ArrayList<Integer> l =new ArrayList<Integer>();
				for (int i=-filterSize/2;i<filterSize/2+1;i++){
					for (int j=-filterSize/2;j<filterSize/2+1;j++){
						l.add(inBound(originImg, x+i, y+j, w, h));
					}
				}
				Collections.sort(l);
				img[x*w+y]=(byte)(l.get(filterSize*filterSize/2).intValue());
				//System.out.println(img[x*w+y]&0xFF);
			}
		}		
	}
	/** 
	 * Homework 3:
	 * Implement the Laplacian filter (isotropic mask for rotations in increments of 45 deg)
	 *
	 * @param img the graylevel image (row major)
	 * @param w width of the image
	 * @param h height of the image
	 */
	public void laplacianFilter(byte img[], int w, int h) {
		//System.out.println("TODO: implement Homework 3");
		byte[] originImg = img.clone();
		
		
		for (int x=0;x<h;x++){
			for (int y=0;y<w;y++){
				int temp=0;
				for (int i=-1;i<2;i++){
					for (int j=-1;j<2;j++){
						
						if (i==0 && j==0)
							temp+=8*inBound(originImg, x, y, w, h);
						else 
							temp+=-inBound(originImg, x+i, y+j, w, h);
						
					}
				}
				
				if (temp<0) temp = 0;if (temp>255) temp = 255;
				
				img[x*w+y]=(byte)temp;
			}
		}
	}
}
