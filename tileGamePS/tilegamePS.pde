PImage bg1, bg2, bg3, bg4, playerImage;
Table map1, map2, map3, map4, editedMap;
PImage[] tiles, editorTiles;
boolean isMouseSpriteActive = false, saveMap = false;

final static float moveSpeed = 5;
final static float jumpSpeed = 10;
final static float gravity = 0.4;
final static int spriteSize = 48;
final static int tileCount = 49;

final static float rightMargin = 300;
final static float leftMargin = 50;
final static float verticalMargin = 40;

final static int neutralFacing = 0;
final static int rightFacing = 1;
final static int leftFacing = 2;

float viewX = 0, viewY = 0;

Player player, player1, player2, player3, player4, editPlayer;
Sprite mouseSprite;

ArrayList<Sprite> firstMapPlatforms, mapPlatforms1, mapPlatforms2, mapPlatforms3, mapPlatforms4, editedMapPlatforms;
ArrayList<Sprite> coins, coins1, coins2, coins3, coins4, editCoins;
ArrayList<Sprite> iterator, editorTileSprites, gridSprites;

int currentLevel = 0, coinCount = 0, matrix = 0, lineWidth = 0;

void setup() {
  size(640, 480); //640 480
  imageMode(CENTER);
  frameRate(40);

  bg1 = loadImage("BG1.png");
  bg2 = loadImage("BG2.png");
  bg3 = loadImage("BG3.png");
  bg4 = loadImage("BG4.png");
  playerImage = loadImage("Player\\standingRight.png");

  mapPlatforms1 = new ArrayList<Sprite>();
  mapPlatforms2 = new ArrayList<Sprite>();
  mapPlatforms3 = new ArrayList<Sprite>();
  mapPlatforms4 = new ArrayList<Sprite>();
  editedMapPlatforms = new ArrayList<Sprite>();

  coins1 = new ArrayList<Sprite>();
  coins2 = new ArrayList<Sprite>();
  coins3 = new ArrayList<Sprite>();
  coins4 = new ArrayList<Sprite>();
  editCoins = new ArrayList<Sprite>();

  iterator = new ArrayList<Sprite>();
  editorTileSprites = new ArrayList<Sprite>();
  gridSprites = new ArrayList<Sprite>();

  editedMap = new Table();
  mouseSprite = new Sprite(mouseX, mouseY, 10, 10);

  tiles = getTiles("tileset.png");
  editorTiles = getTiles("tileset.png");
  resizeTiles(editorTiles, 3);
  initializeEditorTiles(editorTiles, editorTileSprites);
  initializeGridSprites(gridSprites);
  initializeMapTable(editedMap, 40, 10);

  createPlatforms(map1, "gamemap1.csv", tiles, mapPlatforms1, coins1);
  createPlatforms(map2, "gamemap2.csv", tiles, mapPlatforms2, coins2);
  createPlatforms(map3, "gamemap3.csv", tiles, mapPlatforms3, coins3);
  createPlatforms(map4, "gamemap4.csv", tiles, mapPlatforms4, coins4);

  player1 = new Player(playerImage, mapPlatforms1);
  player2 = new Player(playerImage, mapPlatforms2);
  player3 = new Player(playerImage, mapPlatforms3);
  player4 = new Player(playerImage, mapPlatforms4);

  player = player1;
  firstMapPlatforms = mapPlatforms1;
  coins = coins1;

  player.centerX = 40;
  player.centerY = 384;
}

