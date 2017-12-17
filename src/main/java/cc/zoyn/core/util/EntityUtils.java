package cc.zoyn.core.util;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import java.util.List;

public final class EntityUtils {

    // Prevent accidental construction
    private EntityUtils() {
    }

    /**
     * 取目标玩家
     * <br />
     * get player target
     *
     * @param player 玩家
     * @return 该玩家的目标玩家
     */
    public static Player getTargetPlayer(Player player) {
        List<Player> nearPlayers = getNearbyPlayersList(player.getLocation(), 20D);

        Player target = null;
        BlockIterator bItr = new BlockIterator(player, 20);
        while (bItr.hasNext()) {
            Block block = bItr.next();
            int bx = block.getX();
            int by = block.getY();
            int bz = block.getZ();
            for (Player e : nearPlayers) {
                Location loc = e.getLocation();
                double ex = loc.getX();
                double ey = loc.getY();
                double ez = loc.getZ();
                if ((bx - 0.75D <= ex) && (ex <= bx + 1.75D) && (bz - 0.75D <= ez) && (ez <= bz + 1.75D)
                        && (by - 1 <= ey) && (ey <= by + 2.5D)) {
                    target = e;
                    break;
                }
            }
        }
        return target;
    }

    /**
     * 取附近的实体[返回数组]
     *
     * @param loc    坐标
     * @param radius 半径
     * @return 实体数组
     */
    public static Entity[] getNearbyEntitiesArrays(Location loc, double radius) {
        return getNearbyEntitiesList(loc, radius).toArray(new Entity[]{});
    }

    /**
     * 取附近的实体[返回List]
     *
     * @param loc    坐标
     * @param radius 半径
     * @return 实体集合
     */
    public static List<Entity> getNearbyEntitiesList(Location loc, double radius) {
        int Radius = (int) radius;
        int chunkRadius = Radius < 16 ? 1 : (Radius - Radius % 16) / 16;
        List<Entity> radiusEntities = Lists.newArrayList();
        for (int chX = 0 - chunkRadius; chX <= chunkRadius; ++chX) {
            for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; ++chZ) {
                int x = (int) loc.getX();
                int y = (int) loc.getY();
                int z = (int) loc.getZ();
                Entity[] entities;
                int size = (entities = (new Location(loc.getWorld(), (double) (x + chX * 16), (double) y,
                        (double) (z + chZ * 16))).getChunk().getEntities()).length;
                for (int i = 0; i < size; i++) {
                    Entity e = entities[i];
                    if (e.getLocation().distance(loc) <= radius && e.getLocation().getBlock() != loc.getBlock()) {
                        radiusEntities.add(e);
                    }
                }
            }
        }
        return radiusEntities;
    }

    /**
     * 取附近的玩家 [返回List]
     *
     * @param loc    坐标
     * @param radius 半径
     * @return 玩家集合
     */
    public static List<Player> getNearbyPlayersList(Location loc, double radius) {
        List<Entity> radiusEntities = getNearbyEntitiesList(loc, radius);
        List<Player> players = Lists.newArrayList();
        for (Entity radiusEntity : radiusEntities) {
            if (radiusEntity instanceof Player) {
                players.add((Player) radiusEntity);
            }
        }
        return players;
    }
}
