package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

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

}