void draw() {
  background(200, 200, 200);

  //Saving the default camera position
  if (matrix != 1) {
    pushMatrix();
    matrix = 1;
  }

  //Menu
  if (currentLevel == 0) {
    textSize(40);
    fill(0, 0, 0);
    text("Press A to open editor \nPress S to start the game", 100, 100);
  }

  //Editor
  if (currentLevel == -1) {
    textSize(20);
    fill(0, 0, 0);
    text("Editor mode", 10, 20);
    text("Press M to save map", 10, 460);

    //Display grid
    lineWidth = 50;
    for (int i = 0; i < 11; i++) {
      line(0, lineWidth, 640, lineWidth);
      lineWidth += 16;
    }
    lineWidth = 0;
    for (int i = 0; i < 40; i++) {
      line(lineWidth, 50, lineWidth, 210);
      lineWidth += 16;
    }

    //Display image sprites and resolve editing
    for (Sprite editorTile : editorTileSprites) {
      editorTile.display();

      //If all conditions true, the mouseSprite takes the image and index of pressed sprite
      if (mousePressed && (mouseButton == LEFT) && checkCollision(mouseSprite, editorTile) && !isMouseSpriteActive) {
        mouseSprite.image = editorTile.image;
        mouseSprite.index = editorTile.index;
        isMouseSpriteActive = true;
      }
      if (!mousePressed)
        isMouseSpriteActive = false;
    }

    //Display gridSprites and check for deletion by mouse
    for (Sprite gridSprite : gridSprites) {
      if (mousePressed && (mouseButton == RIGHT) && checkCollision(mouseSprite, gridSprite))
        gridSprite.index = 0;

      if (gridSprite.index > 0)
        gridSprite.display();
    }

    //Display and update mouse sprite position
    if (isMouseSpriteActive)
      mouseSprite.display();

    mouseSprite.updateMouse();

    //If player wants to save the edited map it is stored in .csv file and player starts playing
    if (saveMap) {
      saveMapData(editedMap, gridSprites);
      currentLevel = -2;

      createPlatforms(editedMap, "editedmap.csv", tiles, editedMapPlatforms, editCoins);
      Player editPlayer = new Player(playerImage, editedMapPlatforms);

      player = editPlayer;
      firstMapPlatforms = editedMapPlatforms;
      coins = editCoins;

      player.centerX = 40;
      player.centerY = 384;
    }
  }

  //Condition if player starts the game
  if (currentLevel > 0 && currentLevel < 5 || currentLevel == -2) {
    background(51, 204, 255);
    scroll();

    //Edited level of the game
    if (currentLevel == -2) { 
      image(bg1, viewX + width / 2, viewY + height / 2);
      resolvePlatformCollisions(player, firstMapPlatforms);

      if (player.getRight() >= 40 * spriteSize - spriteSize * 3) {
        currentLevel = 1;
        player = player1;
        firstMapPlatforms = mapPlatforms1;
        coins = coins1;
        player.centerX = 40;
        player.centerY = 384;
      }
    }

    //First level of the game
    if (currentLevel == 1) { 
      image(bg1, viewX + width / 2, viewY + height / 2);
      resolvePlatformCollisions(player, firstMapPlatforms);

      if (player.getRight() >= 50 * spriteSize - spriteSize * 3) {
        currentLevel = 2;
        player = player2;
        firstMapPlatforms = mapPlatforms2;
        coins = coins2;
        player.centerX = 40;
        player.centerY = 384;
      }
    }

    //Second level of the game
    if (currentLevel == 2) { 
      image(bg2, viewX + width / 2, viewY + height / 2);
      resolvePlatformCollisions(player, firstMapPlatforms);

      if (player.getRight() >= 70 * spriteSize - spriteSize * 3) {
        currentLevel = 3;
        player = player3;
        firstMapPlatforms = mapPlatforms3;
        coins = coins3;
        player.centerX = 40;
        player.centerY = 384;
      }
    }

    //Third level of the game
    if (currentLevel == 3) { 
      image(bg3, viewX + width / 2, viewY + height / 2);
      resolvePlatformCollisions(player, firstMapPlatforms);

      if (player.getRight() >= 70 * spriteSize - spriteSize * 3) {
        currentLevel = 4;
        player = player4;
        firstMapPlatforms = mapPlatforms4;
        coins = coins4;
        player.centerX = 40;
        player.centerY = 384;
      }
    }

    //Fourth level of the game
    if (currentLevel == 4) { 
      image(bg4, viewX + width / 2, viewY + height / 2);
      resolvePlatformCollisions(player, firstMapPlatforms);

      if (player.getRight() >= 69 * spriteSize - spriteSize * 3) {
        currentLevel = 5;
      }
    }

    //Display map blocks
    for (Sprite tileBlock : firstMapPlatforms) {
      tileBlock.display();
    }

    //Display map coins and detect which of the coins the Player collected
    for (Sprite mapCoins : coins) {
      if (checkCollision(player, mapCoins)) {
        iterator.add(mapCoins);
        coinCount++;
      }

      mapCoins.display();
      ((AnimatedSprite)mapCoins).updateAnimation();
    }

    //Delete collected coins
    for (Sprite toDelete : iterator) {
      coins.remove(toDelete);
    }

    //Display player 
    player.updateAnimation();
    player.display();

    //Display collected coins
    textSize(32);
    fill(255, 130, 40);
    text("coins:" + coinCount, viewX + 20, viewY + 40);

    //If player falls off the map put him to the start of the level
    if (player.centerY > 480) {
      player.centerY = 384;
      player.centerX = 40;
    }
  }

  //End message
  if (currentLevel == 5) {
    if (matrix == 1) {
      popMatrix();
      matrix = 0;
    }
    textSize(40);
    fill(0, 0, 0);
    text("Game finished :^) \nTotal coins: " + coinCount, 100, 100);
  }
}

