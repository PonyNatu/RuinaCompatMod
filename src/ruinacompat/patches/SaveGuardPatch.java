package ruinacompat.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

@SpirePatch2(clz = SaveHelper.class, method = "saveGame")
public class SaveGuardPatch {

    @SpirePrefixPatch
    public static SpireReturn<Void> prefix() {
        try {
            if (AbstractDungeon.player == null) {
                System.out.println("[RuinaCompatMod] saveGame() blocked: player is null.");
                return SpireReturn.Return(null);
            }
            AbstractRoom room = AbstractDungeon.getCurrRoom();
            if (room != null
                    && room.phase == AbstractRoom.RoomPhase.COMBAT
                    && room.monsters == null) {
                System.out.println("[RuinaCompatMod] saveGame() blocked: in COMBAT but monsters is null.");
                return SpireReturn.Return(null);
            }
        } catch (Exception e) {
            System.out.println("[RuinaCompatMod] SaveGuardPatch error: " + e.getMessage());
        }
        return SpireReturn.Continue();
    }
}
