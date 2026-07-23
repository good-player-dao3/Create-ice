package create_ice.Nodes.Blocks.Block_Class;

import create_ice.Nodes.Groups.GroupInterface;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;

public class GroupBlocks {
    public static class Slab extends SlabBlock implements GroupInterface<SlabBlock>
    {
        public Slab(Properties properties)
        {
            super(properties);
        }
    }

    public static class Stairs extends StairBlock implements GroupInterface<Stairs>
    {
        public Stairs(Block block, Properties properties)
        {
            super(block.defaultBlockState(),properties);
        }
    }

    public static class Wall extends WallBlock implements GroupInterface<Wall>
    {
        public Wall(Properties properties)
        {
            super(properties);
        }
    }
}
