/**
  This class implements a an animated coin sprite
  @author Armantas Pikšrys PS3
 */
public class Coin extends AnimatedSprite {
  
  /**
    This constructs an animated coin sprite with an initial image
    @param img Initial image of the coin 
  */
  public Coin(PImage img) {
    super(img);
    standNeutral = new PImage[4];
    standNeutral[0] = loadImage("coin\\coin1.png");
    standNeutral[1] = loadImage("coin\\coin2.png");
    standNeutral[2] = loadImage("coin\\coin3.png");
    standNeutral[3] = loadImage("coin\\coin4.png");
    currentImages = standNeutral;
  }
}
