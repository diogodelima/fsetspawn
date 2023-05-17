package pt.dioguin.fsetspawn.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

public class ItemBuilder {

    private final ItemStack item;

    public ItemBuilder(ItemStack item) {
        this.item = item;
    }

    public ItemBuilder(Material type){
        this(new ItemStack(type));
    }

    public ItemBuilder(Material type , int amount){
        this(new ItemStack(type , amount));
    }

    public ItemBuilder(Material type , int amount, short data){
        this(new ItemStack(type , amount, data));
    }

    public ItemBuilder changeSkull(Consumer<SkullMeta> consumer){
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        consumer.accept(meta);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder changeItem(Consumer<ItemStack> consumer){
        consumer.accept(item);
        return this;
    }

    public ItemBuilder setSkull(String url){

        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "Notch");
        byte[] arrayOfByte = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        gameProfile.getProperties().put("textures", new Property("textures", new String(arrayOfByte)));
        Field field;
        try {
            field = skullMeta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(skullMeta, gameProfile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | SecurityException noSuchFieldException) {
            noSuchFieldException.printStackTrace();
        }
        item.setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder changeItemMeta(Consumer<ItemMeta> consumer){
        ItemMeta itemMeta = item.getItemMeta();
        consumer.accept(itemMeta);
        item.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder glow(boolean glow){

        if (!glow) return this;

        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level){
        return changeItemMeta(meta -> meta.addEnchant(enchantment, level, true));
    }

    public ItemBuilder unbreakable(){
        return changeItemMeta(meta -> meta.spigot().setUnbreakable(true));
    }

    public ItemBuilder name(String name){
        return changeItemMeta(it -> it.setDisplayName(name.replace("&", "§")));
    }

    public ItemBuilder setLore(String... lore){
        return changeItemMeta(it -> it.setLore(Arrays.asList(lore)));
    }

    public ItemBuilder setLore(List<String> lore){
        lore.replaceAll(s -> s.replace("&", "§"));
        return changeItemMeta(it -> it.setLore(lore));
    }

    public ItemBuilder addLore(String... lore){
        return changeItemMeta(meta -> {

            List<String> loreString = new ArrayList<>();
            if (meta.hasLore())
                loreString = meta.getLore();

            loreString.addAll(Arrays.asList(lore));
            meta.setLore(loreString);
        });
    }

    public ItemBuilder addLore(List<String> lore){
        return changeItemMeta(meta -> {

            List<String> loreString = new ArrayList<>();
            if (meta.hasLore())
                loreString = meta.getLore();

            loreString.addAll(lore);
            meta.setLore(loreString);
        });
    }

    public ItemStack build(){
        return item;
    }

}
