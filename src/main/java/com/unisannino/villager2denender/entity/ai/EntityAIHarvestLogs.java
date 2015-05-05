package com.unisannino.villager2denender.entity.ai;

import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.unisannino.villager2denender.entity.EntityDenender;

public class EntityAIHarvestLogs extends EntityAIHarvestStemCrops
{
	private EntityDenender theDenender;
	private boolean canHervest, hasSeeds;
	//回収時true、待機時false
	private boolean stateDenender;

	//EntityAIHarvestFarmlandとほぼ同一処理
	public EntityAIHarvestLogs(EntityDenender denender, double p_i45889_2_)
	{
		super(denender, p_i45889_2_);
	}

    //基準位置(destinationBlock)のyが-1されていると考える？
	@Override
    protected boolean func_179488_a(World worldIn, BlockPos p_179488_2_)
    {
		//y-1対策
		BlockPos pos = p_179488_2_.up();
	    Block top = worldIn.getBlockState(pos).getBlock();
        Block block = worldIn.getBlockState(p_179488_2_).getBlock();

        if(block == Blocks.dirt && worldIn.getBlockState(pos.up()).getBlock().isOpaqueCube())
        {
        	Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
            while (iterator.hasNext())
            {
                EnumFacing enumfacing = (EnumFacing)iterator.next();
                Block target = worldIn.getBlockState(pos.offset(enumfacing)).getBlock();

                if(this.isTarget(target) && this.canHervest)
                {
                	this.cropPos = pos.offset(enumfacing);
                   	this.stateDenender = true;
                	return true;
                }
            }
        }
        return false;
    }

	@Override
	protected boolean isTarget(Block target)
	{
		return target == Blocks.log2 || target == Blocks.log;
	}

	@Override
	protected void breakBlocks(World world, BlockPos basePos)
	{
		System.out.println("breaking");
		BlockPos pos;

		for (int y = -1; y <= 1; y++)
		{
			for (int x = -1; x <= 1; x++)
			{
				for (int z = -1; z <= 1; z++)
				{
					pos = new BlockPos(basePos.getX() + x, basePos.getY()+ y, basePos.getZ() + z);

					if(this.isTarget(world.getBlockState(pos).getBlock()))
					{
						world.destroyBlock(pos, true);
						this.breakBlocks(world, pos);
					}
				}
			}
		}
	}

}
