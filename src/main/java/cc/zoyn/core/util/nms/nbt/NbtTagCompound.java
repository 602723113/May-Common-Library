package cc.zoyn.core.util.nms.nbt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * @author Zoyn
 * @since 2017-12-02
 */
public class NbtTagCompound {

    private JsonObject jsonObject;
    private static final Gson PRETTY_GSON = new GsonBuilder().setPrettyPrinting().create();

}
