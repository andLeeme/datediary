package com.project.datediary.sticker;

import com.project.datediary.sticker.AbstractFlipEvent;
import com.project.datediary.sticker.StickerView; /**
 * @author wupanjie
 */

public class FlipVerticallyEvent extends AbstractFlipEvent {

  @Override @StickerView.Flip protected int getFlipDirection() {
    return StickerView.FLIP_VERTICALLY;
  }
}