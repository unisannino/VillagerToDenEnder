package com.unisannino.villager2denender.entity;



import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIHarvestFarmland;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import com.google.common.collect.Lists;
import com.unisannino.villager2denender.entity.ai.EntityAIHarvestLogs;
import com.unisannino.villager2denender.entity.ai.EntityAIHarvestStemCrops;
import com.unisannino.villager2denender.entity.ai.EntityAIMoveAndThrowChest;

public class EntityDenender extends EntityVillager
{
	private boolean checkedProfession = false;
	private List<Item> pickItemList = Lists.newArrayList(Items.bread, Items.potato, Items.carrot, Items.wheat, Items.wheat_seeds, Items.melon, Item.getItemFromBlock(Blocks.pumpkin), Item.getItemFromBlock(Blocks.log), Item.getItemFromBlock(Blocks.log2));

	public EntityDenender(World worldIn)
	{
		this(worldIn, 0);
	}

	public EntityDenender(World worldIn, int professionId)
	{
		super(worldIn);
		this.setSize(0.6F, 1.8F);
	}

	//農家以外の職業でも農作業をするようにする
	private void checkProfession()
	{
		if (!this.checkedProfession)
		{
			this.checkedProfession = true;

			if(!this.isChild() && this.getProfession() != 0)
			{
				this.tasks.addTask(6, new EntityAIHarvestFarmland(this, 0.6D));
				this.tasks.addTask(6, new EntityAIHarvestStemCrops(this, 0.6D));
				this.tasks.addTask(6, new EntityAIHarvestLogs(this, 0.6D));
				this.tasks.addTask(6, new EntityAIMoveAndThrowChest(this, 0.6D));
			}
		}
	}

	//食料の数が多すぎないかチェック、多いとtrue
	private boolean isInventoryFoods2Many(int i)
	{
        for (int j = 0; j < this.func_175551_co().getSizeInventory(); ++j)
        {
            ItemStack itemstack = this.func_175551_co().getStackInSlot(j);

            if (itemstack != null)
            {
                if (itemstack.getItem() == Items.bread && itemstack.stackSize >= 3 * i || itemstack.getItem() == Items.potato && itemstack.stackSize >= 12 * i || itemstack.getItem() == Items.carrot && itemstack.stackSize >= 12 * i)
                {
                    return true;
                }

                if (itemstack.getItem() == Items.wheat && itemstack.stackSize >= 9 * i)
                {
                    return true;
                }

                if(itemstack.getItem() == Items.melon && itemstack.stackSize >= 11 * i)
                {
                	return true;
                }

                if(itemstack.getItem() == Item.getItemFromBlock(Blocks.pumpkin) && itemstack.stackSize >= 12 * i)
                {
                	return true;
                }

                if(itemstack.getItem() == Item.getItemFromBlock(Blocks.log) && itemstack.stackSize >= 12 * i)
                {
                	return true;
                }

                if(itemstack.getItem() == Item.getItemFromBlock(Blocks.log2) && itemstack.stackSize >= 12 * i)
                {
                	return true;
                }
            }
        }

        return false;
	}

    //拾うことができるアイテムか否かの判定
    private boolean canPickUpItems(Item p_175558_1_)
    {
        for (Item item : this.pickItemList)
		{
			if (p_175558_1_ == item)
			{
				return true;
			}
		}

        return false;
    }

    @Override
    public void onLivingUpdate()
    {
        this.isJumping = false;
        super.onLivingUpdate();
    }

	//アイテムを拾う処理
	@Override
    protected void func_175445_a(EntityItem p_175445_1_)
    {
        ItemStack itemstack = p_175445_1_.getEntityItem();
        Item item = itemstack.getItem();

        if (this.canPickUpItems(item))
        {
            ItemStack itemstack1 = this.func_175551_co().func_174894_a(itemstack);

            if (itemstack1 == null)
            {
                p_175445_1_.setDead();
            }
            else
            {
                itemstack.stackSize = itemstack1.stackSize;
            }
        }
    }

	//チェストへ入れるAIの正常な動作のために必要
	public boolean canFoodThrowChest()
	{
		return this.isInventoryFoods2Many(3);
	}

	//食料投げ渡しAIの正常な動作のために必要
	@Override
    public boolean func_175553_cp()
    {
        return this.isInventoryFoods2Many(1);
    }

	//食料投げ渡しAIの正常な動作のために必要
	@Override
    public boolean func_175555_cq()
    {
        return this.isInventoryFoods2Many(2);
    }

	//作物回収と食料投げ渡しAIの正常な動作のために必要
	@Override
    public boolean func_175557_cr()
    {
		return !this.isInventoryFoods2Many(5);
    }

