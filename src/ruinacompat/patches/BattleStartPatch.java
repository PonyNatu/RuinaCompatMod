package ruinacompat.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

@SpirePatch2(clz = AbstractRoom.class, method = "initializeMonstersForBattle")
public class BattleStartPatch {

    @SpirePostfixPatch
    public static void postfix(AbstractRoom __instance) {
        try {
            if (__instance.monsters == null || __instance.monsters.monsters == null) return;

            ArrayList<AbstractMonster> copy = new ArrayList<>(__instance.monsters.monsters);
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
