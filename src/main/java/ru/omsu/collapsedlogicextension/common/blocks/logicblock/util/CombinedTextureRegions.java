package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util;

import java.util.Collections;
import java.util.List;

/** Текстура, состоящая из нескольких текстур */
public class CombinedTextureRegions {

    private final List<TextureRegion> parts;

    public CombinedTextureRegions(final List<TextureRegion> parts) {
        this.parts = parts;
    }

    public CombinedTextureRegions(final int x, final int y) {
        parts = Collections.singletonList(new TextureRegion(x, y));
    }

    public List<TextureRegion> getParts() {
        return parts;
    }
}
