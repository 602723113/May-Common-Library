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

}
