package ruinacompat.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch2(clz = DamageAllEnemiesAction.class, method = "update")
public class DamageAllEnemiesActionPatch {

    @SpirePrefixPatch
    public static SpireReturn<Void> prefix(DamageAllEnemiesAction __instance) {
        try {
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (m != null && (m.isDead || m.isDying)) {
                    // 必要ならログ
                }
            }
        } catch (Exception e) {
            System.out.println("[RuinaCompatMod] DamageAllEnemiesActionPatch error: " + e.getMessage());
        }
        return SpireReturn.Continue();
    }
}
