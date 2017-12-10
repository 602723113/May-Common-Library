package cc.zoyn.core.util.nms;

import cc.zoyn.core.util.nms.nbt.data.TagCompound;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * 通用ItemStack
 * <p>
 *
 * @author Zoyn
 * @since 2017-12-02
 */
@Getter
@Setter
@AllArgsConstructor
public class UniversalItemStack {

    private Material material;
    private int amount;
    private int subId;

    private String displayName = "";
    private List<String> lore = Lists.newArrayList();

    private TagCompound tagCompound;

    @SuppressWarnings("deprecation")
    public UniversalItemStack(int id, int amount, short damage) {
        this.material = Material.getMaterial(id);
        this.amount = amount;
        this.subId = damage;
    }

    @SuppressWarnings("deprecation")
    public UniversalItemStack(ItemStack itemStack) {
        this.material = itemStack.getType();
        this.amount = itemStack.getAmount();
        this.subId = itemStack.getData().getData();

        if (itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta.hasDisplayName()) {
                this.displayName = itemMeta.getDisplayName();
            }
            if (itemMeta.hasLore()) {
                this.lore = itemMeta.getLore();
            }
        }
    }

    public UniversalItemStack(int id, int amount, short damage, TagCompound tagCompound) {
        this.material = Material.getMaterial(id);
        this.amount = amount;
        this.subId = damage;
    }

    public ItemStack asBukkitCopy() {
        return new ItemStack(material, amount, (short) subId);
    }

}
