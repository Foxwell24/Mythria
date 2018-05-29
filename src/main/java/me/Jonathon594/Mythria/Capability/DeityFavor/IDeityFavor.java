package me.Jonathon594.Mythria.Capability.DeityFavor;

import me.Jonathon594.Mythria.DataTypes.HealthConditionInstance;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.PersonallityTrait;
import me.Jonathon594.Mythria.DataTypes.Time.Date;
import me.Jonathon594.Mythria.Enum.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface IDeityFavor {
    void setFavor(Deity d, int favor);
    int getFavor(Deity d);

    NBTTagCompound toNBT();
    void fromNBT(NBTTagCompound compound);

    boolean hasBlessing(BlessingType blessingType);
}
