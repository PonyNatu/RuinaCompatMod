package ruinacompat.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@SpirePatch2(clz = DamageAction.class, method = "update")
public class DamageActionPatch {

    @SpirePrefixPatch
    public static SpireReturn<Void> prefix(DamageAction __instance) {
        try {
            AbstractCreature source = __instance.source;
            AbstractCreature target = __instance.target;

            if (source instanceof AbstractMonster) {
                AbstractMonster sm = (AbstractMonster) source;
                if (sm.isDead || sm.isDying) {
                    __instance.isDone = true;
                    return SpireReturn.Return(null);
                }
            }

            if (target instanceof AbstractMonster) {
                AbstractMonster tm = (AbstractMonster) target;
                if (tm.isDead || tm.isDying) {
                    __instance.isDone = true;
                    return SpireReturn.Return(null);
                }
            }

        } catch (Exception e) {
            System.out.println("[RuinaCompatMod] DamageActionPatch error: " + e.getMessage());
        }
        return SpireReturn.Continue();
    }
}
