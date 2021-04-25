/**
  This class implements a sprite object
  @author Armantas Pikšrys PS3
 */
public class Sprite {
  int index;
  PImage image;
  float scale;
  float centerX, centerY;
  float changeX, changeY;
  float spriteWidth, spriteHeight;
  
  /**
    This constructs a sprite with a specified sprite image and the initial coordinates of the sprite on the screen
    @param filename filename of the sprite image
    @param x x-coordinate of the sprite
    @param y y-coordinate of the sprite
  */
  public Sprite(String filename, float x, float y) {
    image = loadImage(filename);
    spriteWidth = image.width;
    spriteHeight = image.height;
    centerX = x;
    centerY = y;
    changeX = 0;
    changeY = 0;
  }
  
  /**
    This constructs a sprite with a specified sprite image with initial position of x=0 y=0
    @param filename filename of the sprite image
  */
  public Sprite(String filename) {
    this(filename, 0, 0);
  }
  
  /**
    This constructs a sprite with a specified sprite image with initial position of x=0 y=0
    @param img preloaded image of the sprite
  */
  public Sprite(PImage img) {
    image = img;
    spriteWidth = image.width;
    spriteHeight = image.height;
    centerX = 0;
    centerY = 0;
    changeX = 0;
    changeY = 0;
  }
  
  /**
    This constructs a sprite with a specified sprite image with an index
    @param img preloaded image of the sprite
    @param indx index of this sprite
  */
  public Sprite(PImage img, int indx) {
    index = indx;
    image = img;
    spriteWidth = image.width;
    spriteHeight = image.height;
    centerX = 0;
    centerY = 0;
    changeX = 0;
    changeY = 0;
  }
  
  /**
    This constructs a sprite with initial position of x and y
    @param x x-coordinate of the sprite
    @param y y-coordinate of the sprite
  */
  public Sprite(float x, float y) {
    spriteWidth = 16;
    spriteHeight = 16;
    centerX = x + 8;
    centerY = y + 8;
    changeX = 0;
    changeY = 0;
  }
  
  /**
    This constructs a sprite with initial position of x and y and a specified width and height
    @param x x-coordinate of the sprite
    @param y y-coordinate of the sprite
    @param givenWidth width of the sprite
    @param givenHeight height of the sprite
  */
  public Sprite(float x, float y, float givenWidth, float givenHeight) {
    spriteWidth = givenWidth;
    spriteHeight = givenHeight;
    centerX = x;
    centerY = y;
    changeX = 0;
    changeY = 0;
  }

  //Functions for sprite collision detection, return current sprite position coordinate
  
  /**
    This return the current x coordinate of the sprites left side
    @return x coordinate
  */
  public float getLeft() {
    return centerX - spriteWidth / 2;
  }
  
  /**
    This return the current x coordinate of the sprites right side
    @return x coordinate
  */
  public float getRight() {
    return centerX + spriteWidth / 2;
  }
  
  /**
    This return the current y coordinate of the sprites top
    @return y coordinate
  */
  public float getTop() {
    return centerY - spriteHeight / 2;
  }

  /**
    This return the current y coordinate of the sprites bottom
    @return y coordinate
  */
  public float getBottom() {
    return centerY + spriteHeight / 2;
  }

  //Functions for sprite collision detection, set sprite center based on passed value
  
  /**
    This sets the x coordinate of the sprite based on passed value
    @param newLeft new x coordinate of the sprites left side
  */
  public void setLeft(float newLeft) {
    centerX = newLeft + spriteWidth / 2;
  }

  /**
    This sets the x coordinate of the sprite based on passed value
    @param newRight new x coordinate of the sprites right side
  */
  public void setRight(float newRight) {
    centerX = newRight - spriteWidth / 2;
  }

  /**
    This sets the y coordinate of the sprite based on passed value
    @param newTop new y coordinate of the sprites top
  */
  public void setTop(float newTop) {
    centerY = newTop + spriteHeight / 2;
  }

  /**
    This sets the y coordinate of the sprite based on passed value
    @param newBottom new y coordinate of the sprites bottom
  */
  public void setBottom(float newBottom) {
    centerY = newBottom - spriteHeight / 2;
  }


  /**
    Display sprite
  */
  public void display() {
    image(image, centerX, centerY);
  }

  /**
    Update sprite position
  */
  public void update() {
    centerX += changeX;
    centerY += changeY;
  }

  /**
    Method to update the position of the sprite relative to the position of the mouse
  */
  public void updateMouse() {
    centerX = mouseX;
    centerY = mouseY;
  }
}
