package me.may.core.util;

import me.may.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EffectUtils {

    public static int getLine(Location loc1, Location loc2) {
        return (int)
                Math.sqrt(
                        Math.pow(loc1.getX() - loc2.getX(), 2) +
                                Math.pow(loc1.getY() - loc2.getY(), 2) +
                                Math.pow(loc1.getZ() - loc2.getZ(), 2)
                );
    }

    /**
     * 创建三维旋转特效
     *
     * @param player 玩家
     * @throws Exception
     */
    public void createHelix(Player player) throws Exception {
        Location loc = player.getLocation();
        int radius = 2;
        for (double y = 0; y <= 50; y += 0.05) {
            double x = radius * Math.cos(y);
            double z = radius * Math.sin(y);
            Object packet = NMSUtils.getNMSClass("PacketPlayOutWorldParticles").newInstance();
            packet = packet.getClass()
                    .getConstructor(String.class, float.class, float.class, float.class, float.class, float.class,
                            float.class, float.class, int.class)
                    .newInstance("fireworksSpark", (float) (loc.getX() + x), (float) (loc.getY() + y),
                            (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
            for (Player online : Bukkit.getOnlinePlayers()) {
                Object nmsPlayer = online.getClass().getMethod("getHandle").invoke(online);
                Object playerConnection = nmsPlayer.getClass().getDeclaredField("playerConnection").get(nmsPlayer);
                playerConnection.getClass().getMethod("sendPacket", NMSUtils.getNMSClass("Packet"))
                        .invoke(playerConnection, packet);
            }
        }
    }

    // public void createHelix(Player player) {
    // Location loc = player.getLocation();
    // int radius = 2;
    // for(double y = 0; y <= 50; y+=0.05) {
    // double x = radius * Math.cos(y);
    // double z = radius * Math.sin(y);
    // PacketPlayOutWorldParticles packet = new
    // PacketPlayOutWorldParticles("fireworksSpark", (float) (loc.getX() + x),
    // (float) (loc.getY() + y), (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
    // for(Player online : Bukkit.getOnlinePlayers()) {
    // ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet);
    // }
    // }
    // }

    public static void testEffect(Player player, int amount) {
        BukkitTask task = Core.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(Core.getInstance(),
                new Runnable() {
                    @SuppressWarnings("deprecation")
                    @Override
                    public void run() {
                        // 初始化loc
                        Location loc = player.getLocation();
                        player.getWorld().playEffect(loc.clone().add(0D, 2.0D, 0D), Effect.HAPPY_VILLAGER, 3);
                    }
                }, 0L, 30L);

        int taskid = task.getTaskId();
        System.out.println(taskid);
    }

    public static void testEffect2(Player player) {
        BukkitTask task = Core.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(Core.getInstance(),
                new Runnable() {
                    @SuppressWarnings("deprecation")
                    @Override
                    public void run() {
                        // 初始化loc
                        Location loc = player.getLocation();
                        for (Location loc2 : buildCircle(loc, 2, 1)) {
                            player.getWorld().playEffect(loc2, Effect.HAPPY_VILLAGER, 10);
                        }
                    }
                }, 0L, 0L);

        int taskid = task.getTaskId();
        System.out.println(taskid);
    }

    public static void testEffect3(Player player) {
        BukkitTask task = Core.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(Core.getInstance(),
                new Runnable() {
                    @SuppressWarnings("deprecation")
                    @Override
                    public void run() {
                        Location loc = player.getLocation();
                        for (Location loc2 : buildLine(loc, loc.clone().add(0.75D, 1D, 0.75D))) {
                            player.getWorld().playEffect(loc2, Effect.HAPPY_VILLAGER, 10);
                        }
                    }
                }, 0L, 0L);

        int taskid = task.getTaskId();
        System.out.println(taskid);
    }

    private static HashMap<List<ArmorStand>, Location> lastLoc = new HashMap<List<ArmorStand>, Location>();

    @SuppressWarnings("deprecation")
    public static void callCircle(final Player p, int id, double amount, final int time) {
        // 取玩家的坐标
        Location loc = p.getLocation();
        loc.setDirection(new Vector());
        loc.add(0.0D, -1.0D, 0.0D);
        // 之后将头向下

        // 建立一个存放ArmorStand的list
        final List<ArmorStand> list = new ArrayList<ArmorStand>();
        // 遍历要生成的个数
        for (int i = 0; i < amount; i++) {
            // 计算第i个ArmorStand的位置 公式为 ---> i除以全部数量乘以π再乘以2
            double rand = i / amount * 3.141592653589793D * 2.0D;
            double dx = Math.cos(rand);
            double dz = Math.sin(rand);
            ArmorStand as = (ArmorStand) loc.getWorld().spawn(loc.clone().add(dx, 0.0D, dz), ArmorStand.class);
            as.setVisible(false);
            as.setGravity(false);
            as.setHelmet(new ItemStack(id));
            as.setHeadPose(EulerAngle.ZERO.setY(rand));
            list.add(as);
        }

        // 添加到 第一次开始执行时的Map
        lastLoc.put(list, p.getLocation());

        // 异步操作
        Bukkit.getScheduler().runTaskTimer(Core.getInstance(), new Runnable() {
            // 线程死亡开关
            boolean dead;
            // 倒数
            int countDown = 0;

            public void run() {
                // 如果线程死亡开启则返回
                if (dead) {
                    return;
                }
                // 判断玩家是否在线,并且是活着的
                if ((p.isOnline()) && (!p.isDead()) && (countDown < time)) {
                    // 倒数器 自加1
                    countDown += 1;
                    // 取玩家
                    Vector v = p.getLocation().subtract((Location) lastLoc.get(list)).toVector();
                    for (ArmorStand as : list) {
                        as.teleport(as.getLocation().add(v));
                        double rand = 1 / amount * 3.141592653589793D * 2.0D;
                        double dx = Math.cos(rand);
                        double dz = Math.sin(rand);
                        p.playEffect(lastLoc.get(list).clone().add(dx, 0.0D, dz), Effect.HAPPY_VILLAGER, 3);
                    }
                    lastLoc.put(list, p.getLocation());
                } else {
                    for (ArmorStand as : list) {
                        as.remove();
                    }
                    dead = true;
                }
            }
        }, 1L, 1L);
    }

    public static List<Location> buildCircle(Location center, int radius, float precision) {
        List<Location> ret = new ArrayList<Location>();
        for (float i = precision; i < 6.283185307179586D; i += precision) {
            ret.add(new Location(center.getWorld(), Math.cos(i) * radius + center.getX(), center.getY(),
                    Math.sin(i) * radius + center.getZ()));
        }
        return ret;
    }

    public static List<Location> buildLine(Location loc1, Location loc2) {
        List<Location> line = new ArrayList<Location>();
        int dx = Math.max(loc1.getBlockX(), loc2.getBlockX()) - Math.min(loc1.getBlockX(), loc2.getBlockX());
        int dy = Math.max(loc1.getBlockY(), loc2.getBlockY()) - Math.min(loc1.getBlockY(), loc2.getBlockY());
        int dz = Math.max(loc1.getBlockZ(), loc2.getBlockZ()) - Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        int x1 = loc1.getBlockX();
        int x2 = loc2.getBlockX();
        int y1 = loc1.getBlockY();
        int y2 = loc2.getBlockY();
        int z1 = loc1.getBlockZ();
        int z2 = loc2.getBlockZ();
        int x = 0;
        int y = 0;
        int z = 0;
        int i = 0;
        int d = 1;
        switch (findHighest(dx, dy, dz)) {
            case 1:
                i = 0;
                d = 1;
                if (x1 > x2) {
                    d = -1;
                }
                x = loc1.getBlockX();
                do {
                    i++;
                    y = y1 + (x - x1) * (y2 - y1) / (x2 - x1);
                    z = z1 + (x - x1) * (z2 - z1) / (x2 - x1);
                    line.add(new Location(loc1.getWorld(), x, y, z));
                    x += d;
                } while (i <= Math.max(x1, x2) - Math.min(x1, x2));
                break;
            case 2:
                i = 0;
                d = 1;
                if (y1 > y2) {
                    d = -1;
                }
                y = loc1.getBlockY();
                do {
                    i++;
                    x = x1 + (y - y1) * (x2 - x1) / (y2 - y1);
                    z = z1 + (y - y1) * (z2 - z1) / (y2 - y1);
                    line.add(new Location(loc1.getWorld(), x, y, z));
                    y += d;
                } while (i <= Math.max(y1, y2) - Math.min(y1, y2));
                break;
            case 3:
                i = 0;
                d = 1;
                if (z1 > z2) {
                    d = -1;
                }
                z = loc1.getBlockZ();
                do {
                    i++;
                    y = y1 + (z - z1) * (y2 - y1) / (z2 - z1);
                    x = x1 + (z - z1) * (x2 - x1) / (z2 - z1);
                    line.add(new Location(loc1.getWorld(), x, y, z));
                    z += d;
                } while (i <= Math.max(z1, z2) - Math.min(z1, z2));
        }
        return line;
    }

    private static int findHighest(int x, int y, int z) {
        if ((x >= y) && (x >= z)) {
            return 1;
        }
        if ((y >= x) && (y >= z)) {
            return 2;
        }
        return 3;
    }
}
