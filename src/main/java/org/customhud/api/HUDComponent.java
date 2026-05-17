package org.customhud.api;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.ShadowColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.util.ARGBLike;
import net.kyori.adventure.util.RGBLike;
import org.jetbrains.annotations.Range;

import java.util.Objects;
import java.util.stream.Stream;

public class HUDComponent {
    private String namespace = "minecraft";
    //fontName means the .json file inside font
    private String fontName = "default";
    private String uniqueCharacter;

    public String getFontName() {
        return fontName;
    }
    public String getNamespace() {
        return namespace;
    }
    public String getUniqueCharacter() {
        return uniqueCharacter;
    }
    public void setFontName(String fontName) {
        this.fontName = fontName;
    }
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
    public void setUniqueCharacter(String uniqueCharacter) {
        this.uniqueCharacter = uniqueCharacter;
    }
    public Component render() {
        Component image = Component.text(uniqueCharacter);
        if (!Objects.equals(getNamespace(), "minecraft") || !Objects.equals(getFontName(), "default")) {
            image = image.font(Key.key(getNamespace(),getFontName()));
        }
        image = image.shadowColor(ShadowColor.shadowColor(0,0,0,0));

        return image;
    }
    public Component renderAndJoin(Component... joined) {
        Iterable<Component> sequence = Stream.concat(Stream.of(render()), Stream.of(joined))::iterator;

        return Component.join(JoinConfiguration.noSeparators(), sequence);
    }

    public HUDComponent(String uniqueCharacter) {
        this.uniqueCharacter = uniqueCharacter;
    }

    public HUDComponent(String uniqueCharacter, String namespace) {
        this.uniqueCharacter = uniqueCharacter;
        this.namespace = namespace;
    }

    public HUDComponent(String uniqueCharacter, String namespace, String fontName) {
        this.uniqueCharacter = uniqueCharacter;
        this.namespace = namespace;
        this.fontName = fontName;
    }
}
