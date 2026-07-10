package create_ice.Nodes.Groups;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface GroupBlock<T extends Block> {

    Item asItem();

    default T addGroup()
    {
        Groups.addItem(this.asItem());
        return (T)this;
    }
}
