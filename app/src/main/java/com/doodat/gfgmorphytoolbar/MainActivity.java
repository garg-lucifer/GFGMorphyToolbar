package com.doodat.gfgmorphytoolbar;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.github.badoualy.morphytoolbar.MorphyToolbar;

public class MainActivity extends AppCompatActivity {

    MorphyToolbar morphyToolbar;
    int primary2;
    int primaryDark2;

    AppBarLayout appBarLayout;
    Toolbar toolbar;
    FloatingActionButton fabPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        primary2 = getResources().getColor(R.color.primary2); // determines the color of morphyToolbar when expanded
        primaryDark2 = getResources().getColor(R.color.primary_dark2); // determines the color of status bar when expanded

        appBarLayout = findViewById(R.id.layout_app_bar);
        toolbar = findViewById(R.id.toolbar);
        fabPhoto = findViewById(R.id.fab_photo);

        disableAppBarDrag(); // disables the scrolling-of AppBarLayout in CoordinatorLayout i.e prevents the user from hiding the ToolBar when swiped above
        hideFab(); // hides the floating action button

        // Attaching MorphyToolbar to the given activity/toolbar

        morphyToolbar = MorphyToolbar.builder(this, toolbar)
                .withToolbarAsSupportActionBar()
                .withTitle("GeeksForGeeks DS and Algorithms Course") // Title of Toolbar
                .withSubtitle("16,000 Participants") //  Subtitle of Toolbar
                .withPicture(R.drawable.gfgicon) // Add any img to the toolbar
                .withHidePictureWhenCollapsed(false) // if you want to hide the img when AppBarLayout collapses, set it to true
                .build();

        morphyToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if morphyToolbar is collapsed, expand it and if expanded, collapse it.
                if (morphyToolbar.isCollapsed()) {
                    morphyToolbar.expand(primary2, primaryDark2, new MorphyToolbar.OnMorphyToolbarExpandedListener() {
                        @Override
                        public void onMorphyToolbarExpanded() {
                            showFab(); // shows the floating action button when morphyToolbar expands
                        }
                    });
                } else {
                    hideFab();
                    morphyToolbar.collapse();
                }
            }
        });

        // adding a back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP
                    | ActionBar.DISPLAY_SHOW_TITLE
                    | ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void disableAppBarDrag() {
      //  disables the scrolling-of AppBarLayout in CoordinatorLayout
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        params.setBehavior(behavior);
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                return false;
            }
        });
    }

    // These two methods hideFab() & showFab() are for hiding and showing the floating action button,
    // which is to be used only if we are adding a floating action button.

    private void hideFab() {
        fabPhoto.show();
        fabPhoto.hide();
        final CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) fabPhoto.getLayoutParams();
        layoutParams.setAnchorId(View.NO_ID);
        fabPhoto.requestLayout();
        fabPhoto.hide();
    }

    private void showFab() {
        final CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) fabPhoto.getLayoutParams();
        layoutParams.setAnchorId(R.id.layout_app_bar);
        layoutParams.anchorGravity = Gravity.RIGHT | Gravity.END | Gravity.BOTTOM;
        fabPhoto.requestLayout();
        fabPhoto.show();
    }

    @Override
      public void onBackPressed() {
        // if morphyToolbar is already collapsed finish the activity
        // else collapse the toolbar

        if (!morphyToolbar.isCollapsed()) {
            hideFab();
            morphyToolbar.collapse();
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // It is called, when a user presses back button
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}