void keyPressed() {
  if (keyCode == RIGHT) {
    player.changeX = moveSpeed;
  } else if (keyCode == LEFT) {
    player.changeX = -moveSpeed;
  } else if (keyCode == UP && isOnPlatform(player, firstMapPlatforms)) {
    player.changeY = -jumpSpeed;
  }

  if (keyCode == 'S')
    currentLevel = 1;
  if (keyCode == 'A')
    currentLevel = -1;
  if (keyCode == 'M')
    saveMap = true;
}

void keyReleased() {
  if (keyCode == RIGHT) {
    player.changeX = 0;
  } else if (keyCode == LEFT) {
    player.changeX = 0;
  }
}

void mouseReleased() {
  //Checking if when the mouse is released, if it has an active mouse sprite, if it is active it checks if it collides with a grid sprite
  for (Sprite gridSprite : gridSprites) {
    if (isMouseSpriteActive && checkCollision(mouseSprite, gridSprite)) {
      gridSprite.index = mouseSprite.index;
      gridSprite.image = mouseSprite.image;
    }
  }
}

public void scroll() {
  float rightBoundary = viewX + width - rightMargin;
  if (player.getRight() > rightBoundary) {
    viewX += player.getRight() - rightBoundary;
  }

  float leftBoundary = viewX + leftMargin;
  if (player.getRight() < leftBoundary) {
    viewX -= leftBoundary - player.getLeft();
  }

  float bottomBoundary = viewY + height + verticalMargin;
  if (player.getBottom() > bottomBoundary) {
    viewY += player.getBottom() - bottomBoundary;
  }

  float topBoundary = viewY + verticalMargin;
  if (player.getTop() < topBoundary) {
    viewY -= topBoundary - player.getTop();
  }

  translate(-viewX, -viewY);
}

//Function to check if object is on a platform
public boolean isOnPlatform(Sprite mainObject, ArrayList<Sprite> walls) {
  mainObject.centerY += 5;
  ArrayList<Sprite> collisionList = checkCollisionList(mainObject, walls);
  mainObject.centerY -= 5;

  if (collisionList.size() > 0)
    return true;
  else
    return false;
}

//Checking collisions between two sprites
public boolean checkCollision(Sprite s1, Sprite s2) {
  boolean noXCollision = s1.getRight() <= s2.getLeft() || s1. getLeft() >= s2.getRight();
  boolean noYCollision = s1.getBottom() <= s2.getTop() || s1. getTop() >= s2.getBottom();

  if (noXCollision || noYCollision)
    return false;
  else
    return true;
}

//Function that returns an arraylist with all objects that collide with the main object
public ArrayList<Sprite> checkCollisionList(Sprite mainObject, ArrayList<Sprite> list) {
  ArrayList<Sprite> collisionList = new ArrayList<Sprite>();

  for (Sprite object : list) {
    if (checkCollision(mainObject, object))
      collisionList.add(object);
  }

  return collisionList;
}

//Adds physics and resolves player collisions with objects
public void resolvePlatformCollisions(Sprite mainObject, ArrayList<Sprite> walls) {
  //Add physics
  mainObject.changeY += gravity;

  //Move in the y-direction and resolve collisions
  mainObject.centerY += mainObject.changeY;
  ArrayList<Sprite> collisionList = checkCollisionList(mainObject, walls);

  if (collisionList.size() > 0) {
    Sprite collided = collisionList.get(0);

    if (mainObject.changeY > 0) {
      mainObject.setBottom(collided.getTop());
    } else if (mainObject.changeY < 0) {
      mainObject.setTop(collided.getBottom());
    }
    mainObject.changeY = 0;
  }

  //Move in the x-direction and resolve collisions
  mainObject.centerX += mainObject.changeX;
  collisionList = checkCollisionList(mainObject, walls);

  if (collisionList.size() > 0) {
    Sprite collided = collisionList.get(0);

    if (mainObject.changeX > 0) {
      mainObject.setRight(collided.getLeft());
    } else if (mainObject.changeX < 0) {
      mainObject.setLeft(collided.getRight());
    }
    mainObject.changeX = 0;
  }

  return;
}

