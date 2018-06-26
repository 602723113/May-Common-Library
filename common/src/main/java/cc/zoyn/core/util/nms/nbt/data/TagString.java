package cc.zoyn.core.util.nms.nbt.data;

import cc.zoyn.core.util.nms.nbt.TagBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Zoyn
 * @since 2017-12-02
 */
@Getter
@AllArgsConstructor
public class TagString extends TagBase<String> {

    private String value;

}
