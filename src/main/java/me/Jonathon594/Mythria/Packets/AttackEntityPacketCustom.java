package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Managers.DualSwingManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AttackEntityPacketCustom implements IMessage {

    private int entityID;
    private int extraDamage;
    private boolean reset;
    private int hand;
    private int potionID = -1;
    private int potionTicks = -1;
    private int potionStrength = -1;

    public AttackEntityPacketCustom() {
        // TODO Auto-generated constructor stub
    }

    public AttackEntityPacketCustom(final int entityID, final int extraDamage, final boolean reset, final int hand) {
        super();
        this.entityID = entityID;
        this.extraDamage = extraDamage;
        this.reset = reset;
        this.hand = hand;
    }

    public AttackEntityPacketCustom(final int entityID, final int extraDamage, final boolean reset, final int hand,
                                    final int potionID, final int potionTicks, final int potionStrength) {
        super();
        this.entityID = entityID;
        this.extraDamage = extraDamage;
        this.reset = reset;
        this.hand = hand;
        this.potionID = potionID;
        this.potionTicks = potionTicks;
        this.potionStrength = potionStrength;
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        entityID = buf.readInt();
        extraDamage = buf.readInt();
        reset = buf.readBoolean();
        hand = buf.readInt();
        potionID = buf.readInt();
        potionTicks = buf.readInt();
        potionStrength = buf.readInt();
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(entityID);
        buf.writeInt(extraDamage);
        buf.writeBoolean(reset);
        buf.writeInt(hand);
        buf.writeInt(potionID);
        buf.writeInt(potionTicks);
        buf.writeInt(potionStrength);
    }

    public int getEntityID() {
        return entityID;
    }

    public int getExtraDamage() {
        return extraDamage;
    }

    public int getHand() {
        return hand;
    }

    public int getPotionID() {
        return potionID;
    }

    public int getPotionStrength() {
        return potionStrength;
    }

    public int getPotionTicks() {
        return potionTicks;
    }

    public boolean isReset() {
        return reset;
    }

    public static class AttackEntityPacketCustomHandler implements IMessageHandler<AttackEntityPacketCustom, IMessage> {

        @Override
        public IMessage onMessage(final AttackEntityPacketCustom message, final MessageContext ctx) {
            final World worldserver = ctx.getServerHandler().player.world;
            final Entity entity = worldserver.getEntityByID(message.getEntityID());

            ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
                if (entity != null) {
                    final boolean flag = ctx.getServerHandler().player.canEntityBeSeen(entity);
                    double d0 = 36.0D;

                    if (!flag)
                        d0 = 9.0D;

                    if (ctx.getServerHandler().player.getDistanceSq(entity) < d0) {
                        if (entity instanceof EntityItem || entity instanceof EntityXPOrb || entity instanceof EntityArrow
                                || entity == ctx.getServerHandler().player) {
                            ctx.getServerHandler().disconnect(new TextComponentTranslation(
                                    "multiplayer.disconnect.invalid_entity_attacked"));
                            return;
                        }

                        DualSwingManager.attackEntityCustom(ctx.getServerHandler().player, entity,
                                EnumHand.values()[message.getHand()], message.getExtraDamage(), message.isReset());
                        if (message.getPotionID() != -1) {
                            final PotionEffect potion = new PotionEffect(Potion.getPotionById(message.getPotionID()),
                                    message.getPotionTicks(), message.getPotionStrength());
                            final EntityLivingBase e = (EntityLivingBase) entity;
                            e.addPotionEffect(potion);
                        }
                    }
                }
            });
            return null;
        }

    }
}
