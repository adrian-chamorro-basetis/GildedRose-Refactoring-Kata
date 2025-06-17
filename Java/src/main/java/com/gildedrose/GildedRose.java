package com.gildedrose;

class GildedRose {
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String AGED_BRIE = "Aged Brie";
    public static final String CONJURED = "Conjured";
    private final Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public Item[] getItems() {
        return items;
    }

    public void updateQuality() {
        for (Item item : items) {
            if (isSulfurasItem(item)) continue;

            updateSellIn(item);

            if (isBackstagePassItem(item)) {
                updateQualityOfBackstagePassItem(item);
            } else if (isAgedBrieItem(item)) {
                updateQualityOfAgedBrieItem(item);
            } else if (isConjuredItem(item)) {
                updateQualityOfConjuredItem(item);
            } else {
                updateQualityOfRegularItem(item);
            }
        }
    }

    private static void updateSellIn(Item item) {
        item.sellIn = item.sellIn - 1;
    }

    private static boolean isSellInPassed(Item item) {
        return item.sellIn < 0;
    }

    private static void updateQualityOfRegularItem(Item item) {
        if (isSellInPassed(item)) decreaseItemQuality(item, 2);
        else decreaseItemQuality(item, 1);
    }

    private static void updateQualityOfAgedBrieItem(Item item) {
        if (isSellInPassed(item)) increaseItemQuality(item, 2);
        else increaseItemQuality(item, 1);
    }

    private static void updateQualityOfConjuredItem(Item item) {
        if (isSellInPassed(item)) decreaseItemQuality(item, 4);
        else decreaseItemQuality(item, 2);
    }

    private static void updateQualityOfBackstagePassItem(Item item) {
        if (isSellInPassed(item)) item.quality = 0;
        else if (item.sellIn < 5) increaseItemQuality(item, 3);
        else if (item.sellIn < 10) increaseItemQuality(item, 2);
        else increaseItemQuality(item, 1);
    }

    private static boolean isSulfurasItem(Item item) {
        return item.name.equals(SULFURAS);
    }

    private static boolean isBackstagePassItem(Item item) {
        return item.name.equals(BACKSTAGE_PASSES);
    }

    private static boolean isAgedBrieItem(Item item) {
        return item.name.equals(AGED_BRIE);
    }

    private static boolean isConjuredItem(Item item) {
        return item.name.startsWith(CONJURED);
    }

    private static void increaseItemQuality(Item item, int increment) {
        if (item.quality < 50) {
            int gap = 50 - item.quality;

            if (gap < increment) {
                item.quality = 50;
            } else {
                item.quality += increment;
            }
        }
    }

    private static void decreaseItemQuality(Item item, int decrement) {
        if (item.quality > 0) {
            int gap = item.quality;

            if (gap < decrement) {
                item.quality = 0;
            } else {
                item.quality -= decrement;
            }
        }
    }
}
