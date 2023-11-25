package com.example.test;

import android.annotation.SuppressLint;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonMenu = findViewById(R.id.buttonMenu);
        LinearLayout linearLayout = findViewById(R.id.List);

        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Проверяем, есть ли уже кнопки в linearLayout
                if (linearLayout.getChildCount() == 0) {
                    int[] imageResources = {R.drawable.baseline_access_time_24, R.drawable.baseline_dashboard_24, R.drawable.baseline_search_24, R.drawable.baseline_settings_24, R.drawable.baseline_home_24};

                    for (int i = 0; i < 5; i++) {
                        ImageButton newButton = createCircularButton(imageResources[i]);
                        setSwipeListener(newButton, linearLayout);
                        // Применяем анимацию появления
                        Animation slideUpAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_up);
                        slideUpAnimation.setStartOffset(i * 100);
                        newButton.startAnimation(slideUpAnimation);
                        linearLayout.addView(newButton);
                    }
                } else {
                    for (int i = 0; i < linearLayout.getChildCount(); i++) {
                        // Применяем анимацию скрытия
                        Animation slideDownAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_down);
                        slideDownAnimation.setStartOffset(i * 100);
                        linearLayout.getChildAt(i).startAnimation(slideDownAnimation);
                    }

                    // Очищаем linearLayout после завершения анимации скрытия
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            linearLayout.removeAllViews();
                            linearLayout.invalidate();
                        }
                    }, 500); // Задержка в миллисекундах, соответствующая длительности анимации
                }
            }
        });
    }


    // Параметры для созданных кнопок 
    private ImageButton createCircularButton(int imageResource) {
        ImageButton newButton = new ImageButton(MainActivity.this);
        int sizeInPixels = (int) getResources().getDimension(R.dimen.circular_button_size);
        newButton.setLayoutParams(new LinearLayout.LayoutParams(sizeInPixels, sizeInPixels));

        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setColor(getResources().getColor(R.color.teal_700));
        newButton.setBackground(shapeDrawable);

        newButton.setImageResource(imageResource);

        int marginBottom = (int) getResources().getDimension(R.dimen.vertical_margin);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(sizeInPixels, sizeInPixels);
        layoutParams.setMargins(0, 0, 0, marginBottom);
        newButton.setLayoutParams(layoutParams);

        return newButton;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setSwipeListener(ImageButton button, final LinearLayout linearLayout) {
        final GestureDetector gestureDetector = new GestureDetector(this, new SwipeGestureListener(button, linearLayout));

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    private class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        private ImageButton button;
        private LinearLayout linearLayout;

        public SwipeGestureListener(ImageButton button, LinearLayout linearLayout) {
            this.button = button;
            this.linearLayout = linearLayout;
        }

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        // Обраточик события свайпа в сторону
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float deltaX = e2.getX() - e1.getX();
            float deltaY = e2.getY() - e1.getY();
            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                if (Math.abs(deltaX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    // Свайп влево или вправо
                    if (deltaX > 0) { // Свайп вправо
                        // Код для обработки свайпа вправо
                        linearLayout.removeView(button);
                    } else { // Свайп влево
                        // Код для обработки свайпа влево
                        linearLayout.removeView(button);
                    }
                    return true;
                }
            }
            return false;
        }
    }

}



