package com.app.frame.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.MotionEvent;
import android.view.View;

/**
 * 缩放View
 */

public class ScaleUtil {

    private static final int ANIM_TIME = 100;

    public static void setScaleListener(View view, final View.OnClickListener l) {
        View.OnTouchListener touchListener = new TouchListener(l);
        view.setOnTouchListener(touchListener);
    }

    private static class TouchListener implements View.OnTouchListener {

        private View.OnClickListener l;

        public TouchListener(View.OnClickListener l) {
            this.l = l;
        }

        @Override
        public boolean onTouch(final View v, MotionEvent event) {
            Animator.AnimatorListener al = new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (l != null) {
                        l.onClick(v);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            };


            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    zoomOut(v);
                    break;
                case MotionEvent.ACTION_UP:
                    zoomIn(v, al);
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_CANCEL:
                    zoomIn(v, null);
                    break;
            }
            return true;
        }

        private void zoomOut(View view) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.SCALE_X, 1, 1.2f);
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1, 1.2f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(ANIM_TIME);
            animatorSet.playTogether(animator, animator2);
            animatorSet.start();
        }

        private void zoomIn(View view, Animator.AnimatorListener listener) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.SCALE_X, 1.2f, 1);
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1.2f, 1);
            final AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(ANIM_TIME);
            animatorSet.playTogether(animator, animator2);
            if (null != listener) {
                animatorSet.addListener(listener);
            }
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    animatorSet.removeAllListeners();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animatorSet.start();
        }
    }

}
