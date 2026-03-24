package ruinacompat.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

@SpirePatch2(clz = AbstractRoom.class, method = "endBattle")
public class EndBattlePatch {

    private static final Set<AbstractRoom> inProgress =
            Collections.newSetFromMap(new WeakHashMap<AbstractRoom, Boolean>());

    @SpirePrefixPatch
    public static SpireReturn<Void> prefix(AbstractRoom __instance) {
        try {
            if (inProgress.contains(__instance)) {
                System.out.println("[RuinaCompatMod] endBattle() duplicate blocked.");
                return SpireReturn.Return(null);
            }

            if (__instance.monsters == null || __instance.monsters.monsters == null) {
                return SpireReturn.Continue();
            }

            boolean anyStillDying = false;
            try {
                ArrayList<AbstractMonster> copy = new ArrayList<>(__instance.monsters.monsters);
                for (AbstractMonster m : copy) {
                    if (m != null && m.isDying && !m.isDead) {
                        anyStillDying = true;
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("[RuinaCompatMod] isDying check error: " + e.getMessage());
            }

            if (anyStillDying) {
                return SpireReturn.Return(null);
            }

            inProgress.add(__instance);

        } catch (Exception e) {
            System.out.println("[RuinaCompatMod] EndBattlePatch prefix error: " + e.getMessage());
        }
        return SpireReturn.Continue();
    }

    @SpirePostfixPatch
    public static void postfix(AbstractRoom __instance) {
        try {
            inProgress.remove(__instance);
        } catch (Exception e) {
            System.out.println("[RuinaCompatMod] EndBattlePatch postfix error: " + e.getMessage());
        }
    }
}
