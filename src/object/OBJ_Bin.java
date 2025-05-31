package object;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel; 

public class OBJ_Bin extends SuperObject {

    public OBJ_Bin(GamePanel gp, int x, int y) { 
        name = "Bin";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/ShippingBin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
        worldX = x;
        worldY = y;

        width = gp.tileSize * 3; 
        height = gp.tileSize * 2; 

        solidAreas = new Rectangle[1]; 

        solidAreas[0] = new Rectangle();
        solidAreas[0].x = 0; 
        solidAreas[0].y = 0; 
        solidAreas[0].width = width; 
        solidAreas[0].height = height; 

        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }

// public class ShippingBinInteract implements Interact {
//     private Map<Items, Integer> items = new HashMap<>();

//     public void interact(Player player) {
//         System.out.print("\n\n---- SHIPPING BIN ---- \n\n");

//         System.out.println("Items in the shipping bin:");
//         for (Map.Entry<Items, Integer> entry : items.entrySet()) {
//             System.out.println(entry.getKey().getName() + ": " + entry.getValue());
//         }

//         System.out.println("\n\nWhat would you like to do?");
//         System.out.println("1. Add items to the shipping bin");
//         System.out.println("2. Leave the shipping bin");
//         System.out.print("Enter your choice: ");

//         Scanner scanner = player.getScanner();
//         int choice;
//         try {
//             choice = Integer.parseInt(scanner.nextLine());
//         } catch (NumberFormatException e) {
//             System.out.println("Invalid number. Closing the shipping bin...");
//             return;
//         }

//         if (choice == 1) {
//             System.out.print("Enter the name of the item to add: ");
//             String itemName = scanner.nextLine();

//             Items item = player.getInventory().get(itemName);

//             if (item != null) {
//                 System.out.print("Enter the quantity to add: ");
//                 int quantity;
//                 try {
//                     quantity = Integer.parseInt(scanner.nextLine());
//                 } catch (NumberFormatException e) {
//                     System.out.println("Invalid quantity. Closing the shipping bin...");
//                     return;
//                 }

//                 int available = player.getItemQuantity(item);
//                 if (quantity > available) {
//                     System.out.println("You only have " + available + " " + item.getName() + "(s) in your inventory. Closing the shipping bin...");
//                     return;
//                 }

//                 if (items.containsKey(item)) items.put(item, items.get(item) + quantity);
//                 else {
//                     if (items.size() < 16) items.put(item, quantity);
//                     else {
//                         System.out.println("Shipping bin is full (max 16 item types). Closing the shipping bin...");
//                         return;
//                     }
//                 }

//                 player.removeItemFromInventory(item, quantity);
//                 System.out.println("Added " + quantity + " " + item.getName() + "(s) to the shipping bin.");
//             } else System.out.println("Item not found. Closing the shipping bin...");
//         } else if (choice == 2) System.out.println("Closing the shipping bin...");
//         else System.out.println("Invalid choice. Closing the shipping bin...");
//     }
// }
}