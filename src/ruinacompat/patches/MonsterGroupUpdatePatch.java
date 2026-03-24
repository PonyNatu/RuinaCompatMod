package ruinacompat.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.monsters.MonsterGroup;

@SpirePatch2(clz = MonsterGroup.class, method = "update")
public class MonsterGroupUpdatePatch {

    @SpirePrefixPatch
    public static SpireReturn<Void> prefix(MonsterGroup __instance) {
        try {
            if (__instance.monsters == null || __instance.monsters.isEmpty()) {
                return SpireReturn.Continue();
            }
        } catch (Exception e) {
            System.out.println("[RuinaCompatMod] MonsterGroupUpdatePatch error: " + e.getMessage());
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}
