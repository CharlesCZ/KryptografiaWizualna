import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Main {


    public static BufferedImage user_space(BufferedImage image,int increase)
    {
        //create new_img with the attributes of image
        BufferedImage new_img  = new BufferedImage(image.getWidth()*increase, image.getHeight()*increase, BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D graphics = new_img.createGraphics();
        graphics.drawRenderedImage(image, null);
        graphics.dispose(); //release all allocated memory for this image
        return new_img;
    }

    public static int[] kryptoPixel(){

        int[][] krypto={{0,0,0xFFFFFFFF,0xFFFFFFFF},{0xFFFFFFFF,0xFFFFFFFF,0,0},
                {0,0xFFFFFFFF,0,0xFFFFFFFF},{0,0xFFFFFFFF,0,0xFFFFFFFF},{0xFFFFFFFF,0,0xFFFFFFFF,0},{0,0xFFFFFFFF,0,0xFFFFFFFF}};


        Random rand=new Random();


return krypto[ rand.nextInt(1)];

    }
    public static BufferedImage[] encode(BufferedImage img){
        int height = img.getHeight();
        int width = img.getWidth();
int pixel;
BufferedImage udzial1=user_space(img,2);
        BufferedImage udzial2=user_space(img,2);


        System.out.println("udzial Height"+" "+udzial1.getHeight());
        System.out.println("udzial width"+" "+udzial1.getWidth());
        for (int h = 1; h<udzial1.getHeight()-1; h=h+2) {
            for (int w = 1; w < udzial1.getWidth()-1; w=w+2) {


               pixel= img.getRGB(1+(w-1)/2, 1+(h-1)/2);



                int[] randomkostka=kryptoPixel();
                udzial1.setRGB(w,h,randomkostka[0]);
                udzial1.setRGB(w+1,h,randomkostka[1]);
                udzial1.setRGB(w,h+1,randomkostka[2]);
                udzial1.setRGB(w+1,h+1,randomkostka[3]);

                if(pixel==0xFFFFFFFF) {// ustawianie randomowych kostek do udzialu1
                    udzial2.setRGB(w, h, randomkostka[0]);
                    udzial2.setRGB(w + 1, h, randomkostka[1]);
                    udzial2.setRGB(w, h + 1, randomkostka[2]);
                    udzial2.setRGB(w + 1, h + 1, randomkostka[3]);
                }else { //przeciwne badz nie, udzial2
                    if(randomkostka[0]==0)
                    udzial2.setRGB(w, h, 0xFFFFFFFF);
                    else udzial2.setRGB(w, h, 0);

                    if(randomkostka[1]==0)
                        udzial2.setRGB(w + 1, h, 0xFFFFFFFF);
                    else   udzial2.setRGB(w + 1, h, 0);

                    if(randomkostka[2]==0)
                        udzial2.setRGB(w, h + 1, 0xFFFFFFFF);
                    else   udzial2.setRGB(w, h + 1, 0);

                    if(randomkostka[3]==0)
                        udzial2.setRGB(w + 1, h + 1, 0xFFFFFFFF);
                    else    udzial2.setRGB(w + 1, h + 1, 0);




                }

            }

        }
        File outputfile = new File("udzial1.png");
        try {
            ImageIO.write(udzial1, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

         outputfile = new File("udzial2.png");
        try {
            ImageIO.write(udzial2, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

         outputfile = new File("saved1.png");
        try {
            ImageIO.write( decode(udzial1,udzial2), "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

BufferedImage[] tab=new BufferedImage[2];
        tab[0]=udzial1;
        tab[1]=udzial2;
        return  tab;
    }

    public static BufferedImage decode(BufferedImage udzial1,BufferedImage udzial2){
       BufferedImage zlaczony=new BufferedImage((int)(udzial1.getWidth()*0.5), (int)(udzial1.getHeight()*0.5), BufferedImage.TYPE_BYTE_BINARY);


        for (int h = 1; h<udzial1.getHeight()-1; h=h+2) {
            for (int w = 1; w < udzial1.getWidth()-1; w=w+2) {
             if(  ( udzial1.getRGB(w, h)== udzial2.getRGB(w, h))  && (udzial2.getRGB(w + 1, h)==udzial1.getRGB(w + 1, h)) && (udzial2.getRGB(w, h + 1)==  udzial1.getRGB(w, h + 1))
             &&  ( udzial2.getRGB(w + 1, h + 1)== udzial1.getRGB(w + 1, h + 1)) )

zlaczony.setRGB(1+(w-1)/2, 1+(h-1)/2,0xFFFFFFFF);
             else
                 zlaczony.setRGB(1+(w-1)/2, 1+(h-1)/2,0);


            }
        }


return zlaczony;
    }
    public static void main(String[] args) {
        BufferedImage img = null;
        try {
              img = ImageIO.read(new File("image.png"));
        } catch (IOException e) {

        }




        BufferedImage imgAfter=user_space(img,1);
        BufferedImage[] tab=encode(imgAfter);
        BufferedImage imgDecode=decode(tab[0],tab[1]);
        File outputfile = new File("saved.png");
        try {
            ImageIO.write(imgDecode, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
