package com.unisannino.villager2denender.entity.ai;

import net.minecraft.block.BlockLog;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.unisannino.villager2denender.entity.EntityDenender;

public class EntityAIHarvestLogs extends EntityAIMoveAndDoBlock
{
	public EntityAIHarvestLogs(EntityDenender denender, double p_i45889_2_)
	{
		super(denender, p_i45889_2_, 16);
	}

	@Override
	protected boolean isTarget(BlockPos targetPos, World world)
	{
		return world.getBlockState(targetPos).getBlock() instanceof BlockLog && world.getBlockState(targetPos.up()).getBlock() instanceof BlockLog;
	}

	@Override
	protected boolean isHarvestPos(BlockPos targetPos, World world)
	{
		return !world.getBlockState(targetPos).getBlock().isOpaqueCube() && !world.getBlockState(targetPos.up()).getBlock().isOpaqueCube();
	}

	@Override
	protected void breakBlocks(World world, BlockPos basePos)
	{
		BlockPos pos;

		for (int y = -1; y <= 1; y++)
		{
			for (int x = -1; x <= 1; x++)
			{
				for (int z = -1; z <= 1; z++)
				{
					pos = new BlockPos(basePos.getX() + x, basePos.getY()+ y, basePos.getZ() + z);

					if(this.isTarget(pos, world))
					{
						super.breakBlocks(world, pos);
						this.breakBlocks(world, pos);
					}
				}
			}
		}
	}

}
