package cc.zoyn.core.util.nms.nbt;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Zoyn
 * @since 2017-12-10
 */
@Getter
@ToString
public abstract class TagBase<T> {

    private TagType type;

    public abstract T getValue();

}
