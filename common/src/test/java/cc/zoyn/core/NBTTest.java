package cc.zoyn.core;

import cc.zoyn.core.util.nms.nbt.data.TagCompound;
import org.junit.Assert;
import org.junit.Test;

/**
 * NBT 测试
 *
 * @author Zoyn
 * @since 2017-12-18
 */
public class NBTTest {

    @Test
    public void testNBT() {
        TagCompound compound = new TagCompound();
        compound.putString("233", "测试")
                .putString("二三三", "测试2");
        System.out.println(compound);
        Assert.assertEquals("测试", compound.getValue().get("233").getValue());
    }

}