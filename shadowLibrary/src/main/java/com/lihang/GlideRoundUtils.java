package com.lihang;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * Created by leo
 * on 2020/8/3.
 */
class GlideRoundUtils {
    public static void setRoundCorner(final View view, final Drawable resourceId, final float cornerDipValue, final String currentTag) {

        if (cornerDipValue == 0) {
            view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    view.removeOnLayoutChangeListener(this);
                    Glide.with(view)
                            .asDrawable()
                            .load(resourceId)
                            .transform(new CenterCrop())
                            .override(view.getMeasuredWidth(), view.getMeasuredHeight())
                            .into(new CustomTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                                    String lastTag = (String) view.getTag(R.id.action_container);
                                    if (lastTag.equals(currentTag)) {
                                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                                            view.setBackgroundDrawable(resource);
                                        } else {
                                            view.setBackground(resource);
                                        }
                                    }
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {
                                }
                            });
                }
            });


            if (view.getMeasuredWidth() != 0 || view.getMeasuredHeight() != 0) {
                Glide.with(view)
                        .asDrawable()
                        .load(resourceId)
                        .transform(new CenterCrop())
                        .override(view.getMeasuredWidth(), view.getMeasuredHeight())
                        .into(new CustomTarget<Drawable>() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                                    view.setBackgroundDrawable(resource);
                                } else {
                                    view.setBackground(resource);
                                }
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }
                        });
            }

        } else {

            view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    view.removeOnLayoutChangeListener(this);
                    Glide.with(view)
                            .load(resourceId)
                            .transform(new CenterCrop(), new RoundedCorners((int) cornerDipValue))
                            .override(view.getMeasuredWidth(), view.getMeasuredHeight())
                            .into(new CustomTarget<Drawable>() {
                                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    String lastTag = (String) view.getTag(R.id.action_container);
                                    if (lastTag.equals(currentTag)) {
                                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                                            view.setBackgroundDrawable(resource);
                                        } else {
                                            view.setBackground(resource);
                                        }
                                    }
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {

                                }
                            });
                }
            });


            if (view.getMeasuredWidth() != 0 || view.getMeasuredHeight() != 0) {
                Glide.with(view)
                        .load(resourceId)
                        .transform(new CenterCrop(), new RoundedCorners((int) cornerDipValue))
                        .override(view.getMeasuredWidth(), view.getMeasuredHeight())
                        .into(new CustomTarget<Drawable>() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                                    view.setBackgroundDrawable(resource);
                                } else {
                                    view.setBackground(resource);
                                }
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });
            }

        }

    }


    public static void setCorners(final View view, final Drawable resourceId, final float leftTop_corner, final float leftBottom_corner, final float rightTop_corner, final float rightBottom_corner, final String currentTag) {
        if (leftTop_corner == 0 && leftBottom_corner == 0 && rightTop_corner == 0 && rightBottom_corner == 0) {

            view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    view.removeOnLayoutChangeListener(this);
                    Glide.with(view)
                            .load(resourceId)
                            .override(view.getMeasuredWidth(), view.getMeasuredHeight())
                            .into(new CustomTarget<Drawable>() {
                                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    String lastTag = (String) view.getTag(R.id.action_container);
                                    if (lastTag.equals(currentTag)) {
                                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                                            view.setBackgroundDrawable(resource);
                                        } else {
                                            view.setBackground(resource);
                                        }
                                    }
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {

                                }
                            });
                }
            });


            if (view.getMeasuredWidth() != 0 || view.getMeasuredHeight() != 0) {
                Glide.with(view)
                        .load(resourceId)
                        .override(view.getMeasuredWidth(), view.getMeasuredHeight())
                        .into(new CustomTarget<Drawable>() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                                    view.setBackgroundDrawable(resource);
                                } else {
                                    view.setBackground(resource);
                                }
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });

            }

        } else {

            /**
             * 注意：
             * 有特殊角，长宽不为0的状况(实际上也存在长宽不为0但还未渲染到画面上)
             * */
            final GlideRoundTransform transform = new GlideRoundTransform(view.getContext(), leftTop_corner, leftBottom_corner, rightTop_corner, rightBottom_corner);
            view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    view.removeOnLayoutChangeListener(this);
//                        GlideRoundTransform transform = new GlideRoundTransform(view.getContext(), leftTop_corner, leftBottom_corner, rightTop_corner, rightBottom_corner);
                    Glide.with(view)
                            .load(resourceId)
                            .transform(transform)
                            .override(view.getMeasuredWidth(), view.getMeasuredHeight())
                            .into(new CustomTarget<Drawable>() {
                                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    String lastTag = (String) view.getTag(R.id.action_container);
                                    if (lastTag.equals(currentTag)) {
                                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                                            view.setBackgroundDrawable(resource);
                                        } else {
                                            view.setBackground(resource);
                                        }
                                    }

                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {

                                }
                            });
                }
            });


            if (view.getMeasuredWidth() != 0 || view.getMeasuredHeight() != 0) {
                Glide.with(view)
                        .load(resourceId)
                        .transform(transform)
                        .override(view.getMeasuredWidth(), view.getMeasuredHeight())
                        .into(new CustomTarget<Drawable>() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                String lastTag = (String) view.getTag(R.id.action_container);
                                if (lastTag.equals(currentTag)) {
                                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                                        view.setBackgroundDrawable(resource);
                                    } else {
                                        view.setBackground(resource);
                                    }
                                }
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });
            }
        }

    }

}
