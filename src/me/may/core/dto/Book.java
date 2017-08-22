package me.may.core.dto;

import me.may.core.util.BookUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Book - 数据模型
 *
 * @author May_Speed
 */
public class Book {

    private String author = "notch";
    private String title = "SouthOfSouth";
    private List<Page> pages = new ArrayList<Page>();

    @Override
    public String toString() {
        return "Book [author=" + author + ", title=" + title + ", pages=" + pages + "]";
    }

    public Book(String author, String title, List<Page> pages) {
        super();
        this.author = author;
        this.title = title;
        this.pages = pages;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

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
