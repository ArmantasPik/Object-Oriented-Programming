/**
  This class implements a player animated sprite
  @author Armantas Pikšrys PS3
 */
public class Player extends AnimatedSprite {

  boolean onPlatform, inPlace;
  PImage[] standingLeft;
  PImage[] standingRight;
  PImage[] jumpLeft;
  PImage[] jumpRight;
  ArrayList<Sprite> currentPlatforms;
  
  /**
    This constructs a player sprite with a specified player image and an array list of specified sprite platforms
    @param img Initial image of the player
    @param platforms ArrayList of sprite objects that are considered to be platforms
  */
  public Player(PImage img, ArrayList<Sprite> platforms) {
    super(img);
    currentPlatforms = platforms;
    direction = rightFacing;
    onPlatform = true;
    inPlace = true;
    standingLeft = new PImage[1];
    standingLeft[0] = loadImage("Player\\standingLeft.png");
    standingRight = new PImage[1];
    standingRight[0] = loadImage("Player\\standingRight.png");
    jumpLeft = new PImage[1];
    jumpLeft[0] = loadImage("Player\\jumpLeft.png");
    jumpRight = new PImage[1];
    jumpRight[0] = loadImage("Player\\jumpRight.png");
    moveLeft = new PImage[2];
    moveLeft[0] = loadImage("Player\\runLeft1.png");
    moveLeft[1] = loadImage("Player\\runLeft2.png");
    moveRight = new PImage[2];
    moveRight[0] = loadImage("Player\\runRight1.png");
    moveRight[1] = loadImage("Player\\runRight2.png");
    currentImages = standingRight;
  }
  
  /**
    This updates the animation of the player
  */
  @Override
    public void updateAnimation() {
    onPlatform = isOnPlatform(this, currentPlatforms);
    inPlace = changeX == 0 && changeY == 0;
    super.updateAnimation();
  }
  
  /**
    This selects the direction that this player object is facing
  */
  @Override
    public void selectDirection() {
    if (changeX > 0)
      direction = rightFacing;
    else if (changeX < 0)
      direction = leftFacing;
  }
  
  /**
    This selects the current images to display on the screen, based on the position of the player
  */
  @Override
    public void selectCurrentImages() {
    if (direction == rightFacing) {
      if (inPlace) {
        currentImages = standingRight;
      } else if (!onPlatform) {
        currentImages = jumpRight;
      } else if (!inPlace && onPlatform)
        currentImages = moveRight;
    } else if (direction == leftFacing) {
      if (inPlace) {
        currentImages = standingLeft;
      } else if (!onPlatform) {
        currentImages = jumpLeft;
      } else if (!inPlace && onPlatform)
        currentImages = moveLeft;
    }
  }
}
