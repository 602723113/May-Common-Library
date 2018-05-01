package cc.zoyn.core.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public final class MathUtils {

    // Prevent accidental construction
    private MathUtils() {
    }

    /**
     * get two location's distance
     * 取两坐标点的距离<br />
     * 使用空间两点距离公式计算: √(X2 - X1)^2 + (Y2 - Y1)^2 + (Z2 - Z1)^2
     *
     * @param location1 坐标1
     * @param location2 坐标2
     * @return 坐标间的距离
     */
    public static double getDistance(Location location1, Location location2) {
        //√(X2 - X1)^2 + (Y2 - Y1)^2 + (Z2 - Z1)^2
        return Math.sqrt(Math.pow(location1.getX() - location2.getX(), 2) + Math.pow(location1.getY() - location2.getY(), 2) + Math.pow(location1.getZ() - location2.getZ(), 2));
    }

    public static Vector rotateAroundAxisY(Vector v, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double x = v.getX() * cos + v.getZ() * sin;
        double z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }

    /**
     * 取坐标后的向量
     *
     * @param loc 坐标
     * @return {@link Vector}
     */
    public static Vector getBackVector(Location loc) {
        float newZ = (float) (loc.getZ() + 1.0D * Math.sin(Math.toRadians(loc.getYaw() + 90.0F)));
        float newX = (float) (loc.getX() + 1.0D * Math.cos(Math.toRadians(loc.getYaw() + 90.0F)));
        return new Vector(newX - loc.getX(), 0.0D, newZ - loc.getZ());
    }

    /**
     * get player's direction name
     *
     * @param player a player instance
     * @return the direction name
     */
    public static String getDirectionName(Player player) {
        double rotation = (player.getLocation().getYaw() - 90.0F) % 360.0F;
        if (rotation < 0.0D) {
            rotation += 360.0D;
        }
        return (337.5D <= rotation) && (rotation < 360.0D) ? "N"
                : (292.5D <= rotation) && (rotation < 337.5D) ? "NW"
                : (247.5D <= rotation) && (rotation < 292.5D) ? "W"
                : (202.5D <= rotation) && (rotation < 247.5D) ? "SW"
                : (157.5D <= rotation) && (rotation < 202.5D) ? "S"
                : (112.5D <= rotation) && (rotation < 157.5D) ? "SE"
                : (67.5D <= rotation) && (rotation < 112.5D) ? "E"
                : (22.5D <= rotation) && (rotation < 67.5D) ? "NE"
                : (0.0D <= rotation) && (rotation < 22.5D) ? "N"
                : null;
    }

    /**
     * 取第一个坐标到第二个坐标的向量
     *
     * @param firstLocation  坐标1
     * @param secondLocation 坐标2
     * @return {@link Vector}
     */
    public static Vector getVector(Location firstLocation, Location secondLocation) {
        Vector from = new Vector(firstLocation.getX(), firstLocation.getY(), firstLocation.getZ());
        Vector to = new Vector(secondLocation.getX(), secondLocation.getY(), secondLocation.getZ());
        return to.subtract(from);
    }
}
