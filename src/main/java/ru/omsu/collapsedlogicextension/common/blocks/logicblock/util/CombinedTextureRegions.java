package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util;

import java.util.Collections;
import java.util.Set;

/** Текстура, состоящая из нескольких текстур */
public class CombinedTextureRegions {

    private final Set<TextureRegion> parts;

    public CombinedTextureRegions(final Set<TextureRegion> parts) {
        this.parts = parts;
    }

    public CombinedTextureRegions(final int x, final int y) {
        parts = Collections.singleton(new TextureRegion(x, y));
    }

    public Set<TextureRegion> getParts() {
        return parts;
    }
}