	//農家以外の職業でも農作業をするようにする、大人への成長時に適用？
	@Override
    protected void func_175500_n()
    {
        if (!isChild() && this.getProfession() != 0)
        {
        	this.tasks.addTask(8, new EntityAIHarvestFarmland(this, 0.6D));
            this.tasks.addTask(8, new EntityAIHarvestStemCrops(this, 0.6D));
			this.tasks.addTask(6, new EntityAIHarvestLogs(this, 0.6D));
			this.tasks.addTask(6, new EntityAIMoveAndThrowChest(this, 0.6D));
        }

        super.func_175500_n();
    }

	//Worldに誕生したときに呼ばれる？
	@Override
	public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_)
	{
		p_180482_2_ = super.func_180482_a(p_180482_1_, p_180482_2_);
		this.checkProfession();
		return p_180482_2_;
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);

		this.checkProfession();
	}

	@Override
    protected String getLivingSound()
    {
        return this.isTrading() ? "mob.endermen.idle" : "mob.endermen.idle";
    }

	@Override
    protected String getHurtSound()
    {
        return "mob.endermen.hit";
    }

	@Override
    protected String getDeathSound()
    {
        return "mob.endermen.death";
    }

	@Override
    public void useRecipe(MerchantRecipe p_70933_1_)
    {
		p_70933_1_.incrementToolUses();
		this.livingSoundTime = -this.getTalkInterval();
		this.playSound("mob.endermen.idle", this.getSoundVolume(), this.getSoundPitch());
		int i = 3 + this.rand.nextInt(4);

		try
		{
			if (p_70933_1_.getToolUses() == 1 || this.rand.nextInt(5) == 0)
			{
				//privateなFieldの値の書き換えのためにリフレクションを用いる

				//this.timeUntilReset = 40;
				Field f1 = EntityVillager.class.getDeclaredField("timeUntilReset");
				f1.setAccessible(true);
				f1.setInt(this, 40);

				//this.needsInitilization = true;
				Field f2 = EntityVillager.class.getDeclaredField("needsInitilization");
				f2.setAccessible(true);
				f2.setBoolean(this, true);

				//this.isWillingToTrade = true;
				Field f3 = EntityVillager.class.getDeclaredField("isWillingToTrade");
				f3.setAccessible(true);
				f3.setBoolean(this, true);

				//this.buyingPlayer
				Field f4 = EntityVillager.class.getDeclaredField("buyingPlayer");
				f4.setAccessible(true);

				EntityPlayer ep = (EntityPlayer) f4.get(this);

				//this.lastBuyingPlayer
				Field f5 = EntityVillager.class.getDeclaredField("lastBuyingPlayer");
				f5.setAccessible(true);
				if (ep != null)
				{
					//this.lastBuyingPlayer = this.buyingPlayer.getName();
					f5.set(this, ep.getName());
				}
				else
				{
					//this.lastBuyingPlayer = null;
					f5.set(this, null);
				}

				i += 5;
			}
			if (p_70933_1_.getItemToBuy().getItem() == Items.emerald)
			{
				//this.wealth += p_70933_1_.getItemToBuy().stackSize;
				Field f6 = EntityVillager.class.getDeclaredField("wealth");
				f6.setAccessible(true);
				f6.setInt(this, f6.getInt(this) + p_70933_1_.getItemToBuy().stackSize);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (p_70933_1_.getRewardsExp())
		{
			this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY + 0.5D, this.posZ, i));
		}
    }

	//子供のインスタンス生成
	@Override
    public EntityVillager func_180488_b(EntityAgeable p_180488_1_)
    {
        EntityDenender den = new EntityDenender(this.worldObj);
        den.func_180482_a(this.worldObj.getDifficultyForLocation(new BlockPos(den)), (IEntityLivingData)null);
        return den;
    }

	@Override
    public void onStruckByLightning(EntityLightningBolt lightningBolt)
    {
        if (!this.worldObj.isRemote)
        {
            EntityEnderman entityEnderman = new EntityEnderman(this.worldObj);
            entityEnderman.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entityEnderman.func_180482_a(this.worldObj.getDifficultyForLocation(new BlockPos(entityEnderman)), (IEntityLivingData)null);
            this.worldObj.spawnEntityInWorld(entityEnderman);
            this.setDead();
        }
    }

    @Override
    public void verifySellingItem(ItemStack p_110297_1_)
    {
        if (!this.worldObj.isRemote && this.livingSoundTime > -this.getTalkInterval() + 20)
        {
            this.livingSoundTime = -this.getTalkInterval();

            if (p_110297_1_ != null)
            {
                this.playSound("mob.endermen.idle", this.getSoundVolume(), this.getSoundPitch());
            }
            else
            {
                this.playSound("mob.endermen.idle", this.getSoundVolume(), this.getSoundPitch());
            }
        }
    }

}
