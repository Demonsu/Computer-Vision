package workshop1;
import java.util.LinkedList;
import java.util.Queue;

/*
 * Compile: javac *.java
 * Run: java Workshop1UI
 */
public class Workshop1 {
	/**
	 * Task 1:
	 * Retrieve the intensity value at location (x, y) of the image and return it
	 * Note: 
	 * - the 2D image is stored as an 8bit, 1D, row-major array of type byte
	 * - Note that byte is a signed data type in Java
	 * @param img in row major format
	 * @param x coordinate
	 * @param y coordinate
	 * @param width of img
	 * @param height of img
	 * @return the intensity value at (x, y) if within bounds, -1 otherwise
	 */
	public int getIntensityValue(byte[] img, int x, int y, int width, int height) {
		System.out.println("TODO: implement Task 1");
		
		return (int)(img[x*width+y] & 0xFF);
	}
	/**
	 * Task 2:
	 * Retrieve the intensity value that occurs most often in the image
	 * @param img 
	 * @return the intensity value that occurs most often in the image
	 */
	public int getMostFrequentIntensityValue(byte[] img) {
		System.out.println("TODO: implement Task 2");
		return -1;
	}
	/**
	 * Task 3:
	 * Sets the pixels N8(row, column) to white.
	 * @param img with four neigbors of (row, column) set to white
	 * @param x coordinate
	 * @param y coordinate
	 * @param width of img
	 * @param height of img
	 */
	public void setEightNeighborsToWhite(byte[] img, int x, int y, int width, int height) {
		for (int i=-1;i<2;i++)
		{
			for (int j=-1;j<2;j++)
				if (!(i==0 && j==0))
				img[(x+i)*width+(y+j)] = (byte) 0xFF;
		}
		for (int i=150;i<250;i++)
		{
			for (int j=250;j<300;j++)
			{
				img[i*width+j]=(byte)120;
			}
		}
		System.out.println("TODO: implement Task 3");
	}
	/**
	 * Task 4:
	 * Calculates the d4 distance between (x1, y1) and (x2, y2)
	 * @param img that will be unchanged
	 * @param x1 
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param width of img
	 * @param height of img
	 * @return the d4 distance between (x1, y1) and (x2, y2) 
	 */
	public int getD4Distance(byte[] img, int x1, int y1, int x2, int y2, int width, int height) {
		//System.out.println("TODO: implement Task 4");
		for (int i=0;i<width*height;i++)
		{
			if ((img[i]&0xFF)<=127)
				img[i]=(byte) 0x00;
			else {
				img[i]=(byte) 0xFF;
			}
		}
		return -1;
	}
	/**
	 * Homework 1: 
	 * Marks the shortest m-path with white intensity values. Let V = {0, ..., 127}.
	 * This task was developed to challenge yourself. Can you find a shortest m-path quickly?
	 * @param img with the shortest m-path set to white with V = {0, ..., 127}.
	 * @param x1 
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param width of img
	 * @param height of img
	 */
	class Ipoint{
		public int x;
		public int y;
		public int d;
		public Ipoint from;
		public Ipoint(int i, int j, int k, Ipoint f){
			x = i;
			y = j;
			d = k;
			from = f;
		}
	}
	public boolean inBound(Ipoint p,int w, int h)
	{
		if ((p.x<0) || (p.x>=h))
			return false;
		if ((p.y<0) || (p.y>=w))
			return false;
		return  true;
	}
	public boolean isMPath(byte[] img, Ipoint p1, Ipoint p2,int w)
	{
		int boundary=128;
		//System.out.println(p1.x+","+p1.y+";"+p2.x+","+p2.y);
		//System.out.println((img[p1.x*w+p1.y]&0xFF)+":::"+(img[p2.x*w+p2.y]&0xFF));
		if (((img[p1.x*w+p1.y]&0xFF)<boundary) && ((img[p2.x*w+p2.y]&0xFF)<boundary))
		{
			//System.out.println("keng");
			if ((p1.x==p2.x) || (p1.y==p2.y))
			{
				return true;
			}else {
				if (p1.x>p2.x)
				{
					if (p1.y>p2.y)
					{
						if (((img[p2.x*w+p2.y+1]&0xFF)>127) && ((img[(p2.x+1)*w+p2.y]&0xFF)>(boundary-1)))
						{
							return true;
						} 
					}else {
						if (((img[p1.x*w+p1.y+1]&0xFF)>(boundary-1)) && ((img[(p1.x-1)*w+p1.y]&0xFF)>(boundary-1)))
						{
							return true;
						}
					}
				}else {
					if (p1.y>p2.y)
					{
						if (((img[p2.x*w+p2.y+1]&0xFF)>(boundary-1)) && ((img[(p2.x-1)*w+p2.y]&0xFF)>(boundary-1)))
						{
							return true;
						}
					} else {
						if (((img[p1.x*w+p1.y+1]&0xFF)>(boundary-1)) && ((img[(p1.x+1)*w+p1.y]&0xFF)>(boundary-1)))
						{
							return true;
						}
					}
					
				}
			}
			
		}
		return false;
	}
	public void setShortestMPathToWhite(byte[] img, int x1, int y1, int x2, int y2, int width, int height) {
		System.out.println("width:"+width+" height:"+height);
		boolean[] visit=new boolean[width*height];
		for (int i=0;i<visit.length;i++)
			visit[i]=false;
		visit[x2*width+y2]=true;
		Queue<Ipoint> q=new LinkedList<Ipoint>();
		q.add(new Ipoint(x2, y2, 0, null));
		while (!q.isEmpty()){
			Ipoint now = q.poll();
			//System.out.println(now.x+","+now.y);
			for (int i=-1;i<2;i++)
			{
				for (int j=-1;j<2;j++)
				{
					if ((i==0) && (j==0)) continue;
					
					Ipoint newP=new Ipoint(now.x+i,now.y+j , now.d+1, now);

					if (inBound(newP, width, height))
					{
						if (visit[newP.x*width+newP.y]) continue;
						//System.out.println(newP.x+","+newP.y+","+newP.d);
						if (isMPath(img, now, newP, width)){
							if ((newP.x==x1) && (newP.y==y1)){
								System.out.println("FOUND!!");
								while (newP.from!=null)
								{
									img[newP.from.x*width+newP.from.y] = (byte) 0xFF;
									newP = newP.from;
								}
								return;
							}
							visit[newP.x*width+newP.y]=true;
							img[newP.x*width+newP.y]=(byte)0x22;
							q.add(newP);
						}
					}
				
				}
			}

			
		}
		
		
		System.out.println(x1+","+y1+";"+x2+","+y2);
		
		
		
		
		System.out.println("TODO: implement Homework 1");
	}
}
