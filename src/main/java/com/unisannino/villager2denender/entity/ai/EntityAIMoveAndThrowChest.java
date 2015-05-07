package com.unisannino.villager2denender.entity.ai;

import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.unisannino.villager2denender.entity.EntityDenender;

public class EntityAIMoveAndThrowChest extends EntityAIMoveToBlock
{
	private EntityDenender theDenender;
	private InventoryBasic invDenender;
	//falseで待機、trueで動作
	private boolean stateDenender;
	private BlockPos chestPos;
	private boolean canThrow;

	public EntityAIMoveAndThrowChest(EntityDenender p_i45888_1_, double p_i45888_2_)
	{
		super(p_i45888_1_, p_i45888_2_, 16);
		this.theDenender = p_i45888_1_;
		this.invDenender = this.theDenender.func_175551_co();
		this.chestPos = BlockPos.ORIGIN;
	}

	@Override
    public boolean shouldExecute()
    {
        if (this.field_179496_a <= 0)
        {
            if (!this.theDenender.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
            {
                return false;
            }

            this.stateDenender = false;
            this.canThrow = this.theDenender.canFoodThrowChest();
        }

    	return super.shouldExecute();
    }

	@Override
    public boolean continueExecuting()
    {
        return this.stateDenender && super.continueExecuting();
    }

    public void updateTask()
    {
        super.updateTask();
        this.theDenender.getLookHelper().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.theDenender.getVerticalFaceSpeed());

        //if (this.func_179487_f() && this.stateDenender)
        if(this.theDenender.getDistanceSqToCenter(this.chestPos) <= 8.0D && this.stateDenender)
        {
            World world = this.theDenender.worldObj;
            Block block = world.getBlockState(this.chestPos).getBlock();

            if(block instanceof BlockChest)
            {
            	TileEntityChest chest = (TileEntityChest) world.getTileEntity(this.chestPos);

                if (chest == null)
                {
                    return;
                }
                else
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

            this.stateDenender = false;
            this.field_179496_a = 10;
        }
    }

    //基準位置(destinationBlock)のyが-1されていると考える？
	@Override
	protected boolean func_179488_a(World worldIn, BlockPos p_179488_2_)
	{
		//y-1対策
		BlockPos pos = p_179488_2_.up();
        Block block = worldIn.getBlockState(pos).getBlock();

        if(!block.isOpaqueCube()  && !worldIn.getBlockState(pos.up()).getBlock().isOpaqueCube())
        {
        	Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
            while (iterator.hasNext())
            {
                EnumFacing enumfacing = (EnumFacing)iterator.next();
                Block target = worldIn.getBlockState(pos.offset(enumfacing)).getBlock();

                if(target instanceof BlockChest)
                {
                	TileEntityChest chest = (TileEntityChest) worldIn.getTileEntity(pos.offset(enumfacing));
                	int empty = 0;

                    if (chest == null)
                    {
                        return false;
                    }
                    else
                    {

                        int i = chest.getSizeInventory();

                        for (int j = 0; j < i; ++j)
                        {
                            if (chest.getStackInSlot(j) == null)
                            {
                                empty++;
                            }
                        }

                        if(empty >= this.invDenender.getSizeInventory() && this.canThrow)
        				{
                        	this.chestPos = pos.offset(enumfacing);
                        	this.stateDenender = true;
                        	return true;
        				}
        			}
                }
            }
        }

        return false;
	}

}
