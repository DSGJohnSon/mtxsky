package fr.skyblock.jobs;

import fr.skyblock.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public enum JobInfo {

    CHOMEUR("Chomeur", 0, null),
    MINEUR("Mineur", 50, new ItemStack(Material.STONE_PICKAXE)),
    BUCHERON("Bucheron", 50, new ItemStack(Material.STONE_AXE));

    private final String name;
    private final double price;
    private final ItemStack icon;

    JobInfo(String jobName, double jobPrice, ItemStack jobIcon){
        name = jobName;
        price = jobPrice;
        icon = jobIcon;
    }

    public String getName() {
        return name;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public double getPrice() {
        return price;
    }

    public ItemStack getItem(final double newPrice){
        ItemStack i = new ItemStack(icon);
        ItemMeta m = i.getItemMeta();
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Prix : " + newPrice));

        m.customName(Component.text("§7" + name));
        m.lore(lore);
        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        m.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);

        m.addAttributeModifier(Attribute.LUCK, new AttributeModifier(
                new NamespacedKey(Main.getInstance(), "dummy"),
                0,
                AttributeModifier.Operation.ADD_NUMBER
        ));

        i.setItemMeta(m);
        return i;
    }
}
