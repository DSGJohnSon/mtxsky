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

/**
 * Modélise les différents métiers
 */
public enum EJob {

    CHOMEUR("Chomeur", 0, null),
    MINEUR("Mineur", 50, new ItemStack(Material.STONE_PICKAXE));

    private final String name;
    private final double price;
    private final ItemStack icon;

    /**
     * Constructeur
     * @param jobName String nom
     * @param jobPrice double prix
     * @param jobIcon ItemStack icon
     */
    EJob(final String jobName, final double jobPrice, final ItemStack jobIcon){
        name = jobName;
        price = jobPrice;
        icon = jobIcon;
    }

    /**
     * Retourne le nom du métier
     * @return String nom
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne le prix du métier
     * @return double prix
     */
    public double getPrice() {
        return price;
    }

    /**
     * Retourne l'icon du métier
     * @return ItemStack icon
     */
    public ItemStack getIcon() {
        return icon;
    }

    /**
     * Retourne les détails de l'item du métier
     * @return ItemStack item
     */
    public ItemStack getItem(Double jobPrice){
        ItemStack i = new ItemStack(icon);
        ItemMeta m = i.getItemMeta();
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Prix : " + jobPrice));

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
