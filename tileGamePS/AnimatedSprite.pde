/**
  This class implements a an animated sprite
  @author Armantas Pikšrys PS3
 */
public class AnimatedSprite extends Sprite {
  PImage[] currentImages;
  PImage[] standNeutral;
  PImage[] moveLeft;
  PImage[] moveRight;
  int direction;
  int index;
  int frame;
  
  /**
    This constructs an animated sprite with an initial image
    @param img Initial image of the sprite
  */
  public AnimatedSprite(PImage img) {
    super(img);
    direction = neutralFacing;
    index = 0;
    frame = 0;
  }
  
  /**
    This updates the animation of the sprite every 5 frames
  */
  public void updateAnimation() {
    frame++;
    if (frame % 5 == 0) {
      selectDirection();
      selectCurrentImages();
      advanceToNextImage();
    }
  }
  
  /**
    This selects the direction that this animated sprite object is facing
  */
  public void selectDirection() {
    if (changeX > 0)
      direction = rightFacing;
    else if (changeX < 0)
      direction = leftFacing;
    else
      direction = neutralFacing;
  }
  
  /**
    This selects the current images to display on the screen, based on the position of the animated sprite
  */
  public void selectCurrentImages() {
    if (direction == rightFacing)
      currentImages = moveRight;
    else if (direction == leftFacing)
      currentImages = moveLeft;
    else
      currentImages = standNeutral;
  }
  
  /**
    This advances to the next image of the animated sprite that is stored in the current sprite images array
  */
  public void advanceToNextImage() {
    index++;
    if (index >= currentImages.length) {
      index = 0;
    }

    image = currentImages[index];
  }
}