//Process tile textures and put them in an image array
public PImage[] getTiles(String filename) {
  PImage tileSheet = loadImage(filename);

  int tileRows = tileSheet.height / spriteSize;
  int tileColumns = tileSheet.width / spriteSize;
  int tileCounter = 0;

  PImage[] tileSheetArray = new PImage[tileRows * tileColumns];

  for (int i = 0; i < tileRows; i++) {
    for (int z = 0; z < tileColumns; z++) {
      tileSheetArray[tileCounter] = tileSheet.get(z * spriteSize, i * spriteSize, spriteSize, spriteSize);
      tileCounter++;
    }
  }

  return tileSheetArray;
}

//Function to resize tiles
public void resizeTiles(PImage[] tileArray, int scale) {
  for (PImage tileImage : tileArray) {
    tileImage.resize(spriteSize / scale, spriteSize / scale);
  }
}

//Create arrayList of tile image sprite at exact position in editor window
public void initializeEditorTiles(PImage[] tileArray, ArrayList<Sprite> editorTiles) {
  int index = 0;
  int spriteWidth = 16;
  float x = 500, y = 250;

  for (PImage tileImage : tileArray) {
    Sprite tileBlock = new Sprite(tileImage, index + 1);
    tileBlock.centerX = spriteWidth / 2 + x;
    tileBlock.centerY = spriteWidth / 2 + y;
    editorTiles.add(tileBlock);

    x += spriteWidth;
    index++;
    if (index % 8 == 0) {
      y += spriteWidth;
      x -= spriteWidth * 8;
    }
  }
}

//Create arrayList of empty sprites at exact position on the grid
public void initializeGridSprites(ArrayList<Sprite> gridSprites) {
  int spriteWidth = 16;
  float x = 0, y = 50;

  for (int i = 1; i <= 10; i++) {
    x = 0;
    for (int j = 1; j <= 40; j++) {
      Sprite gridSprite = new Sprite(x, y);
      gridSprites.add(gridSprite);
      x += spriteWidth;
    }
    y += spriteWidth;
  }
}

//Create edited map table and initialize it to 0
public void initializeMapTable(Table editedMap, int columnCount, int rowCount) {
  TableRow row;

  for (int i = 0; i < columnCount; i++)
    editedMap.addColumn();

  for (int i = 0; i < rowCount; i++) {
    row = editedMap.addRow();
    for (int j = 0; j < columnCount; j++)
      row.setInt(j, 0);
  }
}

//Store edited map data to csv file
public void saveMapData(Table editedMap, ArrayList<Sprite> gridSprites) {
  int index = 0;
  int columns = editedMap.getColumnCount();
  int rows = editedMap.getRowCount();
  TableRow row;
  Sprite gridSprite;

  for (int i = 0; i < rows; i++) {
    row = editedMap.getRow(i);
    for (int j = 0; j < columns; j++) {
      gridSprite = gridSprites.get(index++);
      row.setInt(j, gridSprite.index);
    }
  }

  saveTable(editedMap, "Data\\editedmap.csv");
}

//Load map csv data into an array
//And create Sprite objects based on the map block index
public void createPlatforms(Table gameMap, String mapName, PImage[] tiles, ArrayList<Sprite> mapPlatforms, ArrayList<Sprite> coins) {
  gameMap = loadTable(mapName);

  int columns = gameMap.getColumnCount();
  int rows = gameMap.getRowCount();

  int[][] array = new int[rows][columns];

  TableRow row;

  for (int z = 0; z < rows; z++) {
    row = gameMap.getRow(z);
    for (int i = 0; i < columns; i++) {
      array[z][i] = row.getInt(i);
      for (int a = 0; a < tileCount; a++) {
        if (array[z][i] == a + 1) {
          if (array[z][i] == 48) {
            Coin newCoin = new Coin(tiles[47]);
            newCoin.centerX = spriteSize / 2 + i * spriteSize;
            newCoin.centerY = spriteSize / 2 + z * spriteSize;
            coins.add(newCoin);
            continue;
          }
          Sprite tileBlock = new Sprite(tiles[a]);
          tileBlock.centerX = spriteSize / 2 + i * spriteSize;
          tileBlock.centerY = spriteSize / 2 + z * spriteSize;
          mapPlatforms.add(tileBlock);
        }
      }
    }
  }

  return;
}
