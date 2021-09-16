import javax.swing.*;

import java.awt.Dimension;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;



/**
 * @author François Barthélemy
 * 
 * Classe dont les instances sont des fenêtres affichées à l'écran
 * contenant une image.
 *
 */
public class AfficheImage extends JFrame{
  
  private static final long serialVersionUID = 1L;
  private JPanel jp;
  private BufferedImage image;
  
  /**
   * @param pixels l'image à afficher
   */
  public AfficheImage(String filename){
    try {
      image = ImageIO.read(new File(filename));
    }
    catch (IOException e) {
      throw new ImageNonTrouvee();
    }
    jp = new ImagePanel(image);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jp.setPreferredSize(new Dimension(image.getWidth(),image.getHeight()));
    this.add(jp);
    this.pack();
    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    /*JFrame jframe = this;
     this.addWindowListener(new WindowAdapter() {
     public void windowClosing(WindowEvent we) {
     System.out.println("closing");
     jframe.dispose();
     }
     });*/
    
    this.setVisible(true);
  }
  /**
   * Méthode servant à afficher une nouvelle image dans la feneêtre.
   * 
   * Cette image doit avoir les mêmes dimensions que celle donnée en 
   * paramètre au constructeur lors de la création de l'objet sur lequel
   * la méthode est appelée.
   * @param pixels l'image à afficher
   * @throws IndexOutOfBoundsException parfois levée si pixels n'a pas
   * la même dimension que l'image précédemment affichée par l'objet.
   */
 
  /** Méthode qui ferme la fenêtre.
    * 
    */
  //public void fermer(){
  // this.dispose();
  //}
  class ImagePanel extends JPanel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Image image;
    public ImagePanel(Image i) {
      image=i;
    }
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(image, 0, 0, null); 
    }
    
  }
  public static AfficheImage celleCi;
  public static void afficheImage(String filename){
    if (celleCi != null)
      fermeImage();
    celleCi = new AfficheImage(filename);
  }
  public static void fermeImage(){
    celleCi.dispose();
  }
  
  public static void main(String[] args){
    AfficheImage.afficheImage("bebert.png");
    Terminal.lireString();
    AfficheImage.fermeImage();
  }
}
class ImageNonTrouvee extends RuntimeException{}