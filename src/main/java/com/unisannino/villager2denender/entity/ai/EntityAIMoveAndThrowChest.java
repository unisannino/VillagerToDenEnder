package com.unisannino.villager2denender.entity.ai;

import net.minecraft.block.BlockChest;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.unisannino.villager2denender.entity.EntityDenender;

public class EntityAIMoveAndThrowChest extends EntityAIMoveAndWorkBlock
{
	private InventoryBasic invDenender;

	public EntityAIMoveAndThrowChest(EntityDenender p_i45888_1_, double p_i45888_2_)
	{
		super(p_i45888_1_, p_i45888_2_, 16);
		this.invDenender = this.theDenender.func_175551_co();
	}

	@Override
	protected boolean isTarget(BlockPos targetPos, World world)
	{
		if(!(world.getBlockState(targetPos).getBlock() instanceof BlockChest))
		{
			return false;
		}

		TileEntityChest chest = (TileEntityChest) world.getTileEntity(targetPos);
    	int empty = 0;

        if (chest != null)
        {
            int i = chest.getSizeInventory();

            for (int j = 0; j < i; ++j)
            {
                if (chest.getStackInSlot(j) == null)
                {
                    empty++;
                }
            }
        }

        return empty >= this.invDenender.getSizeInventory();
	}

	@Override
	protected boolean isWorkPos(BlockPos targetPos, World world)
	{
		return !world.getBlockState(targetPos).getBlock().isOpaqueCube() && !world.getBlockState(targetPos.up()).getBlock().isOpaqueCube();
	}

	@Override
	protected boolean getWorkCondition()
	{
		return this.theDenender.canFoodThrowChest();
	}

	@Override
	protected void workByBlock(World world, BlockPos basePos)
	{
		if(!(world.getBlockState(basePos).getBlock() instanceof BlockChest))
		{
			return;
		}

		TileEntityChest chest = (TileEntityChest) world.getTileEntity(basePos);

        if (chest != null)
        {
        	ItemStack item, item2;

        	for(int i = 0;i < this.invDenender.getSizeInventory(); i++)
        	{
        		item = this.invDenender.getStackInSlot(i);

            	if(item != null)
            	{
            		for (int j = 0; j < chest.getSizeInventory(); j++)
					{
            			if(chest.getStackInSlot(j) == null)
						{
                    		chest.setInventorySlotContents(j, item);
                    		this.invDenender.setInventorySlotContents(i, null);
                    		break;
						}
            			else if(chest.getStackInSlot(j) != null)
            			{
            				item2 = chest.getStackInSlot(j);

            				if(item2.getItem() == item.getItem() && item2.stackSize + item.stackSize <= item.getMaxStackSize())
            				{
            					chest.setInventorySlotContents(j, new ItemStack(item.getItem(), item.stackSize + item2.stackSize));
	                    		this.invDenender.setInventorySlotContents(i, null);
	                    		break;
            				}
						}
					}
            	}
        	}
        }
	}
}
