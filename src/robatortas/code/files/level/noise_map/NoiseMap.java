package robatortas.code.files.level.noise_map;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class NoiseMap {
	
	public int WIDTH, HEIGHT;
	
	public double[] values;
	
	public Random random = new Random();
	
	/*
	 * A
	 * B
	 * C
	 * D
	 * E
	 * F
	 * G
	 * H
	 */
	
	public NoiseMap(int w, int h, int sampleSize) {
		this.WIDTH=w;
		this.HEIGHT=h;
		
		values = new double[w*h];
		
		for(int y=0;y<w;y+=sampleSize) {
			for(int x=0;x<w;x+=sampleSize) {
				setSample(x,y,random.nextFloat()*2-1);
			}
		}
		
		int sample = sampleSize;
		double scale = 0.009;
		while(sample>1) {
			int halfSample = sample/2;
			for(int y=0;y<w;y+=sample) {
				for(int x=0;x<w;x+=sample) {
					double a = sample(x,y);
					double b = sample(x+sample,y);
					double c = sample(x,y+sample);
					double d = sample(x+sample,y+sample);
					
					double e = (a+b+c+d)/4.0+(random.nextFloat())*sample*scale;
					setSample(x+halfSample,y+halfSample,e);
				}
			}
			for(int y=0;y<w;y+=sample) {
				for(int x=0;x<w;x+=sample) {
					double a = sample(x,y);
					double b = sample(x+sample,y);
					double c = sample(x,y+sample);
					double d = sample(x+halfSample,y+halfSample);
					double e = sample(x+halfSample,y-halfSample);
					double f = sample(x-halfSample,y+halfSample);
				
					double g = (a+c+d+f)/4.0+(random.nextFloat()*2-1)*sample*scale;
					double H = (a+b+d+e)/4.0+(random.nextFloat()*2-1)*sample*scale;
					setSample(x+halfSample,y,H);
					setSample(x,y+halfSample,g);
				}
			}
		sample/=2;
		}
	}
	
	public double sample(int x, int y) {
		return values[((x&(w-1))+(y&(h-1))*w)];
	}
	
	public void setSample(int x, int y, double value) {
		values[(x&(w-1))+(y&(h-1))*w]=value;
	}
	
	public static int w=64, h=64;
	public static void main(String[] args) {
		while(true) {
			System.out.println("Calculating...");
			NoiseMap noiseMap = new NoiseMap(w,h,64);
			BufferedImage image = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
			int[] pixels = new int[w*h];
			
			for(int i=0;i<w*h;i++) {
				int result=(int)(noiseMap.values[i]*120+128);
				pixels[i] = result<<16|result<<8|result;
			}
			
			image.setRGB(0,0,w,h,pixels,0,w);
			JOptionPane.showMessageDialog(null, null, "Generate", JOptionPane.YES_NO_OPTION, new ImageIcon(image.getScaledInstance(w*4, h*4, Image.SCALE_AREA_AVERAGING)));
		}
	}
}
