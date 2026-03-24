package ruinacompat.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch2(clz = DamageAllEnemiesAction.class, method = "update")
public class DamageAllEnemiesActionPatch {

    @SpirePrefixPatch
    public static SpireReturn<Void> prefix(DamageAllEnemiesAction __instance) {
        try {
            if (AbstractDungeon.getCurrRoom() == null) {
                __instance.isDone = true;
                return SpireReturn.Return(null);
            }
            if (AbstractDungeon.getCurrRoom().monsters == null) {
                __instance.isDone = true;
                return SpireReturn.Return(null);
            }
        } catch (Exception e) {
            System.out.println("[RuinaCompatMod] DamageAllEnemiesActionPatch error: " + e.getMessage());
            __instance.isDone = true;
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}
