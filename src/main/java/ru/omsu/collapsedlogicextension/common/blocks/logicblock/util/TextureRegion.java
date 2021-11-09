package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util;

import java.util.Objects;

public class TextureRegion {

    public final int x;
    public final int y;

    public TextureRegion(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextureRegion that = (TextureRegion) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
