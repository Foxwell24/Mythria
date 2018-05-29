package me.Jonathon594.Mythria.Util;

import me.Jonathon594.Mythria.Blocks.MythriaOre;
import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.DataTypes.HealthConditionInstance;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.Time.Date;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.GUI.Container.ContainerAttribute;
import me.Jonathon594.Mythria.GUI.Container.PerkMenuGuiContainer;
import me.Jonathon594.Mythria.GUI.GuiAnvil;
import me.Jonathon594.Mythria.GUI.GuiAttribute;
import me.Jonathon594.Mythria.GUI.PerkMenuGui;
import me.Jonathon594.Mythria.Items.ItemDagger;
import me.Jonathon594.Mythria.Items.MythriaItemHammer;
import me.Jonathon594.Mythria.Managers.PerkManager;
import me.Jonathon594.Mythria.Managers.TimeManager;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.Container;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sun.font.SunFontManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class MythriaUtil {

    public static void addLoreToItemStack(final ItemStack stack, final ArrayList<String> lines) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null)
            nbt = new NBTTagCompound();

        final NBTTagList lore = new NBTTagList();
        for (final String s : lines)
            lore.appendTag(new NBTTagString(s));

        final NBTTagCompound display = nbt.getCompoundTag("display");
        display.setTag("Lore", lore);

        nbt.setTag("display", display);
        stack.setTagCompound(nbt);
    }

    public static boolean isSoil(final Block b) {
        return b.equals(Blocks.DIRT) || b.equals(Blocks.FARMLAND) || b.equals(Blocks.GRAVEL) || b.equals(Blocks.SAND)
                || b.equals(Blocks.MYCELIUM) || b.equals(Blocks.GRASS) || b.equals(Blocks.GRASS_PATH);
    }

    public static boolean isShovel(final Item i) {
        return i instanceof ItemSpade;
    }

    public static boolean isWood(final Block b) {
        return b.equals(Blocks.PLANKS) || b.equals(Blocks.LOG) || b.equals(Blocks.LOG2);
    }

    public static void applyMythriaAttributeModifier(final EntityPlayer p, final String string, final double value,
                                                     final IAttribute movementSpeed) {
        final IAttributeInstance atr = p.getEntityAttribute(movementSpeed);
        removeMythriaAttributeModifier(p, string, movementSpeed);
        atr.applyModifier(new AttributeModifier(string, value, 0));
    }

    public static void removeMythriaAttributeModifier(final EntityPlayer p, final String string,
                                                      final IAttribute movementSpeed) {
        final IAttributeInstance atr = p.getEntityAttribute(movementSpeed);
        final Iterator<AttributeModifier> i = atr.getModifiers().iterator();
        while (i.hasNext()) {
            final AttributeModifier am = i.next();
            if (am.getName().equals(string))
                atr.removeModifier(am);
        }
    }

    public static String Capitalize(final String s) {
        if (s.length() == 0)
            return s;
        String lower = s.toLowerCase();
        final char[] c = lower.toCharArray();
        if (Character.isUpperCase(c[0]))
            return s;
        else {
            c[0] = Character.toUpperCase(c[0]);
            return String.valueOf(c);
        }
    }

    public static void DropAllItems(final EntityPlayer player, final boolean armor, final boolean offhand) {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            final ItemStack is = player.inventory.getStackInSlot(i);
            if (is != null) {
                if (i > 35 && i < 40)
                    if (!armor)
                        continue;
                if (i == 40)
                    if (!offhand)
                        continue;
                final EntityItem item = new EntityItem(player.world, player.posX, player.posY, player.posZ, is);
                item.setPickupDelay(60);
                player.world.spawnEntity(item);
                player.inventory.removeStackFromSlot(i);
            }
        }
    }

    public static Date getDateFromAgeMonthDay(int age, final int month, final int day) {
        final Date currentDate = TimeManager.getCurrentDate();
        final Date d = new Date();
        final int currentYear = currentDate.getYear();
        d.setMGDFromDayMonthYear(day, month, currentYear);
        if (currentDate.getMonth() >= d.getMonth())
            if (currentDate.getDayInMonth() >= d.getDayInMonth())
                age -= 1;
        d.setMGD(d.getMGD() - age * TimeManager.getDaysPerYear());

        return d;
    }

    public static List<EntityLivingBase> getEntitiesWithinCircle(final EntityPlayer player, final int r, final int y) {
        final List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(
                player.posX - r, player.posY - y, player.posZ - r, player.posX + r, player.posY + y, player.posZ + r));
        final List<EntityLivingBase> newEntities = new ArrayList<>();
        for (final Entity e : entities)
            if (player.getDistance(e) <= r && e instanceof EntityLivingBase)
                newEntities.add((EntityLivingBase) e);
        return newEntities;
    }

    public static List<EntityLivingBase> getEntitiesWithinViewCone(final EntityPlayer player, final int r, final int y,
                                                                   final float minA, final float maxA) {
        final List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(
                player.posX - r, player.posY - y, player.posZ - r, player.posX + r, player.posY + y, player.posZ + r));
        final List<EntityLivingBase> newEntities = new ArrayList<>();
        for (final Entity e : entities)
            if (player.getDistance(e) <= r && e instanceof EntityLivingBase)
                newEntities.add((EntityLivingBase) e);

        final List<EntityLivingBase> finalEntities = new ArrayList<>();
        for (final EntityLivingBase e : newEntities) {
            final double rX = e.posX - player.posX;
            final double rZ = e.posZ - player.posZ;

            final double a = Math.toDegrees(Math.atan2(-rX, rZ));

            final float a1 = MathHelper.wrapDegrees(player.rotationYaw);
            if (TrigUtil.isWithin2DCone(a1, a, minA, maxA))
                finalEntities.add(e);
        }

        return finalEntities;
    }

    public static List<Integer> getIdsFromBlocks(final Block[] blocks) {
        final List<Integer> ids = new ArrayList<>();
        for (final Block b : blocks)
            ids.add(Block.getIdFromBlock(b));
        return ids;
    }

    public static List<Integer> getIdsFromItems(final Item[] items) {
        final List<Integer> ids = new ArrayList<>();
        for (final Item i : items)
            ids.add(Item.getIdFromItem(i));
        return ids;
    }

    public static Item getItemFromItemOrBlock(final Block b) {
        return Item.getItemFromBlock(b);
    }

    public static Item getItemFromItemOrBlock(final Item i) {
        return i;
    }

    public static double getPitchAimFactor(final float pitch) {
        return 1 - Math.sin(Math.toRadians(MathHelper.clamp(pitch, 0, 45) * 2));
    }

    public static Vec3d getPitchAimTargetPosition(final double r, final double d) {
        return new Vec3d(Math.cos(r) * d, 0, Math.sin(r) * d);
    }

    public static boolean isAxe(final Item i) {
        return i instanceof ItemAxe;
    }

    public static boolean isDagger(final Item i) {
        return i instanceof ItemDagger;
    }

    public static boolean isFood(final ItemStack is) {
        final Item i = is.getItem();
        if (i == null)
            return false;
        if (i.getCreativeTab() == null)
            return false;
        if (!i.getCreativeTab().equals(CreativeTabs.FOOD))
            return false;
        return !i.equals(Items.CAKE);
    }

    public static boolean isSword(final Item i) {
        return i instanceof ItemSword && !isDagger(i);
    }

    public static void ProfileFromNBTForLoading(final IProfile profile, final NBTTagCompound comp) {
        profile.setFirstName(comp.getString("FirstName"));
        profile.setMiddleName(comp.getString("MiddleName"));
        profile.setLastName(comp.getString("LastName"));
        profile.setBirthDay(new Date(comp.getInteger("Birthday")));
        profile.setGender(comp.getInteger("Gender"));
        profile.setAttributePoints(comp.getInteger("AttributePoints"));
        profile.setCreated(comp.getBoolean("Created"));
        final String uuids = comp.getString("UUID");
        if (uuids == null)
            profile.setProfileUUID(UUID.randomUUID());
        else {
            final UUID uuid = UUID.fromString(uuids);
            profile.setProfileUUID(uuid);
        }
        final String ks = comp.getString("Perks");
        final String[] parts = ks.split(":");
        for (final String s : parts) {
            final Perk pa = PerkManager.getAttribute(s);
            if (pa != null)
                profile.addAttribute(pa);
        }

        final String hcis = comp.getString("HealthConditions");
        final String[] parts1 = hcis.split(":");
        for (final String s : parts1) {
            final HealthConditionInstance hci = HealthConditionInstance.fromString(s);
            if (hci != null)
                profile.addHealthCondition(hci);
        }
        for (final MythicSkills cs : MythicSkills.values()) {
            final Double value = comp.getDouble("Skills." + cs.name());
            profile.getSkillLevels().put(cs, value);
            MythriaUtil.getLevelForXP(value);
        }

        for (final Attribute s : Attribute.values()) {
            final int v = comp.getInteger("Attributes." + s.name());
            profile.getAttributes().put(s, v);
        }
        for (final Consumable s : Consumable.values()) {
            final double v = comp.getDouble("Consumables." + s.name());
            profile.getConsumables().put(s, v);
        }
    }

    public static int getLevelForXP(final Double double1) {
        return (int) Math.floor(Math.sqrt(double1 / 5));
    }

    public static NBTTagCompound ProfileToNBTForSaving(final IProfile profile) {
        final NBTTagCompound comp = new NBTTagCompound();
        comp.setString("FirstName", profile.getFirstName());
        comp.setString("MiddleName", profile.getMiddleName());
        comp.setString("LastName", profile.getLastName());
        comp.setInteger("Birthday", profile.getBirthDay().getMGD());
        comp.setInteger("Gender", profile.getGender());
        comp.setInteger("AttributePoints", profile.getAttributePoints());
        comp.setBoolean("Created", profile.getCreated());
        if (profile.getProfileUUID() == null)
            profile.setProfileUUID(UUID.randomUUID());
        comp.setString("UUID", profile.getProfileUUID().toString());
        String ks = "";
        for (final Perk pa : profile.getPlayerSkills()) {
            if (pa == null)
                continue;
            final String s = pa.getName();
            if (ks == "")
                ks = s;
            else
                ks = ks + ":" + s;
        }
        comp.setString("Perks", ks);

        String hcis = "";
        for (final HealthConditionInstance hci : profile.getHealthConditions()) {
            if (hci == null)
                continue;
            final String data = hci.toString();
            if (hcis == "")
                hcis = data;
            else
                hcis = hcis + ":" + data;
        }
        comp.setString("HealthConditions", hcis);
        for (final MythicSkills cs : MythicSkills.values())
            comp.setDouble("Skills." + cs.name(), profile.getSkillLevels().get(cs));

        for (final Attribute s : Attribute.values())
            comp.setDouble("Attributes." + s.name(), profile.getAttribute(s));
        for (final Consumable s : Consumable.values())
            comp.setDouble("Consumables." + s.name(), profile.getConsumables().get(s));
        return comp;
    }

    public static RayTraceResult RayTrace(final EntityLivingBase player, final float partialTicks, final double blockReachDistance, final boolean stopOnLiquid) {
        final Vec3d vec3d = player.getPositionEyes(partialTicks);
        final Vec3d vec3d1 = player.getLook(partialTicks);
        final Vec3d vec3d2 = vec3d.addVector(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
        RayTraceResult result = player.world.rayTraceBlocks(vec3d, vec3d2, stopOnLiquid, false, true);
        float maxDistance = (float) vec3d2.distanceTo(vec3d);
        if (result != null) {
            maxDistance = (float) result.hitVec.distanceTo(vec3d);
        }
        Entity closestHitEntity = null;
        float closestHit = Float.POSITIVE_INFINITY;
        float currentHit = 0f;
        AxisAlignedBB entityBb; // = ent.getBoundingBox();
        RayTraceResult intercept;
        List<Entity> allEntities = player.world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(new BlockPos(vec3d), new BlockPos(vec3d2)));
        for (Entity e : allEntities) {
            if (!e.canBeCollidedWith()) continue;
            float eb = e.getCollisionBorderSize();
            AxisAlignedBB ebb = e.getEntityBoundingBox();
            if (ebb == null) continue;
            ebb.expand(eb, eb, eb);
            intercept = ebb.calculateIntercept(vec3d, vec3d2);
            if (intercept == null) continue;

            currentHit = (float) intercept.hitVec.distanceTo(vec3d);
            if (currentHit < closestHit || currentHit == 0) {
                closestHit = currentHit;
                closestHitEntity = e;
            }
        }
        if (closestHitEntity != null) {
            result = new RayTraceResult(closestHitEntity);
        }
        return result;
    }

    public static void transferDimensionDirect(final int spawnDimensionID, final EntityPlayer player, final int i,
                                               final int j, final int k) {
        player.dimension = spawnDimensionID;
        if (player.getEntityWorld().getBlockState(new BlockPos(new Vec3d(i, j, k))).getBlock().equals(Blocks.AIR))
            player.getEntityWorld().setBlockState(new BlockPos(new Vec3d(i, j, k)), Blocks.GLASS.getDefaultState());
    }

    public static int WrapInt(int kX, final int kLowerBound, final int kUpperBound) {
        final int range_size = kUpperBound - kLowerBound + 1;
        if (kX < kLowerBound)
            kX += range_size * ((kLowerBound - kX) / range_size + 1);
        return kLowerBound + (kX - kLowerBound) % range_size;
    }

    public static double getYawFromVec3d(Vec3d cvec) {
        return Math.atan(cvec.x / -cvec.z);
    }

    public static double getPitchFromVec3d(Vec3d cvec) {
        return Math.atan(Math.sqrt((Math.pow(cvec.x, 2) + Math.pow(cvec.z, 2))) / cvec.y);
    }

    public static boolean isOre(Block block) {
        if(block == Blocks.REDSTONE_ORE || block == Blocks.COAL_ORE || block == Blocks.DIAMOND_ORE ||
                block == Blocks.GOLD_ORE || block == Blocks.IRON_ORE || block == Blocks.EMERALD_ORE ||
                block == Blocks.LAPIS_ORE || block == Blocks.LIT_REDSTONE_ORE || block instanceof MythriaOre)
            return true;
        return false;
    }

    @SideOnly(Side.CLIENT)
    public static void updateClientGuiScreens() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.currentScreen instanceof PerkMenuGui) {
            final PerkMenuGui gui = (PerkMenuGui) mc.currentScreen;
            final Container c = gui.getInventory();
            if (c instanceof PerkMenuGuiContainer) {
                final PerkMenuGuiContainer pmc = (PerkMenuGuiContainer) c;
                pmc.setContents();
            }
        }
        if (mc.currentScreen instanceof GuiAttribute) {
            final GuiAttribute gui = (GuiAttribute) mc.currentScreen;
            final Container c = gui.getInventory();
            if (c instanceof ContainerAttribute) {
                final ContainerAttribute pmc = (ContainerAttribute) c;
                pmc.setContents();
            }
        }
    }

    public static boolean isLilasiaMob(EntityLivingBase entity) {
        return entity instanceof EntityZombie || entity instanceof EntitySkeleton || entity instanceof EntitySpider ||
                entity instanceof EntityWitch || entity instanceof EntityCreeper;
    }

    public static boolean isFireMob(EntityLivingBase entity) {
        return entity instanceof EntityBlaze || entity instanceof EntityMagmaCube || entity instanceof EntityGhast ||
                entity instanceof EntityWitherSkeleton || entity instanceof EntityPigZombie;
    }

    public static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public static boolean isWaterMob(EntityLivingBase entity) {
        return entity instanceof EntityGuardian || entity instanceof EntityElderGuardian;
    }

    public static boolean isEarthMob(EntityLivingBase entity) {
        return entity instanceof EntityCaveSpider || entity instanceof EntitySilverfish;
    }

    public static boolean isNatureMob(EntityLivingBase entity) {
        return entity instanceof EntityAnimal;
    }

    public static BlockPos getRandomLocationInRadius(int radius, BlockPos position, World worldIn) {
        boolean accept = false;
        Vec3d v = Vec3d.ZERO;
        while(accept == false) {
            double x = (Math.random() - 0.5) * radius * 2;
            double z = (Math.random() - 0.5) * radius * 2;
            v = new Vec3d(x + position.getX(), position.getY(), z + position.getZ());
            if(v.distanceTo(new Vec3d(position.getX(), position.getY(), position.getZ())) < radius) accept = true;
        }
        BlockPos pos = worldIn.getTopSolidOrLiquidBlock(new BlockPos(v));
        return pos;
    }

    public static boolean isHammer(Item item) {
        return item instanceof MythriaItemHammer;
    }
}
