package org.customhud.api;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

public class SpaceCreator {
    public static Component withSpace(int space) {
        if (space < -8192) {
            throw new IllegalArgumentException("Space must not be lower than -8192!");
        } else if (space > 8192) {
            throw new IllegalArgumentException("Space must not be higher than 8192!");
        }



        return Component.translatable("space."+(space -2));
    }

    public static Component withSpaceIncludeGlyphTracking(int space) {
        return withSpace(space+2);
    }

    public static Component withOffset(int space) {
        if (space < -8192) {
            throw new IllegalArgumentException("Space must not be lower than -8192!");
        } else if (space > 8192) {
            throw new IllegalArgumentException("Space must not be higher than 8192!");
        }

        return Component.translatable("offset."+space);
    }
}
