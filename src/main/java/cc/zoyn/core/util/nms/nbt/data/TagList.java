package cc.zoyn.core.util.nms.nbt.data;

import cc.zoyn.core.util.nms.nbt.TagBase;
import cc.zoyn.core.util.nms.nbt.TagType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author Zoyn
 * @since 2017-12-10
 */
@Getter
@AllArgsConstructor
public class TagList<T extends TagBase> extends TagBase<List<T>> {

    private TagType tagType;
    private List<T> value;

    public TagList<T> addValue(T t) {
        value.add(t);
        return this;
    }

    public TagList<T> removeValue(T t) {
        value.remove(t);
        return this;
    }

    public TagList<T> hasValue(T t) {
        value.contains(t);
        return this;
    }

    @Override
    public String toString() {
        return "";
    }
}
