package entity.npc;

import java.util.HashMap;
import java.util.Map;
import main.GamePanel;

public class NPCFactory {
    private static final Map<String, NPC> npcByName = new HashMap<>();

    public static void register(NPC npc) {
        npcByName.put(npc.getName().toLowerCase(), npc);
    }

    public static NPC get(String name) {
        return npcByName.get(name.toLowerCase());
    }

    public static Map<String, NPC> getAllNPC() {
        return npcByName;
    }

    private static void loadNPC(GamePanel gp) {
        register(new Abigail(gp));
        register(new Caroline(gp));
        register(new Dasco(gp));
        register(new Emily(gp));
        register(new MayorTadi(gp));
        register(new Perry(gp));
    }

    public static void loadAll(GamePanel gp) {
        loadNPC(gp);
    }
}
