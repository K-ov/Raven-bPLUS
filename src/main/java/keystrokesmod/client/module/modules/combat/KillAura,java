package ravenNPlus.client.module.modules.combat;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.DescriptionSetting;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.module.setting.impl.TickSetting;
import ravenNPlus.client.utils.InvUtils;
import ravenNPlus.client.utils.RenderUtils;
import ravenNPlus.client.utils.Timer;
import ravenNPlus.client.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class KillAura extends Module {

    public static TickSetting mouse, background, onlySprint, onlySword, swing, drawEntity, drawHUD, silent, ignoreFriends, head;
    public static SliderSetting range, entityX, entityY /*, mode*/, entitySize, delay, chance;
    //public static DescriptionSetting modeMode;
    //public static boolean viewModified = false;

    public KillAura() {
        super("KillAura", ModuleCategory.combat);
        //this.addSetting(mode = new SliderSetting("Mode", 1, 1, 3, 1));
        //this.addSetting(modeMode = new DescriptionSetting(Utils.md +""));
        this.addSetting(chance =  new SliderSetting("Chance %", 100, 0, 100, 1));
        this.addSetting(silent = new TickSetting("Silent", true));
        this.addSetting(swing = new TickSetting("Swing", true));
        this.addSetting(ignoreFriends = new TickSetting("Ignore Friends", true));
        this.addSetting(range = new SliderSetting("Range", 3.7D, 2D, 8D, 0.1D));
        this.addSetting(delay = new SliderSetting("Delay", 5, 0, 100, 1));
        this.addSetting(head = new TickSetting("Head", false));
        this.addSetting(drawHUD = new TickSetting("Draw HUD", true));
        this.addSetting(entityX = new SliderSetting("HUD X", 80, 20, mc.displayWidth+50, 1));
        this.addSetting(entityY = new SliderSetting("HUD Y", 90, 20, mc.displayHeight+50, 1));
        this.addSetting(background = new TickSetting("Background", true));
        this.addSetting(drawEntity = new TickSetting("Draw Entity", false));
        this.addSetting(entitySize = new SliderSetting("Entity Size", 35, 10, 100, 1));
        this.addSetting(mouse = new TickSetting("Mouse X and Y", false));
        this.addSetting(onlySword = new TickSetting("Only Sword", true));
        this.addSetting(onlySprint = new TickSetting("Only Sprint", false));
    }

    @SubscribeEvent
    public void r(TickEvent.PlayerTickEvent p) {
        if (!Utils.Player.isPlayerInGame()) return;
        if(!mc.thePlayer.isEntityAlive()) return;

        List<Entity> targets = (List<Entity>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
        targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < (int) range.getValue() && entity != mc.thePlayer && !entity.isDead &&
                ((EntityLivingBase) entity).getHealth() > 0).collect(Collectors.toList());
        targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase) entity).getDistanceToEntity(mc.thePlayer)));
        if (targets.isEmpty()) return;
        EntityLivingBase target = (EntityLivingBase) targets.get(0);
        if (AntiBot.isBot(target)) return;

        if (ignoreFriends.isToggled())
            if (Utils.FriendUtils.isAFriend(target)) return;

        if (onlySword.isToggled())
            if (!InvUtils.isPlayerHoldingWeapon()) return;

        if (onlySprint.isToggled())
            if (!mc.thePlayer.isSprinting()) return;

        //if (mode.getValue() == 1D) {
        if (Timer.hasTimeElapsed((long) delay.getValue() * 5, true)) {
            Utils.Player.aim(target, 0.0F, false, silent.isToggled());

            int killInteger;
            if (swing.isToggled()) {
                killInteger = 1;

                if (IScoreObjectiveCriteria.playerKillCount.equals(killInteger)) {

                    Utils.Player.swing();
                    mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());

                    killInteger = 0;
                } else killInteger = 0;
            } else killInteger = 0;

            if (!(chance.getValue() == 100 || Math.random() <= chance.getValue() / 100))
                return;

            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
            //    }

            if (Objects.equals(1, mc.thePlayer.getName())) {
                Utils.Player.swing();
            }
        }

       /*
        if (mode.getValue() == 2D) {
            if (target.getDistanceToEntity(mc.thePlayer) < (int) range.getValue()) {
                mc.setRenderViewEntity(target);
                viewModified = true;
            } else {
                mc.setRenderViewEntity(mc.thePlayer);
                viewModified = false;
            }
        }
      */

    }

    @Override
    public void onDisable() {
        if(swing.isToggled())
            Utils.Player.swing();

        //if(mode.getValue() == 2D && viewModified) {
        //    mc.setRenderViewEntity(mc.thePlayer);
        //}
    }


    @SubscribeEvent
    public void p(TickEvent.RenderTickEvent e) {
        if(!Utils.Player.isPlayerInGame()) return;

        List<Entity> targets = (List<Entity>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
        targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < (int) range.getValue() && entity != mc.thePlayer && !entity.isDead &&
        ((EntityLivingBase) entity).getHealth() > 0).collect(Collectors.toList());
        targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase) entity).getDistanceToEntity(mc.thePlayer)));
        if (targets.isEmpty()) return;
        EntityLivingBase target = (EntityLivingBase) targets.get(0);
        if(AntiBot.isBot(target)) return;

        int xxx  = (int) entityX.getValue();
        int yyy  = (int) entityY.getValue();
        int rang = (int) range.getValue();
        int size  = (int) entitySize.getValue();
        int backgroundOffset = 50;

        if(onlySword.isToggled())
            if(!InvUtils.isPlayerHoldingWeapon()) return;

        if(onlySprint.isToggled())
            if(!mc.thePlayer.isSprinting()) return;

        if(drawHUD.isToggled()) {
            RenderUtils.drawStringHUD(xxx, yyy, rang, background.isToggled(), true, head.isToggled());

            if(drawEntity.isToggled()) drawEntity.disable();
        }

        if(drawEntity.isToggled()) {
            RenderUtils.drawEntityHUD(target, xxx, yyy, xxx + backgroundOffset, yyy + backgroundOffset, size, rang, true, background.isToggled(), mouse.isToggled());

            if(drawHUD.isToggled()) drawHUD.disable();
        }
    }

  /*
    public void guiUpdate() {
        switch ((int) mode.getValue()) {
            case 1:
                modeMode.setDesc(Utils.md + "Legit");
                break;
            case 2:
                modeMode.setDesc(Utils.md + "Packets");
                break;
            case 3:
                modeMode.setDesc(Utils.md + "More soon...");
                break;
            case 4:
                modeMode.setDesc(Utils.md + "Test");
                break;
        }
    }
  */

}
