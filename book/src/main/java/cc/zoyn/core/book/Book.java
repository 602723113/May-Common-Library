package cc.zoyn.core.book;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;

/**
 * Book - 数据模型
 *
 * @author Zoyn
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private String author = "Zoyn";
    private String title = "HelloWorld";
    private List<Page> pages = Lists.newArrayList();

    public ItemStack getItem() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.setTitle(title);
        meta.setAuthor(author);
        BookUtils.setPagesAsPage(meta, pages);
        book.setItemMeta(meta);
        return book;
    }
}
