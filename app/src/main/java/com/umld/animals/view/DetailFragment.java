package com.umld.animals.view;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.palette.graphics.Palette;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.umld.animals.R;
import com.umld.animals.model.AnimalModel;

import com.umld.animals.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailFragment extends Fragment {

    private AnimalModel animal;

    @BindView(R.id.animalImage)
    ImageView animalImage;

    @BindView(R.id.animalName)
    TextView animalName;

    @BindView(R.id.animalLocation)
    TextView animalLocation;

    @BindView(R.id.animalLifespan)
    TextView animalLifespan;

    @BindView(R.id.animalDiet)
    TextView animalDiet;

    @BindView(R.id.animalLinearLayout)
    LinearLayout animalLayout;

    @BindView(R.id.animalSpeed)
    TextView animalSpeed;

    @BindView(R.id.animalTaxonomy)
    TextView animalTaxonomy;


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            animal = DetailFragmentArgs.fromBundle(getArguments()).getAnimalModel();
        }

        if (animal != null) {
            animalName.setText(animal.name);
            animalLocation.setText(animal.location);
            animalLifespan.setText(animal.lifeSpan);
            animalDiet.setText(animal.diet);
//            animalSpeed.setText(animal.speed);

            Util.loadImage(animalImage, animal.imageUrl, Util.getProgressDrawable(animalImage.getContext()));
            setupBackgroundColor(animal.imageUrl);
        }
    }

    private void setupBackgroundColor(String url) {
        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Palette.from(resource)
                                .generate(palette -> {
                                    Palette.Swatch color = palette.getLightMutedSwatch();
                                    if (color != null) {
                                        int intColor = color.getRgb();
                                        animalLayout.setBackgroundColor(intColor);
                                    }
                                });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }
}
