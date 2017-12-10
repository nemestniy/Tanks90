import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResourseLoader {
    public static final String PATH = "res/";

    public static BufferedImage leadImage(String fileName){
        BufferedImage image = null;

        try{
            image = ImageIO.read(new File(PATH + fileName));            //load image file
        } catch (IOException e){
            e.printStackTrace();
        }

        return image;
    }
}
