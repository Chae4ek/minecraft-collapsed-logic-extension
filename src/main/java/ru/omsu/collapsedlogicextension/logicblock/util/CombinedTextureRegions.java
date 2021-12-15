package ru.omsu.collapsedlogicextension.logicblock.util;

import java.util.Collections;
import java.util.List;

/** Текстура, состоящая из нескольких текстур */
public class CombinedTextureRegions {

    private final List<TextureRegion> partsOfTexture;

    public CombinedTextureRegions(final List<TextureRegion> partsOfTexture) {
        this.partsOfTexture = partsOfTexture;
    }

    public CombinedTextureRegions(final int x, final int y) {
        partsOfTexture = Collections.singletonList(new TextureRegion(x, y));
    }

    public List<TextureRegion> getPartsOfTexture() {
        return partsOfTexture;
    }
}
