package cc.zoyn.core.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.DumperOptions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * 配置文件工具类
 *
 * @author Zoyn
 * @since 2017-12-17
 */
public final class ConfigurationUtils {

    // Prevent accidental construction
    private ConfigurationUtils() {
    }

    /**
     * 用路径读取Yml
     * <br />
     * load Yml with the path
     *
     * @param path 路径
     * @return 那个Yml的FileConfiguration对象
     */
    public static FileConfiguration loadYml(String path) {
        return loadYml(new File(path));
    }

    /**
     * 保存Yml
     * <br />
     * save a Yml
     *
     * @param fileConfiguration 该Yml的FileConfiguration对象
     * @param file              文件
     */
    public static void saveYml(FileConfiguration fileConfiguration, File file) {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            System.out.println("错误:" + e.toString());
        }
    }

    /**
     * 读取Yml
     * <br />
     * load Yml with the file
     *
     * @param file 文件
     * @return YML的FileConfiguration对象
     */
    public static FileConfiguration loadYml(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("错误:" + e.toString());
            }
        }
        FileConfiguration YML = YamlConfiguration.loadConfiguration(file);
        DumperOptions yamlOptions = null;
        try {
            Field f = YamlConfiguration.class.getDeclaredField("yamlOptions");
            f.setAccessible(true);
            yamlOptions = new DumperOptions() {
                public void setAllowUnicode(boolean allowUnicode) {
                    super.setAllowUnicode(false);
                }

                public void setLineBreak(DumperOptions.LineBreak lineBreak) {
                    super.setLineBreak(DumperOptions.LineBreak.getPlatformLineBreak());
                }
            };
            yamlOptions.setLineBreak(DumperOptions.LineBreak.getPlatformLineBreak());
            f.set(YML, yamlOptions);
        } catch (ReflectiveOperationException ex) {
            System.out.println("错误:" + ex.toString());
        }
        return YML;
    }

}
