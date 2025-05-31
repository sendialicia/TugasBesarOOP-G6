package main;

import farmTile.HarvestableTile;
import farmTile.PlantedTile;
import farmTile.TileLocation;
import farmTile.TileObject;
import items.crops.Crops;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import tile.TileManager;


public class KeyHandler implements KeyListener{

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, minusPressed, plusPressed, iPressed;
    
    // DEBUG
    boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // TITLE STATE
        if(gp.gameState == gp.titleState) {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
    
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }

             if(code == KeyEvent.VK_ENTER) {

                if(gp.ui.commandNum == 0) {
                    gp.gameState = gp.farmNameInputState;
                }
                if(gp.ui.commandNum == 1) {
                    // Add later
                }
                if(gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        }

        // FARM NAME STATE
        else if (gp.gameState == gp.farmNameInputState) {
            if (code == KeyEvent.VK_BACK_SPACE) {
                if (gp.ui.farmName.length() > 0) {
                    gp.ui.farmName.deleteCharAt(gp.ui.farmName.length() - 1);
                }
            } else if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.farmName.length() > 0) {
                    gp.player.setFarmName(gp.ui.farmName.toString());
                    gp.gameState = gp.nameInputState;
                    gp.ui.commandNum = 0; // reset to choose male/female
                    code = KeyEvent.VK_UNDEFINED; // Prevent further processing of Enter key
                }
            } else {
                char c = e.getKeyChar();
                if (Character.isLetterOrDigit(c) && gp.ui.farmName.length() < 9) {
                    gp.ui.farmName.append(c);
                }
            }
        }

        // NAME STATE
        else if (gp.gameState == gp.nameInputState) {
            if (code == KeyEvent.VK_BACK_SPACE) {
                if (gp.ui.playerName.length() > 0) {
                    gp.ui.playerName.deleteCharAt(gp.ui.playerName.length() - 1);
                }
            } else if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.playerName.length() > 0) {
                    gp.player.setName(gp.ui.playerName.toString());
                    gp.gameState = gp.genderInputState;
                    gp.ui.commandNum = 0; // reset to choose male/female
                }
            } else {
                char c = e.getKeyChar();
                if (Character.isLetterOrDigit(c) && gp.ui.playerName.length() < 9) {
                    gp.ui.playerName.append(c);
                }
            }
        }

        // GENDER STATE
        else if (gp.gameState == gp.genderInputState) {
            if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
                gp.ui.commandNum = 0; // Male
            } else if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
                gp.ui.commandNum = 1; // Female
            } else if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.player.setGender("Male");
                } else if (gp.ui.commandNum == 1) {
                    gp.player.setGender("Female");
                }

                gp.gameState = gp.playState;
                gp.playMusic(0);
            }
        }

        // PLAY STATE
        else if(gp.gameState == gp.playState) {

            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                upPressed = true;
            }
    
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
    
            if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
    
            if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
    
            if(code == KeyEvent.VK_R) {
                if (gp.currentMap == 0) {
                    gp.player.teleport();
                    gp.currentMap = 1;
                    gp.maxWorldCol = 70;
                    gp.maxWorldRow = 75;
                    gp.tileM = new TileManager(gp, gp.maxWorldCol, gp.maxWorldRow);
                    gp.tileM.loadMap("/maps/world.txt", 1);
                    gp.gameState = gp.worldMapState;
                } else if (gp.currentMap == 1) {
                    gp.player.teleport();
                    gp.currentMap = 0;
                    gp.maxWorldCol = 51;
                    gp.maxWorldRow = 51;
                    gp.tileM = new TileManager(gp, gp.maxWorldCol, gp.maxWorldRow);
                    gp.tileM.loadMap("/maps/farm.txt", 0);
                }
                e.consume();
            }

            if(code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.pauseState;
            }

            if(code == KeyEvent.VK_E) {
                gp.gameState = gp.viewAttributeState;
                e.consume();
            }

            if(code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
            
            if (code == KeyEvent.VK_I){
                iPressed = true;
            }

            if(code == KeyEvent.VK_Z){
                int pixelX = gp.player.worldX + gp.player.solidArea.x;
                int pixelY = gp.player.worldY + gp.player.solidArea.y;

                int tileX = pixelX / gp.tileSize;
                int tileY = pixelY / gp.tileSize;

                if (gp.tileM.mapTileNum[gp.currentMap][tileX][tileY] != 908) return;

                int worldX = tileX * gp.tileSize;
                int worldY = tileY * gp.tileSize;
                
                TileLocation tileLocation = new TileLocation(worldX, worldY);
                TileObject existing = gp.tiles.get(tileLocation);

                gp.ui.tempWorldX = worldX;
                gp.ui.tempWorldY = worldY;
                gp.ui.tempTileLocation = tileLocation;

                if (existing == null) {
                    TileObject tileObject = new TileObject("Tilled", worldX, worldY);
                    gp.tiles.put(tileLocation, tileObject);
                    gp.player.tilling();
                }
                else if (existing.type.equals("Tilled")) {
                    gp.gameState = gp.plantSeedState;
                }
                else if (existing.type.equals("Planted")){
                    PlantedTile plantedTile = (PlantedTile) existing;
                    if (!plantedTile.isReadyToHarvest()) return;
                }
                else {
                    HarvestableTile harvestableTile = (HarvestableTile) existing;
                    Crops crop = harvestableTile.getCrops();

                    gp.player.addItemToInventory(crop, crop.getHarvestedAmount());
                    gp.player.openInventory();
                    gp.tiles.remove(tileLocation);
                }
            }

            if (code == KeyEvent.VK_X){
                int pixelX = gp.player.worldX + gp.player.solidArea.x;
                int pixelY = gp.player.worldY + gp.player.solidArea.y;

                int tileX = pixelX / gp.tileSize;
                int tileY = pixelY / gp.tileSize;

                int worldX = tileX * gp.tileSize;
                int worldY = tileY * gp.tileSize;
                
                TileLocation tileLocation = new TileLocation(worldX, worldY);
                TileObject existing = gp.tiles.get(tileLocation);

                if (existing != null && existing.type.equals("Tilled")){
                    gp.tiles.remove(tileLocation);
                    gp.player.recoverLand();
                }
            }

            if (code == KeyEvent.VK_C){
                int pixelX = gp.player.worldX + gp.player.solidArea.x;
                int pixelY = gp.player.worldY + gp.player.solidArea.y;

                int tileX = pixelX / gp.tileSize;
                int tileY = pixelY / gp.tileSize;

                int worldX = tileX * gp.tileSize;
                int worldY = tileY * gp.tileSize;
                
                TileLocation tileLocation = new TileLocation(worldX, worldY);
                TileObject existing = gp.tiles.get(tileLocation);

                if (existing != null && existing.type.equals("Planted")){
                    PlantedTile plantedTile = (PlantedTile) existing;
                    plantedTile.water(gp.gameClock.getDate().getOriginDay());
                }
            }
        }

         // WORLD MAP STATE
        else if(gp.gameState == gp.worldMapState) {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                upPressed = true;
            }
    
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
    
            if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
    
            if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
    
            if(code == KeyEvent.VK_R) {
                if (gp.currentMap == 0) {
                    gp.player.teleport();
                    gp.currentMap = 1;
                    gp.maxWorldCol = 70;
                    gp.maxWorldRow = 75;
                    gp.tileM = new TileManager(gp, gp.maxWorldCol, gp.maxWorldRow);
                    gp.tileM.loadMap("/maps/world.txt", 1);
                    gp.gameState = gp.worldMapState;  
                } else if (gp.currentMap == 1) {
                    gp.player.teleport();
                    gp.currentMap = 0;
                    gp.maxWorldCol = 51;
                    gp.maxWorldRow = 51;
                    gp.tileM = new TileManager(gp, gp.maxWorldCol, gp.maxWorldRow);
                    gp.tileM.loadMap("/maps/farm.txt", 0);
                    gp.gameState = gp.playState;
                }
                e.consume();
            }

            if(code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.pauseState;
            }

            if(code == KeyEvent.VK_E) {
                gp.gameState = gp.viewAttributeState;
                e.consume();
            }

            if(code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }

            if (code == KeyEvent.VK_I){
                iPressed = true;
            }
        }

        // PAUSE STATE
        else if(gp.gameState == gp.pauseState) {
            if(code == KeyEvent.VK_ESCAPE) {
                if (gp.currentMap == 0) gp.gameState = gp.playState;
                else gp.gameState = gp.worldMapState;
            }
        }

        // DIALOGUE STATE
        else if(gp.gameState == gp.dialogueState) {
            if(code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
        }

        // VIEW ATTRIBUTE STATE
        else if(gp.gameState == gp.viewAttributeState) {
            if(code == KeyEvent.VK_E) {
                gp.gameState = gp.playState;
            }

            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                if(gp.ui.slotInventoryRow != 0) {
                    gp.ui.slotInventoryRow--;
                    gp.playSE(5);
                }
            }
            if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                if(gp.ui.slotInventoryCol != 0) {
                    gp.ui.slotInventoryCol--;
                    gp.playSE(5);
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                if(gp.ui.slotInventoryRow < 6) {
                    gp.ui.slotInventoryRow++;
                    gp.playSE(5);
                }
            }
            if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                if(gp.ui.slotInventoryCol < 8) {
                    gp.ui.slotInventoryCol++;
                    gp.playSE(5);
                }
            }

            if(code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
        }

        else if(gp.gameState == gp.houseInteractState) {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 3;
                }
            }
    
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 3) {
                    gp.ui.commandNum = 0;
                }
            }

             if(code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0) {
                    gp.player.sleeping();
                    gp.gameState = gp.playState;
                }
                if(gp.ui.commandNum == 1) {
                    gp.player.watching();
                    gp.gameState = gp.watchingState;
                }
                if(gp.ui.commandNum == 2) {
                    gp.player.cooking();
                }
                if(gp.ui.commandNum == 3) {
                    gp.gameState = gp.playState;
                }
            }
        }

        else if(gp.gameState == gp.plantSeedState) {

            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                if(gp.ui.slotInventoryRow != 0) {
                    gp.ui.slotInventoryRow--;
                    gp.playSE(5);
                }
            }
            if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                if(gp.ui.slotInventoryCol != 0) {
                    gp.ui.slotInventoryCol--;
                    gp.playSE(5);
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                if(gp.ui.slotInventoryRow < 3) {
                    gp.ui.slotInventoryRow++;
                    gp.playSE(5);
                }
            }
            if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                if(gp.ui.slotInventoryCol < 3) {
                    gp.ui.slotInventoryCol++;
                    gp.playSE(5);
                }
            }
            if(code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }

            if(code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
        }

        else if(gp.gameState == gp.interactNPCState) {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) gp.ui.commandNum = 3;
            }
    
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 3) gp.ui.commandNum = 0;
            }

            if(code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0) {
                }
                if(gp.ui.commandNum == 1) {
                    gp.player.proposing();
                }
                if(gp.ui.commandNum == 2) {
                    gp.player.marry();
                }
                if(gp.ui.commandNum == 3) {
                    gp.gameState = gp.worldMapState;
                }
            }
        }
       
        else if (gp.gameState == gp.watchingState){
            if (code == KeyEvent.VK_ESCAPE){
                gp.gameState = gp.playState;
            }
        }

        else if (gp.gameState == gp.watchingState || gp.gameState == gp.rejectedState || gp.gameState == gp.tooSoonState || 
                gp.gameState == gp.havePartnerState || gp.gameState == gp.yourPartnerState || gp.gameState == gp.acceptedState) {
            if (code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.worldMapState;
            }
        }

        else if (gp.gameState == gp.fishingInteractState) {
            if (code == KeyEvent.VK_BACK_SPACE) {
                if (gp.ui.guessString.length() > 0) {
                    gp.ui.guessString.deleteCharAt(gp.ui.guessString.length() - 1);
                }
            } else if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.guessString.length() > 0) {
                    try {
                        gp.ui.guess = Integer.parseInt(gp.ui.guessString.toString());
                    } catch (NumberFormatException ex) {
                        gp.ui.fishingWarning = "Enter a valid number!";
                        gp.ui.guess = -1;
                    }

                    if (gp.ui.guess != gp.luckyNumber) {
                        if (gp.ui.fishingAttempts < 10) {
                            if (gp.ui.guess < gp.luckyNumber) gp.ui.fishingWarning = "Too Low!";
                            if (gp.ui.guess > gp.luckyNumber) gp.ui.fishingWarning = "Too High!";
                            gp.ui.fishingAttempts++;
                        } else {
                            gp.gameState = gp.fishingFailed;
                            gp.fished = null;
                            gp.luckyNumber = null;
                            gp.ui.fishingWarning = null;
                            gp.ui.guessString.setLength(0);
                            gp.ui.fishingAttempts = 1;
                            gp.ui.guess = 0;
                        }
                    } else {
                        gp.gameState = gp.fishingSucceeded;
                        gp.player.addItemToInventory(gp.fished, 1);
                        gp.player.openInventory();
                        gp.fished = null;
                        gp.luckyNumber = null;
                        gp.ui.fishingWarning = null;
                        gp.ui.guessString.setLength(0);
                        gp.ui.fishingAttempts = 1;
                        gp.ui.guess = 0;
                    }
                    gp.ui.guessString.setLength(0);
                }
            } else if (code == KeyEvent.VK_ESCAPE) {
                gp.fished = null;
                gp.luckyNumber = null;
                gp.ui.fishingWarning = null;
                gp.ui.guessString.setLength(0);
                gp.ui.fishingAttempts = 1;
                gp.ui.guess = 0;
                gp.gameState = gp.playState;
            } else {
                char c = e.getKeyChar();
                if (Character.isLetterOrDigit(c) && gp.ui.guessString.length() < 9) {
                    gp.ui.guessString.append(c);
                }
            }
        }
        
        else if (gp.gameState == gp.fishingSucceeded) {
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
        }
        
        else if (gp.gameState == gp.fishingFailed) {
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
        }
        
        else if(gp.gameState == gp.binInteractState) {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 1;
                }
            }
    
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 1) {
                    gp.ui.commandNum = 0;
                }
            }

             if(code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0) {
                    gp.gameState = gp.binShopState;
                }
                if(gp.ui.commandNum == 1) {
                    gp.gameState = gp.playState;
                }
            }
        }
        
        else if(gp.gameState == gp.binShopState) {

            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                if(gp.ui.slotInventoryRow != 0) {
                    gp.ui.slotInventoryRow--;
                    gp.playSE(5);
                }
            }
            if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                if(gp.ui.slotInventoryCol != 0) {
                    gp.ui.slotInventoryCol--;
                    gp.playSE(5);
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                if(gp.ui.slotInventoryRow < 6) {
                    gp.ui.slotInventoryRow++;
                    gp.playSE(5);
                }
            }
            if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                if(gp.ui.slotInventoryCol < 8) {
                    gp.ui.slotInventoryCol++;
                    gp.playSE(5);
                }
            }
            if(code == KeyEvent.VK_ENTER) {
                enterPressed = true;
                gp.ui.sellAmount = 1;
                if (gp.ui.selectedItem != null) gp.gameState = gp.binAmountState;
            }

            if(code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
                gp.ui.sellAmount = 0;
            }
        }

        else if (gp.gameState == gp.binAmountState) {

            boolean canSell = gp.ui.selectedItem != null && gp.ui.selectedItem.getSellPrice() != null;

            if (canSell && (code == KeyEvent.VK_PLUS || code == KeyEvent.VK_EQUALS)) {
                if(gp.ui.sellAmount < gp.player.getInventory().getItemQuantity(gp.ui.selectedItem)) {
                    gp.ui.sellAmount++;
                    gp.playSE(5);
                }
            }

            if (canSell && (code == KeyEvent.VK_MINUS || code == KeyEvent.VK_UNDERSCORE)) {
                if(gp.ui.sellAmount > 1) {
                    gp.ui.sellAmount--;
                    gp.playSE(5);
                }
            }

            if (canSell && code == KeyEvent.VK_ENTER) {
                enterPressed = true;
                int currentQty = gp.player.getInventory().getItemQuantity(gp.ui.selectedItem);
                if (gp.ui.sellAmount > 0 && gp.ui.sellAmount <= currentQty) {
                    if ((gp.binShopInventory.count() < 16 || gp.binShopInventory.getItems().containsKey(gp.ui.selectedItem))) {
                        gp.player.getInventory().removeItem(gp.ui.selectedItem, gp.ui.sellAmount);
                        gp.binShopInventory.addItem(gp.ui.selectedItem, gp.ui.sellAmount);
                    } 
                    gp.ui.showMessage("Sold " + gp.ui.sellAmount + " " + gp.ui.selectedItem.getName() + "!");
                    gp.ui.sellAmount = 1;
                    gp.keyH.enterPressed = false;
                    gp.gameState = gp.binShopState;
                    gp.playSE(3);
                } else {
                    gp.ui.showMessage("Not enough items to sell!");
                    gp.keyH.enterPressed = false;
                }
            }

            if(code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.binShopState;
                gp.ui.sellAmount = 1;
            }
        }

        else if (gp.gameState == gp.moveMapState) {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 1;
                }
            }
    
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 1) {
                    gp.ui.commandNum = 0;
                }
            }

             if(code == KeyEvent.VK_ENTER) {

                if (gp.currentMap == 0) {
                    if(gp.ui.commandNum == 0) {
                        gp.player.teleport();
                        gp.currentMap = 1;
                        gp.maxWorldCol = 70;
                        gp.maxWorldRow = 75;
                        gp.tileM = new TileManager(gp, gp.maxWorldCol, gp.maxWorldRow);
                        gp.gameState = gp.worldMapState;
                    } else if (gp.ui.commandNum == 1) {
                        gp.gameState = gp.playState;
                    }
                } else if (gp.currentMap == 1) {
                    if (gp.ui.commandNum == 0) {
                        gp.player.teleport();
                        gp.currentMap = 0;
                        gp.maxWorldCol = 51;
                        gp.maxWorldRow = 51;
                        gp.tileM = new TileManager(gp, gp.maxWorldCol, gp.maxWorldRow);
                        gp.gameState = gp.playState;
                    } else if (gp.ui.commandNum == 1) {
                        gp.gameState = gp.worldMapState;
                    }
                }
                e.consume();
            }
        }

        else if (gp.gameState == gp.sleepingState){
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_ESCAPE) gp.gameState = gp.playState;
        }

        else if (gp.gameState == gp.houseNPCInteractState) {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 1;
                }
            }
    
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 1) {
                    gp.ui.commandNum = 0;
                }
            }

             if(code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0) {
                    gp.npc[1][gp.ui.visitedNPC].teleport(gp.player.worldX + gp.tileSize, gp.player.worldY + 10);
                    gp.gameState = gp.worldMapState;
                } else if (gp.ui.commandNum == 1) gp.gameState = gp.worldMapState;
            }
        }

        else if (gp.gameState == gp.storeInteractState) {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
    
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }

             if(code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0) {
                } else if (gp.ui.commandNum == 1) {
                    gp.npc[1][gp.ui.visitedNPC].teleport(gp.player.worldX + 2 * gp.tileSize, gp.player.worldY);
                    gp.gameState = gp.worldMapState;
                }
                else gp.gameState = gp.worldMapState;
            }
        }

        // DEBUG
        if(code == KeyEvent.VK_F12) {
            if(checkDrawTime == false) {
                checkDrawTime = true;
            } else if(checkDrawTime == true) {
                checkDrawTime = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = false;
        }

        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }

        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }

        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }
}
