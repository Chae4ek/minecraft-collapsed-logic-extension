package ru.omsu.collapsedlogicextension.blocks.logicblock.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

public class ExampleItemHandler extends ItemStackHandler {

    public ExampleItemHandler(final int size, final ItemStack... stacks) {
        super(size);

        for (int index = 0; index < stacks.length; index++) {
            this.stacks.set(index, stacks[index]);
        }
    }

    public void clear() {
        for (int index = 0; index < getSlots(); index++) {
            stacks.set(index, ItemStack.EMPTY);
            onContentsChanged(index);
        }
    }

    public boolean isEmpty() {
        for (final ItemStack stack : stacks) {
            if (stack.isEmpty() || stack.getItem() == Items.AIR) {
                return true;
            }
        }
        return false;
    }

    public ItemStack decrStackSize(final int index, final int count) {
        final ItemStack stack = getStackInSlot(index);
        stack.shrink(count);
        onContentsChanged(index);
        return stack;
    }

    public void removeStackFromSlot(final int index) {
        stacks.set(index, ItemStack.EMPTY);
        onContentsChanged(index);
    }

    public NonNullList<ItemStack> toNonNullList() {
        final NonNullList<ItemStack> items = NonNullList.create();
        for (final ItemStack stack : stacks) {
            items.add(stack);
        }
        return items;
    }

    public void setNonNullList(final NonNullList<ItemStack> items) {
        if (items.size() == 0) {
            return;
        }
        if (items.size() != getSlots()) {
            throw new IndexOutOfBoundsException(
                    "NonNullList must be same size as ItemStackHandler!");
        }
        for (int index = 0; index < items.size(); index++) {
            stacks.set(index, items.get(index));
        }
    }

    @Override
    public String toString() {
        return stacks.toString();
    }
}
