package ruinacompat.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.lang.reflect.Field;

@SpirePatch2(clz = DamageAction.class, method = "update")
public class DamageActionPatch {

    @SpirePrefixPatch
    public static SpireReturn<Void> prefix(DamageAction __instance) {
        try {
            AbstractCreature target = __instance.target;

            // 🔥 Reflectionでinfo取得
            Field f = DamageAction.class.getDeclaredField("info");
            f.setAccessible(true);
            DamageInfo info = (DamageInfo) f.get(__instance);

            if (target == null || info == null || info.owner == null) {
                __instance.isDone = true;
                return SpireReturn.Return(null);
            }

            if (target instanceof AbstractMonster) {
                AbstractMonster m = (AbstractMonster) target;
                if (m.isDead || m.isDying) {
                    __instance.isDone = true;
                    return SpireReturn.Return(null);
                }
            }

            if (info.owner instanceof AbstractMonster) {
                AbstractMonster src = (AbstractMonster) info.owner;
                if (src.isDead || src.isDying) {
                    __instance.isDone = true;
                    return SpireReturn.Return(null);
                }
            }

        } catch (Exception e) {
            System.out.println("[RuinaCompatMod] DamageActionPatch error: " + e.getMessage());
            __instance.isDone = true;
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}