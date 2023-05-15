package com.project.datediary.sticker;

import android.view.MotionEvent;
import com.project.datediary.sticker.StickerIconEvent;
import com.project.datediary.sticker.StickerView;

/**
 * @author wupanjie
 */

public class DeleteIconEvent implements StickerIconEvent {
  @Override public void onActionDown(StickerView stickerView, MotionEvent event) {

  }

  @Override public void onActionMove(StickerView stickerView, MotionEvent event) {

  }

  @Override public void onActionUp(StickerView stickerView, MotionEvent event) {
    stickerView.removeCurrentSticker();
  }
}
