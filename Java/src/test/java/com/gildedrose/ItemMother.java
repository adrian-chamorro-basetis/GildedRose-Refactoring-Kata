package com.gildedrose;

public final class ItemMother {
    private ItemMother() {
        // Prevent instantiation
    }

    public static Item createBackstagePass(int sellIn, int quality) {
        return new Item("Backstage passes to a TAFKAL80ETC concert", sellIn, quality);
    }

    public static Item createSulfuras(int sellIn) {
        return new Item("Sulfuras, Hand of Ragnaros", sellIn, 80);
    }

    public static Item createAgedBrie(int sellIn, int quality) {
        return new Item("Aged Brie", sellIn, quality);
    }

    public static Item createConjured(int sellIn, int quality) {
        return new Item("Conjured Item", sellIn, quality);
    }

    public static Item createWithAnyName(int sellIn, int quality) {
        return new Item("Item", sellIn, quality);
    }

    public static Item[] createMany(Item... items) {
        return items;
    }

    public static Item[] createSingleSulfuras(int sellIn) {
        return createMany(createSulfuras(sellIn));
    }

    public static Item[] createSingleBackstagePass(int sellIn, int quality) {
        return createMany(createBackstagePass(sellIn, quality));
    }

    public static Item[] createSingle(String name, int sellIn, int quality) {
        return createMany(new Item(name, sellIn, quality));
    }
}
