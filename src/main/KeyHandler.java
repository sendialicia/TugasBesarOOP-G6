package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener{

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    
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
                gp.tileM.loadMap("/maps/farmMap.txt");
            }

            if(code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;
            }

            if(code == KeyEvent.VK_V) {
                gp.gameState = gp.viewAttributeState;
                e.consume();
            }

            if(code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
        }

        // PAUSE STATE
        else if(gp.gameState == gp.pauseState) {
            if(code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
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
            if(code == KeyEvent.VK_V) {
                gp.gameState = gp.playState;
            }

            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                if(gp.ui.slotRow != 0) {
                    gp.ui.slotRow--;
                    gp.playSE(5);
                }
            }
            if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                if(gp.ui.slotCol != 0) {
                    gp.ui.slotCol--;
                    gp.playSE(5);
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                if(gp.ui.slotRow < 6) {
                    gp.ui.slotRow++;
                    gp.playSE(5);
                }
            }
            if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                if(gp.ui.slotCol < 8) {
                    gp.ui.slotCol++;
                    gp.playSE(5);
                }
            }
        }

        else if(gp.gameState == gp.houseInteractState) {
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
                    gp.player.sleeping();
                    //ui
                    gp.gameState = gp.playState;
                }
                if(gp.ui.commandNum == 1) {
                    gp.player.cooking();
                }
                if(gp.ui.commandNum == 2) {
                    gp.gameState = gp.playState;
                }
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
                        }
                    } else {
                        gp.gameState = gp.fishingSucceeded;
                        gp.player.addItemToInventory(gp.fished, 1);
                        gp.fished = null;
                        gp.luckyNumber = null;
                        gp.ui.fishingWarning = null;
                    }
                }
            } else if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.fishingFailed;
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
                gp.ui.guessString.setLength(0);
                gp.ui.fishingAttempts = 0;
            }
        }
        else if (gp.gameState == gp.fishingFailed) {
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
                gp.ui.guessString.setLength(0); 
                gp.ui.fishingAttempts = 0;
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
                if(gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }

             if(code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0) {
                }
                if(gp.ui.commandNum == 1) {
                    gp.gameState = gp.playState;
                }
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
