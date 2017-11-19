package cc.zoyn.core.builder;

import cc.zoyn.core.util.NBTUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemStackBuilder {

    /**
     * 生成的ItemStack
     */
    private ItemStack currentItem;

    /**
     * 生成的ItemMeta
     */
    private ItemMeta currentMeta;

    public ItemStackBuilder(Material material) {
        currentItem = new ItemStack(material);
        currentMeta = currentItem.getItemMeta();
    }

    public ItemStackBuilder(Material material, int amount) {
        currentItem = new ItemStack(material, amount);
        currentMeta = currentItem.getItemMeta();
    }

    public ItemStackBuilder(Material material, int amount, short damage) {
        currentItem = new ItemStack(material, amount, damage);
        currentMeta = currentItem.getItemMeta();
    }

    public ItemStackBuilder setDisplayName(String name) {
        currentMeta.setDisplayName(name.replaceAll("&", "§"));
        return this;
    }

    public ItemStackBuilder setLore(String... lore) {
        currentMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemStackBuilder clearLore() {
        currentMeta.setLore(Arrays.asList());
        return this;
    }

    public ItemStackBuilder setLore(List<String> lore) {
        currentMeta.setLore(lore);
        return this;
    }

    public ItemStackBuilder addHideFlag(ItemFlag... flag) {
        currentMeta.addItemFlags(flag);
        return this;
    }

    public ItemStackBuilder setUnbreakable(boolean unBreakable) {
        NBTUtils.setUnbreakable(currentItem, unBreakable);
        return this;
    }

    public ItemStack build() {
        currentItem.setItemMeta(currentMeta);
        return currentItem;
    }

}