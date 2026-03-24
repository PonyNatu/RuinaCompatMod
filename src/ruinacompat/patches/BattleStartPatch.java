package ruinacompat.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

@SpirePatch2(clz = MonsterGroup.class, method = "update")
public class BattleStartPatch {

    @SpirePrefixPatch
    public static void prefix(MonsterGroup __instance) {
        try {
            if (__instance.monsters == null) return;

            ArrayList<AbstractMonster> copy = new ArrayList<>(__instance.monsters);
            for (AbstractMonster m : copy) {
                if (m == null) continue;

                if (m.maxHealth <= 0) {
                    m.maxHealth = 1;
                }

                if (m.currentHealth <= 0 && !m.isDead && !m.isDying) {
                    System.out.println("[RuinaCompatMod] BattleStartPatch: fixing HP for " + m.name);
                    m.currentHealth = 1;
                }
            }

        } catch (Exception e) {
            System.out.println("[RuinaCompatMod] BattleStartPatch error: " + e.getMessage());
        }
    }
}
