//
// Source code recreated from a .class file by Quiltflower
//

package io.github.dailystruggle.insta_mine_deepslate.Listener;

import io.github.dailystruggle.insta_mine_deepslate.Insta_Mine_Deepslate;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public final class MiningDeepslate implements Listener {
    private static final Map<UUID,BukkitTask> potionEnd = new ConcurrentHashMap<>();

    private static final Map<Material,Material> deepslateDrops = new HashMap<>();
    static {
        deepslateDrops.put(Material.DEEPSLATE, Material.COBBLED_DEEPSLATE);
        deepslateDrops.put(Material.POLISHED_DEEPSLATE, Material.POLISHED_DEEPSLATE);
        deepslateDrops.put(Material.POLISHED_DEEPSLATE_SLAB, Material.POLISHED_DEEPSLATE_SLAB);
        deepslateDrops.put(Material.POLISHED_DEEPSLATE_STAIRS, Material.POLISHED_DEEPSLATE_STAIRS);
        deepslateDrops.put(Material.POLISHED_DEEPSLATE_WALL, Material.POLISHED_DEEPSLATE_WALL);
        deepslateDrops.put(Material.DEEPSLATE_BRICKS, Material.DEEPSLATE_BRICKS);
        deepslateDrops.put(Material.DEEPSLATE_BRICK_SLAB, Material.DEEPSLATE_BRICK_SLAB);
        deepslateDrops.put(Material.DEEPSLATE_BRICK_STAIRS, Material.DEEPSLATE_BRICK_STAIRS);
        deepslateDrops.put(Material.DEEPSLATE_BRICK_WALL, Material.DEEPSLATE_BRICK_WALL);
        deepslateDrops.put(Material.DEEPSLATE_TILES, Material.DEEPSLATE_TILES);
        deepslateDrops.put(Material.DEEPSLATE_TILE_SLAB, Material.DEEPSLATE_TILE_SLAB);
        deepslateDrops.put(Material.DEEPSLATE_TILE_STAIRS, Material.DEEPSLATE_TILE_STAIRS);
        deepslateDrops.put(Material.DEEPSLATE_TILE_WALL, Material.DEEPSLATE_TILE_WALL);
        deepslateDrops.put(Material.CHISELED_DEEPSLATE, Material.CHISELED_DEEPSLATE);
    }

    private static final Map<Material,Sound> deepslateSounds = new HashMap<>();
    static {
        deepslateSounds.put(Material.DEEPSLATE, Sound.BLOCK_DEEPSLATE_BREAK);
        deepslateSounds.put(Material.POLISHED_DEEPSLATE, Sound.BLOCK_POLISHED_DEEPSLATE_BREAK);
        deepslateSounds.put(Material.POLISHED_DEEPSLATE_SLAB, Sound.BLOCK_POLISHED_DEEPSLATE_BREAK);
        deepslateSounds.put(Material.POLISHED_DEEPSLATE_STAIRS, Sound.BLOCK_POLISHED_DEEPSLATE_BREAK);
        deepslateSounds.put(Material.POLISHED_DEEPSLATE_WALL, Sound.BLOCK_POLISHED_DEEPSLATE_BREAK);
        deepslateSounds.put(Material.DEEPSLATE_BRICKS, Sound.BLOCK_DEEPSLATE_BRICKS_BREAK);
        deepslateSounds.put(Material.DEEPSLATE_BRICK_SLAB, Sound.BLOCK_DEEPSLATE_BRICKS_BREAK);
        deepslateSounds.put(Material.DEEPSLATE_BRICK_STAIRS, Sound.BLOCK_DEEPSLATE_BRICKS_BREAK);
        deepslateSounds.put(Material.DEEPSLATE_BRICK_WALL, Sound.BLOCK_DEEPSLATE_BRICKS_BREAK);
        deepslateSounds.put(Material.DEEPSLATE_TILES, Sound.BLOCK_DEEPSLATE_TILES_BREAK);
        deepslateSounds.put(Material.DEEPSLATE_TILE_SLAB, Sound.BLOCK_DEEPSLATE_TILES_BREAK);
        deepslateSounds.put(Material.DEEPSLATE_TILE_STAIRS, Sound.BLOCK_DEEPSLATE_TILES_BREAK);
        deepslateSounds.put(Material.DEEPSLATE_TILE_WALL, Sound.BLOCK_DEEPSLATE_TILES_BREAK);
        deepslateSounds.put(Material.CHISELED_DEEPSLATE, Sound.BLOCK_DEEPSLATE_BRICKS_BREAK);
    }

    public MiningDeepslate() {
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onMine(PlayerInteractEvent e) {
        if(e == null) return;
        if (e.getAction() != Action.LEFT_CLICK_BLOCK
                || e.getPlayer().getGameMode() != GameMode.SURVIVAL) {
            return;
        }

        Block var10000 = e.getClickedBlock();
        if (var10000 == null) {
            return;
        }

        Block block = var10000;
        ItemStack var10 = e.getItem();
        if (var10 == null) {
            return;
        }

        ItemStack tool = var10;

        Material type = block.getType();
        if (!deepslateDrops.containsKey(type) || tool.getType() != Material.NETHERITE_PICKAXE) {
            return;
        }

        ItemMeta var11 = tool.getItemMeta();
        if (var11 == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.bukkit.inventory.meta.Damageable");
        }

        Damageable toolMeta = (Damageable) var11;
        Integer digSpeed = toolMeta.getEnchants().get(Enchantment.DIG_SPEED);
        byte requiredSpeed = 5;
        if (digSpeed == null || digSpeed < requiredSpeed) {
            return;
        }

        PotionEffect strengthEffect = e.getPlayer().getPotionEffect(PotionEffectType.INCREASE_DAMAGE);
        byte requiredStrength = 1;
        if (strengthEffect == null || strengthEffect.getAmplifier() < requiredStrength) {
            return;
        }

        PotionEffect hasteEffect = e.getPlayer().getPotionEffect(PotionEffectType.FAST_DIGGING);
        int haste = hasteEffect == null ? 0 : hasteEffect.getAmplifier();
        byte requiredHaste = 1;
        if (haste < requiredHaste) {
            return;
        }

        if(!potionEnd.containsKey(e.getPlayer().getUniqueId())) return;

        boolean hasSilkTouchx = toolMeta.hasEnchant(Enchantment.SILK_TOUCH);
        e.setCancelled(true);
        e.getPlayer()
                .getWorld()
                .dropItemNaturally(
                        block.getLocation(), new ItemStack(hasSilkTouchx ? type : deepslateDrops.getOrDefault(type,Material.COBBLED_DEEPSLATE))
                );


        Sound breakSound = deepslateSounds.getOrDefault(type,Sound.BLOCK_DEEPSLATE_BREAK);
        e.getPlayer().getWorld().spawnParticle(Particle.BLOCK_CRACK,block.getLocation().add(0.5,0.5,0.5),6,block.getType().createBlockData());
        block.setType(Material.AIR);
        e.getPlayer()
                .playSound(
                        block.getLocation(), breakSound, 1.0f, 1.0f
                );
        Integer var15 = toolMeta.getEnchants().get(Enchantment.DURABILITY);
        int unbreakingLevelx = var15 == null ? 0 : var15;
        if (ThreadLocalRandom.current().nextInt(unbreakingLevelx + 1) == 0) {
            toolMeta.setDamage(toolMeta.getDamage() + 1);
        }

        tool.setItemMeta(toolMeta);
    }


    @EventHandler(priority = EventPriority.LOW)
    public void onPotionDrink(PlayerItemConsumeEvent event) {
        if(event == null) return;
        if (!(event.getItem().getItemMeta() instanceof PotionMeta)) return;
        PotionMeta meta = (PotionMeta) event.getItem().getItemMeta();
        PotionType type = meta.getBasePotionData().getType();
        PotionEffectType effectType = type.getEffectType();
        if(effectType == null || !effectType.equals(PotionEffectType.INCREASE_DAMAGE)) return;
        PotionData potionData = meta.getBasePotionData();
        if(!potionData.isUpgraded()) return;
        UUID id = event.getPlayer().getUniqueId();
        BukkitTask task = potionEnd.get(id);
        if(task!=null && !task.isCancelled()) task.cancel();
        task = Bukkit.getScheduler().runTaskLater(Insta_Mine_Deepslate.getInstance(), () -> {
            potionEnd.remove(id);
        }, potionData.isExtended() ? (8 * 30 * 20) : (3 * 30 * 20));
        potionEnd.put(id,task);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPotionSplash(PotionSplashEvent event) {
        if(event == null) return;
        PotionMeta meta = (PotionMeta) event.getPotion().getItem().getItemMeta();
        if(meta == null) return;
        PotionType type = meta.getBasePotionData().getType();
        PotionEffectType effectType = type.getEffectType();
        if(effectType == null || !effectType.equals(PotionEffectType.INCREASE_DAMAGE)) return;
        PotionData potionData = meta.getBasePotionData();
        if(!potionData.isUpgraded()) return;

        for (LivingEntity affectedEntity : event.getAffectedEntities()) {
            if(affectedEntity instanceof Player) {
                UUID id = affectedEntity.getUniqueId();
                BukkitTask task = potionEnd.get(id);
                if(task!=null && !task.isCancelled()) task.cancel();
                task = Bukkit.getScheduler().runTaskLater(Insta_Mine_Deepslate.getInstance(), () -> {
                    potionEnd.remove(id);
                }, potionData.isExtended() ? (8 * 30 * 20) : (3 * 30 * 20));
                potionEnd.put(id,task);
            }
        }
    }
}
