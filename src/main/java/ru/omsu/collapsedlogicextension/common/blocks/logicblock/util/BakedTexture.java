package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util;

import java.util.HashSet;
import java.util.Set;

/**Готовая текстура, состоящая из основы и дополнений*/
public class BakedTexture {

    private final TextureRegion base;
    private final Set<TextureRegion> parts;

    public BakedTexture(TextureRegion base, Set<TextureRegion> parts){
        this.base = base;
        this.parts = parts;
    }
    public BakedTexture(int x, int y){
        this.base = new TextureRegion(x, y);
        parts = new HashSet<>();
    }

    public TextureRegion getBase() {
        return base;
    }

    public Set<TextureRegion> getParts() {
        return parts;
    }
}
