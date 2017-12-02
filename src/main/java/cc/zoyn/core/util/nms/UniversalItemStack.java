package cc.zoyn.core.util.nms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
    private short damage;
    private ItemMeta itemMeta;

    @SuppressWarnings("deprecation")
    public UniversalItemStack(int id, int amount, short damage) {
        this.material = Material.getMaterial(id);
        this.amount = amount;
        this.damage = damage;
    }

    @SuppressWarnings("deprecation")
    public UniversalItemStack(ItemStack itemStack) {
        this.material = itemStack.getType();
        this.amount = itemStack.getAmount();
        this.damage = itemStack.getData().getData();
    }

    public ItemStack asBukkitCopy() {
        return new ItemStack(material, amount, damage);
    }

}
