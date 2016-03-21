package com.golems.entity;

import java.util.ArrayList;
import java.util.List;

import com.golems.entity.ai.EntityAIDefendAgainstMonsters;
import com.golems.main.ContentInit;
import com.golems.main.ExtraGolems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Base class for golem AI, sounds, interaction, and attributes.
 * Child classes should set the texture inside entityInit.
 * @author sky01
 **/
public abstract class GolemBase extends EntityCreature implements IAnimals 
{
	private static final String KEY_PLAYER_CREATED = "isPlayerCreated";
	private int homeCheckTimer = 70;
	protected int attackTimer;
	protected float attackDamage;
	protected boolean isPlayerCreated;
	protected boolean canTakeFallDamage;
	protected boolean hasHome;
	protected ResourceLocation textureLoc;
	protected Block creativeReturn;
	Village villageObj;
	
	/////////////////// CONSTRUCTORS /////////////////////

	/** Private so child classes MUST use other constructors **/
	private GolemBase(World world) 
	{
		super(world);
		this.setSize(1.4F, 2.9F);
		this.setCanTakeFallDamage(false);
		this.stepHeight = 0.5F;
		this.setHomeArea((int)this.posX, (int)this.posY, (int)this.posZ, 4);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(1, new EntityAIAttackOnCollide(this, 1.0D, true));
		this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
		this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(4, new EntityAIMoveThroughVillage(this, 0.6D, true));
		this.tasks.addTask(5, new EntityAIWander(this, 0.4D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIDefendAgainstMonsters(this));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, true, IMob.mobSelector));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
		// fail-safe
		if(this.getTextureType() == null)
		{
			this.setTextureType(this.getGolemTexture("clay"));
		}
	}
	
	public GolemBase(World world, float attack, Block pickBlock)
	{
		this(world);
		this.setCreativeReturn(pickBlock);
		this.attackDamage = attack;
		this.experienceValue = 4 + rand.nextInt((int)this.attackDamage + 2);
	}
	
	public GolemBase(World world, float attack)
	{
		this(world, attack, ContentInit.golemHead);
	}
	
	////////////////// BEHAVIOR OVERRIDES ////////////////////

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	@Override
	public boolean isAIEnabled()
	{
		return true;
	}

	/**
	 * main AI tick function, replaces updateEntityActionState
	 */
	@Override
	protected void updateAITick()
	{
		// try to find home village
		if (--this.homeCheckTimer <= 0)
		{
			this.homeCheckTimer = 70 + this.rand.nextInt(50);
			this.villageObj = this.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 32);

			if (this.villageObj == null)
			{
				if(!this.hasHome)
				{
					this.setHomeArea((int)this.posX, (int)this.posY, (int)this.posZ, 32);
					this.hasHome = true;
				}
			}
			else
			{
				ChunkCoordinates chunkcoordinates = this.villageObj.getCenter();
				this.setHomeArea(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ, (int)((float)this.villageObj.getVillageRadius() * 0.6F));
			}
		}

		super.updateAITick();
	}	

	/**
	 * Decrements the entity's air supply when underwater
	 */
	@Override
	protected int decreaseAirSupply(int i)
	{
		return i;
	}

	@Override
	public boolean allowLeashing()
	{
		return true;
	}

	@Override
	protected void collideWithEntity(Entity entity)
	{
		if (entity instanceof IMob && this.getRNG().nextInt(10) == 0)
		{
			this.setAttackTarget((EntityLivingBase)entity);
		}

		super.collideWithEntity(entity);
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if (this.attackTimer > 0)
		{
			--this.attackTimer;
		}

		if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7D && this.rand.nextInt(5) == 0)
		{
			int i = MathHelper.floor_double(this.posX);
			int j = MathHelper.floor_double(this.posY - 0.20000000298023224D - (double)this.yOffset);
			int k = MathHelper.floor_double(this.posZ);
			Block block = this.worldObj.getBlock(i, j, k);
			if (block.getMaterial() != Material.air)
			{
				this.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(block) + "_" + this.worldObj.getBlockMetadata(i, j, k), this.posX + ((double)this.rand.nextFloat() - 0.5D) * (double)this.width, this.boundingBox.minY + 0.1D, this.posZ + ((double)this.rand.nextFloat() - 0.5D) * (double)this.width, 4.0D * ((double)this.rand.nextFloat() - 0.5D), 0.5D, ((double)this.rand.nextFloat() - 0.5D) * 4.0D);
			}
		}   	
	}

	/**
	 * Returns true if this entity can attack entities of the specified class.
	 */
	@Override
	public boolean canAttackClass(Class p_70686_1_)
	{
		return this.isPlayerCreated() && EntityPlayer.class.isAssignableFrom(p_70686_1_) ? false : super.canAttackClass(p_70686_1_);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		final float CRITICAL_CHANCE = 5; 	// percent chance of multiplying damage
		final float VARIANCE = 0.8F; 		// (0.0 ~ 1.0] lower number results in less variance
		float damage = this.getAttackDamage() + (float)(rand.nextDouble() - 0.5D) * VARIANCE * this.getAttackDamage();
		if(rand.nextInt(100) < CRITICAL_CHANCE)
		{
			damage *= 2.5F;
			// debug:
			//System.out.print("[Extra Golems] :: A golem scored critical damage! :: " + this.toString() + "\n");
		}
		
		this.attackTimer = 10;
		this.worldObj.setEntityState(this, (byte)4);
		boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage);

		if (flag)
		{
			entity.motionY += 0.4000000059604645D;
			// debug:
			//System.out.print("[Extra Golems] Base damage = " + this.getAttackDamage() + "; damage = " + damage + "\n");
		}

		this.playSound(this.getThrowSound(), 1.0F, 1.0F);
		return flag;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte b)
	{
		if (b == 4)
		{
			this.attackTimer = 10;
			this.playSound(this.getThrowSound(), 1.0F, 1.0F);
		}
		else
		{
			super.handleHealthUpdate(b);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
    {
		super.readFromNBT(tag);
		this.setPlayerCreated(tag.getBoolean(KEY_PLAYER_CREATED));
    }
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setBoolean(KEY_PLAYER_CREATED, this.isPlayerCreated());
	}

	@SideOnly(Side.CLIENT)
	public int getAttackTimer()
	{
		return this.attackTimer;
	}

	/**
	 * Called when the mob is falling. Calculates and applies fall damage.
	 */
	@Override
	protected void fall(float f) 
	{
		if(this.getCanTakeFallDamage())
		{
			super.fall(f);
		}
	}

	/**
	 * Plays sound of golem walking
	 */
	@Override
	protected void func_145780_a(int x, int y, int z, Block block)
	{
		this.playSound(this.getWalkingSound(), 1.0F, 1.0F);
	}

	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn()
	{
		return false;
	}

	/**
	 * Get number of ticks, at least during which the living entity will be silent.
	 */
	@Override
	public int getTalkInterval()
	{
		return 120;
	}

	/**
	 * Called when a user uses the creative pick block button on this entity.
	 *
	 * @param target The full target the player is looking at
	 * @return A ItemStack to add to the player's inventory, Null if nothing should be added.
	 */
	@Override
	public ItemStack getPickedResult(MovingObjectPosition target)
	{
		return new ItemStack(getCreativeReturn(), 1);
	}
	
	///////////////// ITEM DROP LOGIC /////////////////////

	/**
	 * Drop items of this living's type. 
	 * @param recentlyHit - Whether this entity has recently been hit by a player. 
	 * @param lootingLevel - Level of Looting used to kill this mob.
	 */
	@Override
	protected void dropFewItems(boolean recentlyHit, int lootingLevel)
	{
		// WeightedRandomChestContent(Item, metadata, minimum amount, maximum amount
		List<WeightedRandomChestContent> drops = new ArrayList();
		addDropEntry(drops, rand.nextBoolean() ? Blocks.yellow_flower : Blocks.red_flower, 0, 1, 2, 65);
		addDropEntry(drops, Items.redstone, 0, 1, 1, 20 + lootingLevel * 10);
		
		this.addGolemDrops(drops, recentlyHit, lootingLevel);

		for(WeightedRandomChestContent w : drops)
		{
			if(this.rand.nextInt(100) < w.itemWeight)
			{
				int min = w.theMinimumChanceToGenerateItem;
				int max = w.theMaximumChanceToGenerateItem;
				ItemStack toDrop = w.theItemId;

				int size = max > min ? min + rand.nextInt(max - min) : min;
				toDrop.stackSize = size;
				this.entityDropItem(toDrop, 0.0F);
			}
		}
	}
	
	public static boolean addGuaranteedDropEntry(List<WeightedRandomChestContent> list, ItemStack in)
	{
		return addDropEntry(list, in.getItem(), in.getItemDamage(), in.stackSize, in.stackSize, 100);
	}
	
	public static boolean addDropEntry(List<WeightedRandomChestContent> list, Block in, int meta, int minAmount, int maxAmount, int percentChance)
	{
		return addDropEntry(list, Item.getItemFromBlock(in), meta, minAmount, maxAmount, percentChance);
	}
	
	public static boolean addDropEntry(List<WeightedRandomChestContent> list, Item in, int meta, int minAmount, int maxAmount, int percentChance)
	{
		return list.add(new WeightedRandomChestContent(in, meta, minAmount, maxAmount, percentChance));
	}
	
	public static boolean removeDropEntry(List<WeightedRandomChestContent> list, Item in, int meta)
	{
		boolean flag = false;
		for(WeightedRandomChestContent w : list)
		{
			if(w.theItemId.getItem() == in && (meta == OreDictionary.WILDCARD_VALUE || w.theItemId.getItemDamage() == meta))
			{
				list.remove(w);
				flag = true;
			}
		}
		return flag;
	}
	
	///////////////// NON-OVERRIDEN GETTERS/SETTERS ////////////////////

	public void setTextureType(ResourceLocation texturelocation)
	{
		this.textureLoc = texturelocation;
	}

	public ResourceLocation getTextureType()
	{
		return this.textureLoc;
	}

	public void setCreativeReturn(Block blockToReturn)
	{
		this.creativeReturn = blockToReturn;
	}

	public Block getCreativeReturn()
	{
		return this.creativeReturn;
	}

	/** Sets the attack damage for this golem */
	protected void setAttackDamage(float f)
	{
		this.attackDamage = f;
	}

	public float getAttackDamage()
	{
		return this.attackDamage;
	}

	public Village getVillage() 
	{
		return this.villageObj;
	}
	
	public void setPlayerCreated(boolean bool)
	{
		this.isPlayerCreated = bool;
	}

	public boolean isPlayerCreated() 
	{
		return this.isPlayerCreated;
	}
	
	public void setCanTakeFallDamage(boolean toSet)
	{
		this.canTakeFallDamage = toSet;
	}

	public boolean getCanTakeFallDamage() 
	{
		return this.canTakeFallDamage;
	}
	
	/** Not used in this project. Will be used in the WAILA addon **/
	public boolean doesInteractChangeTexture()
	{
		return false;
	}
	
	////////////////////// TEXTURE HELPERS ///////////////////////////
	
	/** Makes a ResourceLocation of a texture using modid 'golems' **/
	public static ResourceLocation getGolemTexture(String texture)
	{
		return getGolemTexture(ExtraGolems.MODID, texture);
	}
	
	/** 
	 * Makes a ResourceLocation using the passed mod id and part of the texture name.
	 * Texture should be at 'assets/{@link MODID}/textures/entity/golem_{@link texture}.png'
	 **/
	public static ResourceLocation getGolemTexture(final String MODID, String texture)
	{
		return new ResourceLocation(MODID + ":textures/entity/golem_" + texture + ".png");
	}

	///////////////////// SOUND OVERRIDES ////////////////////

	/** Returns the sound this mob makes while it's alive */
	@Override
	protected String getLivingSound()
	{
		return getGolemSound();
	}

	protected String getWalkingSound()
	{
		return getGolemSound();
	}

	/** Returns the sound this mob makes when it attacks */
	public String getThrowSound() 
	{
		return getGolemSound();	//"mob.irongolem.throw";
	}

	@Override
	protected String getHurtSound()
	{
		return getGolemSound();
	}

	@Override
	protected String getDeathSound()
	{
		return getGolemSound();
	}
	
	@Override
	protected void entityInit()
	{
		super.entityInit();
		applyTexture();
	}
	
	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();
		applyAttributes();
	}

	////////////////////////////////////////////////////////////
	// OVERRIDE ALL OF THE FOLLOWING FUNCTIONS FOR EACH GOLEM //
	////////////////////////////////////////////////////////////
	
	/** Called from applyEntityAttributes. Use this to adjust health, speed, knockback resistance, etc. **/
	protected abstract void applyAttributes();
	
	/** Called from entityInit. Call this.setTextureType in here somewhere. 
	 * Example implementation:
	 * 
	 * protected void applyTexture()
	 * {
	 * 		this.setTextureType(this.getGolemTexture("texture_suffix")) 
	 * } 
	 **/
	protected abstract void applyTexture();

	/** 
	 * Called each time a golem dies. Passes a list of drops already containing some defaults.
	 * Add entries using GolemBase.addDropEntry and GolemBase.addGuaranteedDropEntry 
	 **/
	public abstract void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel);

	/** A String to represent the sound to play when attacking, walking, hurt, and on death **/
	public abstract String getGolemSound();
}
