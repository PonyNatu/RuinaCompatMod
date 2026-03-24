package ruinacompat.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.badlogic.gdx.graphics.Texture;

import java.lang.reflect.Field;

@SpirePatch2(clz = AbstractMonster.class, method = "getIntentImg")
public class MonsterIntentPatch {

    @SpirePostfixPatch
    public static Texture postfix(Texture __result, AbstractMonster __instance) {
        try {
            Field f = __instance.getClass().getDeclaredField("intentImg");
            f.setAccessible(true);
            Texture intentImg = (Texture) f.get(__instance);

            if (__result == null && intentImg != null) {
                return intentImg;
            }
        } catch (Exception e) {
            System.out.println("[RuinaCompatMod] MonsterIntentPatch error: " + e.getMessage());
        }
        return __result;
    }
}