package com.example.animated_card_swiping;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.animated_card_swiping.adapters.ImageViewPagerAdapter;
import com.example.animated_card_swiping.databinding.ActivityMainBinding;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ImageViewPagerAdapter imageViewPagerAdapter;

    private final ViewPager2.OnPageChangeCallback pageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<Integer> imageUrlList = Arrays.asList(
                R.drawable.charizard1,
                R.drawable.charizard_ex,
                R.drawable.charizard_ex_infernal,
                R.drawable.charmaleon,
                R.drawable.charmander
        );

        imageViewPagerAdapter = new ImageViewPagerAdapter(imageUrlList);
        setUpViewPager();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.viewPager.unregisterOnPageChangeCallback(pageChangeCallback);
    }

    private void setUpViewPager() {

        // This allows child views (pages) to draw beyond the ViewPager’s edges
        binding.viewPager.setClipToPadding(false);

        // This allows children to be visible even if they’re outside their parent’s bounds
        binding.viewPager.setClipChildren(false);

        // Loads 3 pages ahead (to the left and right), so animations look smoother
        binding.viewPager.setOffscreenPageLimit(3);

        // Disables the glowing edge effect when scrolling beyond the first/last page
        binding.viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        // Sets the adapter that supplies the images/pages to the ViewPager
        binding.viewPager.setAdapter(imageViewPagerAdapter);

        // Ensures we scroll left/right (horizontal)
        binding.viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        // Start on the second item instead of the first (index is zero-based)
        binding.viewPager.setCurrentItem(1, false);

        // Registers a callback so we can respond when pages are changed
        binding.viewPager.registerOnPageChangeCallback(pageChangeCallback);

        //left side vanish
        binding.viewPager.setPageTransformer((page, position) -> {
            if (position < -1 || position > 1) {
                // This page is way off-screen, keep it invisible
                page.setAlpha(0f);
            } else if (position < 0) {
                // Left-side page fades out as it moves left
                page.setAlpha(1 + position); // goes from 1 to 0 as position goes from 0 to -1
                page.setScaleX(1f);
                page.setScaleY(1f);
                page.setTranslationX(0f);
            } else {
                // Center and right pages get the scale + overlap effect
                float scale = 1 - position * 0.1f;
                float translationX = -position * page.getWidth() * 0.25f;
                float alpha = 0.7f + (1 - position) * 0.3f;

                page.setScaleX(scale);
                page.setScaleY(scale);
                page.setTranslationX(translationX);
                page.setAlpha(alpha);
            }

            // Optional: push right pages behind the center one
            page.setTranslationZ(position == 0 ? 1 : -Math.abs(position));
        });

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    int position = binding.viewPager.getCurrentItem();
                    binding.viewPager.setCurrentItem(position, true); // force gentle re-snap
                }
            }
        });

    }

}