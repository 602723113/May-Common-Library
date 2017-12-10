package cc.zoyn.core.util.nms.nbt.data;

import cc.zoyn.core.util.nms.nbt.TagBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Zoyn
 * @since 2017-12-10
 */
@Getter
@AllArgsConstructor
public class TagInt extends TagBase<Integer> {

    private Integer value;

}
