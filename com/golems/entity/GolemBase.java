package com.golems.entity;

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
import net.minecraft.entity.SharedMonsterAttributes;
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
import net.minecraft.village.Village;
import net.minecraft.world.World;

/**
 * Base class for golem AI, sounds, interaction, and attributes.
 * Child classes should set the texture inside entityInit.
 * @author sky01
 **/
public abstract class GolemBase extends EntityCreature implements IAnimals 
{
	private static final String KEY_PLAYER_CREATED = "isPlayerCreated";
	protected int attackTimer;
	private int homeCheckTimer = 70;
	protected float attackDamage;
	protected boolean isPlayerCreated;
	protected boolean canTakeFallDamage;
	protected boolean hasHome;
	protected ResourceLocation textureLoc;
	protected Block creativeReturn;
	Village villageObj;

	private GolemBase(World world) 
	{
		super(world);
		this.setSize(1.4F, 2.9F);
		this.setAllowFallDamage(false);
		this.stepHeight = 0.5F;
		this.experienceValue = 4 + rand.nextInt((int)this.attackDamage + 2);
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
	}
	
	public GolemBase(World world, float attack)
	{
		this(world, attack, ContentInit.golemHead);
	}

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

	public void setPlayerCreated(boolean bool)
	{
		this.isPlayerCreated = bool;
	}

	public boolean isPlayerCreated() 
	{
		return this.isPlayerCreated;
	}
	
	public void setAllowFallDamage(boolean toSet)
	{
		this.canTakeFallDamage = toSet;
	}

	public boolean getAllowFallDamage() 
	{
		return this.canTakeFallDamage;
	}

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		float damage = this.getAttackDamage();
		if(damage > 3)
		{
			damage -= 2;
		}
		this.attackTimer = 10;
		this.worldObj.setEntityState(this, (byte)4);
		boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage + rand.nextInt(Math.round(damage * 2)));

		if (flag)
		{
			entity.motionY += 0.4000000059604645D;
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
		if(this.getAllowFallDamage())
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

	/**
	 * Drop 0-2 items of this living's type. 
	 * @param recentlyHit - Whether this entity has recently been hit by a player. 
	 * @param lootingLevel - Level of Looting used to kill this mob.
	 */
	@Override
	protected void dropFewItems(boolean recentlyHit, int lootingLevel)
	{
		ItemStack dropMain = this.getGolemDrops();
		Item dropFlower = Item.getItemFromBlock(Blocks.yellow_flower);
		Item dropRedstone = Items.redstone;
		int j;
		int k;

		if(dropMain == null)
		{
			dropMain = new ItemStack(Blocks.pumpkin, 1);
		}
		else if(recentlyHit && lootingLevel > 1)
		{
			dropMain.stackSize++;
		}

		// main mob drop
		this.entityDropItem(dropMain, 0.0F);

		//drop flower with chance
		k = this.rand.nextInt(2);
		for (j = 0; j < k; ++j)
		{
			this.dropItem(dropFlower, 1);
		}

		//drop redstone with chance
		k = this.rand.nextInt(2);
		for (j = 0; j < k; ++j)
		{
			this.dropItem(dropRedstone, 1);
		}
	}

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

	////////////////////////////////////////////////////////////
	// Override ALL OF THE FOLLOWING FUNCTIONS FOR EACH GOLEM //
	////////////////////////////////////////////////////////////
	
	@Override
	protected void entityInit()
	{
		super.entityInit();
	}
	
	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	/** What this golem drops on death. Defaults to 1 pumpkin. **/
	public abstract ItemStack getGolemDrops();

	public abstract String getGolemSound();
}
