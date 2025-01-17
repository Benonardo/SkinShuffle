/*
 *
 *     Copyright (C) 2023 Calum (mineblock11), enjarai
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 *     USA
 */

package com.mineblock11.skinshuffle.client.gui.widgets;

import com.mineblock11.skinshuffle.SkinShuffle;
import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.widget.AbstractSpruceWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class CarouselMoveButton extends AbstractSpruceWidget {
    private static Identifier ARROW_TEXTURES = new Identifier(SkinShuffle.MOD_ID, "textures/gui/carousel_arrows.png");
    private final boolean isRight;
    private @Nullable Runnable action;

    public CarouselMoveButton(Position position, boolean isRight) {
        super(position);
        this.isRight = isRight;
        this.width = 16;
        this.height = 16;
        if (isRight) {
            position.setRelativeX(position.getRelativeX() - width);
        }
    }

    public void setCallback(@Nullable Runnable action) {
        this.action = action;
    }

    @Override
    protected boolean onMouseClick(double mouseX, double mouseY, int button) {
        if(this.action != null) {
            try {
                this.action.run();
                this.playDownSound();
            } catch (Exception e) {
                throw new RuntimeException("Failed to trigger callback for CarouselMoveButton{x=" + getX() + ", y=" + getY() +"}\n" + e);
            }
        }
        return false;
    }

    @Override
    protected void renderWidget(DrawContext guiGraphics, int mouseX, int mouseY, float delta) {
        var matrices = guiGraphics.getMatrices();
        matrices.push();
        // Translate the matrix forward so its above rendered playermodels
        matrices.translate(0, 0, 10000);
        guiGraphics.drawTexture(ARROW_TEXTURES, getX(), getY(), 16, 16, (isRight ? 8 : 0), (this.active ? (this.hovered || this.focused ? 8 : 0) : 8), 8, 8, 16, 16);
        matrices.pop();
    }

    @Override
    protected @Nullable Text getNarrationMessage() {
        return Text.translatable("skinshuffle.carousel." + (this.isRight ? "right" : "left"));
    }
}
