import java.awt.Color;
public class Steganography
{
    public static void main(String[] args)
    {
        //Notice how the picture needs to be created!
        //Don't change the String value in the Picture parameter.
        Picture beach = new Picture("beach_with_hidden_image.png");
        //beach.explore();
        //beach = testClearLow(beach);
        //beach.write("test.png");
        Picture revealed = revealPicture(beach);
        revealed.write("revealed.png");


        // Picture beach = new Picture("beach_with_hidden_image.png"); //use this to have both pictures floower 1 and flower 2
        //them have hidepicture combined

        Picture flower1 = new Picture("flower1.jpg");
        Picture flower2 = new Picture("flower2.jpg");
        Picture combined = hidePicture(flower1, flower2);
        combined.write("cool.png");

        //revealing png
        Picture reveal = revealPicture(combined);
        reveal.write("reveale.png");
        revealPicture(reveal);

        // From here on, this is the code I used for my project

       // my first hiden picture example
       Picture bluePiggyBank = new Picture("BluePigBankresized.jpg");
       Picture bluePig = new Picture("PigBlue.jpg");
       Picture combinedFirstImg = hidePicture(bluePiggyBank, bluePig);
       combinedFirstImg.write("combImg1.png");

       //revaling the combined image 
       Picture revealFirstImg = revealPicture(combinedFirstImg);
       revealFirstImg.write("FirstImgProd.png");
       revealPicture(revealFirstImg);

       //my second hidden picture example
       Picture AvocadoOnBed = new Picture("ChillAvocadoOnBed.jpg");
       Picture Avocado = new Picture("UglyAvocado.jpg");
       Picture combinedSecImg = hidePicture(AvocadoOnBed, Avocado);
       combinedSecImg.write("combImg2.png");

       //revealing the second combined Image
       Picture revealSecImg = revealPicture(combinedSecImg);
       revealSecImg.write("SecondImgProd.png");
       revealPicture(revealSecImg);

        //This is me calling the filter method, and implementing it.
        Picture caterpillar = new Picture("caterpillar.jpg");
        Picture filter = filteredPicture(caterpillar);
        filter.write("filteredPic.png");
       }

       // my filter for the two images

       public static Picture filteredPicture(Picture picture){
        Picture blueFilt = new Picture(picture);
        Pixel[][] pix = blueFilt.getPixels2D();

        for(int r = 0; r < pix.length; r++){
            for(int c = 0; c < pix[0].length;c++){
                int a = 0;
                pix[r][c].setGreen(a);
                pix[r][c].setRed(a);
            }
        }
        return blueFilt;
       }

    
    
    /**
    * Clear the lower (rightmost) two bits in a pixel.
    */
    public static void clearLow( Pixel p )
    {
        /* To be implemented */
        Color oldColor = p.getColor();
       
        int r = oldColor.getRed();
        int g = oldColor.getBlue();
        int b = oldColor.getGreen();

        // cahnging the bits to take away the last two

        r/=16; 
        r*=16;

        g/=16;
        g*=16;

        b/=16;
        b*=16;

        //setting 

        p.setColor(new Color(r,g,b));


    }
    
    /**
     * Returns a new Picture object with the lowest two bits of each pixel cleared.
     */
    public static Picture testClearLow(Picture p)
    {
        /* To be implemented */
        Picture p2 = new Picture(p);

        Pixel[][] pix = p2.getPixels2D();

        //write loops to go to every pixel in the pix
        for (int r = 0; r < pix.length; r++)    //Loops through all the rows
        {
            for (int c = 0; c < pix[0].length; c++)   //loops through the columns
            {
                    Pixel x = pix[r][c];
                clearLow(x);
            }
        }  
        return p2;
    }
    
    public static Picture revealPicture(Picture p){
       Picture copy = new Picture(p);
       Pixel[][] pix_new = copy.getPixels2D();
       Pixel[][] pix_old = p.getPixels2D();
       for(int i = 0; i<pix_new.length; i++){
           for(int j = 0; j<pix_new[0].length; j++){
               Pixel x = pix_new[i][j];


               //get color of old picture
               Pixel x_old = pix_old[i][j];
               Color c_old = x_old.getColor();

               


               //calculate new color
               int oldR = c_old.getRed();
               int oldG = c_old.getGreen();
               int oldB = c_old.getBlue();

                
                //System.out.println("For the first pixel:");
                //System.out.println("R=" + oldR);


               int newR = oldR % 4 * 64;
               int newG = oldG % 4 * 64;
               int newB = oldB % 4 * 64;

               //System.out.println("So that changes to" + newR);


               //update Pixel x
               x.setRed(newR);
               x.setBlue(newB);
               x.setGreen(newG);

               //break;
           }
           //break;
       }
       return copy;
   }

   public static boolean canHide (Picture secret, Picture source)
   {
        if(source.getWidth() == secret.getWidth() && source.getHeight() == secret.getHeight())
        {
            return true;
        }
        return false;
   }
   public static Picture hidePicture(Picture source, Picture secret){

    Picture combined = new Picture(source);
    Pixel[][] newHiddenPicture = secret.getPixels2D();
    Pixel[][] oldHiddenPicture = source.getPixels2D();
    // another array for the secret]
    Pixel[][] newHiddenPic = combined.getPixels2D();


    for(int r = 0; r < newHiddenPic.length; r++)
    {
        for(int c = 0; c < newHiddenPicture[0].length; c++)
        {
            Pixel x = newHiddenPic[r][c];
            clearLow(x);

            Pixel y = oldHiddenPicture[r][c];

            Pixel secretPixel = newHiddenPicture[r][c];
            Color colorOld = y.getColor();
            
            int oldColorR = colorOld.getRed();
            int oldColorG = colorOld.getGreen();
            int oldColorB = colorOld.getBlue();

            int oldColorR_secret = y.getRed();
            int oldColorG_secret = y.getGreen();
            int oldColorB_secret = y.getBlue();

            //clear lowest two bits of rgb for the source picture
            int newColorRed = oldColorR / 4 * 4; 
            int newColorGreen = oldColorG / 4 * 4; 
            int newColorBlue = oldColorB / 4 * 4; 

            //get the top two bits for RGB for the source picture
            int newColorR_secret = secretPixel.getRed() / 64;
            int newColorG_secret = secretPixel.getGreen() / 64;
            int newColorB_secret = secretPixel.getBlue() / 64;

            int newRedMod = newColorRed + newColorR_secret;
            int newGreenMod = newColorGreen + newColorG_secret;
            int newBlueMod = newColorBlue + newColorB_secret;
            
            x.setRed(newRedMod);
            x.setGreen(newGreenMod);
            x.setBlue(newBlueMod);

            




            
            
                                                                                //clear all low 2 bits of old hidden picture
                                                                                //two most significant bits from secrettop 2 bits
                                                                                //once you have the bits needed. combine them
        }
    }
    return combined;




     /*old_r_cleared = (old_r / 4) * 4;
     old_r_cleared = (old_g / 4) * 4;
     old_r_cleared = (old_g / 4) * 4;

    int secret_r_top2bits = secret_r / 64;
    int secret_g_top2bits = secret_g/ 64;
    int secret_b_top2bits = secret_b / 64;

    int new_r = old_r_cleared + secret_r_top2bits;
    int new_g = old_g_cleared + secret_g_top2bits;
    int new_b = old_b_cleared + secret_b_top2bits;
    return combined;*/
   }
   

    
}
