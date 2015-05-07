package com.unisannino.villager2denender.event;

import java.lang.reflect.Field;
import java.util.Random;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.SpecialSpawn;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;

import com.google.common.eventbus.Subscribe;
import com.unisannino.villager2denender.entity.EntityDenender;

public class EventVillagerJoinWorld
{
	@SubscribeEvent
	public void joinVillagers(EntityJoinWorldEvent event)
	{
		if(!event.entity.worldObj.isRemote)
		{
			if (event.entity instanceof EntityVillager && !(event.entity instanceof EntityDenender) && !event.entity.isDead)
			{
				World world = event.entity.worldObj;
				EntityVillager han = (EntityVillager) event.entity;
				EntityDenender denender = new EntityDenender(han.worldObj);
				this.makeDenEnderman(han, denender);
				han.worldObj.removeEntity(han);
				world.spawnEntityInWorld(denender);
			}
		}
	}

	private void makeDenEnderman(EntityVillager han, EntityDenender denender)
	{
		BlockPos pos = han.getPosition();
		denender.setPosition(pos.getX(), pos.getY(), pos.getZ());
		denender.setProfession(han.getProfession());

		Field wealth, career, careerlv, willing, recipe;
		try
		{
			wealth = EntityVillager.class.getDeclaredField("wealth");
			wealth.setAccessible(true);
			wealth.setInt(denender, wealth.getInt(han));

			career = EntityVillager.class.getDeclaredField("careerId");
			career.setAccessible(true);
			if (career.getInt(han) > 0)
			{
				career.setInt(denender, career.getInt(han));
			}
			else
			{
				int i = 1;
				switch (han.getProfession())
				{

				case 0:
					i = denender.getRNG().nextInt(4) + 1;
					break;

				}
				career.setInt(denender, i);
			}

			careerlv = EntityVillager.class.getDeclaredField("careerLevel");
			careerlv.setAccessible(true);
			careerlv.setInt(denender, careerlv.getInt(han));

			willing = EntityVillager.class.getDeclaredField("isWillingToTrade");
			willing.setAccessible(true);
			denender.func_175549_o(willing.getBoolean(han));

			MerchantRecipeList recipeList = han.getRecipes(null);
			recipe = EntityVillager.class.getDeclaredField("buyingList");
			recipe.setAccessible(true);
			recipe.set(denender, recipeList);

	        for (int i = 0; i < han.func_175551_co().getSizeInventory(); ++i)
	        {
	            ItemStack itemstack = han.func_175551_co().getStackInSlot(i);

	            if (itemstack != null)
	            {
	            	denender.func_175551_co().func_174894_a(itemstack);
	            }
	        }
		}
		catch (Exception e)
		{
			e.printStackTrace();

		}
	}
}
