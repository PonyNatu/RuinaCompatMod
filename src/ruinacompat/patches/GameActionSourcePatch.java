package ruinacompat.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@SpirePatch2(clz = AbstractGameAction.class, method = "update")
public class GameActionSourcePatch {

    @SpirePrefixPatch
    public static SpireReturn<Void> prefix(AbstractGameAction __instance) {
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
                if ((tm.isDead || tm.isDying) && isDamageAction(__instance)) {
                    __instance.isDone = true;
                    return SpireReturn.Return(null);
                }
            }

        } catch (Exception e) {
            System.out.println("[RuinaCompatMod] GameActionSourcePatch error: " + e.getMessage());
        }
        return SpireReturn.Continue();
    }

    private static boolean isDamageAction(AbstractGameAction action) {
        String name = action.getClass().getSimpleName();
        return name.contains("Damage") || name.contains("Attack") || name.contains("Strike");
    }
}
